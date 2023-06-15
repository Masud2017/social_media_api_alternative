package com.rapidapialternative.socialmediascrapperapialternative.scheduler;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class Schedular {
    private Logger logger = LoggerFactory.getLogger(Schedular.class);

    @Scheduled(fixedDelay = 17 * 60 * 60 * 1000,initialDelay = 1000)
    public void stad() {
        this.logger.info("Running routine job authenticating to instagram...");
        try (Playwright playwright = Playwright.create()) {
            BrowserContext browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)).newContext();


            Page page = browser.newPage();
            page.navigate("https://www.instagram.com/accounts/login/");
            page.waitForSelector("input[name='username']");

            // Fill in the login form and submit it
            page.fill("input[name='username']", "jibon123420");
            page.fill("input[name='password']", "@amiakjajabor0433");
            page.click("button[type='submit']");
            Thread.sleep(6000);

//
            browser.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("state.json")));
//
//            System.out.println(page.title());





//            Thread.sleep(6000);

            browser.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
