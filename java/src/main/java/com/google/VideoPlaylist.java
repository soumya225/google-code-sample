package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist implements Comparable<VideoPlaylist> {
    private final String title;
    private final List<Video> videos;

    public VideoPlaylist(String title) {
        this.title = title;
        this.videos = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public List<Video> getVideos() {
        return videos;
    }

    @Override
    public int compareTo(VideoPlaylist o) {
        return this.title.compareTo(o.title);
    }
}
