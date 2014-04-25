package business.usuario;

import com.google.gson.annotations.SerializedName;

public class Tag {
    @SerializedName(value = "thumbnails")
    private VimeoThumbnails thumbnails;

    public VimeoThumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(VimeoThumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }
}
