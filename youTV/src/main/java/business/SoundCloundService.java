package business;

import java.util.ArrayList;
import java.util.List;

import business.usuario.ItemView;
import de.voidplus.soundcloud.SoundCloud;
import de.voidplus.soundcloud.Track;

public class SoundCloundService {

    private static SoundCloud soundcloud = new SoundCloud("4396efca860e926e3b214b8fc15e5ebc",
            "3fd42ccfaa15b351acc804ae3be79034", "bferreira.info@gmail.com", "thunder");

    public static List<ItemView> searchMusics(String searchTerm) {
        List<ItemView> result = new ArrayList<ItemView>();
        for (Track track : soundcloud.findTrack(searchTerm)) {
            result.add(new ItemView(track));
        }
        return result;
    }

}
