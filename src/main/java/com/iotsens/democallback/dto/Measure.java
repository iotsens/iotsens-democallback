package com.iotsens.democallback.dto;

import java.util.Date;

public class Measure {

    String sensorId;
    String variableName;
    String value;
    Date timestamp;

    public Measure(String sensorId, String variableName, String value, Date timestamp) {
        this.sensorId = sensorId;
        this.variableName = variableName;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Measure() {
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Measure{");
        sb.append("sensorId='").append(sensorId).append('\'');
        sb.append(", variableName='").append(variableName).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
