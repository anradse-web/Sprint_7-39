package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import pojo.OrderCreateRequest;
import pojo.TrackId;

import java.util.List;

import static data.CourierData.BASE_URI;
import static data.OrderData.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class OrderSteps {

    private RequestSpecification prepareRestSpec() {
        return given()
                .log().ifValidationFails()  // логируем только при ошибках
                .contentType(ContentType.JSON)
                .baseUri( BASE_URI);
    }

    public static RequestSpecification requestSpecification() {
        return given()
                .log().ifValidationFails()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI);
    }
    public static final OrderCreateRequest ORDER = new OrderCreateRequest("Иван", "Петров", "ул. Ленина, 10",
            "123", "+79991234567", 2, "2024-12-31");

    @Step("Создание нового заказа")
    public Response orderCreate(List<String> colors) {
        OrderCreateRequest request = ORDER.withColors(colors);
        return requestSpecification()
                .body(request)
                .post(ORDER_POST_CREATE)
                .then()
                .statusCode(SC_CREATED)  // валидируем статус-код
                .extract().response();  // извлекаем Response
    }

    @Step("Получение заказов")
    public Response listOrders(
            Long courierId,
            List<String> nearestStations,
            Integer limit,
            Integer page) {

        RequestSpecification spec = prepareRestSpec();

        if (courierId != null) {
            spec = spec.param("courierId", courierId);
        }
        if (nearestStations != null) {  // исправлено: было nearestStations
            spec = spec.param("nearestStations", nearestStations);
        }
        if (limit != null) {
            spec = spec.param("limit", limit);
        }
        if (page != null) {
            spec = spec.param("page", page);
        }

        return spec.get(ORDER_GET_LIST)  // используем константу из OrderData
                .then()
                .statusCode(SC_OK)  // валидируем статус-код
                .extract().response();  // извлекаем Response
    }

    @Step("Отменить заказ")
    public ValidatableResponse cancel(Integer track) {
        System.out.println("Пытаемся отменить заказ с track: " + track);

        if (track == null) {
            throw new IllegalArgumentException("Track cannot be null");
        }

        ValidatableResponse response = prepareRestSpec()
                .body(new TrackId(track))
                .put(ORDERS_CANCEL)  // исправлено: было ORDERS_CANCEL
                .then();

        int statusCode = response.extract().statusCode();
        System.out.println("Статус-код отмены заказа " + track + ": " + statusCode);

        return response;  // ожидаем успешный ответ
    }
}