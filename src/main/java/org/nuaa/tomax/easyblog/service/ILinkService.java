package org.nuaa.tomax.easyblog.service;

import org.nuaa.tomax.easyblog.entity.LinkEntity;
import org.nuaa.tomax.easyblog.entity.Response;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/3 20:25
 */
public interface ILinkService {
    /**
     * create new link
     * @param link
     * @return
     */
    Response addLink(LinkEntity link);

    /**
     * get all link data
     * @param page
     * @param limit
     * @return
     */
    Response listLinkData(int page, int limit);

    /**
     * update link data
     * @param link
     * @return
     */
    Response updateLink(LinkEntity link);

    /**
     * delete link
     * @param id
     * @return
     */
    Response removeLink(Long id);
}
