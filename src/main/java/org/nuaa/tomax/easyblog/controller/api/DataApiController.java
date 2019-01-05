package org.nuaa.tomax.easyblog.controller.api;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.service.IBlogService;
import org.nuaa.tomax.easyblog.service.IClassificationService;
import org.nuaa.tomax.easyblog.service.ILinkService;
import org.nuaa.tomax.easyblog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/4 21:15
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class DataApiController {
    private final IBlogService blogService;
    private final ILinkService linkService;
    private final IClassificationService classificationService;
    private final IUserService userService;


    @Autowired
    public DataApiController(IBlogService blogService, ILinkService linkService, IClassificationService classificationService, IUserService userService) {
        this.blogService = blogService;
        this.linkService = linkService;
        this.classificationService = classificationService;
        this.userService = userService;
    }

    @GetMapping("/blog")
    public @ResponseBody
    Response getBlogList(int page, int limit) {
        return blogService.getBlogDataListRencentApi(page, limit);
    }

    @GetMapping("/blog/{id}")
    public @ResponseBody
    Response getBlogById(@PathVariable(name = "id") Long id) {
        return blogService.getBlogById(id);
    }

    @GetMapping("/classification")
    public @ResponseBody
    Response getClassificationList() {
        return classificationService.getClassificationListApi();
    }

    @GetMapping("/classification/{id}")
    public @ResponseBody
    Response getClassificationBlogList(@PathVariable(name = "id") Long id, int page, int limit) {
        return blogService.getBlogDataListByCategoryApi(id, page, limit);
    }

    @GetMapping("/label/{labelName}")
    public @ResponseBody
    Response getLabelBlogList(@PathVariable(name = "labelName") String label, int page, int limit) {
        return blogService.getBlogDataListByLabelApi(label, page, limit);
    }

    @GetMapping("/label")
    public @ResponseBody
    Response getLabelList() {
        return blogService.getLabelListApi();
    }

    @GetMapping("/clsName/{id}")
    public @ResponseBody
    Response getClsName(@PathVariable(name = "id") Long id) {
        return classificationService.getClassificationNameById(id);
    }

    @GetMapping("/user")
    public @ResponseBody
    Response getUserInfo() {
        return userService.userInfoApi();
    }
}
