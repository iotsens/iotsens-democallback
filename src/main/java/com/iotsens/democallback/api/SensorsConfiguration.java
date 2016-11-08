package com.iotsens.democallback.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sensors")
public class SensorsConfiguration {

    String temperatureSensorId;
    String garbageSensorId;

    public String getTemperatureSensorId() {
        return temperatureSensorId;
    }

    public void setTemperatureSensorId(String temperatureSensorId) {
        this.temperatureSensorId = temperatureSensorId;
    }

    public String getGarbageSensorId() {
        return garbageSensorId;
    }

    public void setGarbageSensorId(String garbageSensorId) {
        this.garbageSensorId = garbageSensorId;
    }
}
