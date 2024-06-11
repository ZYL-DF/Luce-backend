package com.ichirinko.luce.mapper;

import com.ichirinko.luce.model.Comment;
import com.ichirinko.luce.model.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoMapper {
    List<Video> getAllVideo();
    Video getVideoById(int id);
    void updateVideoPlayTimes(int id);
    void createCommentsTable(String tableName);
    List<Comment> getComments(String tableName);
    void addComment(String tableName, String comment, int userId);
    void addVideo(Video video);
    void updateVideo(Video video);
}
