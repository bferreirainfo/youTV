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

import com.google.api.services.samples.youtube.cmdline.data.MyUploads;
import com.google.api.services.samples.youtube.cmdline.data.Search;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.SearchResult;

@Controller
@Scope(WebApplicationContext.SCOPE_APPLICATION)
public class PesquisaBean {
    private String termoPesquisa;
    private List<SearchResult> resultadoPesquisa;
    private List<PlaylistItem> myUploads;

    public void pesquisar() {
        resultadoPesquisa = Search.pesquisar(termoPesquisa);
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

    public String getTermoPesquisa() {
        return termoPesquisa;
    }

    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }

    public List<SearchResult> getResultadoPesquisa() {
        return resultadoPesquisa;
    }

    public void setResultadoPesquisa(List<SearchResult> resultadoPesquisa) {
        this.resultadoPesquisa = resultadoPesquisa;
    }
}
