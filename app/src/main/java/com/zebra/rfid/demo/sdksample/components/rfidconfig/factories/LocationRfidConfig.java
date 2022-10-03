package com.zebra.rfid.demo.sdksample.components.rfidconfig.factories;

import com.zebra.rfid.api3.Antennas;
import com.zebra.rfid.api3.INVENTORY_STATE;
import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.OperationFailureException;
import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.api3.RfidEventsListener;
import com.zebra.rfid.api3.SESSION;
import com.zebra.rfid.api3.SL_FLAG;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.actionStrategies.ItemLocationRfidActionStrategy;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.actionStrategies.RfidActionStrategy;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.LocationEventHandler;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.ResponseHandlerInterface;

public class LocationRfidConfig implements RfidConfigFactory{

    private RFIDReader reader;
    private ResponseHandlerInterface responseHandlerInterface;

    private static final int MAX_POWER = 270;

    public LocationRfidConfig(RFIDReader reader, ResponseHandlerInterface responseHandlerInterface) {
        this.reader = reader;
        this.responseHandlerInterface = responseHandlerInterface;
    }

    @Override
    public RfidEventsListener createEventHandler() {
        return new LocationEventHandler(reader, responseHandlerInterface);
    }

    @Override
    public Antennas.AntennaRfConfig createAntennaRfConfig() throws InvalidUsageException, OperationFailureException {
        Antennas.AntennaRfConfig config = reader.Config.Antennas.getAntennaRfConfig(1);
        config.setTransmitPowerIndex(MAX_POWER);
        config.setrfModeTableIndex(0);
        config.setTari(0);
        return config;
    }

    @Override
    public Antennas.SingulationControl createSingulationControl() throws InvalidUsageException, OperationFailureException {
        Antennas.SingulationControl s1_singulationControl = reader.Config.Antennas.getSingulationControl(1);
        s1_singulationControl.setSession(SESSION.SESSION_S2);
        s1_singulationControl.Action.setInventoryState(INVENTORY_STATE.INVENTORY_STATE_A);
        s1_singulationControl.Action.setSLFlag(SL_FLAG.SL_ALL);
        return s1_singulationControl;
    }

    @Override
    public RfidActionStrategy createRfidActionStrategy() {
        return new ItemLocationRfidActionStrategy(reader);
    }
}
