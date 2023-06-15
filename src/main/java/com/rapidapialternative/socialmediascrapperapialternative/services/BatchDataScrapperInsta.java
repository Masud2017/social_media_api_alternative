package com.rapidapialternative.socialmediascrapperapialternative.services;

import de.sstoehr.harreader.HarReaderException;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BatchDataScrapperInsta {
    private Logger logger = LoggerFactory.getLogger(BatchDataScrapperInsta.class);
    private ConcurrentHashMap<String, Integer> batchPlayCount = new ConcurrentHashMap<>();

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    private List<String> urlList;
    @Autowired
    private InstagramDataScrapper instagramDataScrapper;

    @PostConstruct
    public void setUrl() {
//        this.executorService = Executors.newFixedThreadPool(10);
//        this.batchPlayCount = new ConcurrentHashMap<>();
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }


    public ConcurrentHashMap<String,Integer> getBatchPlayCount() {
        return this.batchPlayCount;
    }

    public BatchDataScrapperInsta scrape() throws IOException, HarReaderException {
        for (String urlItem : this.urlList) {
            DataScrapperRunner dataScrapperRunner = new DataScrapperRunner(urlItem
                    ,this.batchPlayCount
                    ,this.instagramDataScrapper);
            this.instagramDataScrapper.setVideoUrl(urlItem);
            this.batchPlayCount.put(urlItem,this.instagramDataScrapper.scrape().getVideoViewsCount());
            this.instagramDataScrapper.clear();
//            this.executorService.submit(dataScrapperRunner);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        return this;
    }

    static class DataScrapperRunner implements Runnable {
        private String url;
        private ConcurrentHashMap<String, Integer> batchPlayCount;
        private InstagramDataScrapper instagramDataScrapper;


        public DataScrapperRunner (String url
                , ConcurrentHashMap<String, Integer> batchPlayCount
                , InstagramDataScrapper instagramDataScrapper) {
            this.url = url;
            this.batchPlayCount = batchPlayCount;
            this.instagramDataScrapper = instagramDataScrapper;
        }

        @SneakyThrows
        @Override
        public void run() {
            this.instagramDataScrapper.setVideoUrl(this.url);
            this.batchPlayCount.put(this.url,this.instagramDataScrapper.scrape().getVideoViewsCount());
            this.instagramDataScrapper.clear();
        }
    }

}
