package business.usuario;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Tags {
    @SerializedName(value = "tag")
    List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
