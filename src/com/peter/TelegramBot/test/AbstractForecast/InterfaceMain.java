package com.peter.TelegramBot.test.AbstractForecast;

public interface InterfaceMain {
    double getTemp();

    long getPressure();

    long getHumidity();

    AbstractWind getWind();
}
