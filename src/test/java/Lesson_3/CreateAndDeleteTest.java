package Lesson_3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

public class CreateAndDeleteTest extends AbstractTest {

    @Test
    @DisplayName("Тест-кейс №1: Создание и удаление блюда из меню")
    void MealPlanIngredientsTest_1() {
        String id = given()
                .queryParam("hash", "d6d3993980e26326fef887f6bb354153a38a6bf0")
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"date\": 1653217200,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post(getBaseUrl()+ "mealplanner/Nikolay1989/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .queryParam("hash", "d6d3993980e26326fef887f6bb354153a38a6bf0")
                .queryParam("apiKey",getApiKey())
                .delete(getBaseUrl()+ "mealplanner/Nikolay1989/items/" + id)
                .then()
                .statusCode(200);
    }
    @Test
    @DisplayName("Тест-кейс №2: Создание и удаление блюда из меню")
    void MealPlanRecipeTest_2() {
        String id = given()
                .queryParam("hash", "d6d3993980e26326fef887f6bb354153a38a6bf0")
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"date\": 1653303600,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"id\": 1,\n"
                        + " \"servings\": 2,\n"
                        + " \"title\": \"Frozen pizza\",\n"
                        + " \"imageType\": \"jpg\",\n"
                        + " }\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/mealplanner/Nikolay1989/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .queryParam("hash", "d6d3993980e26326fef887f6bb354153a38a6bf0")
                .queryParam("apiKey", getApiKey())
                .delete("https://api.spoonacular.com/mealplanner/Nikolay1989/items/" + id)
                .then()
                .statusCode(200);
    }
}
