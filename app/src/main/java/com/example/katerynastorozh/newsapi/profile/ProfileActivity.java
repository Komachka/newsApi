package com.example.katerynastorozh.newsapi.profile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.katerynastorozh.newsapi.R;
import com.example.katerynastorozh.newsapi.login.model.UserModel;
import com.example.katerynastorozh.newsapi.main.view.MainActivity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;


public class ProfileActivity extends AppCompatActivity implements IProfileView, AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String LOG_TAG = ProfileActivity.class.getSimpleName();
    ProfilePresenter presenter;

    EditText firstNameEd;
    EditText lastNameEd;
    Spinner spinnerCountry;
    Spinner spinnerCity;
    EditText phoneEd;
    TextView dateTextField;
    Calendar myCalendar = Calendar.getInstance();

    final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateTextField.setText(dayOfMonth+"/"+month+"/"+year);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra(("singInMode"), false))
        {
            goToMainActivity();
        }

        setContentView(R.layout.activity_profile);
        presenter = new ProfilePresenter(this);

        firstNameEd = findViewById(R.id.firstName);
        lastNameEd = findViewById(R.id.lastName);
        phoneEd = findViewById(R.id.phone);

        spinnerCountry = (Spinner) findViewById(R.id.country_spinner);
        spinnerCountry.setAdapter(ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item));

        spinnerCity = (Spinner) findViewById(R.id.city_spinner);
        spinnerCity.setAdapter(ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_spinner_item));

        spinnerCity.setOnItemSelectedListener(this);
        spinnerCountry.setOnItemSelectedListener(this);
        dateTextField = findViewById(R.id.dateTextField);
        dateTextField.setOnClickListener(this);




    }



    @Override
    public void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void updateUI(UserModel user) {
        firstNameEd.setText(user.firstName);
        lastNameEd.setText(user.lastName);

        ArrayList<String> list=new ArrayList( Arrays.asList(getResources().getStringArray(R.array.city_array)) );
        int pos= list.indexOf(user.city);
        spinnerCountry.setSelection(pos);

        list=new ArrayList( Arrays.asList(getResources().getStringArray(R.array.country_array)) );
        pos= list.indexOf(user.country);
        spinnerCountry.setSelection(pos);


        dateTextField.setText(user.dob);
        phoneEd.setText(user.phone);
    }


    @Override
    protected void onStop() {
        super.onStop();
        //presenter.singOut();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void saveData(View view)
    {

        if (!checkValidationInput())
            return;
        HashMap<String, String> data = new HashMap<>();
        data.put("firstname", firstNameEd.getText().toString());
        data.put("lastname", lastNameEd.getText().toString());
        data.put("city", spinnerCity.getSelectedItem().toString());
        data.put("country", spinnerCountry.getSelectedItem().toString());
        data.put("dob", myCalendar.toString());
        data.put("phone", phoneEd.getText().toString());
        presenter.fillCurrentUserInfo(data);
    }

    private boolean checkValidationInput() {
        firstNameEd.setError(null);
        lastNameEd.setError(null);

        View focusView = null;

        // Store values at the time of the login attempt.
        String firstNameStr = firstNameEd.getText().toString();
        String lastNameStr = lastNameEd.getText().toString();
        String phonStr = phoneEd.getText().toString();

        if (TextUtils.isEmpty(firstNameStr)) {
            firstNameEd.setError("Can not be empty");
            focusView = firstNameEd;
            focusView.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(lastNameStr)) {
            lastNameEd.setError("Can not be empty");
            focusView = lastNameEd;
            focusView.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(phonStr)) {
            phoneEd.setError("Can not be empty");
            focusView = phoneEd;
            focusView.requestFocus();
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View v) {
        if (v == dateTextField)
        {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateListener, 25,1,2019);
            datePickerDialog.show();


        }
    }




}
