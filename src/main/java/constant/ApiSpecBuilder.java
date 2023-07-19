package constant;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static constant.ScooterApiEndpoints.BASE_URL;

// Вынес req spec в отдельный класс, чтобы использовать его в классах OrderSteps и CourierSteps
public class ApiSpecBuilder {

    public RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }
}

