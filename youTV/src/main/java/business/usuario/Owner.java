package business.usuario;

import com.google.gson.annotations.SerializedName;

public class Owner {

    @SerializedName(value = "tags")
    private Tags tags;

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

}
