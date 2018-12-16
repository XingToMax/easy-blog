package org.nuaa.tomax.easyblog.entity;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 20:47
 */
@Entity
@Table(name = "image", schema = "easy-blog")
public class ImageEntity {
    private long id;
    private String path;
    private String name;
    private Long folder;
    private Timestamp time;
    private Long userId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "folder")
    public Long getFolder() {
        return folder;
    }

    public void setFolder(Long folder) {
        this.folder = folder;
    }

    @Basic
    @Column(name = "time")
    @Generated(GenerationTime.INSERT)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageEntity that = (ImageEntity) o;

        if (id != that.id) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (folder != null ? !folder.equals(that.folder) : that.folder != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (folder != null ? folder.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
