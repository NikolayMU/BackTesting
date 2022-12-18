package Lesson_6.ProductTest;

import Lesson_5.utils.RetrofitUtils;
import Lesson_6.api.ProductService;
import Lesson_6.db.dao.ProductsMapper;
import Lesson_6.db.model.Products;
import Lesson_6.db.model.ProductsExample;
import Lesson_6.dto.Product;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeleteProductPositiveTest {

    static ProductService productService;
    Product product = null;
    Faker faker = new Faker();
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
        products.setTitle("apple");
        products.setPrice(150);
        products.setCategory_id(1L);
        productsMapper.insert(products);
        session.commit();

        id = products.getId();

        products = productsMapper.selectByPrimaryKey(id);

        assertThat(products.getCategory_id(),equalTo(1L));
        assertThat(products.getPrice(), equalTo(150));
        assertThat(products.getTitle(), equalTo("apple"));

    }


//    @BeforeEach
//    void setUp() throws IOException {
//        product = new Product()
//                .withTitle(faker.food().ingredient())
//                .withCategoryTitle("Food")
//                .withPrice((int) (Math.random() * 10000));
//
//        Response<Product> response = productService.createProduct(product).execute();
//        id = response.body().getId();
//        assertThat(response.isSuccessful(), CoreMatchers.is(true));}

    @Test
    @SneakyThrows
    void deletePositiveTest() {
        Response<ResponseBody> response = productService.deleteProduct((int) id).execute();

        ProductsExample example = new ProductsExample();
        example.createCriteria().andIdEqualTo(id);
        List<Products> list = productsMapper.selectByExample(example);
        assertThat(list.size(), equalTo(0));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
    }

}