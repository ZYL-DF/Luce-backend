package com.ichirinko.luce.service.implement;

import com.ichirinko.luce.mapper.VideoMapper;
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
}
