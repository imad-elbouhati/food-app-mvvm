package com.example.recipeapp;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public abstract class BaseActivity extends AppCompatActivity {
    public ProgressBar progressBar;
    @Override
    public void setContentView(int layoutResID) {
       ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base,null);
       FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
       progressBar = constraintLayout.findViewById(R.id.progressBar);
       getLayoutInflater().inflate(layoutResID,frameLayout,true);
       super.setContentView(constraintLayout);
    }

    public void showProgressBar(boolean visibility){
        progressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
}
