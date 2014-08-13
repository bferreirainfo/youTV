package business.usuario;

import java.util.ArrayList;
import java.util.List;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.VimeoApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.gson.GsonBuilder;

public class VimeoService {
    private static final String EMPTY_STRING = "";
    private static String apiKey = "71afa97b8a3bb6f112547127d1a3bb7872719870";
    private static String apiSecret = "eb19410d9314411e69ab69bf4ba14a3755fb0354";
    private static OAuthService service;
    private static Token mytoken;
    static {
        oathBuild();
    }

    public static ItemView loadVideoByIdWithRelated(String vimeoVideoId) {
        OAuthRequest request = createFindVideoByIdRequest(vimeoVideoId);
        service = oathBuild();
        service.signRequest(mytoken, request);
        Response response = request.send();
        VimeoVideo vimeoVideo =
                new GsonBuilder().create()
                        .fromJson(response.getBody(), VimeoVideoSearchResult.class).getVideo()[0];
        ItemView videoView = new ItemView(vimeoVideo);
        loadRelatedVideos(videoView);
        return videoView;
    }

    public static List<ItemView> searchVideos(String term) {
        List<ItemView> videos = new ArrayList<ItemView>();
        try {
            OAuthRequest myrequest = createSearchRequest(term);
            service = oathBuild();
            service.signRequest(mytoken, myrequest);
            Response response = myrequest.send();
            VimeoVideoSearchResult vimeoVideoSearchResult =
                    new GsonBuilder().create().fromJson(response.getBody(),
                            VimeoVideoSearchResult.class);

            for (VimeoVideo vimeoVideo : vimeoVideoSearchResult.getVideos().getVideos()) {
                videos.add(new ItemView(vimeoVideo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos;
    }

    private static OAuthRequest createSearchRequest(String term) {
        OAuthRequest myrequest =
                new OAuthRequest(
                        Verb.GET,
                        "http://vimeo.com/api/rest/v2?format=json&method=vimeo.videos.search&full_response=true&page=1&per_page=10&query="
                                + term.replace(" ", "+"));
        return myrequest;
    }

    private static OAuthRequest createFindVideoByIdRequest(String videoId) {
        OAuthRequest myrequest =
                new OAuthRequest(Verb.GET,
                        "http://vimeo.com/api/rest/v2?format=json&method=vimeo.videos.getInfo&video_id="
                                + videoId);
        return myrequest;
    }

    private static OAuthService oathBuild() {
        OAuthService service =
                new ServiceBuilder().provider(VimeoApi.class).apiKey(apiKey).apiSecret(apiSecret)
                        .build();
        // Replace these with your own api key and secret
        mytoken = new Token(EMPTY_STRING, EMPTY_STRING);
        return service;
    }

    public static void loadRelatedVideos(ItemView videoView) {
        StringBuilder searchTerm = new StringBuilder();
        //create an searchTerm with the first two words
        String[] titleWords = videoView.getTitle().split(" ");
        for (int i = 0; (i < titleWords.length && i <= 1); i++) {
            if (i == 1) {
                searchTerm.append('+');
            }
            searchTerm.append(titleWords[i]);
        }
        videoView.setRelatedVideos(searchVideos(searchTerm.toString()));
        videoView.getRelatedVideos().remove(videoView);

    }
}
