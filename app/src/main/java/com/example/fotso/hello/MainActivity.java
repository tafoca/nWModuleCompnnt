package com.example.fotso.hello;

import android.graphics.Color;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ModuleStatusView moduleStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        moduleStatusView =  (ModuleStatusView) findViewById(R.id.moduleStatus);
        loadModuleStatusValues();
    }

    private void loadModuleStatusValues() {
        int totalNumModules = 11;
        int completeNumModules =7;
        boolean[] moduleStatus = new boolean[totalNumModules];
        for (int moduleIndex =0; moduleIndex < completeNumModules;moduleIndex++){
            moduleStatus[moduleIndex] =true;
            moduleStatusView.setmModuleStatus(moduleStatus);
        }

    }

    public void connection(View view) {
        moduleStatusView.setBackgroundColor(Color.rgb(12,12,00));

    }
}
