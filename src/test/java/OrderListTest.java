
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.OrderCreateRequest;
import pojo.TrackId;
import steps.OrderSteps;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

@DisplayName("Получение списка заказов")

    public class OrderListTest {
        public static final String COLOR_GREY = "GREY";
        public static final int DEFAULT_ORDERS_PER_PAGE = 30;

        private final OrderSteps orderSteps = new OrderSteps();
        private List<TrackId> orderCreate;

    @Before
    public void setUp() {
        orderCreate = new ArrayList<>();
        System.out.println("Создаём " + DEFAULT_ORDERS_PER_PAGE + " тестовых заказов");

        for (int i = 0; i < DEFAULT_ORDERS_PER_PAGE; i++) {
            try {
                Response response = orderSteps.orderCreate(List.of(COLOR_GREY));
                if (response.statusCode() == SC_CREATED) {
                    TrackId track = response.body().as(TrackId.class);
                    orderCreate.add(track);
                    System.out.println("Заказ создан, track: " + track.getTrack());
                } else {
                    System.err.println("Не удалось создать заказ " + i +
                            ", статус: " + response.statusCode() +
                            ", тело: " + response.asString());
                }
            } catch (Exception e) {
                System.err.println("Ошибка создания заказа " + i + ": " + e.getMessage());
            }
        }
        System.out.println("Всего создано заказов: " + orderCreate.size());
    }

    @After
    public void tearDown() {
        System.out.println("Начинаем отмену " + orderCreate.size() + " заказов");
        int cancelledCount = 0;
        int failedCount = 0;

        for (TrackId trackId : orderCreate) {
            Integer track = trackId.getTrack();
            if (track == null) {
                System.out.println("Пропускаем заказ: track = null");
                continue;
            }

            try {
                ValidatableResponse response = orderSteps.cancel(track);
                response.statusCode(SC_OK);
                cancelledCount++;
                System.out.println("Заказ " + track + " успешно отменён");
            } catch (AssertionError e) {
                failedCount++;
                System.err.println("Ошибка отмены заказа " + track +
                        ": статус " + e.getMessage());
            } catch (Exception e) {
                failedCount++;
                System.err.println("Критическая ошибка отмены заказа " + track +
                        ": " + e.getMessage());
            }
        }

        System.out.println("Итого: отменено " + cancelledCount +
                ", ошибок " + failedCount);
    }


    @DisplayName("Список заказов с ограничением 1")
        @Description("Получение первой страницы с ограничением 1 заказ на страницу")
        @Test
        public void listOrdersWithLimit() {
            Response response = orderSteps.listOrders(null, null, 1, 0);
            response.then()
                    .body("orders", Matchers.notNullValue())
                    .body("orders", hasSize(1))
                    .statusCode(SC_OK);
        }

        @DisplayName("Список заказов без ограничения")
        @Description("Получение первой страницы с ограничением по-умолчанию 30 заказов на страницу")
        @Test
        public void listOrders() {
            Response response = orderSteps.listOrders(null, null, null, null);
            response.then()
                    .body("orders", notNullValue())
                    .body("orders", hasSize(DEFAULT_ORDERS_PER_PAGE))
                    .statusCode(SC_OK);
        }
    }

