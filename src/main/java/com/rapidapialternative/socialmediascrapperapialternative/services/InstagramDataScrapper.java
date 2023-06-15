package com.rapidapialternative.socialmediascrapperapialternative.services;

import de.sstoehr.harreader.HarReaderException;

import java.io.IOException;

public interface InstagramDataScrapper {
    InstagramDataScrapper scrape();
    Integer getVideoViewsCount() throws IOException, HarReaderException;
    void setVideoUrl(String videoUrl);
    String getScrappedData();
    boolean login();
    boolean clear();
}
