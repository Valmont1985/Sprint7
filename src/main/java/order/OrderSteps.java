package order;

import constant.ApiSpecBuilder;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static constant.ScooterApiEndpoints.*;

public class OrderSteps {

    private final ApiSpecBuilder apiSpecBuilder = new ApiSpecBuilder();

    @Step("Создание заказа")
    public ValidatableResponse createNewOrder(OrderModel orderModel) {
        return apiSpecBuilder.requestSpec()
                .body(orderModel)
                .when()
                .post(ORDER_POST_CREATE)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return apiSpecBuilder.requestSpec()
                .when()
                .get(ORDER_GET_LIST)
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(int track) {
        return apiSpecBuilder.requestSpec()
                .body(track)
                .when()
                .put(ORDER_PUT_CANCEL)
                .then();
    }
}


