package com.ichirinko.luce.utils;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import java.io.IOException;
import java.io.InputStream;


public class FFmpegProcessor {

    /**
     * 这个方法的url地址都必须是一样的类型 同为post
     */
    private ServerProperties server;

    public static void convertMediaToM3u8ByHttp(InputStream inputStream, String m3u8Url, String infoUrl, String serverUrl, int videoId) throws IOException {

        avutil.av_log_set_level(avutil.AV_LOG_INFO);
        FFmpegLogCallback.set();

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
        grabber.start();

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(m3u8Url, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());

        recorder.setFormat("hls");
        recorder.setOption("hls_time", "5");
        recorder.setOption("hls_list_size", "0");
        recorder.setOption("hls_flags", "delete_segments");
        recorder.setOption("hls_delete_threshold", "1");
        recorder.setOption("hls_segment_type", "mpegts");
        recorder.setOption("hls_segment_filename", serverUrl + "/api/video/saveAsM3u8/" + videoId + "/" + videoId + "-%d.ts");
        recorder.setOption("hls_key_info_file", infoUrl);

        // http属性
        recorder.setOption("method", "POST");

        recorder.setFrameRate(25);
        recorder.setGopSize(2 * 25);
        recorder.setVideoQuality(1.0);
        recorder.setVideoBitrate(1024 * 1024);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        recorder.start(grabber.getFormatContext());
        AVPacket packet;
        while ((packet = grabber.grabPacket()) != null) {
            try {
                recorder.recordPacket(packet);
            } catch (FrameRecorder.Exception e) {
                e.printStackTrace();
            }
        }
        recorder.setTimestamp(grabber.getTimestamp());
        recorder.stop();
        recorder.release();
        grabber.stop();
        grabber.release();
    }

}