package com.example.katerynastorozh.newsapi.login.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.katerynastorozh.newsapi.R;
import com.example.katerynastorozh.newsapi.login.model.IUser;
import com.example.katerynastorozh.newsapi.login.presenter.LoginPresenterCompl;
import com.example.katerynastorozh.newsapi.main.view.MainActivity;
import com.example.katerynastorozh.newsapi.profile.ProfileActivity;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {


    // UI references.
    private Boolean singInMode;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordViewConfirm;
    private View mProgressView;
    private View mLoginFormView;
    private Button mEmailSignInButton;
    private Button mEmailSignUpButton;

    private TextView changeToSingUp;
    private TextView changeToSingIn;
    private LoginPresenterCompl loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenterCompl(this);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordViewConfirm = findViewById(R.id.password_confirm);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignUpButton = findViewById(R.id.email_sign_up_button);
        singInMode= true;
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        changeToSingUp = findViewById(R.id.change_to_singup);
        changeToSingIn = findViewById(R.id.change_to_singin);
        mEmailSignInButton.setOnClickListener(this);
        mEmailSignUpButton.setOnClickListener(this);
        changeToSingUp.setOnClickListener(this);
        changeToSingIn.setOnClickListener(this);
    }


    private void setSingInMode()
    {
        mEmailSignInButton.setVisibility(View.VISIBLE);
        changeToSingUp.setVisibility(View.VISIBLE);

        mEmailSignUpButton.setVisibility(View.GONE);
        changeToSingIn.setVisibility(View.GONE);
        mPasswordViewConfirm.setVisibility(View.GONE);
        singInMode = true;
    }


    private void setSingUpMode()
    {
        mEmailSignUpButton.setVisibility(View.VISIBLE);
        changeToSingIn.setVisibility(View.VISIBLE);
        mPasswordViewConfirm.setVisibility(View.VISIBLE);

        mEmailSignInButton.setVisibility(View.GONE);
        changeToSingUp.setVisibility(View.GONE);
        singInMode = false;
    }


    private void attemptLogin() {

        if (loginPresenter.isLoginIn())
        {
            Toast.makeText(this, "Is login in", Toast.LENGTH_LONG).show();
        }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (!singInMode)
        {

            String confirm = mPasswordViewConfirm.getText().toString();
            focusView = mPasswordViewConfirm;
            cancel = !confirm.equals(password);
            mPasswordViewConfirm.setError(getString(R.string.error_diff_pass));
            Toast.makeText(this, "cancel = " + cancel, Toast.LENGTH_LONG).show();
        }



        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            onSetProgressBarVisibility(true);
            if (singInMode)
                loginPresenter.singIN(email, password);
            else
                loginPresenter.singUP(email, password);
        }
    }

    public boolean isEmailValid(String email) {
        return email.contains("@");
    }

    @Override
    public void updateUi(FirebaseUser user) {
        if (user != null)
        {
            Toast.makeText(this, "You are login in as " + user.getDisplayName(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void startProfileActivity() {

        startActivity(new Intent(this, ProfileActivity.class));
    }



    public boolean isPasswordValid(String password) {
        return password.length() > 4;
    }



    @Override
    public void onClearText() {
        mEmailView.setText("");
        mPasswordView.setText("");
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        //if result ok - go to main activity
        //if no make a toast with message


        loginPresenter.setProgressBarVisiblity(false);
        mEmailSignInButton.setEnabled(true);
        if (result){
            Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Login Fail, code = " + code,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetProgressBarVisibility(final Boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }

    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.change_to_singup:
            {
                setSingUpMode();
                break;
            }
            case R.id.change_to_singin:
            {
                setSingInMode();
                break;
            }
            case R.id.email_sign_in_button:
            {
                attemptLogin();
                break;
            }
            case R.id.email_sign_up_button:
            {
                attemptLogin();
                break;
            }
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        loginPresenter.removeAuthListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginPresenter.addAuthListener();
    }
}

