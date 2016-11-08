package com.iotsens.democallback;

import com.iotsens.democallback.api.IoTSensReader;
import com.iotsens.democallback.api.IoTSensWriter;
import com.iotsens.democallback.dto.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class Callback {

    @Autowired
    IoTSensReader ioTSensReader;

    @Autowired
    IoTSensWriter ioTSensWriter;

    @Autowired
    WasteGatheringPlanner planner;

    @RequestMapping("/callback")
    public Boolean receiveMeasure(
            @RequestParam(value = "sensorId") String sensorId,
            @RequestParam(value = "variableName") String variableName,
            @RequestParam(value = "value") String value,
            @RequestParam(value = "timestamp") String timestampString
    ) throws Exception {

        Date timestamp = new Date(Long.parseLong(timestampString) * 1000);

        Measure receivedMeasure = new Measure(sensorId, variableName, value, timestamp);
        Measure tempMeasure = ioTSensReader.readTempMeasure();


        boolean gatherContainer = planner.mustBeGathered(receivedMeasure, tempMeasure);
        ioTSensWriter.sendGatheringMeasure(gatherContainer);

        System.out.println(receivedMeasure.toString());
        System.out.println(tempMeasure.toString());
        System.out.println("------");

        return true;
    }

}
