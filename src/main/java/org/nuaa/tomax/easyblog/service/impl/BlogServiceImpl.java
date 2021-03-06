package org.nuaa.tomax.easyblog.service.impl;

import org.nuaa.tomax.easyblog.constant.ConstProcessState;
import org.nuaa.tomax.easyblog.entity.BlogEntity;
import org.nuaa.tomax.easyblog.entity.LabelEntity;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.repository.IBlogRepository;
import org.nuaa.tomax.easyblog.repository.IClassificationRepository;
import org.nuaa.tomax.easyblog.service.IBlogService;
import org.nuaa.tomax.easyblog.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/9 23:20
 */
@Service
@CacheConfig(cacheNames = "blog")
public class BlogServiceImpl implements IBlogService {

    private final IBlogRepository blogRepository;
    private final IClassificationRepository classificationRepository;
    private static String blogRootPath;
    @Autowired
    public BlogServiceImpl(IBlogRepository blogRepository, IClassificationRepository classificationRepository, Environment environment) {
        this.blogRepository = blogRepository;
        this.classificationRepository = classificationRepository;

        blogRootPath = environment.getProperty("source.blog.path", "source/blog");
    }

    @Override
    public Response create(BlogEntity blog) throws IOException {
        Long check = blogRepository.findBlogEntityByNameAndClassification(blog.getName(), blog.getClassification());
        if (check > 0) {
            return new Response(Response.SERVER_DATA_DUPLICATION, "name is exists");
        }
        // save markdown
        FileUtil.saveMarkdownFile(blog.getMarkdownContent(), blogRootPath + "/", blog.getName() + ".md");
        // save html
        FileUtil.saveMarkdownFile(blog.getHtmlContent(), blogRootPath + "/", blog.getName() + ".html");

        blog.setPath("/" + blog.getName());
        blog = blogRepository.save(blog);
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "创建博客成功",
                blog
        );
    }

    @Override
    public Response getBlogList(int beg, int end) {
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "get blog success",
                blogRepository.getBlogListByLimit(end, (beg - 1) * end),
                blogRepository.count()
        );
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Response updateBlogData(BlogEntity blog) {
        Long check = blogRepository.findBlogEntityByNameAndClassificationAndIdNot(blog.getName(), blog.getClassification(), blog.getId());
        if (check > 0) {
            return new Response(Response.SERVER_DATA_DUPLICATION, "name is exists");
        }

        BlogEntity data = blogRepository.findById(blog.getId()).orElseGet(() -> null);

        FileUtil.renameFile(blogRootPath, data.getName() + ".md", blog.getName() + ".md");
        FileUtil.renameFile(blogRootPath, data.getName() + ".html", blog.getName() + ".html");
        blog.setPath("/" + blog.getName());
        blogRepository.updateBlogData(blog.getName(),
                blog.getCover(),
                blog.getClassification(),
                blog.getLabels(),
                blog.getType(),
                blog.getBrief(),
                blog.getPath(),
                blog.getState(),
                blog.getId());
        return new Response(
                Response.SUCCESS_CODE, "update blog data success");
    }

    @Override
    public Response deleteBlogById(Long id) {
        BlogEntity blog = blogRepository.findBlogEntityById(id);
        if (blog == null) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "blog not exists");
        }

        FileUtil.deleteFile(new File(blogRootPath + "/" + blog.getPath() + ".md"));
        FileUtil.deleteFile(new File(blogRootPath + "/" + blog.getPath() + ".html"));

        blogRepository.deleteById(id);
        return new Response(Response.SUCCESS_CODE, "delete blog success");
    }

    @Override
    public Response getBlogById(Long id) {
        BlogEntity blog = blogRepository.findBlogEntityById(id);
        if(blog == null) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "blog not exists");
        }

        // read content
        try {
            blog.setMarkdownContent(FileUtil.readMarkdownFile(blogRootPath + blog.getPath() + ".md"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "get blog success",
                blog
        );
    }

    @Override
    public Response updateBlogContent(BlogEntity blog) throws IOException {
        BlogEntity blogData = blogRepository.findById(blog.getId()).orElseGet(() -> null);
        if (blogData == null) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "blog not exists");
        }
        // update markdown
        FileUtil.saveMarkdownFile(blog.getMarkdownContent(), blogRootPath + "/", blogData.getName() + ".md");
        // update html
        FileUtil.saveMarkdownFile(blog.getHtmlContent(), blogRootPath + "/", blogData.getName() + ".html");
        return new Response(Response.SUCCESS_CODE, "update blog content success");
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Response updateBlogState(Long id, Integer state) {
        blogRepository.updateBlogState(state, id);
        return new Response(
                Response.SUCCESS_CODE,
                "update blog publish state success"
        );
    }

    @Override
    public Response getBlogDataListRencentApi(int page, int limit) {
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "get blog success",
                blogRepository.getBlogListByLimitOrderByTime(limit, (page - 1) * limit),
                blogRepository.countAllPublishedBlog(ConstProcessState.BLOG_PUBLISH_STATE)
        );
    }

    @Override
    public Response getBlogDataListTimeLineApi(int page, int limit) {
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "get blog success",
                blogRepository.getBlogListByLimitOrderByTime(limit, (page - 1) * limit),
                blogRepository.countAllPublishedBlog(ConstProcessState.BLOG_PUBLISH_STATE)
        );
    }

    @Override
    public Response getBlogDataListByCategoryApi(Long classification, int page, int limit) {
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "get blog success",
                blogRepository.getBlogListByClassificationAndLimit(classification, limit, (page - 1) * limit),
                blogRepository.countClassificationBlog(ConstProcessState.BLOG_PUBLISH_STATE, classification)
        );
    }

    @Override
    public Response getBlogDataListByLabelApi(String label, int page, int limit) {
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "get blog success",
                blogRepository.getBlogListByLabelAndLimit(label, limit, (page - 1) * limit),
                blogRepository.countLabelBlog(ConstProcessState.BLOG_PUBLISH_STATE, label)
        );
    }

    @Override
    public Response getBlogByIdApi(Long id) {
        BlogEntity blog = blogRepository.findBlogEntityByIdAndState(id, ConstProcessState.BLOG_PUBLISH_STATE);
        if(blog == null) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "blog not exists");
        }
        // read content
        blog.setMarkdownContent(getMarkdownContent(blog));
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "get blog success",
                blog
        );
    }

    @Override
    public Response getLabelListApi() {
        List<String> labelList = blogRepository.getLabels();
        List<String> labels = new LinkedList<>();
        for (String labelCell : labelList) {
            for (String cell : labelCell.split(";")) {
                labels.add(cell);
            }
        }
        List<LabelEntity> labelEntities = labels.stream()
                .distinct()
                .map(label -> new LabelEntity(label, blogRepository.countLabelBlog(ConstProcessState.BLOG_PUBLISH_STATE, label)))
                .collect(Collectors.toList());
        return new Response<LabelEntity>(
                Response.SUCCESS_CODE,
                "get labels success",
                labelEntities
        );
    }

    /**
     * get classification name
     * @param classification classification id
     * @return classification name
     */
    private String getClassificationName(Long classification) {
        return classificationRepository.findNameById(classification);
    }

    @Cacheable
    public String getMarkdownContent(BlogEntity blog) {
        String content = "";
        try {
            content = FileUtil.readMarkdownFile(blogRootPath + blog.getPath() + ".md");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
