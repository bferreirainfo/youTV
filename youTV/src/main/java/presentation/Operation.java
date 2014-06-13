package presentation;

public enum Operation {
    playYoutube, playVimeo, search;

    public boolean isPlayYoutube() {
        return this.equals(playYoutube);
    }

    public boolean isPlayVimeo() {
        return this.equals(playVimeo);
    }

    public boolean isSearch() {
        return this.equals(search);
    }

    public boolean isPlaying() {
        boolean result = false;
        if (isPlayYoutube() || isPlayVimeo()) {
            result = true;
        }
        return result;
    }
}
