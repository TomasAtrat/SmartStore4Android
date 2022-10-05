package com.zebra.rfid.demo.sdksample.components.epcschema;

import com.zebra.rfid.demo.sdksample.models.Barcode;

import org.epctagcoder.parse.SGTIN.ParseSGTIN;
import org.epctagcoder.result.SGTIN;

public class SGTIN96Schema implements EPCSchemaStrategy {
    @Override
    public Barcode getBarcodeFromEPC(String epc) throws RuntimeException {
        try {
            ParseSGTIN parseSGTIN = ParseSGTIN.Builder()
                    .withRFIDTag(epc)
                    .build();

            SGTIN sgtin = parseSGTIN.getSGTIN();

            String barcode = sgtin.getCompanyPrefix() + sgtin.getItemReference() + sgtin.getCheckDigit();

            return new Barcode(barcode);

        } catch (Exception e) {
            throw new RuntimeException("Problems while decoding tag " + epc);
        }
    }
}
