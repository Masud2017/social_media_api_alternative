package com.rapidapialternative.socialmediascrapperapialternative.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestTikTokDataScrapperImpl {
    @Autowired
    private TiktokDataScrapper tiktokDataScrapper;

    @Test
    public void testScrape() {
        this.tiktokDataScrapper.setVideoUrl("https://www.tiktok.com/@askthereddit/video/7198592576075697451");
        this.tiktokDataScrapper.scrape();
    }

    @Test
    public void testGetVideoViewCount() {
        this.tiktokDataScrapper.setVideoUrl("https://www.tiktok.com/@askthereddit/video/7198592576075697451");

        Assertions.assertTrue(this.tiktokDataScrapper.scrape().getPlayCount() > 0);
    }

}
