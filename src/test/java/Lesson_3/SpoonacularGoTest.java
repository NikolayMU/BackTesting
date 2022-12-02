package Lesson_3;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class SpoonacularGoTest extends AbstractTest {

    @Test
    @DisplayName("Тест-кейс №1: Проверка Get запроса Search Recipes")
    void getSpecifyingRequestDataTest_1() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .pathParam("id", 716429)
                .when()
                .get(getBaseUrl() + "recipes/{id}/information")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .header("Connection", "keep-alive")
                .contentType(ContentType.JSON);


        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .pathParam("id", 716429)
                .when()
                .get(getBaseUrl() + "recipes/{id}/information")
                .body()
                .jsonPath();
        assertThat(response.get("title"), equalTo("Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"));

    }
    @Test
    @DisplayName("Тест-кейс №2: Проверка Get запроса Search Recipes")
    void getSpecifyingRequestDataTest_2() {

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pizza")
                .queryParam("excludeCuisine", "Italian")
                .when()
                .get(getBaseUrl()+ "recipes/complexSearch")
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pizza")
                .queryParam("excludeCuisine", "Italian")
                .when()
                .get(getBaseUrl()+ "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(2));
    }

    @Test
    @DisplayName("Тест-кейс №:3 Проверка Get запроса Search Recipes")
    void getSpecifyingRequestDataTest_3() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .queryParam("diet", "Pescetarian")
                .when()
                .get(getBaseUrl()+ "recipes/complexSearch")
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .queryParam("diet", "Pescetarian")
                .when()
                .get(getBaseUrl()+ "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(29));
    }

    @Test
    @DisplayName("Тест-кейс №4: Проверка Get запроса Search Recipes")
    void getSpecifyingRequestDataTest_4(){
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "lasagna")
                .queryParam("maxCalories", "350")
                .when()
                .get(getBaseUrl()+ "recipes/complexSearch")
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "lasagna")
                .queryParam("maxCalories", "350")
                .when()
                .get(getBaseUrl()+ "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(4));
    }


    @Test
    @DisplayName("Тест-кейс №5: Проверка Get запроса Search Recipes")
    void getSpecifyingRequestDataTest_5() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "caesar salad")
                .queryParam("diet", "Gluten Free")
                .when()
                .get(getBaseUrl()+ "recipes/complexSearch")
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "caesar salad")
                .queryParam("diet", "Gluten Free")
                .when()
                .get(getBaseUrl()+ "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(1));

    }

    @Test
    @DisplayName("Тест-кейс №1: Проверка Post запроса Search Recipes")
    void postSpecifyingRequestDataTest_1() {
        given()
                .queryParam("apiKey",getApiKey())
                .queryParam("title", "Pizza")
                .when()
                .post(getBaseUrl()+ "recipes/cuisine")
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Pizza")
                .when()
                .post(getBaseUrl()+ "recipes/cuisine")
                .body()
                .jsonPath();

        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
    }
    @Test
    @DisplayName("Тест-кейс №2: Проверка Post запроса Search Recipes")
    void postSpecifyingRequestDataTest_2() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Sushi")
                .when()
                .post(getBaseUrl()+ "recipes/cuisine")
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Sushi")
                .when()
                .post(getBaseUrl()+ "recipes/cuisine")
                .body()
                .jsonPath();
        double confidence = 0.85;
        float conf = (float)confidence;
        assertThat(response.get("confidence"), equalTo(conf));
        assertThat(response.get("cuisine"), equalTo("Japanese"));
    }
    @Test
    @DisplayName("Тест-кейс №3: Проверка Post запроса Search Recipes")
    void postSpecifyingRequestDataTest_3() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Lasagna")
                .when()
                .post(getBaseUrl()+ "recipes/cuisine")
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Lasagna")
                .when()
                .post(getBaseUrl()+ "recipes/cuisine")
                .body()
                .jsonPath();
        double confidence = 0.95;
        float conf = (float)confidence;
        assertThat(response.get("confidence"), equalTo(conf));
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
    }
    @Test
    @DisplayName("Тест-кейс №4: Проверка Post запроса Search Recipes")
    void postSpecifyingRequestDataTest_4() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "caesar salad")
                .when()
                .post(getBaseUrl()+ "recipes/cuisine")
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "caesar salad")
                .when()
                .post(getBaseUrl()+ "recipes/cuisine")
                .body()
                .jsonPath();
        double confidence = 0.95;
        float conf = (float)confidence;
        assertThat(response.get("confidence"), equalTo(conf));
        assertThat(response.get("cuisine"), equalTo("American"));
    }
    @Test
    @DisplayName("Тест-кейс №5: Проверка Post запроса Search Recipes")
    void postSpecifyingRequestDataTest_5() {
    given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Chinese Bbq Pork Ribs")
                .when()
                .post(getBaseUrl()+ "recipes/cuisine")
                .then()
                .statusCode(200);

    JsonPath response = given()
            .queryParam("apiKey", getApiKey())
            .queryParam("title", "Chinese Bbq Pork Ribs")
            .when()
            .post(getBaseUrl()+ "recipes/cuisine")
            .body()
            .jsonPath();
    double confidence = 0.85;
    float conf = (float)confidence;
    assertThat(response.get("confidence"), equalTo(conf));
    assertThat(response.get("cuisine"), equalTo("Chinese"));
}
}