package com.course.activities;

import com.course.fragments.AdminLoginDialog;
import course.com.capp.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class SignUpSignIn extends Activity implements OnClickListener {
    Button signup, signin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_signup);

        signup = (Button) findViewById(R.id.SignUp);
        signup.setText(Html.fromHtml("Admin zone<br/><small>(Create, Manage Users and change settings)</small>"));
        signin = (Button) findViewById(R.id.SignIn);
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.SignUp:
                doAdminValidation();
                break;

           //

            default:
                break;
        }

    }

    private void doAdminValidation()
    {
        AdminLoginDialog adminLogin = new AdminLoginDialog();
        adminLogin.show(getFragmentManager(), "adminLogin");
    }





}
