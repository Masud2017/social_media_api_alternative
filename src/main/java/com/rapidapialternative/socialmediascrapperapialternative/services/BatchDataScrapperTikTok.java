package com.rapidapialternative.socialmediascrapperapialternative.services;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchDataScrapperTikTok {
    private Logger logger = LoggerFactory.getLogger(BatchDataScrapperTikTok.class);
    @Autowired
    private TiktokDataScrapper tiktokDataScrapper;
    private ExecutorService executorService;
    private ConcurrentHashMap<String,Integer> batchPlayCount;

    private List<String> urlList = null;

    @PostConstruct
    public void setup () {
        this.executorService = Executors.newFixedThreadPool(10);
        this.batchPlayCount = new ConcurrentHashMap<>();
    }
    public void setUrlList (List<String> urlList) {
        this.urlList = urlList;
    }

    public BatchDataScrapperTikTok scrape() {
        for (String urlItem : this.urlList) {
           DataScrapperRunner dataScrapperRunner = new DataScrapperRunner(urlItem
                                                                           ,this.batchPlayCount
                                                                           ,this.tiktokDataScrapper);
           this.executorService.submit(dataScrapperRunner);
        }
        return this;
    }

    public ConcurrentHashMap<String,Integer> getBatchPlayCount() {
        return this.batchPlayCount;
    }

    static class DataScrapperRunner implements Runnable {
        private String url;
        private ConcurrentHashMap<String, Integer> batchPlayCount;
        private TiktokDataScrapper tiktokDataScrapper;

        public DataScrapperRunner (String url
                , ConcurrentHashMap<String, Integer> batchPlayCount
                , TiktokDataScrapper tiktokDataScrapper) {
            this.url = url;
            this.batchPlayCount = batchPlayCount;
            this.tiktokDataScrapper = tiktokDataScrapper;
        }

        @Override
        public void run() {
            this.tiktokDataScrapper.setVideoUrl(this.url);
            this.batchPlayCount.put(this.url,this.tiktokDataScrapper.scrape().getPlayCount());
        }
    }
}
