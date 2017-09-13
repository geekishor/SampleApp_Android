package com.healthcamp.healthapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthcamp.healthapp.MainActivity;
import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.helpers.Api;
import com.healthcamp.healthapp.helpers.WebserviceConnect;
import com.healthcamp.healthapp.interfaces.ServerCallBackInterface;
import com.healthcamp.healthapp.utils.AppText;
import com.healthcamp.healthapp.utils.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout llLogin;

    EditText etUsername, etPassword;
    SharedPreferencesManager preferences;
    RelativeLayout rlProgress;
    String userName, password;
    ImageView ivRotate, ivUserNameClear, ivPasswordClear;
    View viewUsername, viewPassword;
    TextView tvUserError, tvPasswordError, textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        llLogin = (LinearLayout) findViewById(R.id.ll_login);
        llLogin.setOnClickListener(this);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        textViewSignUp = (TextView) findViewById(R.id.link_signup);

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "SourceSansPro-Semibold.ttf");
        etUsername.setTypeface(typeFace);
        etPassword.setTypeface(typeFace);

        ivUserNameClear = (ImageView) findViewById(R.id.iv_username_clear);
        ivUserNameClear.setOnClickListener(this);

        ivPasswordClear = (ImageView) findViewById(R.id.iv_password_clear);
        ivPasswordClear.setOnClickListener(this);

        rlProgress = (RelativeLayout) findViewById(R.id.rl_progress);
        ivRotate = (ImageView) findViewById(R.id.pb_login);
        ivRotate.startAnimation(AnimationUtils.loadAnimation(this, R.anim.custom_progressbar));

        viewUsername = findViewById(R.id.view_username);
        viewPassword = findViewById(R.id.view_password);

        tvUserError = (TextView) findViewById(R.id.tv_username_error);
        tvPasswordError = (TextView) findViewById(R.id.tv_password_error);

        tvUserError.setVisibility(View.INVISIBLE);
        tvPasswordError.setVisibility(View.INVISIBLE);
        textViewSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_login:

                userName = etUsername.getText().toString();
                password = etPassword.getText().toString();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    if (TextUtils.isEmpty(userName)) {
                        tvUserError.setVisibility(View.VISIBLE);
                        viewUsername.setBackgroundColor(getResources().getColor(R.color.login_button_border));
                    } else {
                        tvPasswordError.setVisibility(View.VISIBLE);
                        viewPassword.setBackgroundColor(getResources().getColor(R.color.login_button_border));
                    }


                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    callApi(userName, password);
                }
                break;

            case R.id.iv_username_clear:
                etUsername.setText("");
                tvUserError.setVisibility(View.INVISIBLE);
                viewUsername.setBackgroundColor(getResources().getColor(R.color.white));


                break;

            case R.id.iv_password_clear:
                etPassword.setText("");
                tvPasswordError.setVisibility(View.INVISIBLE);
                break;
            case R.id.link_signup:
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

    private void callApi(final String username, final String password) {

        rlProgress.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("username", password);
        WebserviceConnect webserviceConnect = new WebserviceConnect();

        webserviceConnect.callWebService(new ServerCallBackInterface() {

            public void onSuccess(JSONObject response) throws JSONException {
                rlProgress.setVisibility(View.GONE);
                try {
                    String responseString = response.getString("results");
                    if (responseString.equals("false")) {
                        tvUserError.setVisibility(View.VISIBLE);
                        tvPasswordError.setVisibility(View.VISIBLE);
                        viewUsername.setBackgroundColor(getResources().getColor(R.color.login_button_border));
                        viewPassword.setBackgroundColor(getResources().getColor(R.color.login_button_border));
                    } else {
                        JSONObject profileObject = response.getJSONObject("profile");
                        preferences.saveString(AppText.USERNAME, profileObject.getString("name"));
                        preferences.saveString(AppText.PROFILE_IMAGE, profileObject.getString("profile_image"));

                        preferences.saveString(AppText.PASSWORD, password.trim());
                        preferences.saveBoolean(AppText.IS_LOGIN, true);
                        preferences.saveString(AppText.USERNAME, username);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    tvUserError.setVisibility(View.VISIBLE);
                    tvPasswordError.setVisibility(View.VISIBLE);
                    viewUsername.setBackgroundColor(getResources().getColor(R.color.login_button_border));
                    viewPassword.setBackgroundColor(getResources().getColor(R.color.login_button_border));
                    rlProgress.setVisibility(View.GONE);
                }
            }
        }, params, Api.loginUrl, this);

      /*  StringRequest strReq = new StringRequest(Request.Method.POST, Api.loginUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("response is: ",response);
                rlProgress.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String responseString = jsonObject.getString("success");
                    if(responseString.equals("false")){
                        tvUserError.setVisibility(View.VISIBLE);
                        tvPasswordError.setVisibility(View.VISIBLE);
                        viewUsername.setBackgroundColor(getResources().getColor(R.color.login_button_border));
                        viewPassword.setBackgroundColor(getResources().getColor(R.color.login_button_border));
                    }else{
                        JSONObject profileObject = jsonObject.getJSONObject("profile");
                        preferences.saveString(AppText.USER_ID,profileObject.getString("uid"));
                        preferences.saveString(AppText.FIRST_NAME,profileObject.getString("firstname"));
                        preferences.saveString(AppText.LAST_NAME,profileObject.getString("lastname"));
                        preferences.saveString(AppText.USERNAME,profileObject.getString("name"));
                        preferences.saveString(AppText.PROFILE_IMAGE,profileObject.getString("profile_image"));
                        JSONObject roleObject = profileObject.getJSONObject("roles");
                        if((!(roleObject.isNull("6")))) {
                            preferences.saveString(AppText.USERROLE, roleObject.getString("6"));
                        }
                        preferences.saveString(AppText.PASSWORD, password.trim());
                        preferences.saveBoolean(AppText.IS_LOGIN, true);
                        preferences.saveInt(AppText.SYNC_STATE, 0);
//                        preferences.saveString(AppText.USERNAME, username);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAg", " Error: " + error);
                tvUserError.setVisibility(View.VISIBLE);
                tvPasswordError.setVisibility(View.VISIBLE);
                viewUsername.setBackgroundColor(getResources().getColor(R.color.login_button_border));
                viewPassword.setBackgroundColor(getResources().getColor(R.color.login_button_border));
                rlProgress.setVisibility(View.GONE);
            }

        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("Username", username);
//                params.put("Password", password);

                return params;
            }

            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username.trim());
                params.put("password", password.trim());
                return params;
            }

        };


        strReq.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.getInstance(getApplicationContext()).getRequestQueue().add(strReq);
    }*/

    }
}
