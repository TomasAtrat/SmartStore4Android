package com.zebra.rfid.demo.sdksample.utils;

public class Constants {
    public static final String API_URL = "https://b4eb-2800-a4-24f8-af00-c42b-fc95-52fa-b411.ngrok.io";
    public static final String AUTH_SERVICE = "/api/v1/auth/username=%s&password=%s";
    public static final String INVENTORY_SERVICE = "/api/v1/inventories/";
    public static final String GET_RECEPTION_SERVICE = "/api/v1/reception/";
    public static final String ADD_RECEPTION_SERVICE = "/api/v1/reception/";
    public static final String GET_RECEPTION_DETAILS_SERVICE = "/api/v1/reception/details/%d";
    public static final String GET_INVENTORY_DETAILS_SERVICE = "/api/v1/inventories/details/%d";
    public static final String ADD_INVENTORY_DETAILS_SERVICE = "/api/v1/inventories/";

    public static final String ADD_ORDER_SERVICE = "/api/v1/orders/";
    public static final String GET_ORDERS_TO_PREPARE_SERVICE = "/api/v1/orders/to-prepare";

    public static final String GET_PREPARATION_SERVICE = "/api/v1/preparation/%d/%d";

    public static final String GET_STOCK_SERVICE = "/api/v1/stock/%s";
    public static final String GET_EXPEDITION_TYPES_SERVICE = "/api/v1/expedition/types/";

    public static final String GET_TOP10_EPC_BARCODE_SERVICE = "/api/v1/epc/top-ten/%s";

    public static final String ERP_URL = "";

    public static final String INVENTORY_OBJ = "InventoryObj";
    public static final String RECEPTION_OBJ = "ReceptionObj";
    public static final String CUSTOMER_OBJ = "CustomerObj";
    public static final String LIST_OF_STOCK_OBJ = "StockObj";
    public static final String BARCODE_OBJ = "BarcodeObj";
    public static final String ORDER_WRAPPER_OBJ = "OrderWrapperObj";
    public static final String ORDER_INFO_OBJ = "OrderInfoObj";
}
