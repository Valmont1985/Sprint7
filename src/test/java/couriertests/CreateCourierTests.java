package couriertests;

import courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CreateCourierTests {
    protected final CourierRandomizer courierRandomizer = new CourierRandomizer();
    private int courierId;
    private CourierSteps courierSteps;
    private CourierModel courierModel;
    private CourierAssert courierAssert;

    @Before
    @Step("Создание тестовых данных курьера")
    public void setUp() {
        courierSteps = new CourierSteps();
        courierModel = courierRandomizer.createNewRandomCourier();
        courierAssert = new CourierAssert();
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем, что курьера можно создать")
    public void courierCanBeCreated() {
        ValidatableResponse responseCreateCourier = courierSteps.createCourier(courierModel);
        CourierCreds courierCreds = CourierCreds.from(courierModel);
        courierId = courierSteps.loginCourier(courierCreds).extract().path("id");
        courierAssert.createCourierOk(responseCreateCourier);
        responseCreateCourier.assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверяем, что курьера нельзя создать без логина")
    public void courierCanNotBeCreatedWithoutLogin() {
        courierModel.setLogin(null);
        ValidatableResponse responseNullLogin = courierSteps.createCourier(courierModel);
        courierAssert.createCourierError(responseNullLogin);
        responseNullLogin.assertThat().statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверяем, что курьера нельзя создать без пароля")
    public void courierCanNotBeCreatedWithoutPassword() {
        courierModel.setPassword(null);
        ValidatableResponse responseNullPassword = courierSteps.createCourier(courierModel);
        courierAssert.createCourierError(responseNullPassword);
        responseNullPassword.assertThat().statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера без логина и пароля")
    @Description("Проверяем, что курьера нельзя создать без ввода логина и пароля")
    public void courierCanNotBeCreatedWithoutLoginAndPassword() {
        courierModel.setLogin(null);
        courierModel.setPassword(null);
        ValidatableResponse responseNullFields = courierSteps.createCourier(courierModel);
        courierAssert.createCourierError(responseNullFields);
        responseNullFields.assertThat().statusCode(400);
    }

    @After
    @Step("Удаление тестовых данных")
    public void deleteCourier() {
        if (courierId != 0) {
            courierSteps.deleteCourier(courierId);
            System.out.println("Удален - " + courierId);
        } else {
            System.out.println("Не удалось удалить курьера, так как ID не найден");
        }
    }
}


