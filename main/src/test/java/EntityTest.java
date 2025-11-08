import io.qameta.allure.*;
import org.example.models.AdditionRequest;
import org.example.models.EntityRequest;
import org.example.models.EntityResponse;
import org.example.models.PaginatedEntityResponse;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.given;

@Epic("API тесты")
@Feature("API запросы к БД PostgresSQL")
@Story("API запросы к таблице Entity")
@Severity(SeverityLevel.BLOCKER)
public class EntityTest {

    private final static String BASE_URL = "http://localhost:8080/api";
    private final static String POST_CREATE_ENTITY = "/create";
    private final static String GET_ENTITY = "/get/{id}";
    private final static String GET_ALL_ENTITY = "/getAll";
    private final static String PATCH_UPDATE_ENTITY = "/patch/{id}";
    private final static String DELETE_ENTITY = "/delete/{id}";

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

    @Severity(SeverityLevel.BLOCKER)
    @Description("Создание сущности, заполнив все поля в запросе")
    @Test(description = "Создание сущности")
    public void createEntityTest() {
        EntityRequest request = createEntityRequest(
                "Новая созданная сущность",
                Arrays.asList(15, 25, 35),
                true,
                "Доп инфа",
                155);
        String newEntityId = createEntityAndGetId(request);

        EntityResponse response = given()
                .baseUri(BASE_URL)
                .when()
                .get(GET_ENTITY, newEntityId)
                .then()
                .statusCode(200)
                .extract()
                .as(EntityResponse.class);

        assertNotNull(newEntityId, "id не может быть null");
        assertFalse(newEntityId.isEmpty(), "id не может быть пустым");

        assertEquals(response.getTitle(), request.getTitle(),
                "Заголовок не совпадает: " + response.getTitle());
        assertEquals(response.getImportantNumbers(), request.getImportantNumbers(),
                "Важные номера не совпадают: " + response.getImportantNumbers());
        assertEquals(response.getVerified(), request.getVerified(),
                "Верификация не совпадат: " + response.getVerified());
        assertEquals(response.getAddition().getAdditionalInfo(), request.getAddition().getAdditionalInfo(),
                "Дополнительная информация не совпадает: " + response.getAddition().getAdditionalInfo());
        assertEquals(response.getAddition().getAdditionalNumber(), request.getAddition().getAdditionalNumber(),
                "Дополнительный номер не совпадает: " + response.getAddition().getAdditionalNumber());

        deleteEntity(newEntityId);
    }

    @Description("Получение определенной сущности")
    @Test(description = "Получение сущности")
    public void getEntityTest() {
        EntityRequest request = createEntityRequest(
                "Сущность для получения",
                Arrays.asList(4, 8, 15, 16, 23, 42));
        String entityId = createEntityAndGetId(request);

        EntityResponse response = given()
                .baseUri(BASE_URL)
                .when()
                .get(GET_ENTITY, entityId)
                .then()
                .statusCode(200)
                .extract()
                .as(EntityResponse.class);

        assertNotNull(entityId, "id не может быть null");
        assertFalse(entityId.isEmpty(), "id не может быть пустым");

        assertEquals(response.getTitle(), request.getTitle(),
                "Заголовок не совпадает: " + response.getTitle());
        assertEquals(response.getImportantNumbers(), request.getImportantNumbers(),
                "Важные номера не совпадают: " + response.getImportantNumbers());

        deleteEntity(entityId);
    }

    @Description("Получение всех сущностей c пагинацией")
    @Test(description = "Получение всех сущностей")
    public void getAllEntityWithPaginationTest() {
        EntityRequest requestOne = createEntityRequest(
                "Сущность 1 вида",
                Arrays.asList(1, 2, 3));
        String firstEntityId = createEntityAndGetId(requestOne);

        EntityRequest requestTwo = createEntityRequest(
                "Сущность 2 вида",
                Arrays.asList(2, 5, 7));
        String secondEntityId = createEntityAndGetId(requestTwo);

        PaginatedEntityResponse response = given()
                .baseUri(BASE_URL)
                .queryParam("title", "Сущность 1 вида")
                .queryParam("verified", true)
                .queryParam("page", 1)
                .queryParam("perPage", 10)
                .when()
                .get(GET_ALL_ENTITY)
                .then()
                .statusCode(200)
                .extract()
                .as(PaginatedEntityResponse.class);

        assertFalse(response.getEntity().isEmpty(), "Список сущностей не должен быть пустым");
        assertEquals(response.getPage().intValue(), 1, "Номер страницы должен быть равен 1");
        assertEquals(response.getPerPage().intValue(), 10, "Размер страницы должен быть равен 10");

        for (EntityResponse entity : response.getEntity()) {
            assertNotNull(entity.getId(),"id не должен быть null: " + entity);
            assertEquals(entity.getTitle(), "Сущность 1 вида", "Заголовок должен соответствовать фильтру");
            assertEquals(entity.getVerified(), true, "Варификация должна соответствовать фильтру");
        }

        deleteEntity(firstEntityId);
        deleteEntity(secondEntityId);
    }

    @Description("Обновление сущности")
    @Test(description = "Обновление сущности")
    public void updateEntityTest() {
        EntityRequest request = createEntityRequest(
                "Старое название сущности",
                Arrays.asList(9, 10, 7, 10));
        String entityId = createEntityAndGetId(request);

        EntityRequest updateEntityRequest = createEntityRequest(
                "Новое название сущности",
                Arrays.asList(10, 10, 10, 10));

        given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(updateEntityRequest)
                .when()
                .patch(PATCH_UPDATE_ENTITY, entityId)
                .then()
                .statusCode(204);

        EntityResponse updatedEntity = given()
                .baseUri(BASE_URL)
                .when()
                .get(GET_ENTITY, entityId)
                .then()
                .statusCode(200)
                .extract()
                .as(EntityResponse.class);

        assertEquals(updatedEntity.getTitle(),"Новое название сущности",
                "Заголовок не обновился: " + updatedEntity);
        assertEquals(updatedEntity.getImportantNumbers(),Arrays.asList(10,10,10,10),
                "Важные номера не обновились: " + updatedEntity);

        deleteEntity(entityId);
    }

    @Description("Удаление сущности")
    @Test(description = "Удаление сущности")
    public void deleteEntityTest() {
        EntityRequest request = createEntityRequest(
                "Сущность для удаления",
                Arrays.asList(9,1,1));
        String entityId = createEntityAndGetId(request);

        given()
                .baseUri(BASE_URL)
                .when()
                .delete(DELETE_ENTITY, entityId)
                .then()
                .statusCode(204);

        given()
                .baseUri(BASE_URL)
                .when()
                .get(GET_ENTITY, entityId)
                .then()
                .statusCode(500);
    }
}
