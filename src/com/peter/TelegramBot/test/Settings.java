package com.peter.TelegramBot.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {
    private static volatile Settings instance;
    //    INPUT  DEFAULT CONFIGURATION
    private String BotUsername = "BotUsername";
    private String BotToken = "BotToken";
    private String proxySet = "false";
    private String socksProxyHost = "127.0.0.1";
    private String socksProxyPort = "9150";
    private String serverBD = "serverBD";
    private String userNameBD = "userNameBD";
    private String passwordBD = "passwordBD";
    private String appidKEY = "appidKEY";
    private Settings() {
        Properties properties = new Properties();
        String propFileName = "mysettings.properties";
        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream(propFileName)) {
            properties.load(inputStream);
            this.BotUsername = properties.getProperty("BotUsername");
            this.BotToken = properties.getProperty("BotToken");
            this.proxySet = properties.getProperty("proxySet", "false");
            this.socksProxyHost = properties.getProperty("socksProxyHost");
            this.socksProxyPort = properties.getProperty("socksProxyPort");
            this.serverBD = properties.getProperty("serverBD");
            this.userNameBD = properties.getProperty("userNameBD");
            this.passwordBD = properties.getProperty("passwordBD");
            this.appidKEY = properties.getProperty("AppidKEY");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static Settings getInstance() {
        if (instance == null)
            synchronized (Settings.class) {
                if (instance == null) instance = new Settings();
            }
        return instance;
    }

    public String getAppidKEY() {
        return appidKEY;
    }

    public void setAppidKEY(String appidKEY) {
        this.appidKEY = appidKEY;
    }

    public String getBotUsername() {
        return BotUsername;
    }

    public void setBotUsername(String botUsername) {
        BotUsername = botUsername;
    }

    public String getBotToken() {
        return BotToken;
    }

    public void setBotToken(String botToken) {
        BotToken = botToken;
    }

    public String getProxySet() {
        return proxySet;
    }

    public void setProxySet(String proxySet) {
        this.proxySet = proxySet;
    }

    public String getSocksProxyHost() {
        return socksProxyHost;
    }

    public void setSocksProxyHost(String socksProxyHost) {
        this.socksProxyHost = socksProxyHost;
    }

    public String getSocksProxyPort() {
        return socksProxyPort;
    }

    public void setSocksProxyPort(String socksProxyPort) {
        this.socksProxyPort = socksProxyPort;
    }

    public String getServerBD() {
        return serverBD;
    }

    public void setServerBD(String serverBD) {
        this.serverBD = serverBD;
    }

    public String getUserNameBD() {
        return userNameBD;
    }

    public void setUserNameBD(String userNameBD) {
        this.userNameBD = userNameBD;
    }

    public String getPasswordBD() {
        return passwordBD;
    }

    public void setPasswordBD(String passwordBD) {
        this.passwordBD = passwordBD;
    }
}
