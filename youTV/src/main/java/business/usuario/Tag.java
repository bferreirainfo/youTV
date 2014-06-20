package business.usuario;

import com.google.gson.annotations.SerializedName;

public class Tag {
    @SerializedName(value = "normalized")
    private String normalized;

    public String getNormalized() {
        return normalized;
    }

    public void setNormalized(String normalized) {
        this.normalized = normalized;
    }

}
