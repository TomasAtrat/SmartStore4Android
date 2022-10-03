package com.zebra.rfid.demo.sdksample.utils.enums;

public enum ExpeditionTypeEnum {
    BOPIS(1L),
    SEND_TO_ADDRESS(2L);

    private Long value;

    ExpeditionTypeEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
