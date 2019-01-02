package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/30 19:14
 */
public interface IResourceRepository extends JpaRepository<ResourceEntity, Long> {

    /**
     * update all children folders path when update one folder
     * @param fatherCurrentPath target folder current path
     * @param fatherOriginalPath target folder origin path
     * @param likePath like path
     * @param type folder type
     */
    @Modifying
    @Query(value = "update resource set path = concat(?1, substr(path, 1 + length(?2), length(path))) where path like ?3 and type = ?4", nativeQuery = true)
    void updateChildrenResourcePath(String fatherCurrentPath, String fatherOriginalPath, String likePath, int type);


}
