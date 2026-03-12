import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.CourierModel;
import steps.CourierSteps;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.createCourier;

public class CourierCreateTest extends BaseApiTest {
    public static final String PASSWORD = "1234";
    public static final String FIRSTNAME = "saske";
    public final String LOGIN = "ninja_" + System.currentTimeMillis();
    private final CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp() {
        System.out.println("Courier login is " + LOGIN);
    }

    @After
    public void tearDown() {
        // Тут убираем за собой
        courierSteps.cleanupCourier(LOGIN, PASSWORD);
    }
    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем, что курьера можно создать с валидными данными")

    public void createNewCourier() {

        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Попытка создать курьера без передачи поля login. Создание курьера должно провалиться")

    public void createCourierWithoutLogin() {

        CourierModel courier = new CourierModel(null, PASSWORD, FIRSTNAME);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Попытка создать курьера без передачи поля password. Создание курьера должно провалиться")

    public void createCourierWithoutPassword() {

        CourierModel courier = new CourierModel(LOGIN, null, FIRSTNAME);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}




