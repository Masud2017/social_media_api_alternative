package com.rapidapialternative.socialmediascrapperapialternative.services;

import de.sstoehr.harreader.HarReaderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class TestInstagramDataScrapperImpl {
    @Autowired
    private InstagramDataScrapper instagramDataScrapper;


    @Test
    public void testScrape() {
        this.instagramDataScrapper.setVideoUrl("https://www.instagram.com/reel/ConNmz1OE0n/?hl=en");
        InstagramDataScrapper resultData = this.instagramDataScrapper.scrape();
    }

    @Test
    public void testGetVideoViewsCount() throws IOException, HarReaderException {
        Assertions.assertTrue(this.instagramDataScrapper.getVideoViewsCount() > 0);
        System.out.println(this.instagramDataScrapper.getVideoViewsCount());
    }

    @Test
    public void testClear() {
        Assertions.assertTrue(this.instagramDataScrapper.clear());
    }
}
