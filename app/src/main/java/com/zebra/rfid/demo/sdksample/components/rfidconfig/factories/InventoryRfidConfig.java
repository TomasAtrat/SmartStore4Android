package com.zebra.rfid.demo.sdksample.components.rfidconfig.factories;

import android.util.Log;

import com.zebra.rfid.api3.Antennas;
import com.zebra.rfid.api3.INVENTORY_STATE;
import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.OperationFailureException;
import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.api3.RfidEventsListener;
import com.zebra.rfid.api3.SESSION;
import com.zebra.rfid.api3.SL_FLAG;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.actionStrategies.InventoryRfidActionStrategy;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.actionStrategies.RfidActionStrategy;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.ResponseHandlerInterface;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.InventoryEventHandler;

public class InventoryRfidConfig implements RfidConfigFactory {
    private static final int MAX_POWER = 270;
    private static final String TAG = "InventoryRfidConfig";

    private RFIDReader reader;
    private ResponseHandlerInterface responseHandlerInterface;

    public InventoryRfidConfig(RFIDReader reader, ResponseHandlerInterface responseHandlerInterface) {
        this.reader = reader;
        this.responseHandlerInterface = responseHandlerInterface;
    }

    @Override
    public RfidEventsListener createEventHandler() {
        Log.d(TAG, "Creating event handler");
        return new InventoryEventHandler(reader, responseHandlerInterface);
    }

    @Override
    public Antennas.AntennaRfConfig createAntennaRfConfig() throws InvalidUsageException, OperationFailureException {
        Log.d(TAG, "Creating antenna rf config");
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
        s1_singulationControl.setTagTransitTime((short)60);
        s1_singulationControl.Action.setInventoryState(INVENTORY_STATE.INVENTORY_STATE_A);
        s1_singulationControl.Action.setSLFlag(SL_FLAG.SL_ALL);
        return s1_singulationControl;
    }

    @Override
    public RfidActionStrategy createRfidActionStrategy() {
        return new InventoryRfidActionStrategy(reader);
    }
}
