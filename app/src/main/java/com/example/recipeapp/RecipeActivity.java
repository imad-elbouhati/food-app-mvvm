package com.example.recipeapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.recipeapp.models.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RecipeActivity extends BaseActivity {
    private AppCompatImageView imageView;
    private TextView recipeTitle,recipeRank;
    private LinearLayout recipeIngredientsContainer;
    private ScrollView scrollView;
    private static final String TAG = "RecipeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        imageView = findViewById(R.id.recipeImage);
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeRank = findViewById(R.id.recipe_social_score);
        scrollView = findViewById(R.id.parent);
        recipeIngredientsContainer = findViewById(R.id.ingredients_container);
        getIncomingIntetnt();

    }
    private void getIncomingIntetnt(){
        if(getIntent().hasExtra("recipe")){
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d(TAG, "getIncomingIntetnt: "+recipe.getTitle());
        }
    }
}