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
import static org.testng.Assert.assertEquals;

@Story("PATCH_UPDATE_ENTITY")
public class UpdateEntityTest extends BaseEntity {

    @Severity(SeverityLevel.BLOCKER)
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
}
