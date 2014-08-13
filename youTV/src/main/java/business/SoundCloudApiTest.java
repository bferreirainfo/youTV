package business;

import java.util.ArrayList;

import de.voidplus.soundcloud.SoundCloud;
import de.voidplus.soundcloud.Track;

public class SoundCloudApiTest {
    //https://apigee.com/embed/console/soundcloud test endPoints
    public static void main(String[] args) {
        SoundCloud soundcloud =
                new SoundCloud("4396efca860e926e3b214b8fc15e5ebc",
                        "3fd42ccfaa15b351acc804ae3be79034", "bferreira.info@gmail.com", "thunder");

        ArrayList<Track> result = soundcloud.findTrack("the hades rage");
        for (Track track : result) {
            System.out.println("music name: " + track.getDescription());
            System.out.println("music id: " + track.getId());
        }
    }
}
