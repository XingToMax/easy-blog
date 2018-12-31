package org.nuaa.tomax.easyblog.service;

import org.nuaa.tomax.easyblog.entity.Response;

import java.io.IOException;

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
     * @throws IOException file exception
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
     * @throws IOException file exception
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
}
