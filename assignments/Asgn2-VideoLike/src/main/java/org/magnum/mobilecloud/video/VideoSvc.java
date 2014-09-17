/*
 * 
 * Copyright 2014 Camilo Cota
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.magnum.mobilecloud.video;

import com.google.common.collect.Lists;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class VideoSvc {

    public static final String TITLE_PARAMETER = "title";

    public static final String DURATION_PARAMETER = "duration";

    public static final String TOKEN_PATH = "/oauth/token";

    // The path where we expect the VideoSvc to live
    public static final String VIDEO_SVC_PATH = "/video";

    // The path to search videos by title
    public static final String VIDEO_TITLE_SEARCH_PATH = VIDEO_SVC_PATH + "/search/findByName";

    // The path to search videos by title
    public static final String VIDEO_DURATION_SEARCH_PATH = VIDEO_SVC_PATH + "/search/findByDurationLessThan";

    @Autowired
    private VideoRepo videos;

    @RequestMapping(value = VIDEO_SVC_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    Collection<Video> getVideoList() {
        return Lists.newArrayList(videos.findAll());
    }

    @RequestMapping(value = VIDEO_SVC_PATH + "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Video getVideoById(@PathVariable(value = "id") long id) {
        return videos.findById(id);
    }

    @RequestMapping(value = VIDEO_TITLE_SEARCH_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    Collection<Video> findByTitle(@RequestParam(value = TITLE_PARAMETER) String title) {
        return videos.findByName(title);
    }

    @RequestMapping(value = VIDEO_DURATION_SEARCH_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    Collection<Video> findByDurationLessThan(@RequestParam(value = DURATION_PARAMETER) long duration) {
        return videos.findByDurationLessThan(duration);
    }

    @RequestMapping(value = VIDEO_SVC_PATH + "/{id}/like", method = RequestMethod.POST)
    public
    @ResponseBody
    void likeVideo(@PathVariable(value = "id") long id, Principal p) {
        Video v = videos.findById(id);
        String username = p.getName();

        if (v == null) throw new ResourceNotFoundException();
        if (v.isUser(username)) throw new BadRequestException();

        v.addUser(username);
        videos.save(v);

    }

    @RequestMapping(value = VIDEO_SVC_PATH + "/{id}/unlike", method = RequestMethod.POST)
    public
    @ResponseBody
    void unlikeVideo(@PathVariable(value = "id") long id, Principal p) {
        Video v = videos.findById(id);
        String username = p.getName();

        if (v == null) throw new ResourceNotFoundException();
        if (!v.isUser(username)) throw new BadRequestException();

        v.removeUser(username);
        videos.save(v);

    }

    @RequestMapping(value = VIDEO_SVC_PATH + "/{id}/likedby", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getUsersWhoLikedVideo(@PathVariable(value = "id") long id) {
        Video v = videos.findById(id);

        if (v == null) throw new ResourceNotFoundException();
        return v.getUsers();
    }

    @RequestMapping(value = VIDEO_SVC_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    Video addVideo(@RequestBody Video v) {
        Video returnedVideo = videos.save(v);
        return returnedVideo;
    }

}
