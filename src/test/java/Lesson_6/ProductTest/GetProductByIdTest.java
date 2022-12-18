package Lesson_6.ProductTest;

import Lesson_5.api.ProductService;
import Lesson_5.dto.Product;
import Lesson_5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetProductByIdTest {

    static ProductService productService;
    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @SneakyThrows
    @Test
    void getProductByIdPositiveTest() {
        Response<Product> response = productService.getProductById(5).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
        assertThat(response.body().getId(), equalTo(5));
        assertThat(response.body().getTitle(), equalTo("LG TV 1"));
    }

    @SneakyThrows
    @Test
    void getProductByIdNegativeTest() {
        Response<Product> response = productService.getProductById(555).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }
}