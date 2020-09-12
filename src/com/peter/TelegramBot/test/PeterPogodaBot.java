package com.peter.TelegramBot.test;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;


public class PeterPogodaBot extends TelegramLongPollingBot {
    private final HashMap<Long, Cast> pairChatIdCast = new HashMap<>();

    public PeterPogodaBot() {
        System.out.println("Отработал конструктор, \n первые ступени отошли. \nПолет стабильный... \n Выходим на Орбиту");
    }

    public PeterPogodaBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public void onUpdateReceived(Update update) {
        // TODO
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            if (update.getMessage().getText().equals("/weather")) {
                pairChatIdCast.put(update.getMessage().getChatId(), Cast.WEATHER);
                message.setChatId(update.getMessage().getChatId())
                        .setText("Для получения текущей погоды отправте координаты");
            }
            if (update.getMessage().getText().equals("/forecast")) {
                pairChatIdCast.put(update.getMessage().getChatId(), Cast.FORECAST);
                message.setChatId(update.getMessage().getChatId())
                        .setText("Для получения прогноза погоды на завтра отправте координаты");
            }

            if (update.getMessage().getText().equals("/subscribe")) {
                pairChatIdCast.put(update.getMessage().getChatId(), Cast.SUBCRIBLE);
                message.setChatId(update.getMessage().getChatId())
                        .setText("Для подписки пришлите свою локацию");
            }

            if (update.getMessage().getText().equals("/unsubscribe")) {
                Runnable runnable = new SetSubscribe(update.getMessage().getChatId().toString(), true);
                Thread thread = new Thread(runnable);
                thread.start();
                message.setChatId(update.getMessage().getChatId())
                        .setText("Заявка на отписку принята");
            }


            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (update.getMessage().hasLocation()) {
            Runnable runnable = null;

            if (pairChatIdCast.containsKey(update.getMessage().getChatId())) {
                Cast value = pairChatIdCast.get(update.getMessage().getChatId());
                switch (value) {

                    case SUBCRIBLE:
                        pairChatIdCast.remove(update.getMessage().getChatId());
                        String lat = update.getMessage().getLocation().getLatitude().toString();
                        String lon = update.getMessage().getLocation().getLongitude().toString();
                        runnable = new SetSubscribe(update.getMessage().getChatId().toString(),
                                false,
                                lat,
                                lon);
                        break;

                    case FORECAST:
                        pairChatIdCast.remove(update.getMessage().getChatId());
                        runnable = new SenderWeather(update, true);
                        break;

                    default:
                        pairChatIdCast.remove(update.getMessage().getChatId());
                        runnable = new SenderWeather(update, false);
                        break;
                }
            } else {
                runnable = new SenderWeather(update, false);
            }
            if (runnable != null) {
                Thread thread = new Thread(runnable);
                thread.start();
            }
        }
    }

    @Override
    public String getBotUsername() {
        Settings settings = Settings.getInstance();
        return settings.getBotUsername();
    }

    @Override
    public String getBotToken() {
        Settings settings = Settings.getInstance();
        return settings.getBotToken();
    }

    private enum Cast {WEATHER, FORECAST, SUBCRIBLE, UNSUBSCRIBLE}


}
