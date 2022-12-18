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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostProductPositiveTest {

    static ProductService productService;
    Product product = null;
    long id;
    static ProductsMapper productsMapper;
    static SqlSession session;
    static Products products;


    @BeforeAll
    static void beforeAll() throws IOException {productService = RetrofitUtils.getRetrofit().create(ProductService.class);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
        productsMapper = session.getMapper(ProductsMapper.class);

    }

    @Test
    @SneakyThrows
    void postProductPositiveTest() {
        product = new Product()
                .withTitle("Tomato")
                .withCategoryTitle("Food")
                .withPrice(55);

        Response<Product> response = productService.createProduct(product).execute();

        id = response.body().getId();

        products = productsMapper.selectByPrimaryKey(id);

        assertThat(products.getTitle(), equalTo("Tomato"));
        assertThat(products.getCategory_id(), equalTo(1L));
        assertThat(products.getPrice(), equalTo(55));

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(201));

    }


    @SneakyThrows
    @AfterEach
    void tearDown() {

        productsMapper.deleteByPrimaryKey(id);
        session.commit();

        ProductsExample example = new ProductsExample();
        example.createCriteria().andIdEqualTo(id);
        List<Products> list = productsMapper.selectByExample(example);
        assertThat(list.size(), equalTo(0));

    }


    @AfterAll
    static void closeSession() {
        session.close();
    }
}
