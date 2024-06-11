package com.ichirinko.luce.service.implement;

import com.ichirinko.luce.mapper.VideoMapper;
import com.ichirinko.luce.model.Comment;
import com.ichirinko.luce.model.Video;
import com.ichirinko.luce.service.VideoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImplement implements VideoService {
    private final VideoMapper videoMapper;

    public VideoServiceImplement(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }

    @Override
    public List<Video> getAllVideo() {
        return videoMapper.getAllVideo();
    }

    @Override
    public Video getVideoById(int id) {
        return videoMapper.getVideoById(id);
    }

    @Override
    public void updateVideoPlayTimes(int id) {
        videoMapper.updateVideoPlayTimes(id);
    }

    @Override
    public void createCommentsTable(int id) {
        videoMapper.createCommentsTable("comments_"+id);
    }

    @Override
    public List<Comment> getComments(int id) {
        return videoMapper.getComments("comments_"+id);
    }

    @Override
    public void addComment(int id, String comment, int userId) {
        videoMapper.addComment("comments_"+id, comment, userId);
    }

    @Override
    public void addVideo(Video video) {
        videoMapper.addVideo(video);
    }

    @Override
    public void updateVideo(Video video) {
        videoMapper.updateVideo(video);
    }

}
