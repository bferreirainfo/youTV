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

    public static void main(String[] args) {
        searchVideos("fun");
    }

    public static VimeoVideoSearchResult searchVideos(String term) {
        // Replace these with your own api key and secret

        OAuthService service =
                new ServiceBuilder().provider(VimeoApi.class).apiKey(apiKey).apiSecret(apiSecret)
                        .build();

        OAuthRequest myrequest =
                new OAuthRequest(Verb.GET,
                        "http://vimeo.com/api/rest/v2?format=json&method=vimeo.videos.search&full_response=true&query="
                                + term.replace(" ", "+"));

        Token mytoken = new Token(EMPTY_STRING, EMPTY_STRING);
        service.signRequest(mytoken, myrequest);
        Response response = myrequest.send();
        return new GsonBuilder().create()
                .fromJson(response.getBody(), VimeoVideoSearchResult.class);
    }
}
