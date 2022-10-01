package com.zebra.rfid.demo.sdksample.components.rfidconfig.factories;

import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.ResponseHandlerInterface;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RfidUseCase;

public class RfidFactory {
    public static RfidConfigFactory getRfidConfigFactory(RfidUseCase useCase,
                                                         RFIDReader reader,
                                                         ResponseHandlerInterface responseHandlerInterface) {
        switch (useCase) {
            case RECEPTION:
            case INVENTORY:
                return new InventoryRfidConfig(reader, responseHandlerInterface);

            case SEARCH_ITEM:
                return new LocationRfidConfig(reader, responseHandlerInterface);

            default:
                return null;
        }
    }
}
