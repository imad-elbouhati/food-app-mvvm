package com.example.recipeapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView categoryTitle;
    CircleImageView categoryImage;
    OnRecipeListener listener;

    public CategoryViewHolder(@NonNull View itemView,OnRecipeListener listener) {
        super(itemView);
        this.listener = listener;
        categoryTitle = itemView.findViewById(R.id.category_title);
        categoryImage = itemView.findViewById(R.id.category_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onCategoryClick(categoryTitle.getText().toString());
    }
}
