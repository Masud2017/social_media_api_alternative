package com.rapidapialternative.socialmediascrapperapialternative.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.rapidapialternative.socialmediascrapperapialternative.models.InstagramHarData;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

@Service
public class InstagramDataScrapperImpl implements InstagramDataScrapper {
    private String videoUrl = null;
    private String scrappedData = null;
    private final String loginUrl = "https://www.instagram.com/api/v1/web/accounts/login/ajax/";
    private Logger logger = LoggerFactory.getLogger(InstagramDataScrapperImpl.class);
    private String harFileName = null;


    @Override
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        this.harFileName = String.valueOf(System.currentTimeMillis()) + Thread.currentThread().getName()+".har";
    }

    @Override
    public String getScrappedData() {return this.scrappedData;}

    @Override
    public boolean login() {


        return false;
    }

    @Override
    public boolean clear() {
        File file = new File(this.harFileName);
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;

        }
        return false;
    }

    @Override
    public InstagramDataScrapper scrape() {
        try (Playwright playwright = Playwright.create()) {
//            BrowserContext browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)).newContext(new Browser.NewContextOptions()
//                    .setRecordHarPath(Paths.get("example.har"))
//                    .setRecordHarUrlFilter("**/info/**"));
            BrowserContext browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setProxy("http://localhost:8118/")).newContext(new Browser.NewContextOptions().setRecordHarPath(Paths.get(this.harFileName))
                    .setRecordHarUrlFilter("**/*info/*").setStorageStatePath(Paths.get("state.json")));

            Page page = browser.newPage();
//            page.navigate("https://www.instagram.com/accounts/login/");
//            page.waitForSelector("input[name='username']");
//
//            // Fill in the login form and submit it
//            page.fill("input[name='username']", "jibon123420");
//            page.fill("input[name='password']", "@amiakjajabor0433");
//            page.click("button[type='submit']");
//            Thread.sleep(6000);

//
//            browser.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("state.json")));
//
//            System.out.println(page.title());




            page.navigate(this.videoUrl);

//            Thread.sleep(6000);


            page.route("**/*info/*", route -> {
                System.out.println("Intercepting request for: " + route.request().url());
                System.out.println("Data of route ; "+route.request().response().text());
//                route.continueRequest();
                route.resume();
            });

            Thread.sleep(1500);



            browser.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public Integer getVideoViewsCount() throws IOException, HarReaderException {
        File file = new File(this.harFileName);
        Scanner scanner = new Scanner(new FileInputStream(file));
        String harData = "";

        while (scanner.hasNext()) {
            harData += scanner.nextLine();
        }

        ObjectMapper itemsMapper= new ObjectMapper();

        HarReader harReader = new HarReader();
        Har har = harReader.readFromFile(file);
        JsonNode itemsNode = itemsMapper.readTree(har.getLog().getEntries().get(0).getResponse().getContent().getText());

        this.logger.info("play count : "+itemsNode.get("items").get(0).get("play_count"));
        return Integer.parseInt(itemsNode.get("items").get(0).get("play_count").toString());
    }
}
