package com.ichirinko.luce.mapper;

import com.ichirinko.luce.model.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoMapper {
    List<Video> getAllVideo();
}
