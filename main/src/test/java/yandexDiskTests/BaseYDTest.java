package yandexDiskTests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.example.utils.ReadProperty;
import org.testng.annotations.BeforeClass;

@Epic("API тесты")
@Feature("Тестирование Yandex Disk")
public class BaseYDTest {

    private final static ReadProperty urlProperty = new ReadProperty("yandexApi/yandexApiURL");
    public final static String BASE_URL = urlProperty.get("base.url");
    public final static String BASE_PATH = "/v1/disk/";
    public final static String RESOURCES_PATH = BASE_PATH + "resources/";
    public final static String TRASH_PATH = BASE_PATH + "trash/resources/";

    private final static ReadProperty authProperty = new ReadProperty("yandexApi/authYD");
    private final static String AUTH_TOKEN = authProperty.get("OAuth.token");

    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseGetSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .build().header("Authorization", AUTH_TOKEN);

        responseGetSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .build();
    }
}
