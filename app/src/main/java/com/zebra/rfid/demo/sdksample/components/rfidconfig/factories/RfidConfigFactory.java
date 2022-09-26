package com.zebra.rfid.demo.sdksample.components.rfidconfig.factories;

import com.zebra.rfid.api3.Antennas;
import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.OperationFailureException;
import com.zebra.rfid.api3.RfidEventsListener;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.actionStrategies.RfidActionStrategy;

public interface RfidConfigFactory {

    RfidEventsListener createEventHandler();

    Antennas.AntennaRfConfig createAntennaRfConfig() throws InvalidUsageException, OperationFailureException;

    Antennas.SingulationControl createSingulationControl() throws InvalidUsageException, OperationFailureException;

    RfidActionStrategy createRfidActionStrategy();
}
