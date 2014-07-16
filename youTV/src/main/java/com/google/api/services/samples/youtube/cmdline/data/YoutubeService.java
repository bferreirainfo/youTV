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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import business.PlayListView;
import business.usuario.VideoView;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
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

    private static com.google.api.services.youtube.YouTube.Videos.List youtubeVideosListBydId;
    /**
     * Define a global variable that identifies the name of a file that contains the developer's
     * API key.
     */
    private static final String PROPERTIES_FILENAME = "youtube.properties";
    private static Properties properties;

    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;

    /**
     * Define a global instance of a Youtube object, which will be used to make YouTube Data API
     * requests.
     */
    private static YouTube youtube;

    public static void main(String[] args) throws IOException {
        //método para testes
        com.google.api.services.youtube.YouTube.Playlists.List listService =
                youtube.playlists().list("snippet");
        listService.setId("UCVtFOytbRpEvzLjvqGG5gxQ");
        listService.setFields("items(id,snippet/title,snippet/description)");

        Map<PlayListView, List<VideoView>> playListVideosMap =
                new HashMap<PlayListView, List<VideoView>>();

        StringBuilder idsList = new StringBuilder();

        for (Playlist playList : listService.execute().getItems()) {
            PlayListView playListView = new PlayListView();
            playListView.setTitle(playList.getSnippet().getTitle());
            playListView.setDescription(playList.getSnippet().getDescription());
            playListView.setId(playList.getId());
            idsList.append(playList.getId());
            if (idsList.length() > 0)
                idsList.append(",");
            playListVideosMap.put(playListView, null);
        }
        com.google.api.services.youtube.YouTube.PlaylistItems.List playsLIstService =
                youtube.playlistItems().list("snippet").setPlaylistId(idsList.toString());
        playsLIstService
                .setFields("items(id,snippet/title,snippet/description,snippet/thumbnails/medium/url,snippet/playlistId)");
        for (PlaylistItem playlistItem : playsLIstService.execute().getItems()) {
            PlayListView playListView = new PlayListView();
            playListView.setId(playlistItem.getSnippet().getPlaylistId());
            playListVideosMap.get(playListView).add(new VideoView(playlistItem));

        }
        System.out.println(playsLIstService.execute());
    }

    public static void loadRelatedVideos(VideoView video) {
        if (video.isYoutubeVideo())
            try {
                StringBuilder relatedVideosIds = obtainRelatedVideosIds(video.getId());
                video.setRelatedVideos(findVideosByIds(relatedVideosIds.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        else {

        }
    }

    public static VideoView loadVideoByIdWithRelated(String videoId) {
        VideoView actualVideo = null;
        try {

            StringBuilder relatedVideosIds = obtainRelatedVideosIds(videoId);
            //insert the original video on the query at position 0.
            String ids = relatedVideosIds.insert(0, videoId + ",").toString();
            List<VideoView> findResult = findVideosByIds(ids);
            //the actual video is at position 0, after that, all others videos are related to this. 
            actualVideo = findResult.get(0);
            //remove this video to get only related
            findResult.remove(actualVideo);
            actualVideo.setRelatedVideos(findResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return actualVideo;

    }

    private static StringBuilder obtainRelatedVideosIds(String videoId) throws IOException {
        com.google.api.services.youtube.YouTube.Search.List listService =
                youtube.search().list("id,snippet");
        listService.setRelatedToVideoId(videoId);
        listService.setType("video");
        // To increase efficiency, only retrieve the fields that the
        // application uses.
        listService.setFields("items(id/videoId)");
        listService.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
        SearchListResponse response = listService.execute();

        StringBuilder videosList = new StringBuilder();
        for (SearchResult item : response.getItems()) {
            videosList.insert(0, item.getId().getVideoId() + ",");
        }
        return videosList;
    }

    public static List<VideoView> searchVideos(String queryTerm) {

        try {
            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            //String apiKey = properties.getProperty("youtube.apikey");
            //search.setKey(apiKey);
            search.setQ(queryTerm);
            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/videoId)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            // Call the API with Ids
            return findVideosById(search.execute());
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

    private static List<VideoView> findVideosById(SearchListResponse searchResponse)
            throws IOException {
        StringBuilder videosList = new StringBuilder();
        for (SearchResult item : searchResponse.getItems()) {
            videosList.insert(0, "," + item.getId().getVideoId());
        }
        return findVideosByIds(videosList.toString());
    }

    /**
     * ids must be separeted by comma.
     * 
     * */
    private static List<VideoView> findVideosByIds(String ids) throws IOException {
        List<VideoView> youtubeVideos = new ArrayList<VideoView>();
        for (Video video : youtubeVideosListBydId.setId(ids).execute().getItems()) {
            youtubeVideos.add(new VideoView(video));
        }
        return youtubeVideos;
    }

    static {
        properties = new Properties();
        try {
            InputStream in = YoutubeService.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": "
                    + e.getCause() + " : " + e.getMessage());
            System.exit(1);
        }

    }
    static {
        youtube = obtainYoutubeService();
        loadYoutubeSearchVideosByIdConfig(youtube);
    }

    private static com.google.api.services.youtube.YouTube obtainYoutubeService() {
        Credential credential = obtainAutorization();
        // This object is used to make YouTube Data API requests.
        return constructYoutubeService(credential);
    }

    private static com.google.api.services.youtube.YouTube constructYoutubeService(
            Credential credential) {
        com.google.api.services.youtube.YouTube youtube =
                new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                        .setApplicationName("youtv").build();
        return youtube;
    }

    private static Credential obtainAutorization() {
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        Credential credential = null;

        try {
            credential = Auth.authorize(scopes, "uploadvideo");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return credential;
    }

    private static void loadYoutubeSearchVideosByIdConfig(
            com.google.api.services.youtube.YouTube youtube) {
        try {
            youtubeVideosListBydId = youtube.videos().list("statistics,snippet,contentDetails");
            youtubeVideosListBydId
                    .setFields("items(id,statistics/viewCount,statistics/likeCount,statistics/dislikeCount,snippet/title,snippet/description,snippet/channelTitle,snippet/thumbnails/medium/url,snippet/publishedAt,contentDetails/duration)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
