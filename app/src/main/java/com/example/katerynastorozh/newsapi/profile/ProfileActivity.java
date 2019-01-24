package com.example.katerynastorozh.newsapi.profile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.katerynastorozh.newsapi.R;
import com.example.katerynastorozh.newsapi.login.model.UserModel;
import com.example.katerynastorozh.newsapi.login.presenter.LoginPresenterCompl;
import com.example.katerynastorozh.newsapi.main.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements IProfileView, AdapterView.OnItemSelectedListener {

    public static final String LOG_TAG = ProfileActivity.class.getSimpleName();
    ProfilePresenter presenter;

    EditText firstNameEd;
    EditText lastNameEd;
    Spinner spinnerCountry;
    Spinner spinnerCity;
    EditText phoneEd;
    DatePicker dobPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        presenter = new ProfilePresenter(this);
        spinnerCountry = (Spinner) findViewById(R.id.country_spinner);
        spinnerCountry.setAdapter(ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item));

        spinnerCity = (Spinner) findViewById(R.id.city_spinner);
        spinnerCity.setAdapter(ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item));

        spinnerCity.setOnItemSelectedListener(this);
        spinnerCountry.setOnItemSelectedListener(this);






    }

    public void updateProfile(UserModel curUser) {
        Toast.makeText(this, curUser.email + " " + curUser.getFirstName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
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
        HashMap<String, String> data = new HashMap<>();
        data.put("firstname", firstNameEd.getText().toString());
        data.put("lastname", lastNameEd.getText().toString());
        data.put("city", spinnerCity.getSelectedItem().toString());
        data.put("country", spinnerCountry.getSelectedItem().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.set(dobPicker.getYear(), dobPicker.getMonth(), dobPicker.getDayOfMonth());
        data.put("dob", calendar.toString());
        data.put("phone", phoneEd.getText().toString());
        presenter.fillCurrentUserInfo(data);
    }
}
