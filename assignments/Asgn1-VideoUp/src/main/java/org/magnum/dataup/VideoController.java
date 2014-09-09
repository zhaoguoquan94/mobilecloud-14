/*
 * 
 * Copyright 2014 Jules White
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
package org.magnum.dataup;

import org.apache.commons.io.IOExceptionWithCause;
import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.magnum.dataup.repo.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

@Controller
public class VideoController {

	/**
	 * You will need to create one or more Spring controllers to fulfill the
	 * requirements of the assignment. If you use this file, please rename it
	 * to something other than "VideoController"
	 * 
	 * 
		 ________  ________  ________  ________          ___       ___  ___  ________  ___  __       
		|\   ____\|\   __  \|\   __  \|\   ___ \        |\  \     |\  \|\  \|\   ____\|\  \|\  \     
		\ \  \___|\ \  \|\  \ \  \|\  \ \  \_|\ \       \ \  \    \ \  \\\  \ \  \___|\ \  \/  /|_   
		 \ \  \  __\ \  \\\  \ \  \\\  \ \  \ \\ \       \ \  \    \ \  \\\  \ \  \    \ \   ___  \  
		  \ \  \|\  \ \  \\\  \ \  \\\  \ \  \_\\ \       \ \  \____\ \  \\\  \ \  \____\ \  \\ \  \ 
		   \ \_______\ \_______\ \_______\ \_______\       \ \_______\ \_______\ \_______\ \__\\ \__\
		    \|_______|\|_______|\|_______|\|_______|        \|_______|\|_______|\|_______|\|__| \|__|
                                                                                                                                                                                                                                                                        
	 * 
	 */
    @Autowired
    private VideoRepo videos;
    @RequestMapping(value="/video",method = RequestMethod.GET)
    public @ResponseBody Collection<Video> getVideoList() {

        return videos.getVideos();
    }
    @RequestMapping(value = "/video",method = RequestMethod.POST)
    public @ResponseBody Video addVideo(@RequestBody Video v) {
        v.setId((v.hashCode()+new Date().hashCode()+1)>0?(v.hashCode()+new Date().hashCode()+1):-(v.hashCode()+new Date().hashCode()+1));
        v.setDataUrl("http://localhost:8080/video/" + v.getId() + "/data");
        videos.addVideo(v);
        return v;
    }
    @RequestMapping(value = "/video/{id}/data",method = RequestMethod.POST)
    public @ResponseBody VideoStatus setVideoData(@PathVariable long id, @RequestParam MultipartFile data,HttpServletResponse response) {
        boolean found=false;
        for (Video v : videos.getVideos()) {
            if (v.getId() == id) {
                found=true;
                break;
            }
        }
        if (!found) {
            response.setStatus(404);

        }


        InputStream in;
        FileOutputStream fw;
        try {
            in = data.getInputStream();
            fw = new FileOutputStream(String.valueOf(id));
            byte[] b = new byte[1024 * 1024];
            int size = 0;
            while ((size = in.read(b)) != -1) {
                fw.write(b, 0, size);
            }
            in.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new VideoStatus(VideoStatus.VideoState.READY);


    }
    @RequestMapping(value = "/video/{id}/data",method = RequestMethod.GET)
    public void getData(@PathVariable long id,HttpServletResponse response) {
        boolean found=false;
        for (Video v : videos.getVideos()) {
            if (v.getId() == id) {
                found=true;
                break;
            }
        }
        if (!found) {
            response.setStatus(404);

        }
        FileInputStream in=null;
        OutputStream os;
        try {
            in = new FileInputStream(String.valueOf(id));
            os = response.getOutputStream();
            byte[] b = new byte[1024*1024];
            int size=0;
            while ((size = in.read(b)) != -1) {
                os.write(b, 0, size);
            }
            in.close();
            os.close();


        } catch (FileNotFoundException e) {
            response.setStatus(404);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public void setVideos(VideoRepo videos) {
        this.videos = videos;
    }

    public VideoRepo getVideos() {
        return videos;

    }

}
