package com.rapidapialternative.socialmediascrapperapialternative.services;

public interface TiktokDataScrapper {
    TiktokDataScrapper scrape();
    void setVideoUrl(String videoUrl);
    Integer getPlayCount();
}
