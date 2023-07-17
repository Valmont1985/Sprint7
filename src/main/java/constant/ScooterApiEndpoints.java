package constant;

public class ScooterApiEndpoints {

    public final static String BASE_URL = "http://qa-scooter.praktikum-services.ru/";
    public final static String COURIER_POST_LOGIN = "/api/v1/courier/login";
    public final static String COURIER_POST_CREATE = "/api/v1/courier";
    public final static String COURIER_DELETE = "/api/v1/courier/:id";
    public final static String COURIER_GET_ORDERS_COUNT = "/api/v1/courier/:id/ordersCount";
    public final static String ORDER_PUT_FINISH = "/api/v1/orders/finish/:id";
    public final static String ORDER_PUT_CANCEL = "/api/v1/orders/cancel";
    public final static String ORDER_GET_LIST = "/api/v1/orders";
    public final static String ORDER_GET_BY_NUMBER = "/api/v1/orders/track";
    public final static String ORDER_PUT_ACCEPT = "/api/v1/orders/accept/:id";
    public final static String ORDER_POST_CREATE = "/api/v1/orders";
    public final static String UTILS_GET_PING = "/api/v1/ping";
    public final static String UTILS_GET_STATION = "/api/v1/stations/search";

}