package business.usuario;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class VimeoVideos {
    //"on_this_page":"50","page":"1","perpage":"50","total":"421901","video":[{
    @SerializedName(value = "on_this_page")
    private String totalVideosThisPage;
    @SerializedName(value = "page")
    private String totalPages;
    @SerializedName(value = "perpage")
    private String perPage;
    @SerializedName(value = "total")
    private String total;
    @SerializedName(value = "video")
    private List<VimeoVideo> videos;

    public String getTotalVideosThisPage() {
        return totalVideosThisPage;
    }

    public void setTotalVideosThisPage(String totalVideosThisPage) {
        this.totalVideosThisPage = totalVideosThisPage;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getPerPage() {
        return perPage;
    }

    public void setPerPage(String perPage) {
        this.perPage = perPage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<VimeoVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<VimeoVideo> videos) {
        this.videos = videos;
    }

}
