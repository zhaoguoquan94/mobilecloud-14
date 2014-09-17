
/**
 * Created by 赵国铨 on 14-9-15.
 */


package org.magnum.mobilecloud.video.dal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.google.common.base.Objects;


@Entity
public class VideoDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String url;
    private long duration;

    public VideoDAO() {
    }

    public VideoDAO(String name, String url, long duration) {
        super();
        this.name = name;
        this.url = url;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Two Videos will generate the same hashcode if they have exactly the same
     * values for their name, url, and duration.
     *
     */
    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        return Objects.hashCode(name, url, duration);
    }

    /**
     * Two Videos are considered equal if they have exactly the same values for
     * their name, url, and duration.
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VideoDAO) {
            VideoDAO other = (VideoDAO) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(name, other.name)
                    && Objects.equal(url, other.url)
                    && duration == other.duration;
        } else {
            return false;
        }
    }

}
