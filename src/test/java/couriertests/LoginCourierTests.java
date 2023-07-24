package couriertests;

import courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class LoginCourierTests {
    private final CourierRandomizer courierRandomizer = new CourierRandomizer();
    private CourierAssert courierAssert;
    private int courierId;
    private CourierCreds courierCreds;
    private CourierSteps courierSteps;
    private CourierModel courierModel;

    @Before
    @Step("Создание тестовых данных для логина курьера")
    public void setUp() {
        courierSteps = new CourierSteps();
        courierModel = courierRandomizer.createNewRandomCourier();
        courierSteps.createCourier(courierModel);
        courierCreds = CourierCreds.from(courierModel);
        courierAssert = new CourierAssert();
    }

    @Test
    @DisplayName("Логин курьера успешен")
    @Description("Проверяем, что курьер может войти в систему с корректными данными")
    public void courierLoginOkValidData() {
        ValidatableResponse responseLoginCourier = courierSteps.loginCourier(courierCreds);
        courierAssert.loginCourierOk(responseLoginCourier);
        courierId = responseLoginCourier.extract().path("id");
        responseLoginCourier.assertThat().body("id", greaterThan(0)).and().statusCode(200);
    }

    @Test
    @DisplayName("Логин курьера с пустым полем логина")
    @Description("Проверяем, что курьер не может войти в систему с пустым полем логина")
    public void courierLoginErrorEmptyLogin() {
        CourierCreds courierCredsWithoutLogin = new CourierCreds("", courierModel.getPassword());
        ValidatableResponse responseLoginErrorMessage = courierSteps.loginCourier(courierCredsWithoutLogin);
        courierAssert.loginCourierError(responseLoginErrorMessage);
        responseLoginErrorMessage.assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);
    }

    @Test
    @DisplayName("Логин курьера с пустым полем пароля")
    @Description("Проверяем, что курьер не может войти в систему с пустым полем пароля")
    public void courierLoginErrorEmptyPassword() {
        CourierCreds courierCredsWithoutPass = new CourierCreds(courierModel.getLogin(), "");
        ValidatableResponse responseLoginErrorMessage = courierSteps.loginCourier(courierCredsWithoutPass);
        courierAssert.loginCourierError(responseLoginErrorMessage);
        responseLoginErrorMessage.assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);
    }

    @Test
    @DisplayName("Логин курьера с пустыми полями логина и пароля")
    @Description("Проверяем, что курьер не может войти в систему с пустыми полями логина и пароля")
    public void courierLoginErrorEmptyLoginAndPassword() {
        CourierCreds courierCredsWithoutLoginAndPassword = new CourierCreds("", "");
        ValidatableResponse responseLoginErrorMessage = courierSteps.loginCourier(courierCredsWithoutLoginAndPassword);
        courierAssert.loginCourierError(responseLoginErrorMessage);
        responseLoginErrorMessage.assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);
    }


    @Test
    @DisplayName("Логин курьера c не корректным логином")
    @Description("Проверяем, что курьер не может войти в систему не зарегистрированным логином")
    public void courierLoginErrorAccountNotFound() {
        CourierCreds courierCredsErrorAccountNotFound = new CourierCreds(CourierRandomizer.NEW_LOGIN_FAKED, courierModel.getPassword());
        ValidatableResponse responseLoginErrorMessage = courierSteps.loginCourier(courierCredsErrorAccountNotFound);
        courierAssert.loginCourierErrorAccountNotFound(responseLoginErrorMessage);
        responseLoginErrorMessage.assertThat().body("message", equalTo("Учетная запись не найдена")).and().statusCode(404);
    }

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if (courierId != 0) {
            courierSteps.deleteCourier(courierId);
            System.out.println("Удален - " + courierId);
        } else {
            System.out.println("Не удалось удалить курьера, так как ID не найден");
        }
    }
}

