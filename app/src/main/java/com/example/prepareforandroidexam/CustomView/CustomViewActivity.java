package com.example.prepareforandroidexam.CustomView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.prepareforandroidexam.R;

public class CustomViewActivity extends AppCompatActivity {
ModuleStatusView moduleStatusView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        moduleStatusView=(ModuleStatusView) findViewById(R.id.view);
        loadModuleStatusView();
    }


    private void loadModuleStatusView(){
        int totalNumberOfModules=11;
        int completedModules=7;
        boolean[] modulesArray=new boolean[totalNumberOfModules];
        for(int moduleIndex=0;moduleIndex<completedModules;moduleIndex++){
            modulesArray[moduleIndex]=true;
        }
        moduleStatusView.setmModuleStatus(modulesArray);
    }

}
