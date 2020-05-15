package com.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
    Properties pr;

    public ReadConfig() {
        File src = new File("./Configuration/config.properties");
        try {
            FileInputStream fis = new FileInputStream(src);
            pr = new Properties();
            pr.load(fis);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getAppURL() {
        String url = pr.getProperty("URL");
        return url;
    }

    public String getBrowserName() {
        String browser = pr.getProperty("Browser");
        return browser;
    }

    public String getUsername() {
        String username = pr.getProperty("Username");
        return username;
    }

    public String getPassword() {
        String password = pr.getProperty("Password");
        return password;
    }

    public String getEnvironment() {
        String env = pr.getProperty("Environment");
        return env;
    }
}