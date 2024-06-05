package com.ichirinko.luce.controller;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.IoUtil;
import com.ichirinko.luce.utils.FFmpegProcessor;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.*;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


@RestController
@RequestMapping("/video")
public class VideoController {

    /**
     * 目录路径,这个路径需要包含test.info文件，test.key文件和test.mp4文件
     */
    @Autowired
    private ServerProperties serverProperties;
    private InetAddress localHost = InetAddress.getLocalHost();

    private static final String PATH = "D:\\luce\\videos\\";

    public VideoController() throws UnknownHostException {
    }


    @PostMapping("uploadToM3u8")
    public void uploadToM3u8() throws Exception {
        String prefixUrl = "http://" + localHost.getHostAddress() +":"+ serverProperties.getPort();
        FileInputStream inputStream = new FileInputStream(PATH + "test.mp4");
        String m3u8Url = prefixUrl + "/video/upload/test.m3u8";
        String infoUrl = prefixUrl + "/video/preview/test.info";
        FFmpegProcessor.convertMediaToM3u8ByHttp(inputStream, m3u8Url, infoUrl, prefixUrl);
    }

    @PostMapping("upload/{fileName}")
    public void upload(HttpServletRequest request, @PathVariable("fileName") String fileName) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        FileWriter writer = new FileWriter(PATH + fileName);
        writer.writeFromStream(inputStream);
        IoUtil.close(inputStream);
    }

    /**
     * 预览加密文件
     */
    @PostMapping("preview/{fileName}")
    public void preview(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {

        FileReader fileReader = new FileReader(PATH + fileName);

        fileReader.writeToStream(response.getOutputStream());
    }

    /**
     * 下载加密文件
     */
    @GetMapping("download/{fileName}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
        FileReader fileReader = new FileReader(PATH + fileName);
        fileReader.writeToStream(response.getOutputStream());
    }

}