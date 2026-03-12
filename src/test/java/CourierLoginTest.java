import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import pojo.CourierLoginRequest;
import pojo.CourierModel;
import steps.CourierSteps;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.isA;

public class CourierLoginTest extends BaseApiTest {

    public static final String PASSWORD = "1234";
    public final String LOGIN = "ninja_" + System.currentTimeMillis();
    private final CourierSteps courierSteps = new CourierSteps();
    private Integer createdCourierId;
    @Before
    public void setUp() {
        System.out.println("Creating courier with login: " + LOGIN);

        // Создаём курьера перед авторизацией
        System.out.println("Creating courier with login: " + LOGIN);

        // Создаём объект курьера
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, "Ninja");

        // Передаём объект в метод создания курьера
        createdCourierId = courierSteps.createCourier(courier)
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body()
                .path("id");
    }

    @After
    public void tearDown() {
        // Убираем за собой, только если курьер был создан
        if (createdCourierId != null) {
            courierSteps.deleteCourier(createdCourierId);
        }
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться с набором валидных данных")
    public void loginCourier() {
        courierSteps.loginCourier(LOGIN, PASSWORD)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", instanceOf(Integer.class));
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка, что курьер НЕ может авторизоваться без передачи поля login")
    public void loginCourierWithoutLogin() {

        courierSteps.loginCourier(null, PASSWORD)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка, что курьер НЕ может авторизоваться без передачи поля password")
    public void loginCourierWithoutPassword() {
        courierSteps.loginCourier(LOGIN, "")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера, используя несуществующим логином")
    @Description("Проверка, что курьер НЕ может авторизоваться, используя используя несуществующий логин")
    public void loginCourierNotFound () {

        courierSteps.loginCourier("loggin", PASSWORD)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Авторизация курьера с неверным паролем")
    @Description("Проверка, что курьер НЕ может авторизоваться с неверным паролем")
    public void incorrectPassword () {
        courierSteps.loginCourier(LOGIN, "kukuku")
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
