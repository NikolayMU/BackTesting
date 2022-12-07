package Lesson_4;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class PostTest extends AbstractTest {
    @Test
    @DisplayName("Тест-кейс №1: Проверка Post запроса Classify Cuisine")
    void postRecipePositiveTest_1() {
        given().spec(requestSpecification)
                .when()
                .formParam("title", "Burger")
                .formParam("language", "en")
                .post("https://api.spoonacular.com/recipes/cuisine").prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Тест-кейс №2: Проверка Post запроса Add to meal plan")
    void addMealPositiveTest_2() {
        String id = given(requestSpecification)
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
                .log()
                .all()
                .when()
                .post(getBaseUrl()+ "mealplanner/Nikolay1989/items")
                .prettyPeek()
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .as(AddMealResponse.class)
                .getId();
    }
    @Test
    @DisplayName("Тест-кейс №3: Проверка Post запроса Add to meal plan")
    void getRecipePositiveTest_3() {
        ResponseCuisine response = (ResponseCuisine) given().spec(requestSpecification)
                .when()
                .queryParam("title", "Chinese Bbq Pork Ribs")
                .post("https://api.spoonacular.com/recipes/cuisine").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseCuisine.class);
        assertThat(response.getCuisine(), containsString("Mediterranean"));
    }

    @Test
    @DisplayName("Тест-кейс №4: Проверка Post запроса Add to meal plan")
    void getRecipePositiveTest_4() {
        ResponseCuisine response = (ResponseCuisine) given().spec(requestSpecification)
                .when()
                .formParam("title", "Thai Soup")
                .post("https://api.spoonacular.com/recipes/cuisine").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseCuisine.class);
        assertThat(response.getCuisine(), containsString("Asian"));
    }
    @Test
    @DisplayName("Тест-кейс №5: Проверка Post запроса Add to meal plan")
    void MealPlanRecipeTest_5() {
        String id = given(requestSpecification)
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
                .post(getBaseUrl()+ "mealplanner/Nikolay1989/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given(requestSpecification)
                .queryParam("hash", "d6d3993980e26326fef887f6bb354153a38a6bf0")
                .queryParam("apiKey", getApiKey())
                .delete("https://api.spoonacular.com/mealplanner/Nikolay1989/items/" + id)
                .then()
                .statusCode(200);
    }
}


