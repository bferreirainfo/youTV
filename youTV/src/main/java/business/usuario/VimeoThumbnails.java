package business.usuario;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class VimeoThumbnails {
    @SerializedName(value = "thumbnail")
    List<Thumbnail> thumbnails;

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public Thumbnail getMediumThumbail() {
        return thumbnails.get(2);
    }
}
