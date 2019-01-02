package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/30 17:34
 */
public interface IImageRepository extends JpaRepository<ImageEntity, Long>{

    /**
     * get image list by folder id
     * @param folder folder id
     * @return image data
     */
    List<ImageEntity> findImageEntitiesByFolder(Long folder);

    /**
     * update all children folders path when update one folder
     * @param fatherCurrentPath target folder current path
     * @param fatherOriginalPath target folder origin path
     * @param likePath like path
     * @param type folder type
     */
    @Modifying
    @Query(value = "update image set path = concat(?1, substr(path, 1 + length(?2), length(path))) where path like ?3 and type = ?4", nativeQuery = true)
    void updateChildrenImagePath(String fatherCurrentPath, String fatherOriginalPath, String likePath, int type);

    /**
     * update image name and image path by image id
     * @param path
     * @param name
     * @param id
     */
    @Modifying
    @Query(value = "update image set path = ?1, name = ?2 where id = ?3", nativeQuery = true)
    void updateImagePathAndNameById(String path, String name, Long id);

}
