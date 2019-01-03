package org.nuaa.tomax.easyblog.service.impl;

import org.nuaa.tomax.easyblog.entity.LinkEntity;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.repository.ILinkRepository;
import org.nuaa.tomax.easyblog.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/3 20:27
 */
@Service
public class LinkServiceImpl implements ILinkService{

    private final ILinkRepository linkRepository;

    @Autowired
    public LinkServiceImpl(ILinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Response addLink(LinkEntity link) {
        return new Response<LinkEntity>(
                Response.SUCCESS_CODE,
                "create link success",
                linkRepository.save(link)
        );
    }

    @Override
    public Response listLinkData(int page, int limit) {
        return page > 0 ?
                new Response<LinkEntity>(
                        Response.SUCCESS_CODE,
                        "get data success",
                        linkRepository.getLinkData(limit, (page - 1) * limit)
                        ) :
                new Response<LinkEntity>(
                        Response.SUCCESS_CODE,
                        "get data success",
                        linkRepository.findAll()
                );
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Response updateLink(LinkEntity link) {
        linkRepository.updateLinkData(
                link.getName(), link.getDescription(), link.getType(),
                link.getIcon(), link.getAddress(), link.getId()
        );
        return new Response(Response.SUCCESS_CODE, "update data succes");
    }

    @Override
    public Response removeLink(Long id) {
        linkRepository.deleteById(id);
        return new Response(Response.SUCCESS_CODE, "delete data success");
    }
}
