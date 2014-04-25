package business.usuario;

import com.google.gson.annotations.SerializedName;

public class Thumbnail {
    @SerializedName(value = "height")
    private String height;
    @SerializedName(value = "width")
    private String width;
    @SerializedName(value = "_content")
    private String content;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
