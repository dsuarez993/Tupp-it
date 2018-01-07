package com.example.david.apitest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by david on 18/5/17.
 */

public class Food {

    @SerializedName("food")
    @Expose
    public FoodObject data;


    public class FoodObject {

        @SerializedName("title")
        @Expose
        public String title;

        @SerializedName("image")
        @Expose
        public String image;

        @SerializedName("price")
        @Expose
        public Float price;

        @SerializedName("location")
        @Expose
        public LocationObject location;

        @SerializedName("userId")
        @Expose
        public String userId;

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("_id")
        @Expose
        public String id;

    }

    public class LocationObject{

        @SerializedName("latitude")
        @Expose
        public float latitude;

        @SerializedName("longitude")
        @Expose
        public float longitude;

    }

    public class FoodList{
        @SerializedName("food")
        @Expose
        public List<FoodObject> foodList;
    }
}
