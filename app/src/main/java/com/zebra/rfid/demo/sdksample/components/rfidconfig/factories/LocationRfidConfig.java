package com.zebra.rfid.demo.sdksample.components.rfidconfig.factories;

import com.zebra.rfid.api3.Antennas;
import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.OperationFailureException;
import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.api3.RfidEventsListener;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.actionStrategies.RfidActionStrategy;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.ResponseHandlerInterface;

public class LocationRfidConfig implements RfidConfigFactory{

    public LocationRfidConfig(RFIDReader reader, ResponseHandlerInterface responseHandlerInterface) {

    }

    @Override
    public RfidEventsListener createEventHandler() {
        return null;
    }

    @Override
    public Antennas.AntennaRfConfig createAntennaRfConfig() throws InvalidUsageException, OperationFailureException {
        return null;
    }

    @Override
    public Antennas.SingulationControl createSingulationControl() throws InvalidUsageException, OperationFailureException {
        return null;
    }

    @Override
    public RfidActionStrategy createRfidActionStrategy() {
        return null;
    }
}
