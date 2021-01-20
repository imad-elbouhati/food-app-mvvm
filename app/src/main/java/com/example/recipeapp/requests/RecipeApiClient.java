package com.example.recipeapp.requests;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.recipeapp.AppExecutors;
import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.repositories.RecipeRepository;
import com.example.recipeapp.requests.responses.RecipeSearchResponse;
import com.example.recipeapp.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class RecipeApiClient {
    private static final String TAG = "RecipeApiClient";
    RetrieveRecipesRunnable retrieveRecipesRunnable;
    MutableLiveData<List<Recipe>> mRecipes;
    private static RecipeApiClient instance;

    public static RecipeApiClient getInstance() {
        if(instance==null){
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private RecipeApiClient(){
        mRecipes = new MutableLiveData<>();
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public void searchRecipeApi(String query,int pageNumber){
        if(retrieveRecipesRunnable!=null){
            retrieveRecipesRunnable = null;
        }
        retrieveRecipesRunnable = new RetrieveRecipesRunnable(query,pageNumber);
        final Future handler = AppExecutors.getInstance().getNetWorkIO().submit(retrieveRecipesRunnable);

        AppExecutors.getInstance().getNetWorkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }
    public void cancelRequest(){
        if(retrieveRecipesRunnable!=null)
            retrieveRecipesRunnable.cancelRequest();
    }

    private class RetrieveRecipesRunnable implements Runnable {
        String query;
        int pageNumber;
        boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query,pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code()==200){
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse)response.body()).getRecipes());
                    if (pageNumber == 1){
                        mRecipes.postValue(list);
                    }else{
                        List<Recipe> currentList = mRecipes.getValue();
                        currentList.addAll(list);
                        mRecipes.postValue(currentList);
                    }
                }else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: "+error);
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecipes.postValue(null);
            }
        }
        Call<RecipeSearchResponse> getRecipes (String query,int pageNumber){

            return ServiceGenerator.getRecipeApi().searchRecipe(
                    String.valueOf(pageNumber),
                    query,
                    Constants.KEY_API
            );
        }
        private void cancelRequest(){
            cancelRequest = true;
        }
    }

}
