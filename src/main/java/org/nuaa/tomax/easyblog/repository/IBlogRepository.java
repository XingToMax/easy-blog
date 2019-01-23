package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


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

    /**
     * check blog name exists or not
     * @param name
     * @param id
     * @return
     */
    @Query(value = "select count(1) from blog b where b.name = ?1 and b.classification = ?2", nativeQuery = true)
    Long findBlogEntityByNameAndClassification(String name, Long id);
    /**
     * check blog name exists or not
     * @param name
     * @param classification
     * @param id
     * @return
     */
    @Query(value = "select count(1) from blog b where b.name = ?1 and b.classification = ?2 and b.id != ?3", nativeQuery = true)
    Long findBlogEntityByNameAndClassificationAndIdNot(String name, Long classification, Long id);

    /**
     * query data
     * @param limit
     * @param offset
     * @return
     */
    @Query(value = "select b.id, b.name, b.cover, b.path, b.state, b.brief, b.classification, b.labels, b.time, b.update_time, b.watch_count, b.recommend_count, b.user_id, b.type, c.name as classification_name from blog as b inner join classification as c on b.classification = c.id limit ?1 offset ?2", nativeQuery = true)
    List<BlogEntity> getBlogListByLimit(int limit, int offset);
    @Query(value = "select b.id, b.name, b.cover, b.path, b.state, b.brief, b.classification, b.labels, b.time, b.update_time, b.watch_count, b.recommend_count, b.user_id, b.type, c.name as classification_name from blog as b inner join classification as c on b.classification = c.id where b.state = 1 order by time desc limit ?1 offset ?2", nativeQuery = true)
    List<BlogEntity> getBlogListByLimitOrderByTime(int limit, int offset);
    @Query(value = "select b.id, b.name, b.cover, b.path, b.state, b.brief, b.classification, b.labels, b.time, b.update_time, b.watch_count, b.recommend_count, b.user_id, b.type, c.name as classification_name from blog as b inner join classification as c on b.classification = c.id where b.state = 1 and b.classification = ?1 order by time desc limit ?2 offset ?3", nativeQuery = true)
    List<BlogEntity> getBlogListByClassificationAndLimit(Long classification, int limit, int offset);
    @Query(value = "select b.id, b.name, b.cover, b.path, b.state, b.brief, b.classification, b.labels, b.time, b.update_time, b.watch_count, b.recommend_count, b.user_id, b.type, c.name as classification_name from blog as b inner join classification as c on b.classification = c.id where b.state = 1 and b.labels like %?1% order by time desc limit ?2 offset ?3", nativeQuery = true)
    List<BlogEntity> getBlogListByLabelAndLimit(String label, int limit, int offset);

    /**
     * query data by id
     * @param id
     * @return
     */
    @Query(value = "select b.id, b.name, b.cover, b.state, b.path, b.brief, b.classification, b.labels, b.time, b.update_time, b.watch_count, b.recommend_count, b.user_id, b.type, c.name as classification_name from blog as b inner join classification as c on b.classification = c.id where b.id = ?1", nativeQuery = true)
    BlogEntity findBlogEntityById(Long id);

    /**
     * limit visit by state
     * @param id
     * @param state
     * @return
     */
    @Query(value = "select b.id, b.name, b.cover, b.state, b.path, b.brief, b.classification, b.labels, b.time, b.update_time, b.watch_count, b.recommend_count, b.user_id, b.type, c.name as classification_name from blog as b inner join classification as c on b.classification = c.id where b.id = ?1 and state = ?2", nativeQuery = true)
    BlogEntity findBlogEntityByIdAndState(Long id, Integer state);
    /**
     * update blog data
     * @param name
     * @param cover
     * @param classification
     * @param labels
     * @param type
     * @param id
     */
    @Modifying
    @Query(value = "update blog set name = ?1, cover = ?2, classification= ?3, labels = ?4, type = ?5, brief = ?6, path = ?7, state = ?8 where id = ?9", nativeQuery = true)
    void updateBlogData(String name, String cover, Long classification, String labels, int type, String brief, String path, Integer state, Long id);

    /**
     * update blog publish state
     * @param state
     * @param id
     */
    @Modifying
    @Query(value = "update blog set state = ?1 where id = ?2", nativeQuery = true)
    void updateBlogState(Integer state, Long id);

    /**
     * count blog data under classification
     * @param state
     * @param classificationId
     * @return
     */
    Long countBlogEntitiesByStateAndClassification(Integer state, Long classificationId);

    /**
     * get this id's next
     * @param id
     * @return
     */
    @Query(value = "select id from blog where id > ?1 limit 1", nativeQuery = true)
    Long getNextId(Long id);

    @Query(value = "select count(1) from blog where state = ?1 and classification = ?2", nativeQuery = true)
    Long countClassificationBlog(Integer state, Long classification);

    @Query(value = "select count(1) from blog where state = ?1 and labels like %?2%", nativeQuery = true)
    Long countLabelBlog(Integer state, String label);

    @Query(value = "select count(1) from blog where state = ?1", nativeQuery = true)
    Long countAllPublishedBlog(Integer state);

    @Query(value = "select labels from blog", nativeQuery = true)
    List<String> getLabels();
}
