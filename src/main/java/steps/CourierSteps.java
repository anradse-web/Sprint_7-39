package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import pojo.CourierLoginRequest;
import pojo.CourierLoginResponse;
import pojo.CourierModel;

import static data.CourierData.*;
import static io.restassured.RestAssured.given;


@Slf4j
public class CourierSteps {
    @Step("Создание курьера")
    public static Response createCourier(CourierModel courier) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_POST_CREATE)
                .then().extract().response();

    }

    @Step("Логин курьера")
    public static Response loginCourier(String login, String password) {
        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, password);
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierLoginRequest)
                .when()
                .post(COURIER_POST_LOGIN)
                .then().extract().response();

    }

    @Step("Удаление курьера")
    public static Response deleteCourier(int courierId) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierId)
                .when()
                .delete(COURIER_DELETE + courierId)
                .then().extract().response();

    }

    @Step("Удалить курьера по логину, если он существует")
    public void cleanupCourier(String login, String password) {
        Response response = loginCourier(login, password);
        if (response.statusCode() == 404) {
            // 2a. Если нет такого пользователя, то успешно тестируем
        } else if (response.statusCode() == 200) {
            // 2b. Если залогинились успешно, то удаляем по ID
            CourierLoginResponse id = response.body().as(CourierLoginResponse.class);
            deleteCourier(id.getId());
        } else {
            // 2c. Не можем обработать такую ситуацию
            throw new IllegalStateException("Courier can't be removed! Response code is " + response.statusCode());
        }
    }
}


