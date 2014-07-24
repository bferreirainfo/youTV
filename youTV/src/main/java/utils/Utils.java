package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;

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
        DateTime publishedAt = video.getSnippet().getPublishedAt();
        PrettyTime t = new PrettyTime();
        t.setLocale(new Locale("pt", "BR"));

        return t.format(new Date(publishedAt.getValue()));
    }

    public static int percentage(Float amount, Float total) {
        int result = new Float(Math.ceil((amount * 100) / total)).intValue();
        return result;
    }

    public static int[] calculateLikesAndDislikesPercentage(float likes, float dislikes) {
        int[] result = new int[2];
        int percentage = percentage(likes, likes + dislikes);
        result[0] = percentage;
        result[1] = percentage == 0 ? 0 : 100 - percentage;
        return result;
    }

    public static String obtainFormatVimeoVideoDate(String uploadDate) {
        PrettyTime t = new PrettyTime();
        t.setLocale(new Locale("pt", "BR"));
        String result;
        try {
            result = t.format(formatDate(uploadDate));
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

    public static Date formatDate(String data) throws Exception {
        if (data == null || data.equals(""))
            return null;

        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = (java.util.Date) formatter.parse(data);
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }
}
