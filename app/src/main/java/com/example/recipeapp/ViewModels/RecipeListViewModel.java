package com.example.recipeapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {


    private boolean isViewingRecipes;
    private boolean isPerformingQuery;


    public void setPerformingQuery(boolean performingQuery) {
        isPerformingQuery = performingQuery;
    }

    RecipeRepository recipeRepository;

    public RecipeListViewModel() {
        recipeRepository = RecipeRepository.getInstance();
        isPerformingQuery = false;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipeRepository.getRecipes();
    }

    public void searchRecipeApiClient(String query,int pageNumber){
        isViewingRecipes=true;
        isPerformingQuery = true;
        recipeRepository.searchRecipeApiClient(query,pageNumber);
    }
    public void searchNexPage(){
        if(!isPerformingQuery &&  isViewingRecipes)
                recipeRepository.searchNextPage();
    }
    public void setIsViewingRecipes(boolean viewingRecipes) {
        isViewingRecipes = viewingRecipes;
    }
    public boolean isViewingRecipes() {
        return isViewingRecipes;
    }
    public void setViewingRecipes(boolean viewingRecipes) {
        isViewingRecipes = viewingRecipes;
    }
    public boolean isPerformingQuery() {
        return isPerformingQuery;
    }

    public boolean onBackPress() {
        if(isPerformingQuery){
           recipeRepository.cancelRequest();
        }
        if(isViewingRecipes){
            isViewingRecipes = false;
            return false;
        }
        return true;
    }
}
