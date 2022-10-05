package com.zebra.rfid.demo.sdksample.components.rfidconfig.actionStrategies;

import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.OperationFailureException;
import com.zebra.rfid.api3.RFIDReader;

public class ItemLocationRfidActionStrategy implements RfidActionStrategy {
    private RFIDReader reader;

    public ItemLocationRfidActionStrategy(RFIDReader reader) {
        this.reader = reader;
    }

    public synchronized void performRfidAction(String[] params) throws InvalidUsageException, OperationFailureException {
        reader.Actions.TagLocationing.Perform(params[0], null, null);
    }

    @Override
    public void stopRfidAction() throws InvalidUsageException, OperationFailureException {
        reader.Actions.TagLocationing.Stop();
    }
}
