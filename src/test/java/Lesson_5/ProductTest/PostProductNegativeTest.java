package Lesson_5.ProductTest;

import Lesson_5.api.ProductService;
import Lesson_5.dto.Product;
import Lesson_5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;



public class PostProductNegativeTest {

    static ProductService productService;
    Product product = null;
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @Test
    @SneakyThrows
    void postProductWithoutBodyNegativeTest() {

        product = new Product();

        Response<Product> response = productService.createProduct(product).execute();

        assertThat(response.isSuccessful(), equalTo(false));
        assertThat(response.code(), equalTo(500));
    }



    @Test
    @SneakyThrows
    void postProductWithoutTitleNegativeTest() {

        product = new Product()
                .withCategoryTitle("Electronic")
                .withPrice(10500);

        Response<Product> response = productService.createProduct(product).execute();

        id = response.body().getId();

        assertThat(response.isSuccessful(), equalTo(true));
        assertThat(response.code(), equalTo(201));
        assertThat(response.body().getTitle(), equalTo(null));

        Response<ResponseBody> responseDel = productService.deleteProduct(id).execute();
        assertThat(responseDel.isSuccessful(), CoreMatchers.is(true));

    }


    @Test
    @SneakyThrows
    void postProductWithWrongStringNegativeTest() {

        product = new Product()
                .withCategoryTitle("Food")
                .withTitle("dsbvkdsfkkkhdkfhsdkhfkjkhdkfhkjsdhfkshkhkjfdhakjhgkjhkjkbkjbjbjbjdsbvkdsfkkkhdkfhsdkhfkjkhdkfhkjsdhfkshkhkjfdhakjhgkjhkjkbkjbjbjbjdsbvkdsfkkkhdkfhsdkhfkjkhdkfhkjsdhfkshkhkjfdhakjhgkjhkjkbkjbjbjbjdsbvkdsfkkkhdkfhsdkhfkjkhdkfhkjsdhfkshkhkjfdhakjhgkjhkjkbkjbjbjbjdsbvkdsfkkkhdkfhsdkhfkjkhdkfhkjsdhf")
                .withPrice(650);

        Response<Product> response = productService.createProduct(product).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(500));
    }

