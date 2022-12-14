package Lesson_5.api;

import Lesson_5.dto.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ProductService {

    @GET("products")
    Call<ResponseBody> getProduct();

    @PUT("products")
    Call<Product> modifyProduct(@Body Product modifyProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") int id);

    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);
}
