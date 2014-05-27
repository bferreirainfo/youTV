package presentation;

public enum Operation {
    watching, searching;

    public boolean isWatching() {
        return this.equals(watching);
    }

    public boolean isSearching() {
        return this.equals(searching);
    }
}
