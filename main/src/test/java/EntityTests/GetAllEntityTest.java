package EntityTests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.models.EntityRequest;
import org.example.models.EntityResponse;
import org.example.models.PaginatedEntityResponse;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

@Story("GET_ALL_ENTITY")
public class GetAllEntityTest extends BaseEntity {

    @Severity(SeverityLevel.BLOCKER)
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
}
