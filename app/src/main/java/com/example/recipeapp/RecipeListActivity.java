package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.ViewModels.RecipeListViewModel;
import com.example.recipeapp.adapters.OnRecipeListener;
import com.example.recipeapp.adapters.RecipeRecyclerAdapter;
import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.utils.VerticalSpacingItem;

import java.util.List;


public class RecipeListActivity extends BaseActivity  implements OnRecipeListener {
    private RecyclerView recyclerView;
    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    private static final String TAG ="RecipeListActivity" ;
    private RecipeListViewModel recipeListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recyclerView = findViewById(R.id.recyclerView);
        recipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        initSearchView();
        if(!recipeListViewModel.isViewingRecipes()){
            displaySearchCategories();
        }
        setSupportActionBar(findViewById(R.id.toolbar));
    }
    private void initRecyclerView(){
        recipeRecyclerAdapter = new RecipeRecyclerAdapter(this);
        VerticalSpacingItem spacingItem = new VerticalSpacingItem(30);
        recyclerView.addItemDecoration(spacingItem);
        recyclerView.setAdapter(recipeRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1))
                recipeListViewModel.searchNexPage();
            }
        });
    }
    private void subscribeObservers(){
        recipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes!=null){
                    if(recipeListViewModel.isViewingRecipes()) {
                        recipeListViewModel.setPerformingQuery(false);
                        recipeRecyclerAdapter.setRecipes(recipes);
                    }
                }
            }
        });
    }
    private void searchRecipeApiClient(String query,int pageNumber){
        recipeListViewModel.searchRecipeApiClient(query,pageNumber);
    }

    private void initSearchView(){
        final SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                recipeRecyclerAdapter.displayLoading();
                searchRecipeApiClient(query,1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @Override
    public void onRecipeClick(int i) {
        Intent intent = new Intent(this,RecipeActivity.class);
        intent.putExtra("recipe",recipeRecyclerAdapter.getSelectedRecipe(i));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {
        recipeRecyclerAdapter.displayLoading();
        recipeListViewModel.searchRecipeApiClient(category,1);
    }
    private void displaySearchCategories(){
        Log.d(TAG, "displaySearchCategories: called.");
        recipeListViewModel.setIsViewingRecipes(false);
        recipeRecyclerAdapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed() {
        if(recipeListViewModel.onBackPress()){
            super.onBackPressed();
        }else{
            displaySearchCategories();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_categories){
            displaySearchCategories();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}