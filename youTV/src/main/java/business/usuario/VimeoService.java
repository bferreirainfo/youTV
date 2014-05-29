package business.usuario;

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

    public static VimeoVideo getVideoById(String vimeoVideoId) {
        OAuthRequest request = createFindVideoByIdRequest(vimeoVideoId);
        service = oathBuild();
        service.signRequest(mytoken, request);
        Response response = request.send();
        return new GsonBuilder().create()
                .fromJson(response.getBody(), VimeoVideoSearchResult.class).getVideo()[0];
    }

    public static VimeoVideoSearchResult searchVideos(String term) {
        OAuthRequest myrequest = createSearchRequest(term);
        service = oathBuild();
        service.signRequest(mytoken, myrequest);
        Response response = myrequest.send();
        return new GsonBuilder().create()
                .fromJson(response.getBody(), VimeoVideoSearchResult.class);
    }

    private static OAuthRequest createSearchRequest(String term) {
        OAuthRequest myrequest =
                new OAuthRequest(Verb.GET,
                        "http://vimeo.com/api/rest/v2?format=json&method=vimeo.videos.search&full_response=true&query="
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

}
