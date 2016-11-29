package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static com.taek_aaa.locationdiary.DataSet.mainCategory_arr_index;
import static com.taek_aaa.locationdiary.DataSet.subCategory_arr_index;


public class List extends Activity {
    Spinner mainspinner;
    Spinner subspinner;
    TextView startdayTv, enddayTv;
    int iYears;     //이는 컴퓨터가 인지하는
    int iMonths;
    int iDates;
    int hMonths;  //h가 붙은 것들은 사람이 아는 값
    int iYeare;     //이는 컴퓨터가 인지하는
    int iMonthe;
    int iDatee;
    int hMonthe;  //h가 붙은 것들은 사람이 아는 값


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        startdayTv = (TextView)findViewById(R.id.Text_StartDay);
        enddayTv = (TextView)findViewById(R.id.Text_EndDay);

        Calendar today;
        today = Calendar.getInstance();
        iYears = today.get(Calendar.YEAR);
        iMonths = today.get(Calendar.MONTH) ;
        iDates = today.get(Calendar.DAY_OF_MONTH);
        hMonths = today.get(Calendar.MONTH)+1;
        iYeare = today.get(Calendar.YEAR);
        iMonthe = today.get(Calendar.MONTH) ;
        iDatee = today.get(Calendar.DAY_OF_MONTH);
        hMonthe = today.get(Calendar.MONTH)+1;


        startdayTv.setText(iYears + "년 " + hMonths + "월 " + iDates + "일");
        enddayTv.setText(iYeare + "년 " + hMonthe + "월 " + iDatee + "일");



        mainspinner = (Spinner) findViewById(R.id.mainCategoryspinner);
        subspinner = (Spinner) findViewById(R.id.subCategoryspinner);
        mainspinner.setSelection(0);
        subspinner.setSelection(0);

        mainspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainCategory_arr_index = position;
                switch (position) {
                    case (0):
                        populateSubSpinners(R.array.subSpinnerContentsCategory);
                        break;
                    case (1):
                        populateSubSpinners(R.array.subSpinnerContentsTime);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subCategory_arr_index = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populateSubSpinners(int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this,
                itemNum,
                android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subspinner.setAdapter(fAdapter);
    }

    public void onclickstart(View v){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { //datepicker


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                hMonths = monthOfYear+1;
                TextView caltv = (TextView) findViewById(R.id.Text_StartDay);
                caltv.setText(year + "년 " + hMonths + "월 " + dayOfMonth + "일");

                iYears = year;                 //이부분을 하지 않으면 클릭하여서 날짜를 바꾸면 그게 DatePickerDialog에 반영되지 않음
                iMonths = monthOfYear;
                iDates = dayOfMonth;


            }
        };
        new DatePickerDialog(this, dateSetListener, iYears, iMonths, iDates).show();
    }
    public void onclickend(View v){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { //datepicker


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                hMonthe = monthOfYear+1;
                TextView caltv = (TextView) findViewById(R.id.Text_EndDay);
                caltv.setText(year + "년 " + hMonthe + "월 " + dayOfMonth + "일");

                iYeare = year;                 //이부분을 하지 않으면 클릭하여서 날짜를 바꾸면 그게 DatePickerDialog에 반영되지 않음
                iMonthe = monthOfYear;
                iDatee = dayOfMonth;


            }
        };
        new DatePickerDialog(this, dateSetListener, iYeare, iMonthe, iDatee).show();

    }

    public void onClickspinnerShowbtn(View v){
        final int datestart = iYears*10000 + iMonths*100 + iDates;
        final int dateend = iYeare*10000 + iMonthe*100 + iDatee;
        if (datestart > dateend) {
            Toast.makeText(this, "잘못된 입력이 있습니다.", Toast.LENGTH_SHORT).show();
        }


    }

}
