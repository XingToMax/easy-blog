package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.ClassificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/1 23:27
 */
public interface IClassificationRepository extends JpaRepository<ClassificationEntity, Long> {

    /**
     * find classification by father and name (for check)
     * @param father
     * @param name
     * @return
     */
    Optional<ClassificationEntity> findClassificationEntityByFatherAndName(Long father, String name);

    /**
     * find classification by father and name (for update check)
     * @param father
     * @param name
     * @param id
     * @return
     */
    Optional<ClassificationEntity> findClassificationEntityByFatherAndNameAndIdNot(Long father, String name, Long id);

    /**
     * update classification name, brief by id
     * @param name
     * @param brief
     * @param id
     */
    @Modifying
    @Query(value = "update classification set name = ?1, brief = ?2 where id = ?3", nativeQuery = true)
    void updateClassificationById(String name, String brief, Long id);

    /**
     * get list by limit
     * @param limit
     * @param offset
     * @return
     */
    @Query(value = "select c1.id, c1.name, c1.father, c1.brief, c1.time, c1.user_id, c2.name as father_name from classification as c1 left join classification as c2 on c1.father = c2.id limit ?1 offset ?2", nativeQuery = true)
    List<ClassificationEntity> findClassificationEntitiesByLimit(int limit, int offset);

    @Query(value = "select new ClassificationEntity (id, name, father) from ClassificationEntity")
    List<ClassificationEntity> findClassificationEntitiesSimple();

    /**
     * get classification name
     * @param id
     * @return
     */
    @Query(value = "select name from classification where id = ?", nativeQuery = true)
    String findNameById(Long id);
}
