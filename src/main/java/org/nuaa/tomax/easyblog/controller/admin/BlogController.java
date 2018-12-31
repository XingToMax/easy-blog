package org.nuaa.tomax.easyblog.controller.admin;

import org.nuaa.tomax.easyblog.entity.BlogEntity;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/9 23:12
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/blog")
public class BlogController {

    private final IBlogService blogService;

    @Autowired
    public BlogController(IBlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("")
    public Response createBlog(BlogEntity blog) {
        // TODO : 输入校验
        return blogService.create(blog);
    }

    @GetMapping("/test")
    public Response getBlog() {
        String content = "# 个人简历（后台开发）\n" +
                "\n" +
                "## 联系方式\n" +
                "\n" +
                "- 手机：15195963968\n" +
                "- Email：1121584497@qq.com\n" +
                "- 微信：Tmx0620\n" +
                "\n" +
                "## 个人信息\n" +
                "\n" +
                " - 汤茂行/男/1997.6.20\n" +
                " - 本科/南京航空航天大学计算机科学与技术学院软件工程专业/英语六级/CCF-CSP认证考试300分\n" +
                " - Github：[https://github.com/XingToMax ](https://github.com/XingToMax)\n" +
                "\n" +
                "## 主要技能\n" +
                "\n" +
                "+ Java（熟悉）\n" +
                "+ C/C++/Python（了解）\n" +
                "+ Spring（了解）\n" +
                "+ MySQL（了解）\n" +
                "+ Redis（了解）\n" +
                "+ Tomcat（了解）\n" +
                "+ Hadoop（了解）\n" +
                "+ tensorflow（了解）\n" +
                "+ opencv（了解）\n" +
                "+ Git（了解）\n" +
                "\n" +
                "## 实践经历\n" +
                "\n" +
                "### PDF2EPUB项目\n" +
                "\n" +
                "##### 项目描述\n" +
                "\n" +
                "+ 将规则化较差的PDF出版物转换成epub格式，主要基于开源项目PDFBox、Itext等进行开发，包括对PDF中目录结构、图表信息、图解图注信息进行提取，完成章节拆分，最终输出可供阅读器使用的epub文件。\n" +
                "\n" +
                "##### 责任描述\n" +
                "\n" +
                "+ 在本项目中负责项目管理、项目设计以及部分开发工作。\n" +
                "\n" +
                "+ 开发部分主要负责目录结构解析、章节拆分、解析结果整合与epub输出。\n" +
                "\n" +
                "### 基于深度学习的情绪模型系统\n" +
                "\n" +
                "##### 项目描述\n" +
                "\n" +
                "- 基于tensorflow、opencv等框架实现了表情分类模型，主要包括人脸检测与表情识别，支持happy、sad、surprise等7种表情的识别。\n" +
                "\n" +
                "##### 责任描述\n" +
                "\n" +
                "1. 项目基本由个人完成。\n" +
                "\n" +
                "2. 通过该项目初步学习了深度学习相关知识。\n" +
                "\n" +
                "### 南京航空航天大学体育教学平台\n" +
                "\n" +
                "#####  项目描述\n" +
                "\n" +
                "+ 平台业务包括南京航空航天大学学生体育课程重修、补选登记以及重修、补选课程的管理。\n" +
                "\n" +
                "##### 责任描述\n" +
                "\n" +
                "+ 主要负责后端开发，使用了struts2、Spring等框架。\n" +
                "\n" +
                "### 隧道超欠挖检测系统\n" +
                "\n" +
                "##### 项目描述\n" +
                "\n" +
                "+ 主要包含隧道数据预警后台、android端的隧道超欠挖检测app。使用了struts2、Spring等框架.\n" +
                "\n" +
                "##### 责任描述\n" +
                "\n" +
                "1. 主要负责项目管理、后端开发以及app端的数据库开发。\n" +
                "\n" +
                "\n" +
                "### 2018全国大学生测试大赛\n" +
                "\n" +
                "##### 项目描述\n" +
                "\n" +
                "+ 开发者测试，使用Junit完成对指定项目的测试，评分标准包括分支覆盖率与变异率。\n" +
                "\n" +
                "#####  责任描述\n" +
                "\n" +
                "+ 获取国赛二等奖，排名第9。\n" +
                "\n" +
                "## 自我评价\n" +
                "\n" +
                "爱好篮球、吉他，性格内敛，做事比较认真，对于新技术有学习热情。\n" +
                "\n" +
                ">  brief ... ... \n" +
                "\n" +
                "``` java\n" +
                "// main\n" +
                "public static void main(String[] args) {\n" +
                "    System.out.println(\"Hello world\");\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "$$\n" +
                "exp\n" +
                "$$\n" +
                "\n" +
                "| 1    | 2    | 3    |\n" +
                "| ---- | ---- | :--- |\n" +
                "| 1    | 2    | 3    |\n" +
                "| 1    | 2    | 3    |\n" +
                "| 1    | 2    | 3    |\n" +
                "\n" +
                "\n" +
                "\n" +
                "`hello`\n" +
                "``hello``\n" +
                "- hello, world\n" +
                "- hello, world\n" +
                "![图片](http://tomax.xin/images/softtest/1540472361920.png)\n" +
                "### 感谢您花时间阅读我的简历，期待能有机会和您共事。\n" +
                "\n";
        return new Response<String>(
                Response.SUCCESS_CODE,
                "获取数据成功",
                content
                );
    }
}
