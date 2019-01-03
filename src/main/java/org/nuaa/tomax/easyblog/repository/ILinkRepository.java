package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/3 20:24
 */
public interface ILinkRepository extends JpaRepository<LinkEntity, Long> {
    /**
     * get pageable data
     * @param limit
     * @param offset
     * @return
     */
    @Query(value = "select * from link limit ?1 offset ?2", nativeQuery = true)
    List<LinkEntity> getLinkData(int limit, int offset);

    @Modifying
    @Query(value = "update link set name = ?1, description = ?2, type = ?3, icon = ?4, address = ?5 where id = ?6", nativeQuery = true)
    void updateLinkData(String name, String description, int type, String icon, String address, Long id);
}
