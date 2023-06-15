package com.rapidapialternative.socialmediascrapperapialternative.controllers;

import com.rapidapialternative.socialmediascrapperapialternative.models.ControllerResponse;
import com.rapidapialternative.socialmediascrapperapialternative.services.BatchDataScrapperInsta;
import com.rapidapialternative.socialmediascrapperapialternative.services.InstagramDataScrapper;
import com.rapidapialternative.socialmediascrapperapialternative.services.TiktokDataScrapper;
import de.sstoehr.harreader.HarReaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/api",method = RequestMethod.GET)
public class VideoViewCounterController {
    @Autowired
    private InstagramDataScrapper instagramDataScrapper;
    @Autowired
    private TiktokDataScrapper tiktokDataScrapper;
    @Autowired
    private BatchDataScrapperInsta batchDataScrapperInsta;

    private Logger logger = LoggerFactory.getLogger(VideoViewCounterController.class);

    @GetMapping(value = "/insta")
    public ResponseEntity<ControllerResponse> getInstagramVideoViewCount(@RequestParam String videoUrl) throws IOException, HarReaderException {
        this.instagramDataScrapper.setVideoUrl(videoUrl);
        ControllerResponse controllerResponse = new ControllerResponse();

        Map<String,Integer> map = new HashMap<>();
        map.put("playCount",this.instagramDataScrapper.scrape().getVideoViewsCount());
        controllerResponse.setPlayCount(map);

        try {
            this.instagramDataScrapper.clear();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(controllerResponse);
    }

    @GetMapping(value = "/tiktok")
    public ResponseEntity<ControllerResponse> getTikTokVideoViewCounter(@RequestParam String videoUrl) {
        this.logger.info("Checking the video url from tiktok controller : "+videoUrl);
        this.tiktokDataScrapper.setVideoUrl(videoUrl);

        ControllerResponse controllerResponse = new ControllerResponse();

        Map<String,Integer> map = new HashMap<>();
        map.put("playCount",this.tiktokDataScrapper.scrape().getPlayCount());
        controllerResponse.setPlayCount(map);

        return ResponseEntity.ok(controllerResponse);
    }

    @PostMapping(value = "/batch/insta")
    public ResponseEntity<ControllerResponse> getInstagramBatchViewCounter(@RequestBody List<String> videoUrlList) throws InterruptedException, IOException, HarReaderException {
        for (String videoItem : videoUrlList) {
            System.out.println(videoItem);
        }

        this.batchDataScrapperInsta.setUrlList(videoUrlList);
        ControllerResponse response = new ControllerResponse();

        Map<String,Integer> batchDataMap = this.batchDataScrapperInsta.scrape().getBatchPlayCount();

        response.setPlayCount(batchDataMap);

        return ResponseEntity.ok(response);
    }
}
