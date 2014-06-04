package utils;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.Video;

public class Utils {

    public static String formatNumberWithDots(String number) {
        StringBuilder result = new StringBuilder();
        if (number != null) {
            int cont = 0;
            for (char digit : new StringBuilder(number).reverse().toString().toCharArray()) {
                if (cont == 3) {
                    result.insert(0, ".");
                    cont = 0;
                }
                cont++;
                result.insert(0, digit);
            }

        } else {
            result.append("0");
        }
        return result.toString();
    }

    public static String obtainFormatYoutubeVideoDate(Video video) {
        String date = "";
        DateTime publishedAt = video.getSnippet().getPublishedAt();
        for (String datePart : publishedAt.toString().substring(0, 10).split("-")) {
            date = datePart + (date != "" ? "/" + date : "");
        }
        return date;
    }

}
