package com.example.recipeapp.requests;

import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.requests.responses.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RecipeApi {
    @GET("api/recipe/search")
    Call<RecipeSearchResponse> searchRecipe(
        @Query("page")String page,
        @Query("query")String query,
        @Header("Authorization")String auth
    );
    @GET("api/recipe/get")
    Call<Recipe> getRecipe(
      @Query("id")String id,
      @Header("Authorization")String auth
    );
}
