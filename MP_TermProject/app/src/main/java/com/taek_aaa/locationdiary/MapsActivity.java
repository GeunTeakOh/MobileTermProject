package com.taek_aaa.locationdiary;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;
import static com.taek_aaa.locationdiary.DataSet.category_arr;
import static com.taek_aaa.locationdiary.DataSet.llistCategory;
import static com.taek_aaa.locationdiary.DataSet.llistLatitude;
import static com.taek_aaa.locationdiary.DataSet.llistLocation;
import static com.taek_aaa.locationdiary.DataSet.llistLongitude;
import static com.taek_aaa.locationdiary.DataSet.llistNum;
import static com.taek_aaa.locationdiary.DataSet.llistText;
import static com.taek_aaa.locationdiary.DataSet.llistTime;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Spinner spinner;
    String type_str = "";
    EditText editText;
    LinearLayout type_ll;
    static String outermemo ;
    int slistsize;
    static int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        MapsInitializer.initialize(getApplicationContext());
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int listsize = llistLongitude.size();
        slistsize=listsize;
        for (int i = 0; i < listsize; i++) {
            MarkerOptions opt = new MarkerOptions();
            opt.position(llistLocation.get(i));
            opt.title(llistNum.get(i));
            opt.snippet(llistText.get(i)+"@"+llistTime.get(i));

            mMap.addMarker(opt).showInfoWindow();
            if (i != 0) {
                mMap.addPolyline(new PolylineOptions().geodesic(true).add(new LatLng(Double.valueOf(llistLatitude.get(i - 1)), Double.valueOf(llistLongitude.get(i - 1))), new LatLng(Double.valueOf(llistLatitude.get(i)), Double.valueOf(llistLongitude.get(i)))).width(5).color(Color.RED));
            }
        }
        mMap.moveCamera(newLatLng(llistLocation.get(0)));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MapsActivity.this);
                type_ll = new LinearLayout(MapsActivity.this);
                setSpinner();
                setEditText();
                type_ll.addView(spinner);
                type_ll.addView(editText);
                type_ll.setPadding(50, 0, 0, 0);
                int a = Integer.valueOf(marker.getTitle());
                temp=a;
                adb
                        .setTitle("메모")
                        .setCancelable(false)
                        .setMessage("메모를 입력해 주세요.")
                        .setView(type_ll)
                        .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                outermemo = editText.getText().toString();
                                llistText.set(temp,outermemo);
                                Log.d("ppp",String.valueOf(temp));
                                SimpleDateFormat df = new SimpleDateFormat("MM/dd/hh:mm");
                                Date clsTime = new Date();
                                String result = df.format(clsTime);
                                llistTime.set(temp,result);
                                llistCategory.set(temp,type_str);
                                temp=0;
                                type_str = "";
                                final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                                mapFragment.getMapAsync(MapsActivity.this);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog ad = adb.create();
                ad.show();
            }

        });
    }

    public void setSpinner() {
        spinner = new Spinner(this);
        ArrayAdapter memoAdapter = new ArrayAdapter(MapsActivity.this, android.R.layout.simple_spinner_item, category_arr);
        memoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(memoAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_str = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void setEditText(){
        editText = new EditText(this);
        editText.setHint("메모를 입력하세요.");
        editText.setHintTextColor(0x50000000);
        editText.setEms(12);
    }
}
