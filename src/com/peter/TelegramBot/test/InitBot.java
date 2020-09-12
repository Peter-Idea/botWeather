package com.peter.TelegramBot.test;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class InitBot {

    public InitBot() {
        System.out.println("инициализация API Context");
        ApiContextInitializer.init();
        System.out.println("инициализация Telegram Bots API");
        TelegramBotsApi botsApi = new TelegramBotsApi();
        Settings settings = Settings.getInstance();
        System.out.println(settings.getProxySet());
        if (settings.getProxySet().equals("true")) {
            System.out.println("Используем прокси");
            System.getProperties().put("proxySet", settings.getProxySet());
            System.getProperties().put("socksProxyHost", settings.getSocksProxyHost());
            System.getProperties().put("socksProxyPort", settings.getSocksProxyPort());
        }
        System.out.println("Переходим к запуску Бота");
        try {
            botsApi.registerBot(new PeterPogodaBot());
            System.out.println("Бот в эфире");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
