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

package org.magnum.mobilecloud.video.repository;

import org.magnum.mobilecloud.video.VideoSvc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(path = VideoSvc.VIDEO_SVC_PATH)
public interface VideoRepo extends CrudRepository<Video, Long> {

    // Find all videos with a matching title (e.g., Video.name)
    public Collection<Video> findByName(
            @Param(VideoSvc.TITLE_PARAMETER) String title);

    // Find all videos that are shorter than a specified duration
    public Collection<Video> findByDurationLessThan(
            // The @Param annotation tells tells Spring Data Rest which HTTP request
            // parameter it should use to fill in the "duration" variable used to
            // search for Videos
            @Param(VideoSvc.DURATION_PARAMETER) long maxduration);

    public Video findById(long id);

	/*
	 * See: http://docs.spring.io/spring-data/jpa/docs/1.3.0.RELEASE/reference/html/jpa.repositories.html 
	 * for more examples of writing query methods
	 */

}