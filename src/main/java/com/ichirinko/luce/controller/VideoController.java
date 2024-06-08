package com.ichirinko.luce.controller;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.IoUtil;
import com.ichirinko.luce.dto.ResponseDTO;
import com.ichirinko.luce.model.Video;
import com.ichirinko.luce.service.VideoService;
import com.ichirinko.luce.utils.FFmpegProcessor;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


@RestController
@RequestMapping("api/video")
public class VideoController {

    private final VideoService videoService;
    /**
     * 目录路径,这个路径需要包含test.info文件，test.key文件和test.mp4文件
     */
    @Autowired
    private ServerProperties serverProperties;
    private InetAddress localHost = InetAddress.getLocalHost();

    private static final String PATH = "D:\\luce\\videos\\";

    public VideoController(VideoService videoService) throws UnknownHostException {
        this.videoService = videoService;
    }


    @PostMapping("/upload")
    public void uploadToM3u8(HttpServletRequest request) throws Exception {
        String prefixUrl = "http://" + localHost.getHostAddress() +":"+ serverProperties.getPort();
        // FileInputStream inputStream = new FileInputStream(PATH + fileName);
        FileInputStream inputStream = new FileInputStream(PATH + "2/2.mp4");
        String m3u8Url = prefixUrl +  "/api/video/saveAsM3u8/" + "2/2.m3u8";
        String infoUrl = prefixUrl +  "/api/video/generateM3u8File/" + "2/2.info";
        FFmpegProcessor.convertMediaToM3u8ByHttp(inputStream, m3u8Url, infoUrl, prefixUrl);
    }

    @PostMapping("/saveAsM3u8/**")
    @ResponseBody
    public void upload(HttpServletRequest request) throws IOException {
        String requestURL = request.getRequestURL().toString();
        String fileName = requestURL.split("/saveAsM3u8/",2)[1];
        ServletInputStream inputStream = request.getInputStream();
        FileWriter writer = new FileWriter(PATH + fileName);
        writer.writeFromStream(inputStream);
        IoUtil.close(inputStream);
    }

    /**
     * 预览加密文件
     */
    @PostMapping("/generateM3u8File/**")
    @ResponseBody
    public void preview(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String requestURL = request.getRequestURL().toString();
        String fileName = requestURL.split("/generateM3u8File/",2)[1];
        FileReader fileReader = new FileReader(PATH + fileName);
        fileReader.writeToStream(response.getOutputStream());
    }

    /**
     * 下载加密文件
     */
    @GetMapping("/download/**")
    @ResponseBody
    public void download(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String requestURL = request.getRequestURL().toString();
        String fileName = requestURL.split("/download/",2)[1];
        FileReader fileReader = new FileReader(PATH + fileName);
        fileReader.writeToStream(response.getOutputStream());
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<Video>>> getAllVideo() throws IOException {
        var videos = videoService.getAllVideo();

        if(videos.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>("视频列表为空",null), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ResponseDTO<>("获取全部视频列表成功",videos),HttpStatus.OK);
        }
    }
}