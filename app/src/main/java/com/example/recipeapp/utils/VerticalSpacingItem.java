package com.example.recipeapp.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacingItem extends RecyclerView.ItemDecoration {
    private final int spacingHeight;

    public VerticalSpacingItem(int spacingHeight) {
        this.spacingHeight = spacingHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.top = spacingHeight;
    }
}
