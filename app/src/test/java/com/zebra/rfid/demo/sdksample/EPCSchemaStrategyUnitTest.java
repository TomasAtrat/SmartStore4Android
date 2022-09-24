package com.zebra.rfid.demo.sdksample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.zebra.rfid.demo.sdksample.components.epcschema.EPCSchemaStrategy;
import com.zebra.rfid.demo.sdksample.components.epcschema.SGTIN96Schema;
import com.zebra.rfid.demo.sdksample.models.Barcode;

import org.junit.Test;

public class EPCSchemaStrategyUnitTest {

    @Test
    public void SGTIN96SchemaStrategyShouldReturnBarcodeFromEPC() throws Exception {
        EPCSchemaStrategy epcSchema = new SGTIN96Schema();
        Barcode barcode = epcSchema.getBarcodeFromEPC("3035D7CF14003C0000000001");
        assertNotNull(barcode);
    }

    @Test
    public void SGTIN96SchemaStrategyShouldReturnCorrectBarcodeFromEPC() throws Exception {
        EPCSchemaStrategy epcSchema = new SGTIN96Schema();
        Barcode barcode = epcSchema.getBarcodeFromEPC("3035D7CF14003C0000000001");
        assertEquals("7730117002408", barcode.getId());
    }

}
