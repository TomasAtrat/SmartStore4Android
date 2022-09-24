package com.zebra.rfid.demo.sdksample.services;

import org.epctagcoder.option.SGTIN.SGTINExtensionDigit;
import org.epctagcoder.option.SGTIN.SGTINFilterValue;
import org.epctagcoder.option.SGTIN.SGTINTagSize;
import org.epctagcoder.option.SSCC.*;
import org.epctagcoder.parse.SGTIN.ParseSGTIN;
import org.epctagcoder.parse.SSCC.ParseSSCC;
import org.epctagcoder.result.SGTIN;
import org.epctagcoder.result.SSCC;

public class EPCEncoder {
    public EPCEncoder(){

    }

    public void doSth(){
        try {

            ParseSGTIN parseSGTIN = ParseSGTIN.Builder()
                    .withRFIDTag("3035D7CF14003C0000000001")
                    .build();

            SGTIN sgtin = parseSGTIN.getSGTIN();
            System.out.println("parseSGTIN  " + sgtin.toString());

            ParseSSCC parseSSCC2 = ParseSSCC.Builder()
                    .withEPCTagURI("urn:epc:tag:sscc-96:5.023356789.30200002")
                    .build();
            SSCC sscc2 = parseSSCC2.getSSCC();
            System.out.println("parseSSCC  " + sscc2.toString());


            ParseSSCC parseSSCC3 = ParseSSCC.Builder()
                    .withEPCPureIdentityURI("urn:epc:id:sscc:023356789.30200002")
                    .withTagSize(SSCCTagSize.BITS_96)
                    .withFilterValue(SSCCFilterValue.RESERVED_5)
                    .build();
            SSCC sscc3 = parseSSCC3.getSSCC();
            System.out.println("parseSSCC  " + sscc3.toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
