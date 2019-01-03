package org.nuaa.tomax.easyblog.service;

import org.nuaa.tomax.easyblog.entity.BlogEntity;
import org.nuaa.tomax.easyblog.entity.Response;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/9 23:14
 */
public interface IBlogService {
    /**
     * create blog
     * @param blog
     * @return
     */
    Response create(BlogEntity blog) throws IOException;

    /**
     * get blog list
     * @param beg
     * @param end
     * @return
     */
    Response getBlogList(int beg, int end);

    /**
     * update blog data
     * @param blog
     * @return
     */
    Response updateBlogData(BlogEntity blog);

    /**
     * delete blog by id
     * @param id
     * @return
     */
    Response deleteBlogById(Long id);

    /**
     * get blog by id
     * @param id
     * @return
     */
    Response getBlogById(Long id);

    /**
     * update blog content
     * @param blog
     * @return
     */
    Response updateBlogContent(BlogEntity blog) throws IOException;
}
