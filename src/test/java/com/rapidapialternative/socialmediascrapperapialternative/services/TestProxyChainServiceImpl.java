package com.rapidapialternative.socialmediascrapperapialternative.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@SpringBootTest
public class TestProxyChainServiceImpl {
    @Test
    public void testSetupProxy() throws InterruptedException {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setProxy("107.6.27.132:80"));
            BrowserContext browserContext = browser.newContext();

//            ProxyChainService proxyChainService = new ProxyChainServiceImpl("list.txt",browserContext);
//            if (proxyChainService.readFile()) {
//                browserContext = proxyChainService.setupProxy();
//            }

            Page page = browser.newPage();
            page.navigate("https://httpbin.org/ip");

            Thread.sleep(6000);

            String content = page.content().toString();

            System.out.print(content);

            browser.close();
        }

    }
}
