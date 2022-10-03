package com.zebra.rfid.demo.sdksample.utils;

public class Constants {
    public static final String API_URL = "https://9a32-2800-a4-2554-6500-345f-ea70-de17-319c.ngrok.io";
    public static final String AUTH_SERVICE = "/api/v1/auth/username=%s&password=%s";
    public static final String INVENTORY_SERVICE = "/api/v1/inventories/";
    public static final String GET_RECEPTION_SERVICE = "/api/v1/reception/";
    public static final String ADD_RECEPTION_SERVICE = "/api/v1/reception/";
    public static final String GET_RECEPTION_DETAILS_SERVICE = "/api/v1/reception/details/%d";
    public static final String GET_INVENTORY_DETAILS_SERVICE = "/api/v1/inventories/details/%d";
    public static final String ADD_INVENTORY_DETAILS_SERVICE = "/api/v1/inventories/";

    public static final String ADD_ORDER_SERVICE = "/api/v1/orders/";

    public static final String GET_STOCK_SERVICE = "/api/v1/stock/%s";
    public static final String GET_EXPEDITION_TYPES_SERVICE = "/api/v1/expedition/types/";

    public static final String ERP_URL = "";

    public static final String INVENTORY_OBJ = "InventoryObj";
    public static final String RECEPTION_OBJ = "ReceptionObj";
    public static final String CUSTOMER_OBJ = "CustomerObj";
    public static final String LIST_OF_STOCK_OBJ = "StockObj";
    public static final String BARCODE_OBJ = "BarcodeObj";
    public static final String ORDER_WRAPPER_OBJ = "OrderWrapperObj";
}
