package com.myapps.androidconcepts.Interfaces;

import com.myapps.androidconcepts.Models.AllTypesRetrofit_Model;
import com.myapps.androidconcepts.Models.CurrencyModel;
import com.myapps.androidconcepts.Models.FcmPostModel;
import com.myapps.androidconcepts.Models.RecyclerRetrofit_Model;
import com.myapps.androidconcepts.Models.Request_Model;
import com.myapps.androidconcepts.Models.Response_Model;
import com.myapps.androidconcepts.Models.RetrofitAirlines_Model;
import com.myapps.androidconcepts.Models.RetrofitEcommerce_Model;
import com.myapps.androidconcepts.Models.Retrofit_Model;
import com.myapps.androidconcepts.Models.WeatherModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApi {

    /*____________________________________________________________________________________________________________________________*/
    /*Below section fake apis belongs to https://jsonplaceholder.typicode.com  */

    @GET("users")
    Call<List<RecyclerRetrofit_Model>> getRecyclerModel();


    @GET("comments/")
    Call<List<Retrofit_Model>> getModel();


    @GET("posts")
    Call<List<AllTypesRetrofit_Model>> GET_data();

    @POST("posts")
    Call<AllTypesRetrofit_Model> POST_data(@Body AllTypesRetrofit_Model setPOST_model);

    @PUT("posts/{id}")
    Call<AllTypesRetrofit_Model> PUT_data(@Path("id") int id, @Body AllTypesRetrofit_Model setPUT_model);

    @PATCH("posts/{id}")
    Call<AllTypesRetrofit_Model> PATCH_data(@Path("id") int id, @Body AllTypesRetrofit_Model setPATCH_model);

    @DELETE("posts/{id}")
    Call<AllTypesRetrofit_Model> DELETE_data(@Path("id") int id);



    /*____________________________________________________________________________________________________________________________*/
    /*Below section fake apis belongs to https://gorest.co.in  */

    @POST("users")
    Call<Response_Model> postUserData(@Body Request_Model request_model);  //only retrofit approach

    @POST("users")
    Single<Response_Model> postUserData1(@Body Request_Model request_model);  //reactiveX with retrofit approach

    @GET("users")
    Single<List<Response_Model>> getAllPostData(@Query("page") int page, @Query("per_page") int per_page);

    /*@GET("users/{id}/")
    Call<Response_Model> getNetworkDetails(@Path("id") int id);*/   //if want to fetch certain user data with its id(params) from the list of users
                                                                    //then we use this query


    /*____________________________________________________________________________________________________________________________*/
    /*Below section fake apis belongs to https://fakestoreapi.com  */

    @GET("products")
    Call<List<RetrofitEcommerce_Model>> getEcommerceRetroModel();



    /*____________________________________________________________________________________________________________________________*/
    /*Below section fake apis belongs to https://picsum.photos  */

    @GET("v2/list")
    Call<String> STRING_CALL(@Query("page") int page, @Query("limit") int limit);

    /*@GET("v2/list")   //triedToGetUsingArrayList&ModelButNotGettingInThisWay.
    Call<List<Pagination_Model>> getPaginationModel(@Query("page") int page, @Query("limit") int limit);*/



    /*____________________________________________________________________________________________________________________________*/
    /*Below section fake apis belongs to https://api.instantwebtools.net , but sometimes not working. */

    @GET("airlines")
    Call<List<RetrofitAirlines_Model>> getAirlinesData();

    @GET("airlines")
    Call<String> STRING_CALL1(@Query("page") int page, @Query("limit") int limit);



    /*____________________________________________________________________________________________________________________________*/
    /*Below fake api belongs to https://api.weather.gov/gridpoints/TOP/31,80/  we're using this api in another interfaces.*/

    @GET("forecast")
    Call<WeatherModel> getWeatherModel();



    /*____________________________________________________________________________________________________________________________*/
    /*Below section fake apis belongs to http://api.coinlayer.com/api/  */

    //@GET("live?access_key=3b11ed83db02736b9dca66c9a6e51f8c")
    @GET("live")
    Call<CurrencyModel> getCurrencyModel(@Query("access_key") String key);



    /*____________________________________________________________________________________________________________________________*/
    /*Below section api belongs to https://fcm.googleapis.com/fcm/send  */

    @POST("send")               //present we're using volley process for this fcm_api.
    Call<FcmPostModel> postFcmData(@Body FcmPostModel fcmPostModel);



    /*____________________________________________________________________________________________________________________________*/
    /*Below section api belongs to https://fssservices.bookxpert.co/api/getownerslist/2021-01-16/payments/owner  */

    @GET("owner")
    Call<String> STRING_ACT_CALL();

}