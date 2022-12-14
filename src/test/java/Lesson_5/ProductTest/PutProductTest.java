package Lesson_5.ProductTest;


import Lesson_5.api.ProductService;
import Lesson_5.dto.Product;
import Lesson_5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PutProductTest {
    static ProductService productService;
    Product product = null;
    int id;


    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }


    @BeforeEach
    @SneakyThrows
    void beforeTest() {
        product = new Product()
                .withTitle("Nuts")
                .withCategoryTitle("Food")
                .withPrice(450);

        Response<Product> response = productService.createProduct(product).execute();

        id = response.body().getId();

        assertThat(response.code(), equalTo(201));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getTitle(), equalTo("Nuts"));
        assertThat(response.body().getPrice(), equalTo(450));
        assertThat(response.body().getCategoryTitle(), equalTo("Food"));
    }

    @Test
    @SneakyThrows
    void putProductTitlePositiveTest() {

        product = new Product()
                .withId(id)
                .withTitle("hazelnuts")
                .withCategoryTitle("Food")
                .withPrice(450);

        Response<Product> response = productService.modifyProduct(product).execute();
        assertThat(response.code(), equalTo(200));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getTitle(), equalTo("hazelnuts"));
    }


    @Test
    @SneakyThrows
    void putProductCategoryPositiveTest() {

        product = new Product()
                .withId(id)
                .withTitle("Nuts")
                .withCategoryTitle("Electronic")
                .withPrice(450);

        Response<Product> response = productService.modifyProduct(product).execute();
        assertThat(response.code(), equalTo(200));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getCategoryTitle(), equalTo("Electronic"));
    }

    @Test
    @SneakyThrows
    void putProductPricePositiveTest() {

        product = new Product()
                .withId(id)
                .withTitle("Nuts")
                .withCategoryTitle("Food")
                .withPrice(550);

        Response<Product> response = productService.modifyProduct(product).execute();
        assertThat(response.code(), equalTo(200));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getPrice(), equalTo(550));
    }


    @Test
    @SneakyThrows
    void putProductWithoutCategoryNegativeTest() {

        product = new Product()
                .withId(id)
                .withTitle("Nut")
                .withPrice(550);

        Response<Product> response = productService.modifyProduct(product).execute();
        assertThat(response.code(), equalTo(500));
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
    }


    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
    }

}