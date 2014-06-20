package business.usuario;

import com.google.gson.annotations.SerializedName;

public class Owner {

    @SerializedName(value = "display_name")
    private String channelTitle;

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

}
