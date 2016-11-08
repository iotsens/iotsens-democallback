package com.iotsens.democallback;

import com.iotsens.democallback.dto.Measure;
import org.springframework.stereotype.Component;

@Component
public class WasteGatheringPlanner {

    public boolean mustBeGathered(Measure distance, Measure temperature) {
        Double distanceValue = Double.valueOf(distance.getValue());
        Double temperatureValue = Double.valueOf(temperature.getValue());


        if (distanceValue < 20) {
            return true;
        } else if ((distanceValue < 40) && (temperatureValue > 20)) {
            return true;
        } else if (distanceValue < 50 && anyTruckAvailable()) {
            return true;
        } else {
            return false;
        }


    }

    private boolean anyTruckAvailable() {
        // query a database or external application
        return true;
    }
}
