package Lesson_4;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetTest extends AbstractTest {

    @Test
    @DisplayName("Тест-кейс №1: Проверка Get запроса Search Recipes")
    void getRecipePositiveTest_1() {
        given().spec(getRequestSpecification())
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Тест-кейс №2: Проверка Get запроса Search Recipes")
    void getRecipePositiveTest_2() {
        given().spec(requestSpecification)
                .queryParam("includeNutrition", "false")
                .pathParam("id", 716429)
                .when()
                .get(getBaseUrl() + "recipes/{id}/information").prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Тест-кейс №3: Проверка Get запроса Search Recipes")
    void getRecipePositiveTest_3() {
        given().spec(requestSpecification)
                .queryParam("query", "pizza")
                .queryParam("excludeCuisine", "Italian")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch").prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Тест-кейс №4: Проверка Get запроса Search Recipes")
    void getRecipePositiveTest_4() {
        given().spec(requestSpecification)
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .queryParam("diet", "Pescetarian")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch").prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Тест-кейс №5: Проверка Get запроса Search Recipes")
    void getRecipePositiveTest_5() {
        given().spec(requestSpecification)
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "lasagna")
                .queryParam("maxCalories", "350")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch").prettyPeek()
                .then()
                .spec(responseSpecification);
    }
}