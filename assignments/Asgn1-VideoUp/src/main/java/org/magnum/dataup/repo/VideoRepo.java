package org.magnum.dataup.repo;

import org.magnum.dataup.model.Video;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by 赵国铨 on 14-9-9.
 */
public class VideoRepo {
    Collection<Video> videos=new ArrayList<Video>();
    public Collection<Video> getVideos(){
        return videos;
    }
    public boolean addVideo(Video v){
        videos.add(v);
        return true;
    }

    // Find all videos with a matching title (e.g., Video.name)
    public Collection<Video> findByTitle(String title){
        return null;
        //TODO
    }

}
