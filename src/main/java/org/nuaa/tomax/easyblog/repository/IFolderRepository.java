package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/30 17:12
 */
public interface IFolderRepository extends JpaRepository<FolderEntity, Long> {


    /**
     * select folder list by father
     * @param father father id
     * @param type folder type
     * @return folder entities
     */
    List<FolderEntity> findFolderEntitiesByFatherAndType(Long father, int type);


    /**
     * select all folder data
     * @param limit limit
     * @param offset offset
     * @return folder entities
     */
    @Query(value = "select * from folder limit ?1 offset ?2", nativeQuery = true)
    List<FolderEntity> findFolderEntitiesLimit(int limit, int offset);

    /**
     * select all folder data
     * @param limit limit
     * @param offset offset
     * @param type type
     * @return folder entities
     */
    @Query(value = "select * from folder where type = ?1 limit ?2 offset ?3", nativeQuery = true)
    List<FolderEntity> findFolderEntitiesByTypeLimit(int type, int limit, int offset);

    /**
     * select folder by father id and folder name
     * @param father father id
     * @param name folder name
     * @param type folder type
     * @return result
     */
    Optional<FolderEntity> findFolderEntityByFatherAndNameAndType(Long father, String name, int type);

    /**
     * update folder name
     * @param name target name
     * @param folderId folder id
     * @return folder entity data
     */
    @Modifying
    @Query(value = "update folder set name = ?1 where id = ?2", nativeQuery = true)
    void updateFolderName(String name, long folderId);


    /**
     * update all children folders path when update one folder
     * @param fatherCurrentPath target folder current path
     * @param fatherOriginalPath target folder origin path
     * @param likePath like path
     * @param type folder type
     */
    @Modifying
    @Query(value = "update folder set path = concat(?1, substr(path, 1 + length(?2), length(path))) where path like ?3 and type = ?4", nativeQuery = true)
    void updateChildrenFolderPath(String fatherCurrentPath, String fatherOriginalPath, String likePath, int type);


//    void deleteById()
}
