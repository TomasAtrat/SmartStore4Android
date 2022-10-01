package com.zebra.rfid.demo.sdksample.components.rfidconfig.actionStrategies;

import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.OperationFailureException;
import com.zebra.rfid.api3.RFIDReader;

public class ItemLocationRfidActionStrategy implements RfidActionStrategy{
    private RFIDReader reader;

    public ItemLocationRfidActionStrategy(RFIDReader reader) {
        this.reader = reader;
    }

    public synchronized void performRfidAction() throws InvalidUsageException, OperationFailureException {
        //reader.Actions.Inventory.perform();
    }

    @Override
    public void stopRfidAction() throws InvalidUsageException, OperationFailureException {
        //reader.Actions.Inventory.stop();
    }
}
