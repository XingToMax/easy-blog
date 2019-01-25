package org.nuaa.tomax.easyblog.controller.admin;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/30 22:11
 */
@Controller
@CrossOrigin
@RequestMapping("/admin/resource")
public class ResourceController {

    private final IResourceService resourceService;

    @Autowired
    public ResourceController(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    // TODO : param check

    /**
     * list all folder data
     * @param type folder type
     * @param page from
     * @param limit end
     * @return query data
     */
    @GetMapping("/folder")
    public @ResponseBody
    Response listFolderData(int type, int page, int limit) {
        return resourceService.listFolders(type, page, limit);
    }

    /**
     * create new folder
     * @param name folder name
     * @param parentId folder parent id
     * @param type folder type
     * @return create response
     */
    @PostMapping("/folder")
    public @ResponseBody
    Response createFolder(String name, Long parentId, int type) {
        return resourceService.createNewFolder(name, parentId, type);
    }

    /**
     * update folder name
     * @param folderId folder id
     * @param name folder new name
     * @return update response
     */
    @PutMapping("/folder/name")
    public @ResponseBody
    Response updateFolderName(Long folderId, String name) {
        return resourceService.updateFolderName(folderId, name);
    }

    /**
     * list folder data by folder parent
     * @param parentId parent folder id
     * @param type folder type
     * @return folder query result
     */
    @GetMapping("/folder/parent")
    public @ResponseBody
    Response listFolderByParent(Long parentId, int type) {
        return resourceService.listFolderByFather(parentId, type);
    }

    /**
     * get single folder data by id
     * @param folderId folder id
     * @return folder data
     */
    @GetMapping("/folder/id")
    public @ResponseBody
    Response getFolderById(Long folderId) {
        return resourceService.getFolderById(folderId);
    }

    /**
     * delete target folder
     * @param folderId folder id
     * @return delete response
     */
    @DeleteMapping("/folder")
    public @ResponseBody
    Response deleteFolder(Long folderId) {
        return new Response(
            Response.SUCCESS_CODE,
                "delete folder success"
        );
    }

    // image

    /**
     * upload image
     * @param image
     * @param parentId
     * @return
     * @throws IOException
     */
    @PostMapping("/image")
    public @ResponseBody
    Response uploadImage(MultipartFile image, Long parentId) throws IOException, NoSuchAlgorithmException {
        // TODO : image check
        return resourceService.saveImage(image, parentId);
    }

    /**
     * get image data by folder id
     * @param parentId
     * @return
     */
    @GetMapping("/image/folder")
    public @ResponseBody
    Response getImageDataByFolder(Long parentId) {
        return resourceService.getImageListByFolderId(parentId);
    }

    /**
     * get image data by image id
     * @param imageId
     * @return
     */
    @GetMapping("/image/id")
    public @ResponseBody
    Response getImageDataById(Long imageId) {
        return resourceService.getImageById(imageId);
    }

    /**
     * update image name by image id
     * @param id
     * @param name
     * @return
     */
    @PutMapping("/image/id")
    public @ResponseBody
    Response updateImageName(Long id, String name) {
        return resourceService.updateImageName(id, name);
    }

    /**
     * delete image by image id
     * @param id
     * @return
     */
    @DeleteMapping("/image/id")
    public @ResponseBody
    Response deleteImageById(Long id) throws IOException {
        return resourceService.deleteImage(id);
    }

    @DeleteMapping("/image/batch")
    public @ResponseBody
    Response deleteImageByIdList(@RequestParam(value = "idList[]") List<Long> idList) throws IOException {
        return resourceService.deleteImgList(idList);
    }

    @PostMapping("/file")
    public @ResponseBody
    Response uploadFileResource(MultipartFile file, Long folder, String brief) throws IOException {
        return resourceService.saveFileResource(file, folder, brief);
    }

    @GetMapping("/file/folder")
    public @ResponseBody
    Response getFileListByFolderId(Long folder) {
        return resourceService.getFileResourceListByFolderId(folder);
    }

    @DeleteMapping("/file/id")
    public @ResponseBody
    Response deleteFileById(Long id) throws IOException {
        return resourceService.deleteFileResource(id);
    }

    @DeleteMapping("/file/batch")
    public @ResponseBody
    Response deleteFileByIdList(@RequestParam(value = "idList[]") List<Long> idList) throws IOException {
        return resourceService.deleteFileResourceList(idList);
    }

    @GetMapping("/file/download")
    public @ResponseBody
    ResponseEntity<Resource> downloadFile(Long id, HttpServletRequest request) throws UnsupportedEncodingException {
        Resource resource = resourceService.downloadFileResource(id);
        if (resource == null) {
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            // TODO log
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(resource.getFilename(), "UTF-8") + "\"")
                .body(resource);
    }

    @GetMapping("/file")
    public @ResponseBody
    Response getFileById(Long id) {
        return resourceService.getFileById(id);
    }

    @PutMapping("/file")
    public @ResponseBody
    Response updateFileInfo(@RequestParam(name = "id") Long id,
                            @RequestParam(name = "name") String name,
                            @RequestParam(name = "brief") String brief,
                            @RequestParam(name = "file", required = false) MultipartFile file) throws IOException, NoSuchAlgorithmException {
        return resourceService.updateFileInfo(id, file, name, brief);
    }
}
