package com.rapidapialternative.socialmediascrapperapialternative.utils;

import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <h1>ProxyListRotatorSingleton</h1>
 * A class that holds proxy list and return new proxy in every invocation of `getProxyFromList()` method.
 * Not thread safe
 * */
public class ProxyListRotatorSingleton {
    private static ProxyListRotatorSingleton instance = null;
    private Integer cursorIdx = 0;

    @Getter
    private List<String> proxyList;
    private ProxyListRotatorSingleton () throws FileNotFoundException {
        this.proxyList = new ArrayList<>();
        Scanner scanner = new Scanner(new FileInputStream(new File("list.txt")));

        while (scanner.hasNext()) {
            this.proxyList.add(scanner.nextLine());
        }
    }


    public static ProxyListRotatorSingleton getInstance() throws FileNotFoundException {
        if (instance == null) {
            instance = new ProxyListRotatorSingleton();
            return instance;
        }
        return instance;
    }

    /**
     * Every time there is an invocation of this method there will be a new proxy and the
     * internal cursor (cursorIdx) will be pointed to the next child of the proxy list.
     * */
    public String getProxyFromList () {
        String proxyUrl = this.proxyList.get(this.cursorIdx);
        this.cursorIdx++;

        return proxyUrl;
    }
}
