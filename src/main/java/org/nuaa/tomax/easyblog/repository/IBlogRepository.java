package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/9 23:13
 */
public interface IBlogRepository extends JpaRepository<BlogEntity, Long> {
    /**
     * update all children folders path when update one folder
     * @param fatherCurrentPath target folder current path
     * @param fatherOriginalPath target folder origin path
     * @param likePath like path
     * @param type folder type
     */
    @Modifying
    @Query(value = "update blog set path = concat(?1, substr(path, 1 + length(?2), length(path))) where path like ?3 and type = ?4", nativeQuery = true)
    void updateChildrenBlogPath(String fatherCurrentPath, String fatherOriginalPath, String likePath, int type);

}
