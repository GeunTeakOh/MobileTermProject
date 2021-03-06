package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import static com.taek_aaa.locationdiary.DataSet.moveCameraIter;

/**
 * Created by taek_aaa on 2016. 11. 26..
 */

/** 메인 부분  **/
public class MainActivity extends Activity {
    private long lastTimeBackPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inputbtn:
                startActivity(new Intent(this, InsertActivity.class));
                break;
            case R.id.mapbtn:
                startActivity(new Intent(this, MapsActivity.class));
                break;
            case R.id.staticbtn:
                startActivity(new Intent(this, List.class));
                break;
            case R.id.goalbtn:
                startActivity(new Intent(this, GoalActivity.class));
                break;
            case R.id.exitbtn:
                moveCameraIter=0;

                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            moveCameraIter=0;
            finish();
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            return;
        }
        Toast.makeText(MainActivity.this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
