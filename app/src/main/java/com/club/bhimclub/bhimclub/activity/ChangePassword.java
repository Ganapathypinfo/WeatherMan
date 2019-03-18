package com.club.bhimclub.bhimclub.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.club.bhimclub.bhimclub.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePassword extends BaseActivity  {
    private static final String TAG = ChangePassword.class.getSimpleName();
    private EditText newPassword;
    private EditText oldPassword;
    private EditText confirmPassword;
    private CheckBox showPassword;
    private SharedPreferences mLoginSettings;
//    private String from="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassworddialog);
//        from=getIntent().getExtras().getString("from");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setFinishOnTouchOutside(false);
        this.setTitle("Change password                ");
        oldPassword = findViewById(R.id.oldpassword);
        newPassword = findViewById(R.id.newpassword);
        confirmPassword = findViewById(R.id.confirmpassword);
        showPassword = findViewById(R.id.showPass);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    oldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    oldPassword.setInputType(129);
                    newPassword.setInputType(129);
                    confirmPassword.setInputType(129);
                }
            }
        });
        TextView ok = findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View view) {
                String password[] = new String[3];
                password[0] = newPassword.getText().toString();
                password[1] = confirmPassword.getText().toString();
                password[2] = oldPassword.getText().toString();
                if ("".equals(password[2])) {
                    newPassword.setError("Old Password is required");
                }
                else if ("".equals(password[0])) {
                    newPassword.setError("New Password is required");
                } else if ("".equals(password[1])) {
                    confirmPassword.setError("New Password is required");
                } /*else if (!isValidPassword(password[0])) {
                    newPassword.setError("Password invalid");
                }*/
                else if (!password[0].equals(password[1])) {
                    confirmPassword.setError("Password do not match");
                } else {

//                    new LongOperation().execute(password);
                }
            }
        });

        TextView cancel = findViewById(R.id.cancel);
        /*if(from.equalsIgnoreCase("dashboard")){
            cancel.setEnabled(false);
            cancel.setTextColor(ContextCompat.getColor(ChangePassword.this, R.color.black));
        }*/
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private boolean isValidPassword(String s) {
        Pattern pattern;
        Matcher matcher;
        final String PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PATTERN);
        matcher = pattern.matcher(s);
        return matcher.matches();
    }

   /* @Override
    public void onAlertRefresh() {
        finish();
    }*/


    // Class with extends AsyncTask class

   /* private class LongOperation extends AsyncTask<String, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(ChangePassword.this);

        protected void onPreExecute() {
            dialog.setMessage("Changing passwordView..");
            dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(final String... passwords) {

            try {
                List<String> apeParam = new ArrayList<>();
                apeParam.add(passwords[0]);
                apeParam.add(passwords[1]);
                apeParam.add(passwords[2]);

                APIRequest.instance().mPostMethodeObject(apeParam, new APIResponse() {
                    @Override
                    public void HandleResponse(int type, final String Response) {
                        Logger.v(TAG + "MessageResponse", "" + Response);
                        runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                            @Override
                            public void run() {
//                                {"Data":"Old passwordView is not correct","StatusCode":"200"}
                                if (!Response.contains("errorCode")) {
                                    mLoginSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = mLoginSettings.edit();
                                    editor.putString("RMPassword", passwords[0]);
                                    editor.apply();
                                    dialog.dismiss();
                                    Util.mAlertDialog("Info", getString(R.string.changepasswordsuccess), ChangePassword.this,ChangePassword.this);

                                }else
                                {
                                    try {
                                        JSONObject json=new JSONObject(Response);
                                        json.optString("message");
                                        Util.mAlertDialog("Info", json.optString("message"), ChangePassword.this);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                        });
                    }

                    @Override
                    public void HandleErrorResponse(int type, final int Response,final String messageResponse) {
                        Logger.v(TAG + "MessageResponse", Integer.toString(Response));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(messageResponse!=null && !messageResponse.isEmpty())
                                {
                                    Util.mAlertDialog("Info", messageResponse, ChangePassword.this);

                                }else
                                    Util.mAlertDialog("Info", getString(R.string.changepasswordfailure), ChangePassword.this);
                            }
                        });
                        // progressDialog1.dismiss();
                    }
                }, CHANGEPASSWORD,CHANGEUSERPASSWORD);
            } catch (Exception e) {
                Logger.e("Exception", e.getMessage());
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            dialog.dismiss();
        }


    }
*/
    @Override
    public void onBackPressed() {

    }
}

