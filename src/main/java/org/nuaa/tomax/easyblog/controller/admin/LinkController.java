package org.nuaa.tomax.easyblog.controller.admin;

import org.nuaa.tomax.easyblog.annotation.ServiceLog;
import org.nuaa.tomax.easyblog.entity.LinkEntity;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/3 20:36
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/link")
public class LinkController {
    private final ILinkService linkService;

    @Autowired
    public LinkController(ILinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    @ServiceLog
    public @ResponseBody
    Response getLinkList(int page, int limit) {
        return linkService.listLinkData(page, limit);
    }

    @PostMapping
    @ServiceLog
    public @ResponseBody
    Response createLink(LinkEntity link) {
        return linkService.addLink(link);
    }

    @PutMapping
    @ServiceLog
    public @ResponseBody
    Response updateLink(LinkEntity link) {
        return linkService.updateLink(link);
    }

    @DeleteMapping
    @ServiceLog
    public @ResponseBody
    Response deleteLink(Long id) {
        return linkService.removeLink(id);
    }
}
