package org.nuaa.tomax.easyblog.entity.view;

import lombok.Data;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/6 0:33
 */
@Data
public class SystemCountDataView {
    private Long blogCount;
    private Long clsCount;
    private Long labelCount;
    private Long imgCount;
    private Long resourceCount;

    public SystemCountDataView(Long blogCount, Long clsCount, Long labelCount, Long imgCount, Long resourceCount) {
        this.blogCount = blogCount;
        this.clsCount = clsCount;
        this.labelCount = labelCount;
        this.imgCount = imgCount;
        this.resourceCount = resourceCount;
    }
}
