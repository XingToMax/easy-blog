package org.nuaa.tomax.easyblog.service;

import org.nuaa.tomax.easyblog.entity.Response;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author: ToMax
 * @Description: resource admin service
 * @Date: Created in 2018/12/30 19:17
 */
public interface IResourceService {
    /**
     * create new folder
     * @param name folder name
     * @param parentId parent folder id
     * @param type folder type
     * @return create result
     */
    Response createNewFolder(String name, Long parentId, int type);

    /**
     * delete folder
     * @param folderId folder id
     * @return delete result
     * @throws IOException file exception
     */
    Response deleteFolder(Long folderId) throws IOException;

    /**
     * update folder name
     * @param folderId folder id
     * @param name folder name
     * @return update result
     */
    Response updateFolderName(Long folderId, String name);

    /**
     * get folder information list
     * @param type folder type
     * @param beg begin item
     * @param end end item
     * @return list data response
     */
    Response listFolders(int type, int beg, int end);

    /**
     * get folders of father folder
     * @param parentId father folder id
     * @param type folder type
     * @return list data response
     */
    Response listFolderByFather(Long parentId, int type);

    /**
     * get folder by id
     * @param folderId folder id
     * @return folder data
     */
    Response getFolderById(Long folderId);

    /**
     * image save
     * @param file
     * @param parentId
     * @return
     */
    Response saveImage(MultipartFile file, Long parentId) throws IOException, NoSuchAlgorithmException;

    /**
     * get image list by folder id
     * @param parentId
     * @return
     */
    Response getImageListByFolderId(Long parentId);

    /**
     * delete image by id
     * @param id
     * @return
     */
    Response deleteImage(Long id) throws IOException;

    /**
     * update the name of image
     * @param id
     * @param name
     * @return
     */
    Response updateImageName(Long id, String name);

    /**
     * get image data by image id
     * @param imageId
     * @return
     */
    Response getImageById(Long imageId);

    /**
     * delete img list
     * @param imageIdList
     * @return
     */
    Response deleteImgList(List<Long> imageIdList) throws IOException;

    /**
     * save file resource
     * @param file
     * @param parentId
     * @param brief
     * @return
     */
    Response saveFileResource(MultipartFile file, Long parentId, String brief) throws IOException;

    /**
     * get file resource list by folder id
     * @param id
     * @return
     */
    Response getFileResourceListByFolderId(Long id);

    /**
     * delete file resource
     * @param id
     * @return
     */
    Response deleteFileResource(Long id) throws IOException;

    /**
     * delete resource list
     * @param idList
     * @return
     */
    Response deleteFileResourceList(List<Long> idList) throws IOException;

    /**
     * download file resource
     * @param id
     * @return
     */
    Resource downloadFileResource(Long id);

    /**
     * get file information by id
     * @param id
     * @return
     */
    Response getFileById(Long id);

    /**
     * update file resource info
     * @param id
     * @param file
     * @param name
     * @param brief
     * @return
     */
    Response updateFileInfo(Long id, MultipartFile file, String name, String brief) throws IOException, NoSuchAlgorithmException;

    Response getAllFileResourceApi();

    Response getFileByIdApi(Long id);
}
