package ordertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderSteps;
import org.junit.Test;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;


public class OrderListReceivingTest {
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяем, что список заказов успешно получен и не пустой")
    public void getOrderList() {
        OrderSteps orderSteps = new OrderSteps();
        ValidatableResponse responseOrderList = orderSteps.getOrderList();
        responseOrderList.assertThat()
                .statusCode(200)
                .body("orders", not(empty()));
    }
}

