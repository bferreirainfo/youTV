package presentation;

public enum Operation {
    playYoutube, playVimeo, search;

    public boolean isPlayYoutube() {
        return this.equals(playYoutube);
    }

    public boolean isSearch() {
        return this.equals(search);
    }

}
