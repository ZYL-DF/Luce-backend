package com.ichirinko.luce.controller;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.IoUtil;
import com.ichirinko.luce.dto.CommentDTO;
import com.ichirinko.luce.dto.CommentResponseDTO;
import com.ichirinko.luce.dto.ResponseDTO;
import com.ichirinko.luce.model.Video;
import com.ichirinko.luce.service.UserService;
import com.ichirinko.luce.service.VideoService;
import com.ichirinko.luce.utils.FFmpegKey;
import com.ichirinko.luce.utils.FFmpegProcessor;
import com.ichirinko.luce.utils.LocalHostExactAddress;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("api/video")
public class VideoController {

    private final VideoService videoService;
    private final UserService userService;
    /**
     * 目录路径,这个路径需要包含info文件，key文件和mp4文件
     */
    @Autowired
    private ServerProperties serverProperties;
    private InetAddress localHost = LocalHostExactAddress.getLocalHostExactAddress();

    @Value("${server-file.src}")
    private String PATH;

    public VideoController(VideoService videoService, UserService userService) throws UnknownHostException {
        this.videoService = videoService;
        this.userService = userService;
    }


    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO<Void>> uploadToM3u8(@RequestParam("title") String title, @RequestParam("detail") String detail, @RequestParam("uploadUserId") int uploadUserId, @RequestParam("length") double length, @RequestParam("video") MultipartFile video, @RequestParam("cover") MultipartFile cover) throws Exception {
        try {
            // 数据库初始化新视频
            Video newVideo = new Video(0, "", "", title, detail, 0, new Date(), uploadUserId, new Time((long) (length * 1000 - 8 * 60 * 60 * 1000)));
            System.out.println(newVideo.getLength().toString());
            videoService.addVideo(newVideo);

            // 下载视频到本地
            String folderPath = newVideo.getId() + "/";
            File folder = new File(PATH + folderPath);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    System.out.println("创建文件夹失败");
                }
            }
            File videoFile = new File(PATH + folderPath + "video.mp4");
            video.transferTo(videoFile);
            File coverFile = new File(PATH + folderPath + "cover.png");
            cover.transferTo(coverFile);

            // 创建评论表
            videoService.createCommentsTable(newVideo.getId());

            // 创建切片需要的文件
            // String prefixUrl = "http://" + localHost.getHostAddress() + ":" + serverProperties.getPort();
            String prefixUrl = "http://47.115.214.246" + ":" + serverProperties.getPort();

            Files.writeString(Paths.get(PATH + folderPath + newVideo.getId() + ".info"),
                    prefixUrl + "/api/video/download/" + folderPath + newVideo.getId() + ".key\n" + prefixUrl + "/api/video/generateM3u8File/" + folderPath + newVideo.getId() + ".key");
            Files.writeString(Paths.get(PATH + folderPath + newVideo.getId() + ".key"),
                    "n4DHLX7kMPeewvW3");

            FileInputStream inputStream = new FileInputStream(PATH + folderPath + "video.mp4");
            String m3u8Url = prefixUrl + "/api/video/saveAsM3u8/" + folderPath + newVideo.getId() + ".m3u8";
            String infoUrl = prefixUrl + "/api/video/generateM3u8File/" + folderPath + newVideo.getId() + ".info";
            FFmpegProcessor.convertMediaToM3u8ByHttp(inputStream, m3u8Url, infoUrl, prefixUrl, newVideo.getId());
            Video preVideo = videoService.getVideoById(newVideo.getId());
            preVideo.setCoverUrl(folderPath + "cover.png");
            preVideo.setUrl(folderPath + newVideo.getId() + ".m3u8");
            videoService.updateVideo(preVideo);
            videoFile.delete();
            return new ResponseEntity<>(new ResponseDTO<>("上传视频成功"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDTO<>("上传视频失败"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveAsM3u8/**")
    @ResponseBody
    public void upload(HttpServletRequest request) throws IOException {
        String requestURL = request.getRequestURL().toString();
        String fileName = requestURL.split("/saveAsM3u8/", 2)[1];
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
    public void preview(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURL = request.getRequestURL().toString();
        String fileName = requestURL.split("/generateM3u8File/", 2)[1];
        FileReader fileReader = new FileReader(PATH + fileName);
        fileReader.writeToStream(response.getOutputStream());
    }

    /**
     * 下载加密文件
     */
    @GetMapping("/download/**")
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURL = request.getRequestURL().toString();
        String fileName = requestURL.split("/download/", 2)[1];
        FileReader fileReader = new FileReader(PATH + fileName);
        fileReader.writeToStream(response.getOutputStream());
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<Video>>> getAllVideo() throws IOException {
        var videos = videoService.getAllVideo();

        if (videos.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>("视频列表为空", videos), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO<>("获取全部视频列表成功", videos), HttpStatus.OK);
        }
    }

    @PostMapping("/play/{id}")
    public ResponseEntity<ResponseDTO<Video>> playVideo(@PathVariable("id") Integer id) throws IOException {
        videoService.updateVideoPlayTimes(id);
        var video = videoService.getVideoById(id);

        if (video != null) {
            return new ResponseEntity<>(new ResponseDTO<>("获取视频成功", video), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO<>("获取视频失败", null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<ResponseDTO<List<CommentResponseDTO>>> addComment(@RequestBody CommentDTO commentDTO) throws IOException {
        videoService.addComment(commentDTO.getVideoId(), commentDTO.getComment(), commentDTO.getUserId());
        var comments = videoService.getComments(commentDTO.getVideoId());

        List<CommentResponseDTO> response = new ArrayList<>();

        for (var comment : comments) {
            CommentResponseDTO commentResponseDTO = new CommentResponseDTO(comment.getId(), comment.getComment(), comment.getUserId(), userService.getUserById(comment.getUserId()).getUsername(), comment.getDate());
            response.add(commentResponseDTO);
        }

        if (!comments.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>("获取评论列表成功", response), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO<>("评论列表为空", response), HttpStatus.OK);
        }
    }

    @GetMapping("/comments/{videoId}")
    public ResponseEntity<ResponseDTO<List<CommentResponseDTO>>> getComments(@PathVariable("videoId") Integer videoId) throws IOException {
        var comments = videoService.getComments(videoId);
        List<CommentResponseDTO> response = new ArrayList<>();

        for (var comment : comments) {
            CommentResponseDTO commentResponseDTO = new CommentResponseDTO(comment.getId(), comment.getComment(), comment.getUserId(), userService.getUserById(comment.getUserId()).getUsername(), comment.getDate());
            response.add(commentResponseDTO);
        }

        if (!comments.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>("获取评论列表成功", response), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO<>("评论列表为空", response), HttpStatus.OK);
        }
    }
}