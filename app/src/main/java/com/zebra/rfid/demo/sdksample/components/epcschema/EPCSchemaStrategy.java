package com.zebra.rfid.demo.sdksample.components.epcschema;

import com.zebra.rfid.demo.sdksample.models.Barcode;

public interface EPCSchemaStrategy {
    Barcode getBarcodeFromEPC(String epc) throws RuntimeException;
}
