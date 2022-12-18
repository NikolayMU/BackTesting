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
import org.junit.jupiter.api.*;
import retrofit2.Response;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PutProductTest {
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
    }

    @AfterAll
    static void closeSession() {
        session.close();
    }


    @BeforeEach
    @SneakyThrows
    void beforeTest() {
        productsMapper = session.getMapper(ProductsMapper.class);

        products = new Products();
        products.setTitle("Nuts");
        products.setPrice(450);
        products.setCategory_id(1L);
        productsMapper.insert(products);
        session.commit();

        id = products.getId();

        products = productsMapper.selectByPrimaryKey(id);

        assertThat(products.getCategory_id(),equalTo(1L));
        assertThat(products.getPrice(), equalTo(450));
        assertThat(products.getTitle(), equalTo("Nuts"));

    }

    @Test
    @SneakyThrows
    void putProductTitlePositiveTest() {

        product = new Product()
                .withId((int) id)
                .withTitle("hazelnuts")
                .withCategoryTitle("Food")
                .withPrice(450);

        Response<Product> response = productService.modifyProduct(product).execute();

        ProductsExample example = new ProductsExample();
        example.createCriteria().andIdEqualTo(id);
        List<Products> list = productsMapper.selectByExample(example);
        Products products1 = list.get(0);
        productsMapper.updateByPrimaryKey(products1);


        assertThat(products1.getTitle(), equalTo("hazelnuts"));
        assertThat(response.code(), equalTo(200));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

    }


    @Test
    @SneakyThrows
    void putProductCategoryPositiveTest() {

        product = new Product()
                .withId((int) id)
                .withTitle("Nuts")
                .withCategoryTitle("Electronic")
                .withPrice(450);

        Response<Product> response = productService.modifyProduct(product).execute();

        ProductsExample example = new ProductsExample();
        example.createCriteria().andIdEqualTo(id);
        List<Products> list = productsMapper.selectByExample(example);
        Products products1 = list.get(0);
        productsMapper.updateByPrimaryKey(products1);

        assertThat(products1.getCategory_id(), equalTo(2L));
        assertThat(response.code(), equalTo(200));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    @SneakyThrows
    void putProductPricePositiveTest() {

        product = new Product()
                .withId((int) id)
                .withTitle("Nuts")
                .withCategoryTitle("Food")
                .withPrice(550);

        Response<Product> response = productService.modifyProduct(product).execute();

        ProductsExample example = new ProductsExample();
        example.createCriteria().andIdEqualTo(id);
        List<Products> list = productsMapper.selectByExample(example);
        Products products1 = list.get(0);
        productsMapper.updateByPrimaryKey(products1);

        assertThat(products1.getPrice(), equalTo(550));
        assertThat(response.code(), equalTo(200));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }


    @Test
    @SneakyThrows
    void putProductWithoutCategoryNegativeTest() {

        product = new Product()
                .withId((int) id)
                .withTitle("Nut")
                .withPrice(550);

        Response<Product> response = productService.modifyProduct(product).execute();
        assertThat(response.code(), equalTo(500));
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
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

}