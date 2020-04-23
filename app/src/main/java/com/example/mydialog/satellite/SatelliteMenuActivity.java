package com.example.mydialog.satellite;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

;import com.example.mydialog.R;
import com.example.mydialog.satellite.view.ArcMenu;

public class SatelliteMenuActivity extends Activity implements View.OnClickListener {

    private ArcMenu arcMenu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statellite_menu);

        initView();
    }

    private void initView() {
        arcMenu = findViewById(R.id.arcMenu);
        findViewById(R.id.btn).setOnClickListener(this);
        arcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if (arcMenu.isOpen()) {
                    arcMenu.toggleMenu(600);
                }
                break;
        }
    }
}
