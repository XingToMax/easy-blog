package org.nuaa.tomax.easyblog.entity.view;

import lombok.Data;
import org.nuaa.tomax.easyblog.entity.ClassificationEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/5 19:26
 */
@Data
public class CategoryEntity {
    private Long id;
    private Long father;
    private List<CategoryEntity> sons;
    private String name;
    private Long blogCount;

    public CategoryEntity() {}
    public CategoryEntity(ClassificationEntity cls) {
        this.id = cls.getId();
        this.father = cls.getFather();
        this.name = cls.getName();
        this.sons = new ArrayList<>();
        this.blogCount = cls.getBlogCount();
    }

    public void addNode(CategoryEntity cls) {
        this.sons.add(cls);
        blogCount += cls.getBlogCount();
    }
}
