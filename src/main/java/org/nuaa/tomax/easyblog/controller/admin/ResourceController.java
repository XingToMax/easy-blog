package org.nuaa.tomax.easyblog.controller.admin;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping("/image")
    public @ResponseBody
    Response uploadImage(MultipartFile image, Long parentId) throws IOException {
        // TODO : image check
        return resourceService.saveImage(image, parentId);
    }
}
