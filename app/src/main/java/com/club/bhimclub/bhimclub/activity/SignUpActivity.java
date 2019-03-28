package com.club.bhimclub.bhimclub.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.club.bhimclub.bhimclub.EndPointAPIcall;
import com.club.bhimclub.bhimclub.R;
import com.club.bhimclub.bhimclub.model.LoginInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SignUpActivity extends BaseActivity {

    private Unbinder unbinder;// UI references.
    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.full_name)
    EditText mFullName;
    @BindView(R.id.phone_number)
    EditText mPhoneNumber;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.repassword)
    EditText mRetryPasswordView;
    @BindView(R.id.type_spinner)
    Spinner mTypeSpinner;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));


        unbinder = ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        this.getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.select_user_type, R.layout.spinner_item_white);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mTypeSpinner.setAdapter(adapter);


    }

    @OnItemSelected(R.id.type_spinner)
    void onItemSelected(int pos) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Toast.makeText(this, "Selected position " + pos + ", " + mTypeSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.sign_up_button)
    public void UserSignup(){
        Timber.d("NewUser Signup");
        boolean cancel = false;
        View focusView = null;
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mRetryPasswordView.setError(null);
        mFullName.setError(null);
        mPhoneNumber.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String rePassword = mRetryPasswordView.getText().toString();
        String fullName = mFullName.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();

        if (TextUtils.isEmpty(fullName)) {
            mFullName.setError(getString(R.string.error_field_required));
            focusView = mFullName;
            cancel = true;
        }else if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }else  if (mTypeSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select user type." , Toast.LENGTH_SHORT).show();
            focusView= mTypeSpinner;
            cancel=true;
        }else  if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumber.setError(getString(R.string.error_field_required));
            focusView = mPhoneNumber;
            cancel = true;
        }else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }else if (!TextUtils.isEmpty(rePassword) && !isPasswordValid(rePassword)) {
            mRetryPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mRetryPasswordView;
            cancel = true;
        }else if (!rePassword.equals(password)) {
            mRetryPasswordView.setError(getString(R.string.error_incorrect_repassword));
            focusView = mRetryPasswordView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            setProgressDialog();
            showProgressDialog(true);
            EndPointAPIcall endPointAPIcall = retrofit.create(EndPointAPIcall.class);
            Observable<LoginInfo> call = endPointAPIcall.getSignUpInfo(fullName, email, phoneNumber, password, mTypeSpinner.getSelectedItem().toString());
            call.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .map(result ->result)
                    .subscribe(this::handleResults, this::handleError);
        }








    }

    private void handleError(Throwable throwable) {
        showProgressDialog(false);
        Toast.makeText(SignUpActivity.this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }

    private void handleResults(LoginInfo loginInfo) {
        showProgressDialog(false);


        if(loginInfo.getSuccess() == 1){
            Toast.makeText(getApplicationContext(), loginInfo.getMessage() + " completed, Please try to do login.",
                    Toast.LENGTH_LONG).show();
            Intent mySuperIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(mySuperIntent);
            finish();
        }else{

            Toast.makeText(SignUpActivity.this, loginInfo.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
