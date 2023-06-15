package com.rapidapialternative.socialmediascrapperapialternative.services;

import com.microsoft.playwright.BrowserContext;

public interface ProxyChainService {
    boolean readFile();
    BrowserContext setupProxy();
}
