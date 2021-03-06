package org.nuaa.tomax.easyblog.service.impl;

        import org.nuaa.tomax.easyblog.constant.ConstResourceType;
        import org.nuaa.tomax.easyblog.entity.FolderEntity;
        import org.nuaa.tomax.easyblog.entity.ImageEntity;
        import org.nuaa.tomax.easyblog.entity.ResourceEntity;
        import org.nuaa.tomax.easyblog.entity.Response;
        import org.nuaa.tomax.easyblog.repository.IBlogRepository;
        import org.nuaa.tomax.easyblog.repository.IFolderRepository;
        import org.nuaa.tomax.easyblog.repository.IImageRepository;
        import org.nuaa.tomax.easyblog.repository.IResourceRepository;
        import org.nuaa.tomax.easyblog.service.IResourceService;
        import org.nuaa.tomax.easyblog.util.Base64Util;
        import org.nuaa.tomax.easyblog.util.FileUtil;
        import org.nuaa.tomax.easyblog.util.Md5Util;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.core.env.Environment;
        import org.springframework.core.io.FileUrlResource;
        import org.springframework.core.io.Resource;
        import org.springframework.core.io.UrlResource;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.interceptor.TransactionAspectSupport;
        import org.springframework.util.StringUtils;
        import org.springframework.web.multipart.MultipartFile;

        import javax.transaction.Transactional;
        import java.io.File;
        import java.io.IOException;
        import java.io.UnsupportedEncodingException;
        import java.net.MalformedURLException;
        import java.security.NoSuchAlgorithmException;
        import java.util.ArrayList;
        import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/30 19:17
 */
@Service
public class ResourceServiceImpl implements IResourceService{
    private final IFolderRepository folderRepository;
    private final IResourceRepository resourceRepository;
    private final IImageRepository imageRepository;
    private final IBlogRepository blogRepository;

    private final Environment environment;

    /**
     * source path root list, index is file type
     */
    private final String[] sourceRootList;

    private final String galleryPath;

    /**
     * image gallery visit url
     */
    private static final String VISIT_URL_PATH = "/api/image/gallery/particular/";
    /**
     * file default logo url
     */
    private static final String FILE_DEFAULT_LOGO_URL = "/images/file-logo.png";

    @Autowired
    public ResourceServiceImpl(IFolderRepository folderRepository, IResourceRepository resourceRepository, IImageRepository imageRepository, Environment environment, IBlogRepository blogRepository) {
        this.folderRepository = folderRepository;
        this.resourceRepository = resourceRepository;
        this.imageRepository = imageRepository;
        this.environment = environment;

        sourceRootList = new String[4];
        sourceRootList[ConstResourceType.BLOG_FOLDER_TYPE] = environment.getProperty("source.blog.path", "source/blog");
        sourceRootList[ConstResourceType.IMAGE_FOLDER_TYPE] = environment.getProperty("source.image.path", "source/image");
        sourceRootList[ConstResourceType.FILE_FOLDER_TYPE] = environment.getProperty("source.file.path", "source/file");
        galleryPath = environment.getProperty("source.gallery.path", "source/gallery");
        this.blogRepository = blogRepository;
    }

    @Override
    public Response createNewFolder(String name, Long parentId, int type) {
        // check file
        if (isFileExists(parentId, name, type)) {
            return new Response(Response.SERVER_DATA_DUPLICATION, "file exists in this directory");
        }

        // get parent data
        FolderEntity parent = folderRepository.findById(parentId).orElseGet(() -> null);

        // folder type conflicts with parent folder type
        if (parent != null && parent.getType() != type) {
            return new Response(Response.INPUT_ERROR_CODE, "folder type is not valid(conflicts with parent directory type)");
        }
        // get path
        String path = (parent != null ? parent.getPath() : "") + "/" + name;

        // TODO : check type range

        // create file in system
        boolean result = FileUtil.createFolder(sourceRootList[type] + "/" + path);
        if (!result) {
            return new Response(Response.SERVER_FILE_SYSTEM_ERROR, "server file system error");
        }

        // construct folder entity object
        FolderEntity folder = new FolderEntity(name, parentId, 1L, path, type);

        // save folder data to database
        folderRepository.save(folder);
        return new Response(Response.SUCCESS_CODE, "create new folder success");
    }

    @Override
    public Response deleteFolder(Long folderId) throws IOException {
        // check file exist
        if (!folderRepository.findById(folderId).isPresent()) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "folder not exist");
        }

        // TODO : the logic of folder delete is not explicit
        folderRepository.deleteById(folderId);
        return null;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Response updateFolderName(Long folderId, String name) {
        // get origin folder data
        FolderEntity folder = folderRepository.findById(folderId).orElseGet(() -> null);
        if (folder == null) {
            return new Response(
                    Response.SERVER_DATA_NOT_FOUND_ERROR,
                    "folder is not exists"
            );
        }

        // get parent
        FolderEntity parent = folderRepository.findById(folder.getFather()).orElseGet(() -> null);

        // create path
        String path = sourceRootList[folder.getType()] + "/" + (parent != null ? parent.getPath() : "");

        // judge folder is exists or not
        boolean updateFileResult = isFileExists(folder.getFather(), name, folder.getType()) ||
                !FileUtil.renameFile(path, folder.getName(), name);
        if (updateFileResult) {
            return new Response(
                    Response.SERVER_DATA_DUPLICATION,
                    "new folder is exists"
            );
        }

        // update folder data to database
        try {
            String currentPath = (parent != null ? parent.getPath() : "") + "/" + name;
            folderRepository.updateFolderName(name, folder.getId());
            // update folders path include children
            folderRepository.updateChildrenFolderPath(currentPath, folder.getPath(), folder.getPath() + "%", folder.getType());
            // update resource path
            if (folder.getType() == ConstResourceType.BLOG_FOLDER_TYPE) {
                blogRepository.updateChildrenBlogPath(currentPath, folder.getPath(), folder.getPath() + "%", folder.getType());
            } else if (folder.getType() == ConstResourceType.IMAGE_FOLDER_TYPE) {
                imageRepository.updateChildrenImagePath(currentPath, folder.getPath(), folder.getPath() + "%", folder.getType());
            } else if (folder.getType() == ConstResourceType.FILE_FOLDER_TYPE) {
                resourceRepository.updateChildrenResourcePath(currentPath, folder.getPath(), folder.getPath() + "%", folder.getType());
            }
        } catch (Exception e) {
            // rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // file rename rollback
            FileUtil.renameFile(path, name, folder.getName());
            return new Response(Response.NORMAL_EROOR_CODE, "data update to database fail");
        }

        // return update result
        return new Response(Response.SUCCESS_CODE, "update folder name success");
    }

    @Override
    public Response listFolders(int type, int beg, int end) {
        return type == ConstResourceType.BLOG_UNKNOWN_TYPE ?
                new Response<FolderEntity>(
                        Response.SUCCESS_CODE,
                        "get all folders success",
                        folderRepository.findFolderEntitiesLimit(end, (beg - 1) * end),
                        folderRepository.count()
                ):
                new Response<FolderEntity>(
                        Response.SUCCESS_CODE,
                        "get all folders success",
                        folderRepository.findFolderEntitiesByTypeLimit(type, end, (beg - 1) * end),
                        folderRepository.countFolderEntitiesByType(type)
                );
    }

    @Override
    public Response listFolderByFather(Long folderId, int type) {
        return new Response<FolderEntity>(
                Response.SUCCESS_CODE,
                "get folder by father success",
                folderRepository.findFolderEntitiesByFatherAndType(folderId, type)
        );
    }

    @Override
    public Response getFolderById(Long folderId) {
        return new Response<FolderEntity>(
                Response.SUCCESS_CODE,
                "get folder data success",
                folderRepository.findById(folderId).orElseGet(() -> null)
        );
    }

    @Override
    public Response saveImage(MultipartFile file, Long parentId) throws IOException, NoSuchAlgorithmException {
        // TODO : think of file name may be same

        // get parent path
        FolderEntity folder = folderRepository.findById(parentId).orElseGet(() -> null);
        String path = (folder != null ? folder.getPath() : "") + "/";

        // suffix
        String[] imageName = file.getOriginalFilename().split("\\.");

        // save image
        boolean result = FileUtil.saveFile(file, sourceRootList[ConstResourceType.IMAGE_FOLDER_TYPE] + '/' + path,
                file.getOriginalFilename());
        if (!result) {
            return new Response(Response.SERVER_FILE_SYSTEM_ERROR, "save image fail");
        }

        // TODO : image size compress
        String id = Md5Util.encode(String.valueOf(System.nanoTime()));
        String visitImgPath = galleryPath + "/" + id + "." + imageName[1];
        FileUtil.copyFile(
                sourceRootList[ConstResourceType.IMAGE_FOLDER_TYPE] + "/" + path + file.getOriginalFilename(),
                visitImgPath
        );

        // save data to database
        ImageEntity image = new ImageEntity(path + "/" + file.getOriginalFilename(),
                imageName[0], parentId, 1L, imageName[1],
                VISIT_URL_PATH + id + "." + imageName[1], file.getSize());

        return new Response<ImageEntity>(
                Response.SUCCESS_CODE,
                "save image success",
                imageRepository.save(image)
        );
    }

    @Override
    public Response getImageListByFolderId(Long parentId) {
        return new Response<ImageEntity>(
                Response.SUCCESS_CODE,
                "get image data success",
                imageRepository.findImageEntitiesByFolder(parentId)
        );
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Response deleteImage(Long id) throws IOException {
        ImageEntity image = imageRepository.findById(id).orElseGet(() -> null);
        if (image == null) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "image not exists");
        }

        // delete data
        imageRepository.deleteById(id);

        // delete image file
        if (!FileUtil.deleteFile(new File(sourceRootList[ConstResourceType.IMAGE_FOLDER_TYPE] + image.getPath()))
                || !FileUtil.deleteFile(new File(
                galleryPath + "/" + image.getUrl().replaceAll(VISIT_URL_PATH, "")))) {
            throw new IOException("server file process error");
        }

        return new Response(Response.SUCCESS_CODE, "image delete success");
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Response updateImageName(Long id, String name) {
        ImageEntity image = imageRepository.findById(id).orElseGet(() -> null);
        if (image == null) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "image not exists");
        }

        // get image suffix, assume the path is valid
        String path = image.getPath().replace(image.getName() + "." + image.getSuffix(),
                "");
        // get prefix path
        boolean result = FileUtil.renameFile(
                sourceRootList[ConstResourceType.IMAGE_FOLDER_TYPE] + path, image.getName() + "." + image.getSuffix(),
                name + "." + image.getSuffix());

        if (!result) {
            return new Response(Response.SERVER_FILE_SYSTEM_ERROR, "rename file fail");
        }

        // update data to database
        imageRepository.updateImagePathAndNameById(path + name + "." + image.getSuffix(),
                name, image.getId());

        return new Response(Response.SUCCESS_CODE, "update image name success");
    }

    @Override
    public Response getImageById(Long imageId) {
        return new Response<ImageEntity>(
                Response.SUCCESS_CODE,
                "get image data success",
                imageRepository.findById(imageId).orElseGet(() -> null)
        );
    }

    @Override
    public Response deleteImgList(List<Long> imageIdList) throws IOException {
        int count = 0;
        List<Long> errorIdList = new ArrayList<>();
        for (Long id : imageIdList) {
            Response response = deleteImage(id);
            if (response.getCode() == Response.SUCCESS_CODE) {
                count++;
            } else {
                errorIdList.add(id);
            }
        }

        return new Response<Long>(
                Response.SUCCESS_CODE,
                count + "条成功," + errorIdList.size() + "条失败",
                errorIdList
        );
    }

    @Override
    public Response saveFileResource(MultipartFile file, Long parentId, String brief) throws IOException {
        // TODO : think of file name may be same

        // get parent path
        FolderEntity folder = folderRepository.findById(parentId).orElseGet(() -> null);
        String path = (folder != null ? folder.getPath() : "") + "/";

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
//
        // save file
        boolean result = FileUtil.saveFile(file, sourceRootList[ConstResourceType.FILE_FOLDER_TYPE] + '/' + path,
                filename);
        if (!result) {
            return new Response(Response.SERVER_FILE_SYSTEM_ERROR, "save file fail");
        }
//
        ResourceEntity resource = new ResourceEntity(path + "/" + filename,
                filename, brief, FILE_DEFAULT_LOGO_URL, parentId, file.getSize());
        // save data to database
        resourceRepository.save(resource);

        return new Response(Response.SUCCESS_CODE, "save file success");
    }

    @Override
    public Response getFileResourceListByFolderId(Long id) {
        return new Response<ResourceEntity>(
                Response.SUCCESS_CODE,
                "get file list success",
                resourceRepository.findResourceEntitiesByFolder(id)
        );
    }
    @Transactional(rollbackOn = Exception.class)
    @Override
    public Response deleteFileResource(Long id) throws IOException {
        ResourceEntity resource = resourceRepository.findById(id).orElseGet(() -> null);
        if (resource == null) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "resource not exists");
        }

        // delete data
        resourceRepository.deleteById(id);

        // delete image file
        if (!FileUtil.deleteFile(new File(
                sourceRootList[ConstResourceType.FILE_FOLDER_TYPE] + resource.getPath()))) {
            throw new IOException("server file process error");
        }

        return new Response(Response.SUCCESS_CODE, "resource delete success");
    }

    @Override
    public Response deleteFileResourceList(List<Long> idList) throws IOException {
        int count = 0;
        List<Long> errorIdList = new ArrayList<>();
        for (Long id : idList) {
            Response response = deleteFileResource(id);
            if (response.getCode() == Response.SUCCESS_CODE) {
                count++;
            } else {
                errorIdList.add(id);
            }
        }

        return new Response<Long>(
                Response.SUCCESS_CODE,
                count + "条成功," + errorIdList.size() + "条失败",
                errorIdList
        );
    }

    @Override
    public Resource downloadFileResource(Long id) {
        ResourceEntity resourceEntity = resourceRepository.findById(id).orElseGet(() -> null);
        if (resourceEntity == null) {
            return null;
        }
        try {
            Resource resource = new FileUrlResource(sourceRootList[ConstResourceType.FILE_FOLDER_TYPE] + resourceEntity.getPath());
            if (resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            // TODO : log
            return null;
        }
        return null;
    }

    @Override
    public Response getFileById(Long id) {
        return new Response<ResourceEntity>(
                Response.SUCCESS_CODE,
                "get data success",
                resourceRepository.findById(id).orElseGet(() -> null)
        );
    }
    @Transactional(rollbackOn = Exception.class)
    @Override
    public Response updateFileInfo(Long id, MultipartFile file, String name, String brief) throws IOException, NoSuchAlgorithmException {
        ResourceEntity resource = resourceRepository.findById(id).orElseGet(() -> null);
        if (resource == null) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "resource not exists");
        }

        String logo = resource.getLogo();
        if (file != null) {
            // TODO : save logo in specific folder
            // save target logo
            logo = ((ImageEntity)(saveImage(file, 0L).getData())).getUrl();
            // TODO : delete origin logo
        }

        resourceRepository.updateResourceInfo(name, brief, logo, id);
        return new Response(
                Response.SUCCESS_CODE,
                "update resource info success"
        );
    }

    @Override
    public Response getAllFileResourceApi() {
        return new Response<ResourceEntity>(
                Response.SUCCESS_CODE,
                "get file data success",
                resourceRepository.findAll()
        );
    }

    @Override
    public Response getFileByIdApi(Long id) {
        return new Response<ResourceEntity>(
                Response.SUCCESS_CODE,
                "get file data success",
                resourceRepository.findById(id).orElseGet(() -> null)
        );
    }

    /**
     * check file name in one folder
     * @param parentId father folder id
     * @param name father folder name
     * @param type root path id is same 0, name may be same if type is different
     * @return exist status
     */
    private boolean isFileExists(Long parentId, String name, int type) {
        return folderRepository.findFolderEntityByFatherAndNameAndType(parentId, name, type).isPresent();
    }
}
