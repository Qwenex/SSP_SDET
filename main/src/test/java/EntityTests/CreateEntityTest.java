package EntityTests;

import io.qameta.allure.*;
import org.example.models.EntityRequest;
import org.example.models.EntityResponse;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

@Story("POST_CREATE_ENTITY")
public class CreateEntityTest extends BaseEntity {

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
}
