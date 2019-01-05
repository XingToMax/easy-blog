package org.nuaa.tomax.easyblog.entity;

import lombok.Data;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/3 19:48
 */
@Data
public class LabelEntity {
    private String name;
    private Long count;
    private Integer id;
    private Integer level;

    public LabelEntity(String name, Long count) {
        this.name = name;
        this.count = count;
    }
}
