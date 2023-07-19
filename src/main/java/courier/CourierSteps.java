package courier;

import constant.ApiSpecBuilder;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static constant.ScooterApiEndpoints.*;

public class CourierSteps {

    private ApiSpecBuilder apiSpecBuilder = new ApiSpecBuilder();

    @Step("Регистрация нового курьера")
    public ValidatableResponse createCourier(CourierModel courierModel) {
        return apiSpecBuilder.requestSpec()
                .body(courierModel)
                .when()
                .post(COURIER_POST_CREATE)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(CourierCreds courierCreds) {
        return apiSpecBuilder.requestSpec()
                .body(courierCreds)
                .when()
                .post(COURIER_POST_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int courierId) {
        return apiSpecBuilder.requestSpec()
                .when()
                .delete(COURIER_DELETE + courierId)
                .then();
    }
}
