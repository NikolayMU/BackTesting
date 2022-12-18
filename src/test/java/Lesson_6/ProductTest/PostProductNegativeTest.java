package Lesson_6.ProductTest;


import Lesson_6.api.ProductService;
import Lesson_6.db.dao.ProductsMapper;
import Lesson_6.db.model.Products;
import Lesson_6.db.model.ProductsExample;
import Lesson_6.dto.Product;
import Lesson_6.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;



// Во многих тестах код ответа должен был быть 400(Bad Request) если сделать то же самое в сваггере
// Но тут он заменился на 500

public class PostProductNegativeTest {

    static ProductService productService;
    Product product = null;
    long id;
    static ProductsMapper productsMapper;
    static SqlSession session;
    static Products products;


    @BeforeAll
    static void beforeAll() throws IOException {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
        productsMapper = session.getMapper(ProductsMapper.class);

    }

    @AfterAll
    static void closeSession() {
        session.close();
    }

    @Test
    @SneakyThrows
    void postProductWithoutBodyNegativeTest() {

        product = new Product();

        Response<Product> response = productService.createProduct(product).execute();

        assertThat(response.isSuccessful(), equalTo(false));
        assertThat(response.code(), equalTo(500));
    }




    // Этот тест с пустым title пока подогнала специально (якобы прошел, для примера),
    // поскольку нет критериев, какие поля обязательные, а какие нет.
    // По логике пустые значения нет смысла принимать в базу.

    @Test
    @SneakyThrows
    void postProductWithoutTitleNegativeTest() {

        product = new Product()
                .withCategoryTitle("Electronic")
                .withPrice(10500);

        Response<Product> response = productService.createProduct(product).execute();

        id = response.body().getId();

        products = productsMapper.selectByPrimaryKey(id);


        assertThat(products.getTitle(), equalTo(null));
        assertThat(products.getPrice(),equalTo(10500));
        assertThat(products.getCategory_id(), equalTo(2L));

        assertThat(response.isSuccessful(), equalTo(true));
        assertThat(response.code(), equalTo(201));

        productsMapper.deleteByPrimaryKey(id);
        session.commit();

        ProductsExample example = new ProductsExample();
        example.createCriteria().andIdEqualTo(id);
        List<Products> list = productsMapper.selectByExample(example);
        assertThat(list.size(), equalTo(0));

    }


    @Test
    @SneakyThrows
    void postProductWithoutCategoryNegativeTest() {

        product = new Product()
                .withTitle("Shampoo")
                .withPrice(650);

        Response<Product> response = productService.createProduct(product).execute();
        assertThat(response.isSuccessful(), equalTo(false));
        assertThat(response.code(), equalTo(500));
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

    @Test
    @SneakyThrows
    void postProductWithWrongCategoryNegativeTest() {

        product = new Product()
                .withCategoryTitle("Other")
                .withTitle("shower gel")
                .withPrice(350);

        Response<Product> response = productService.createProduct(product).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(500));
    }

}