package com.zebra.rfid.demo.sdksample.components.rfidconfig.actionStrategies;

import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.OperationFailureException;

public interface RfidActionStrategy {
    void performRfidAction(String [] params) throws InvalidUsageException, OperationFailureException;

    void stopRfidAction() throws InvalidUsageException, OperationFailureException;
}
