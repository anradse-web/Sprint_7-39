package data;

import io.restassured.RestAssured;

public class CourierData {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    public static final String LOGIN = "ninja_" + System.currentTimeMillis();
    public static final String PASSWORD = "1234";
    public static final String FIRSTNAME = "saske_";

    public static String COURIER_POST_CREATE = "/api/v1/courier";
    public static String COURIER_POST_LOGIN = "/api/v1/courier/login"; // Логин курьера
    public static String COURIER_DELETE = "/api/v1/courier/"; // Удаление курьера
}
