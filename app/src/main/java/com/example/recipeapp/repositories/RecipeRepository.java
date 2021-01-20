package com.example.recipeapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {
    private String mQuery;
    private int mPageNumber;
    RecipeApiClient recipeApiClient;
    private static RecipeRepository instance;
    public static RecipeRepository getInstance() {
        if(instance==null) {
            instance = new RecipeRepository();
        }
        return instance;
    }
    private RecipeRepository(){
       recipeApiClient = RecipeApiClient.getInstance();
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return recipeApiClient.getRecipes();
    }
    public void searchRecipeApiClient(String query,int pageNumber){

       if(pageNumber==0){
           pageNumber=1;
       }
        mQuery = query;
        mPageNumber = pageNumber;
        recipeApiClient.searchRecipeApi(query,pageNumber);
    }
    public void searchNextPage(){
        recipeApiClient.searchRecipeApi(mQuery,mPageNumber+1);
    }
    public void cancelRequest(){
        recipeApiClient.cancelRequest();
    }
}
