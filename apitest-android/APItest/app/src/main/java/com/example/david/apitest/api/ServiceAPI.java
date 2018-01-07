package com.example.david.apitest.api;

import com.example.david.apitest.model.Chat;
import com.example.david.apitest.model.Food;
import com.example.david.apitest.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

/**
 * Created by david on 14/5/17.
 */


public interface ServiceAPI {

    /**
     * User calls
     */
    @GET("api/user/{userId}")
    Call<User> getUser (@Path("userId") String userId);

    @POST("/api/user")
    @FormUrlEncoded
    Call<User> createUser(@Field("email") String email,
                          @Field("displayName") String displayName,
                          @Field("password") String password,
                          @Field("avatar") String avatar,
                          @Field("description") String description);

    @PUT("/api/user/{userId}")
    @FormUrlEncoded
    Call<User> updateUser(@Path("userId") String userId,
                          @Field("email") String email,
                          @Field("displayName") String displayName,
                          @Field("password") String password,
                          @Field("avatar") String avatar,
                          @Field("description") String description);

    @DELETE("api/user/{userId}")
    Call<User> deleteUser (@Path("userId") String userId);

    @GET("api/user/login/{email}/{password}")
    Call<User> loginUser (@Path("email") String email,
                        @Path("password") String password);


    /**
     * Food calls
     */
    @GET("api/food/{foodId}")
    Call<Food> getFood (@Path("foodId") String foodId);

    @POST("/api/food")
    @FormUrlEncoded
    Call<Food> createFood(@Field("title") String title,
                          @Field("image") String image,
                          @Field("price") Float price,
                          @Field("latitude") Float latitude,
                          @Field("longitude") Float longitude,
                          @Field("userId") String userId);

    @PUT("/api/food/{foodId}")
    @FormUrlEncoded
    Call<Food> updateFood(@Path("foodId") String foodId,
                          @Field("title") String title,
                          @Field("image") String image,
                          @Field("price") Float price,
                          @Field("latitude") Float latitude,
                          @Field("longitude") Float longitude,
                          @Field("userId") String userId);

    @DELETE("api/food/{foodId}")
    Call<Food> deleteFood (@Path("foodId") String foodId);

    @GET("api/foodlist")
    Call<Food.FoodList> getFoodList();

    /*@GET("api/food/location/{longitude}/{latitude}")
    Call<Food> getFoodListByLocation (@Path("longitude") String longitude,
                          @Path("latitude") String latitude);
    */


    /**
     * Chat calls
     */
    @POST("/api/chat/")
    @FormUrlEncoded
    Call<Chat> createChat(@Field("foodId") String foodId,
                          @Field("uid1") String uid1,
                          @Field("dn1") String dn1,
                          @Field("uid2") String uid2,
                          @Field("dn2") String dn2);

    @GET("/api/chat/{chatId}")
    Call<Chat> getChat(@Path("chatId") String chatId);

    @DELETE("/api/chat/{chatId}")
    Call<Chat> deleteChat(@Path("chatId") String chatId);

    @PUT("/api/chat/{chatId}")
    @FormUrlEncoded
    Call<Chat> sendChatMessage(@Path("chatId") String chatId,
                               @Field("userId") String userId,
                               @Field("message")String message);

}
