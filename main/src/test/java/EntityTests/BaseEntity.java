package EntityTests;

import io.qameta.allure.*;
import org.example.models.AdditionRequest;
import org.example.models.EntityRequest;

import java.util.List;

import static io.restassured.RestAssured.given;

@Epic("API тесты")
@Feature("Тестирование Entity")
public class BaseEntity {

    protected final static String BASE_URL = "http://localhost:8080/api";
    protected final static String POST_CREATE_ENTITY = "/create";
    protected final static String GET_ENTITY = "/get/{id}";
    protected final static String GET_ALL_ENTITY = "/getAll";
    protected final static String PATCH_UPDATE_ENTITY = "/patch/{id}";
    protected final static String DELETE_ENTITY = "/delete/{id}";

    /**
     * Создание запроса для новой сущности с вводом необходимымых 2-ух параметров
     *
     * @param title            Заголовок
     * @param importantNumbers Важные номера
     * @return запрос на создание сущности
     */
    @Step("Создание нового запроса")
    public EntityRequest createEntityRequest(String title, List<Integer> importantNumbers) {
        AdditionRequest additionRequest = new AdditionRequest("Дополнительные сведения", 0);
        return new EntityRequest(additionRequest, importantNumbers, title, true);
    }

    /**
     * Создание запроса для новой сущности с вводом всех параметров
     *
     * @param title            Заголовок
     * @param importantNumbers Важные номера
     * @param verified         Верификация
     * @param additionalInfo   Дополнительная информация
     * @param additionalNumber Дополнительный номер
     * @return запрос на создание сущности
     */
    @Step("Создание нового запроса")
    public EntityRequest createEntityRequest(
            String title,
            List<Integer> importantNumbers,
            Boolean verified,
            String additionalInfo,
            Integer additionalNumber) {
        AdditionRequest additionRequest = new AdditionRequest(additionalInfo, additionalNumber);
        return new EntityRequest(additionRequest, importantNumbers, title, verified);
    }

    /**
     * Создание новой сущности в бд и получение ее id
     *
     * @param request запрос на создание сущности
     * @return id
     */
    @Step("Создание новой сущности по запросу и получение id")
    public String createEntityAndGetId(EntityRequest request) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(request)
                .when()
                .post(POST_CREATE_ENTITY)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
    }

    /**
     * Удаление сущности из БД по id
     *
     * @param id id сущности
     */
    @Step("Удаление сущности по id")
    public void deleteEntity(String id) {
        given().baseUri(BASE_URL).delete(DELETE_ENTITY, id);
    }
}
