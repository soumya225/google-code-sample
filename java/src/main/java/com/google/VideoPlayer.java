package com.google;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video currentlyPlaying = null;
  private boolean isPaused;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    List<Video> sortedVideos = videoLibrary.getVideos();
    Collections.sort(sortedVideos);
    System.out.println("Here's a list of all available videos:");
    for(Video video: sortedVideos) {
      System.out.println(video);
    }
  }

  public void playVideo(String videoId) {
    Video videoToPlay = videoLibrary.getVideo(videoId);
    if(videoToPlay == null) {
      System.out.println("Cannot play video: Video does not exist");
      return;
    }
    if(currentlyPlaying != null) {
      System.out.println("Stopping video: " + currentlyPlaying.getTitle());
    }
    System.out.println("Playing video: " + videoToPlay.getTitle());
    currentlyPlaying = videoToPlay;
    isPaused = false;
  }

  public void stopVideo() {
    if(currentlyPlaying == null) {
      System.out.println("Cannot stop video: No video is currently playing\n");
      return;
    }

    System.out.println("Stopping video: " + currentlyPlaying.getTitle());
    currentlyPlaying = null;
    isPaused = false;
  }

  public void playRandomVideo() {
    int videoLibrarySize = videoLibrary.getVideos().size();

    if(videoLibrarySize == 0) {
      System.out.println("No videos available");
      return;
    }

    Random rand = new Random();
    Video randomVideo = videoLibrary.getVideos().get(rand.nextInt(videoLibrarySize));
    playVideo(randomVideo.getVideoId());
  }

  public void pauseVideo() {
    if(currentlyPlaying == null) {
      System.out.println("Cannot pause video: No video is currently playing");
      return;
    }

    if(isPaused) {
      System.out.println("Video already paused: " + currentlyPlaying.getTitle());
      return;
    }

    System.out.println("Pausing video: " + currentlyPlaying.getTitle());
    isPaused = true;
  }

  public void continueVideo() {
    if(currentlyPlaying == null) {
      System.out.println("Cannot continue video: No video is currently playing");
      return;
    }

    if(!isPaused) {
      System.out.println("Cannot continue video: Video is not paused");
      return;
    }

    System.out.println("Continuing video: " + currentlyPlaying.getTitle());
    isPaused = false;
  }

  public void showPlaying() {
    if(currentlyPlaying == null) {
      System.out.println("No video is currently playing");
      return;
    }

    String toPrint = "Currently playing: " + currentlyPlaying.toString();
    if(isPaused) toPrint += " - PAUSED";

    System.out.println(toPrint);
  }

  public void createPlaylist(String playlistName) {
    System.out.println("createPlaylist needs implementation");
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    System.out.println("addVideoToPlaylist needs implementation");
  }

  public void showAllPlaylists() {
    System.out.println("showAllPlaylists needs implementation");
  }

  public void showPlaylist(String playlistName) {
    System.out.println("showPlaylist needs implementation");
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}