package EntityTests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.models.EntityRequest;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

@Story("DELETE_ENTITY")
public class DeleteEntityTest extends BaseEntity {

    @Severity(SeverityLevel.BLOCKER)
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
