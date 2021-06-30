package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;

  private Video currentlyPlaying;
  private boolean isPaused;
  private List<VideoPlaylist> playlists;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.playlists = new ArrayList<>();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    List<Video> sortedVideos = videoLibrary.getVideos();
    Collections.sort(sortedVideos);
    System.out.println("Here's a list of all available videos:");
    for(Video video: sortedVideos) {
      showVideo(video);
    }
  }

  public void playVideo(String videoId) {
    Video videoToPlay = videoLibrary.getVideo(videoId);
    if(videoToPlay == null) {
      System.out.println("Cannot play video: Video does not exist");
      return;
    }
    if(videoToPlay.getFlag() != null) {
      System.out.println("Cannot play video: Video is currently flagged (reason: " + videoToPlay.getFlag() +")");
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

    boolean isAllFlagged = true;

    for(Video video: videoLibrary.getVideos()) {
      if(video.getFlag() == null) {
        isAllFlagged = false;
        break;
      }
    }

    if(videoLibrary.getVideos().isEmpty() || isAllFlagged) {
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
    boolean isUnique = true;

    for(VideoPlaylist playlist: playlists) {
      if(playlist.getTitle().equalsIgnoreCase(playlistName)) {
        isUnique = false;
        break;
      }
    }

    if(!isUnique) {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    } else {
      playlists.add(new VideoPlaylist(playlistName));
      System.out.println("Successfully created new playlist: " + playlistName);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    Video videoToBeAdded = videoLibrary.getVideo(videoId);

    VideoPlaylist playlistToAddTo = null;
    boolean videoExistsInPlaylist = false;

    for(VideoPlaylist playlist: playlists) {
      if(playlist.getTitle().equalsIgnoreCase(playlistName)) {
        playlistToAddTo = playlist;

        if(videoToBeAdded != null && videoToBeAdded.getFlag() != null) {
          break;
        }

        //Check if video is already added in playlist
        for(Video video: playlist.getVideos()) {
          if(video.equals(videoToBeAdded)) {
            System.out.println("Cannot add video to " + playlistName + ": Video already added");
            videoExistsInPlaylist = true;
            break;
          }
        }
        break;
      }
    }

    if(playlistToAddTo == null) {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
      return;
    }

    if(videoToBeAdded == null) {
      System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      return;
    }

    if(videoToBeAdded.getFlag() != null) {
      System.out.println("Cannot add video to " + playlistName + ": Video is currently flagged (reason: " +  videoToBeAdded.getFlag() +")");
      return;
    }

    if(!videoExistsInPlaylist) {
      playlistToAddTo.getVideos().add(videoToBeAdded);
      System.out.println("Added video to " + playlistName + ": " + videoToBeAdded.getTitle());
    }
  }

  public void showAllPlaylists() {
    if(playlists.size() == 0) {
      System.out.println("No playlists exist yet");
      return;
    }

    List<VideoPlaylist> sortedPlaylists = playlists;
    Collections.sort(sortedPlaylists);

    System.out.println("Showing all playlists:");
    for(VideoPlaylist playlist: sortedPlaylists) {
      System.out.println(playlist.getTitle());
    }
  }

  public void showPlaylist(String playlistName) {

    for(VideoPlaylist playlist: playlists) {
      if(playlist.getTitle().equalsIgnoreCase(playlistName)) {

        System.out.println("Showing playlist: " + playlistName);
        if(playlist.getVideos().size() == 0) {
          System.out.println("No videos here yet.");
        }
        for(Video video: playlist.getVideos()) {
          showVideo(video);
        }
        return;
      }
    }

    System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    Video videoToBeRemoved = videoLibrary.getVideo(videoId);

    VideoPlaylist playlistToRemoveFrom = null;
    boolean videoExistsInPlaylist = false;

    for(VideoPlaylist playlist: playlists) {
      if(playlist.getTitle().equalsIgnoreCase(playlistName)) {
        playlistToRemoveFrom = playlist;

        boolean removedVideo = playlist.getVideos().remove(videoToBeRemoved);
        if(removedVideo) {
          videoExistsInPlaylist = true;
          System.out.println("Removed video from " + playlistName + ": " + videoToBeRemoved.getTitle());
        }

        break;
      }
    }

    if(playlistToRemoveFrom == null) {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
      return;
    }

    if(videoToBeRemoved == null) {
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
      return;
    }

    if(!videoExistsInPlaylist) {
      System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
    }

  }

  public void clearPlaylist(String playlistName) {
    for(VideoPlaylist playlist: playlists) {
      if(playlist.getTitle().equalsIgnoreCase(playlistName)) {
        playlist.getVideos().clear();
        System.out.println("Successfully removed all videos from " + playlistName);
        return;
      }
    }
    System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
  }

  public void deletePlaylist(String playlistName) {
    for(VideoPlaylist playlist: playlists) {
      if(playlist.getTitle().equalsIgnoreCase(playlistName)) {
        playlists.remove(playlistName);
        System.out.println("Deleted playlist: " + playlistName);
        return;
      }
    }

    System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");

  }

  public void searchVideos(String searchTerm) {
    List<Video> matchingVideos = new ArrayList<>();

    for(Video video: videoLibrary.getVideos()) {
      if(video.getFlag() == null && video.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
        matchingVideos.add(video);
      }
    }

    if(matchingVideos.isEmpty()) {
      System.out.println("No search results for " + searchTerm);
      return;
    }

    System.out.println("Here are the results for " + searchTerm + ":");
    Collections.sort(matchingVideos);
    for(int i = 1; i <= matchingVideos.size(); i++) {
      System.out.println("\t" + i + ") " + matchingVideos.get(i-1));
    }
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
            "If your answer is not a valid number, we will assume it's a no.");

    try {
      Scanner in = new Scanner(System.in);
      int choice = in.nextInt();
      playVideo(matchingVideos.get(choice-1).getVideoId());
    } catch (Exception e) {

    }
  }

  public void searchVideosWithTag(String videoTag) {
    List<Video> matchingVideos = new ArrayList<>();

    for(Video video: videoLibrary.getVideos()) {
      if(video.getFlag() == null && video.getTags().contains(videoTag.toLowerCase())) {
        matchingVideos.add(video);
      }
    }

    if(matchingVideos.isEmpty()) {
      System.out.println("No search results for " + videoTag);
      return;
    }

    System.out.println("Here are the results for " + videoTag + ":");
    Collections.sort(matchingVideos);
    for(int i = 1; i <= matchingVideos.size(); i++) {
      System.out.println("\t" + i + ") " + matchingVideos.get(i-1));
    }
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
            "If your answer is not a valid number, we will assume it's a no.");

    try {
      Scanner in = new Scanner(System.in);
      int choice = in.nextInt();
      playVideo(matchingVideos.get(choice-1).getVideoId());
    } catch (Exception e) {

    }
  }

  public void flagVideo(String videoId) {
    flagVideo(videoId, "Not supplied");
  }

  public void flagVideo(String videoId, String reason) {
    Video videoToBeFlagged = videoLibrary.getVideo(videoId);
    if (videoToBeFlagged == null) {
      System.out.println("Cannot flag video: Video does not exist");
      return;
    }

    if(videoToBeFlagged.getFlag() == null) {
      if(currentlyPlaying != null && currentlyPlaying.equals(videoToBeFlagged)) {
        stopVideo();
      }

      videoToBeFlagged.setFlag(reason);
      System.out.println("Successfully flagged video: " + videoToBeFlagged.getTitle() + " (reason: " + reason + ")");
    } else {
      System.out.println("Cannot flag video: Video is already flagged");
    }

  }

  public void allowVideo(String videoId) {
    Video videoToBeUnFlagged = videoLibrary.getVideo(videoId);
    if (videoToBeUnFlagged == null) {
      System.out.println("Cannot remove flag from video: Video does not exist");
      return;
    }

    if(videoToBeUnFlagged.getFlag() == null) {
      System.out.println("Cannot remove flag from video: Video is not flagged");
      return;
    }

    videoToBeUnFlagged.setFlag(null);
    System.out.println("Successfully removed flag from video: " + videoToBeUnFlagged.getTitle());

  }


  private void showVideo(Video video) {
    StringBuilder builder = new StringBuilder();
    builder.append(video);
    if(video.getFlag() != null) {
      builder.append(" - FLAGGED (reason: ").append(video.getFlag()).append(")");
    }
    System.out.println(builder.toString());
  }
}