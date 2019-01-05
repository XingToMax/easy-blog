package org.nuaa.tomax.easyblog.entity.view;

import lombok.Data;
import org.nuaa.tomax.easyblog.entity.LinkEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/5 23:50
 */
@Data
public class UserView {
    private String username;
    private Long blogCount;
    private Long clsCount;
    private Long labelCount;
    private Long resourceCount;
    private String avatar;
    private List<LinkEntity> links;

    public UserView(String username, Long blogCount, Long clsCount, Long labelCount, Long resourceCount, String avatar, List<LinkEntity> links) {
        this.username = username;
        this.blogCount = blogCount;
        this.clsCount = clsCount;
        this.labelCount = labelCount;
        this.resourceCount = resourceCount;
        this.avatar = avatar;
        this.links = links;
    }
}
