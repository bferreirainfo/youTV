package presentation;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import business.usuario.VimeoService;
import business.usuario.VimeoVideoSearchResult;

import com.google.api.services.samples.youtube.cmdline.data.MyUploads;
import com.google.api.services.samples.youtube.cmdline.data.Search;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.SearchResult;

@Controller
@Scope(WebApplicationContext.SCOPE_APPLICATION)
public class PesquisaBean {
    private String searchTerm;
    private List<SearchResult> resultadoPesquisa;
    private List<PlaylistItem> myUploads;
    private VimeoVideoSearchResult searchVideos;
    private String youtubeVideoId;
    private String vimeoVideoId;
    private static final String FACES_REDIRECT = "?faces-redirect=true";

    public void carregarDadosVimeo() {
    }

    public void carregarDadosYoutube() {

    }

    public String consultarPagina() {
        return "/template/devoops/index.faces?youtube=" + youtubeVideoId + FACES_REDIRECT;
    }

    public String consultarVimeo() {
        return "index.faces" + FACES_REDIRECT;
    }

    public void pesquisar() {
        resultadoPesquisa = Search.pesquisar(searchTerm);
        searchVideos = VimeoService.searchVideos(searchTerm);
        List<SearchResult> aux = new ArrayList<SearchResult>();
        for (SearchResult item : resultadoPesquisa) {
            if ("youtube#video".equals(item.getId().getKind())) {
                aux.add(item);
            }
        }
        resultadoPesquisa = aux;
    }

    public void onTabChange(TabChangeEvent event) {
        if ("tab3".equals(event.getTab().getId())) {
            obtainMyUploads();
        }
    }

    public void obtainMyUploads() {
        myUploads = MyUploads.myUploads();
    }

    public List<PlaylistItem> getMyUploads() {
        return myUploads;
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg =
                new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        //        UploadVideo.upload(event.getFile());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String termoPesquisa) {
        this.searchTerm = termoPesquisa;
    }

    public List<SearchResult> getResultadoPesquisa() {
        return resultadoPesquisa;
    }

    public void setResultadoPesquisa(List<SearchResult> resultadoPesquisa) {
        this.resultadoPesquisa = resultadoPesquisa;
    }

    public VimeoVideoSearchResult getSearchVideos() {
        return searchVideos;
    }

    public void setSearchVideos(VimeoVideoSearchResult searchVideos) {
        this.searchVideos = searchVideos;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public String getVimeoVideoId() {
        return vimeoVideoId;
    }

    public void setVimeoVideoId(String vimeoVideoId) {
        this.vimeoVideoId = vimeoVideoId;
    }

}
