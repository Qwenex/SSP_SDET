package EntityTests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.models.EntityRequest;
import org.example.models.EntityResponse;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

@Story("GET_ENTITY")
public class GetEntityTest extends BaseEntity {

    @Severity(SeverityLevel.BLOCKER)
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
}
