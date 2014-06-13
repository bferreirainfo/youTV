/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.services.samples.youtube.cmdline.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import business.usuario.VideoView;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.common.collect.Lists;

/**
 * Print a list of videos matching a search term.
 * 
 * @author Jeremy Walker
 */
public class YoutubeService {

    private static com.google.api.services.youtube.YouTube.Videos.List videosList;
    /**
     * Define a global variable that identifies the name of a file that contains the developer's
     * API key.
     */
    private static final String PROPERTIES_FILENAME = "youtube.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;

    /**
     * Define a global instance of a Youtube object, which will be used to make YouTube Data API
     * requests.
     */
    private static YouTube youtube;

    public static VideoView getVideoById(String videoId) {
        VideoView result = null;
        try {
            videosList.setId(videoId);
            List<Video> items = videosList.execute().getItems();
            result = items.isEmpty() ? null : new VideoView(items.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static List<VideoView> searchVideos(String queryTerm) {
        Properties properties = new Properties();
        try {
            InputStream in = YoutubeService.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": "
                    + e.getCause() + " : " + e.getMessage());
            System.exit(1);
        }

        try {
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.
            youtube =
                    new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY,
                            new HttpRequestInitializer() {
                                public void initialize(HttpRequest request) throws IOException {
                                }
                            }).setApplicationName("youtube-cmdline-search-sample").build();

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(queryTerm);
            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/videoId)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            // Call the API with Ids
            SearchListResponse searchResponse = search.execute();

            StringBuilder videosList = new StringBuilder();
            for (SearchResult item : searchResponse.getItems()) {
                videosList.insert(0, "," + item.getId().getVideoId());
            }
            return findVideoListByIds(videosList.toString());
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: 12" + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    private static List<VideoView> findVideoListByIds(String ids) throws IOException {
        List<VideoView> youtubeVideos = new ArrayList<VideoView>();
        for (Video video : videosList.setId(ids).execute().getItems()) {
            youtubeVideos.add(new VideoView(video));
        }
        return youtubeVideos;
    }

    static {
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        Credential credential = null;
        
        try {
            credential = Auth.authorize(scopes, "uploadvideo");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // This object is used to make YouTube Data API requests.
        com.google.api.services.youtube.YouTube youtube =
                new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                        .setApplicationName("youtv1988").build();

        try {
            videosList = youtube.videos().list("statistics,snippet,contentDetails");
            videosList
                    .setFields("items(id,statistics/viewCount,statistics/likeCount,statistics/dislikeCount,snippet/title,snippet/thumbnails/medium/url,snippet/publishedAt,contentDetails/duration)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
