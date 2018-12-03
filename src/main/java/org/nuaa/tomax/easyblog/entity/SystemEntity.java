package org.nuaa.tomax.easyblog.entity;

import javax.persistence.*;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 20:47
 */
@Entity
@Table(name = "system", schema = "easy-blog")
public class SystemEntity {
    private String host;
    private String imageRoot;
    private String resourceRoot;
    private Integer port;
    private Integer enableRss;
    private Integer enableComment;
    private long id;

    @Basic
    @Column(name = "host")
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Basic
    @Column(name = "image_root")
    public String getImageRoot() {
        return imageRoot;
    }

    public void setImageRoot(String imageRoot) {
        this.imageRoot = imageRoot;
    }

    @Basic
    @Column(name = "resource_root")
    public String getResourceRoot() {
        return resourceRoot;
    }

    public void setResourceRoot(String resourceRoot) {
        this.resourceRoot = resourceRoot;
    }

    @Basic
    @Column(name = "port")
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Basic
    @Column(name = "enable_rss")
    public Integer getEnableRss() {
        return enableRss;
    }

    public void setEnableRss(Integer enableRss) {
        this.enableRss = enableRss;
    }

    @Basic
    @Column(name = "enable_comment")
    public Integer getEnableComment() {
        return enableComment;
    }

    public void setEnableComment(Integer enableComment) {
        this.enableComment = enableComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemEntity that = (SystemEntity) o;

        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (imageRoot != null ? !imageRoot.equals(that.imageRoot) : that.imageRoot != null) return false;
        if (resourceRoot != null ? !resourceRoot.equals(that.resourceRoot) : that.resourceRoot != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;
        if (enableRss != null ? !enableRss.equals(that.enableRss) : that.enableRss != null) return false;
        if (enableComment != null ? !enableComment.equals(that.enableComment) : that.enableComment != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + (imageRoot != null ? imageRoot.hashCode() : 0);
        result = 31 * result + (resourceRoot != null ? resourceRoot.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (enableRss != null ? enableRss.hashCode() : 0);
        result = 31 * result + (enableComment != null ? enableComment.hashCode() : 0);
        return result;
    }

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
