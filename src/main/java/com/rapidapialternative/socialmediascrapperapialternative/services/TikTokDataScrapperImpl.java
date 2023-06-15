package com.rapidapialternative.socialmediascrapperapialternative.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TikTokDataScrapperImpl implements TiktokDataScrapper {
    private String videoUrl;
    private String playCount;


    @Override
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public TiktokDataScrapper scrape() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate(this.videoUrl);
            String source = page.mainFrame().content();

            String regex = "<script id=\"SIGI_STATE\".*?>\\s*(\\{.*\\})\\s*</script>";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(source);
            if (matcher.find()) {
                String json = matcher.group(1);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(json);


               String videoId = this.videoUrl.split("/")[5].split("\\?")[0];
                this.playCount = node.get("ItemModule").get(videoId).get("stats").get("playCount").toString();

            }

            browser.close();



        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public Integer getPlayCount() {
        return Integer.parseInt(this.playCount);
    }
}
