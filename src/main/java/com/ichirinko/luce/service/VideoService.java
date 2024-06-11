package com.ichirinko.luce.service;

import com.ichirinko.luce.model.Comment;
import com.ichirinko.luce.model.Video;
import java.util.List;

public interface VideoService {
    List<Video> getAllVideo();
    Video getVideoById(int id);
    void updateVideoPlayTimes(int id);
    void createCommentsTable(int id);
    List<Comment> getComments(int id);
    void addComment(int id, String comment, int userId);
    void addVideo(Video video);
    void updateVideo(Video video);
}
