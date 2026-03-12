import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.OrderCreateRequest;
import steps.OrderSteps;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;


@RunWith(Parameterized.class)
    public class OrderCreateTest {
    public static final String COLOR_BLACK = "BLACK";
    public static final String COLOR_GREY = "GREY";

    private final OrderSteps orderSteps;
    private final List<String> color;
    private Response order;

    public OrderCreateTest(List<String> color) {
        this.orderSteps = new OrderSteps();
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката - {0}")
    public static Object[][] dataGen() {
        return new Object[][]{
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()}
        };
    }
    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с самокатами разных цветов через параметризованный тест")
    public void orderCreate() {
        order = orderSteps.orderCreate(color);
        order
                .then()
                .statusCode(201)
                .body("track", instanceOf(Integer.class));
    }
}
