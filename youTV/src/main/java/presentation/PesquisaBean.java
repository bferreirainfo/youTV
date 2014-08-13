package presentation;

import java.util.List;

import javax.faces.bean.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import business.SoundCloundService;
import business.YoutubeService;
import business.usuario.ItemView;
import business.usuario.VimeoService;

@Controller
@ManagedBean
@Scope("session")
public class PesquisaBean {
    //Search Term
    private String searchTerm;

    //Carrousel entities
    private List<ItemView> youTubeVideosSearchResult;
    private List<ItemView> vimeoVideosSearchResult;

    //VideoView attributes
    private String videoViewId;
    private Operation operation;
    private ItemView videoView;
    private ItemView relatedVideoView;
    private List<ItemView> soundCloudMusicsSearchResult;

    public void setOperationToDefault() {
        operation = Operation.search;
        clearValues();
    }

    //This method is need until the aplication scope is fixed
    private void clearValues() {
        searchTerm = null;
        //for develop porpuse dont clear carrousel entities
        //        youTubeVideosSearchResult.clear();
        //        vimeoVideosSearchResult.clear();
    }

    public void loadMiniplayerYoutubeVideo() {
        operation = Operation.playYoutube;
    }

    public void loadMiniplayerVimeoVideo() {
        operation = Operation.playVimeo;
    }

    public void loadRelatedVideoView() {
        videoView = relatedVideoView;
        if (videoView.isYoutubeVideo()) {
            YoutubeService.loadRelatedVideos(videoView);
        } else if (videoView.isVimeoVideo()) {
            VimeoService.loadRelatedVideos(videoView);
        }
    }

    public void showPerfil() {
    }

    public void loadVimeoVideo() {
        videoView = localLoad(vimeoVideosSearchResult);
        if (videoView == null) {
            videoView = VimeoService.loadVideoByIdWithRelated(videoViewId);
        } else if (videoView.getRelatedVideos() == null) {
            VimeoService.loadRelatedVideos(videoView);
        }
        operation = Operation.playVimeo;
        clearValues();
    }

    public void loadYoutubeVideo() {
        videoView = localLoad(youTubeVideosSearchResult);
        if (videoView == null) {
            videoView = YoutubeService.loadVideoByIdWithRelated(videoViewId);
        } else if (videoView.getRelatedVideos() == null) {
            YoutubeService.loadRelatedVideos(videoView);
        }
        operation = Operation.playYoutube;
        clearValues();
    }

    private ItemView localLoad(List<ItemView> localVideos) {
        ItemView result = null;
        if (localVideos != null) {
            for (ItemView videoView : localVideos) {
                if (videoView.getId().equals(videoViewId)) {
                    result = videoView;
                    break;
                }
            }
        }
        return result;
    }

    public void pesquisar() {
        try {
            youTubeVideosSearchResult = YoutubeService.searchVideos(searchTerm);
            vimeoVideosSearchResult = VimeoService.searchVideos(searchTerm);
            soundCloudMusicsSearchResult = SoundCloundService.searchMusics(searchTerm);
            //            new SoundCloundService();
            System.out.println("youtube: " + youTubeVideosSearchResult.size());
            System.out.println("vimeo: " + vimeoVideosSearchResult.size());
            System.out.println("soundClound: " + soundCloudMusicsSearchResult.size());
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    //    public void onTabChange(TabChangeEvent event) {
    //        if ("tab3".equals(event.getTab().getId())) {
    //            obtainMyUploads();
    //        }
    //    }

    //    public void obtainMyUploads() {
    //        myUploads = MyUploads.myUploads();
    //    }
    //
    //    public List<PlaylistItem> getMyUploads() {
    //        return myUploads;
    //    }
    //
    //    public void handleFileUpload(FileUploadEvent event) {
    //        FacesMessage msg =
    //                new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
    //        //        UploadVideo.upload(event.getFile());
    //        FacesContext.getCurrentInstance().addMessage(null, msg);
    //    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String termoPesquisa) {
        this.searchTerm = termoPesquisa;
    }

    public List<ItemView> getYouTubeVideosSearchResult() {
        return youTubeVideosSearchResult;
    }

    public void setYouTubeVideosSearchResult(List<ItemView> resultadoPesquisa) {
        this.youTubeVideosSearchResult = resultadoPesquisa;
    }

    public List<ItemView> getVimeoVideosSearchResult() {
        return vimeoVideosSearchResult;
    }

    public void setVimeoVideosSearchResult(List<ItemView> searchVideos) {
        this.vimeoVideosSearchResult = searchVideos;
    }

    public String getVideoViewId() {
        return videoViewId;
    }

    public void setVideoViewId(String videoViewId) {
        this.videoViewId = videoViewId;
    }

    public ItemView getVideoView() {
        return videoView;
    }

    public void setVideoView(ItemView videoView) {
        this.videoView = videoView;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public ItemView getRelatedVideoView() {
        return relatedVideoView;
    }

    public void setRelatedVideoView(ItemView relatedVideoView) {
        this.relatedVideoView = relatedVideoView;
    }

    public List<ItemView> getSoundCloudMusicsSearchResult() {
        return soundCloudMusicsSearchResult;
    }

    public void setSoundCloudMusicsSearchResult(List<ItemView> soundCloudMusicsSearchResult) {
        this.soundCloudMusicsSearchResult = soundCloudMusicsSearchResult;
    }
}
