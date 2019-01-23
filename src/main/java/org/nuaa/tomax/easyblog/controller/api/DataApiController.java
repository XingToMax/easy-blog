package org.nuaa.tomax.easyblog.controller.api;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.service.IBlogService;
import org.nuaa.tomax.easyblog.service.IClassificationService;
import org.nuaa.tomax.easyblog.service.ILinkService;
import org.nuaa.tomax.easyblog.service.IUserService;
import org.nuaa.tomax.easyblog.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

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

    private final String galleryPath;

    @Autowired
    public DataApiController(IBlogService blogService, ILinkService linkService, IClassificationService classificationService, IUserService userService, Environment environment) {
        this.blogService = blogService;
        this.linkService = linkService;
        this.classificationService = classificationService;
        this.userService = userService;
        galleryPath = environment.getProperty("source.gallery.path", "source/gallery");
    }

    @GetMapping("/blog")
    public @ResponseBody
    Response getBlogList(int page, int limit) {
        return blogService.getBlogDataListRencentApi(page, limit);
    }

    @GetMapping("/blog/{id}")
    public @ResponseBody
    Response getBlogById(@PathVariable(name = "id") Long id) {
        return blogService.getBlogByIdApi(id);
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

    @GetMapping("/image/gallery/particular/{token}")
    public @ResponseBody
    void visitImage(@PathVariable(name = "token") String token, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "max-age=604800");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(galleryPath + "/" + token);
            FileUtil.getImageResource(inputStream, response.getOutputStream());
            inputStream.close();
        } catch (IOException e) {
            inputStream = new FileInputStream(this.getClass().getResource("/").getPath() + "/static/images/404.png");
            FileUtil.getImageResource(inputStream, response.getOutputStream());
            inputStream.close();
        }
    }
}
