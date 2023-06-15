package com.rapidapialternative.socialmediascrapperapialternative.services;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.options.Proxy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ProxyChainServiceImpl implements ProxyChainService {
    private String proxyListFileName;
    private BrowserContext browserContext;
    private String proxyFileContent;

    public ProxyChainServiceImpl(String proxyListFileName, BrowserContext browserContext) {
        this.proxyListFileName = proxyListFileName;
        this.browserContext = browserContext;
    }

    @Override
    public boolean readFile() {
        try {
            File file = new File(this.proxyListFileName);
            Scanner scanner = new Scanner(new FileInputStream(file));

            while (scanner.hasNext()) {
                this.proxyFileContent += scanner.nextLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public BrowserContext setupProxy() {
        this.browserContext = browserContext.
                browser().newContext(new Browser
                        .NewContextOptions()
                        .setProxy(new Proxy("123.205.68.113:80")));

        return this.browserContext;
    }
}
