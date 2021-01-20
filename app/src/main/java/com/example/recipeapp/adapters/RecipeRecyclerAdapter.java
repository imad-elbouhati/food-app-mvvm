package com.example.recipeapp.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.recipeapp.R;
import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int RECIPE_TYPE = 1;
    public static final int LOADING_TYPE = 2;
    public static final int CATEGORY_TYPE = 3;
    List<Recipe> recipes ;
    OnRecipeListener onRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case RECIPE_TYPE:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_recipe_list_item,parent,false);
                return new RecipeViewHolder(view,onRecipeListener);
            }
            case LOADING_TYPE : {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_loading_item,parent,false);
                return new LoadingViewHolder(view);
            }
            case CATEGORY_TYPE :{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_list_item,parent,false);
                return new CategoryViewHolder(view,onRecipeListener);
            }
            default:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_recipe_list_item,parent,false);
                return new RecipeViewHolder(view,onRecipeListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);

        if(itemViewType == RECIPE_TYPE){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipes.get(position).getFeatured_image())
                    .into(((RecipeViewHolder)holder).image);

            ((RecipeViewHolder)holder).title.setText(recipes.get(position).getTitle());
            ((RecipeViewHolder)holder).publisher.setText(recipes.get(position).getPublisher());
            ((RecipeViewHolder)holder).socialScore.setText(String.valueOf(Math.round(recipes.get(position).getRating())));
        }else if(itemViewType==CATEGORY_TYPE){
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);
            Uri path = Uri.parse("android.resource://"+R.class.getPackage().getName()+"/drawable/" + recipes.get(position).getFeatured_image());
            Glide.with(((CategoryViewHolder)holder).itemView)
                    .setDefaultRequestOptions(requestOptions)
                    .load(path)
                    .into(((CategoryViewHolder)holder).categoryImage);
            ((CategoryViewHolder)holder).categoryTitle.setText(recipes.get(position).getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(recipes.get(position).getRating() == -1){
            return CATEGORY_TYPE;
        }
        else if(recipes.get(position).getTitle().equals("LOADING...")) {
            return LOADING_TYPE;
        }
        else if(position==recipes.size()-1
                && position!=0
                && !recipes.get(position).getTitle().equals("EXHAUSTED...")){
            return LOADING_TYPE;
        }
        else{
            return RECIPE_TYPE;
        }
    }
    public void displayLoading(){
        if(!isLoading()){
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            recipes = loadingList;
            notifyDataSetChanged();
        }
    }
    private boolean isLoading(){
        if(recipes!=null) {
            if (recipes.size() > 0) {
                return recipes.get(recipes.size() - 1).getTitle().equals("LOADING...");
            }
        }
        return false;
    }
    @Override
    public int getItemCount() {
        if(recipes != null){
            return  recipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
    public void displaySearchCategories(){
        List<Recipe> categories = new ArrayList<>();
        for(int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORIES.length; i++){
            Recipe recipe = new Recipe();
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setFeatured_image(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            recipe.setRating(-1);
            categories.add(recipe);
        }
        recipes = categories;
        notifyDataSetChanged();
    }

    public Recipe getSelectedRecipe(int position){
        if(recipes!=null){
            if(recipes.size() > 0 ){
                return recipes.get(position);
            }
        }
        return null;
    }
}
