package com.course.fragments;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.course.activities.AdminActivity;
import com.course.controller.LoginController;
import com.course.model.LoginDataModel;


import course.com.capp.R;

import static java.lang.Thread.sleep;


public class AdminLoginDialog extends DialogFragment implements OnClickListener, Handler.Callback
{

        private static final String TAG = AdminLoginDialog.class.getSimpleName();
        public static final int Login_Success            = 1;
        public static final int Login_Failed             = 0;
        public static final int Login_STATE_BUSY         = 2;
        public static final int LOGIN_STATE_FREE         = 3;

        static int ref ;

        private EditText username, password;

        private Button login, cancel;

        private Handler handler;

        private LoginController loginControler;

        private LoginDataModel model;

        static int logCount;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            super.onCreateView(inflater, container, savedInstanceState);
            View view = inflater.inflate(R.layout.fragment_admindialog,null);
            getUiControl(view);
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().setCanceledOnTouchOutside(false);

            handler = new Handler(this);

            return view;
        }

        private void getUiControl(View view)
        {
            username = (EditText) view.findViewById(R.id.admin_username);

            password = (EditText) view.findViewById(R.id.admin_pwd);

            login = (Button) view.findViewById(R.id.admin_signin);
            login.setOnClickListener(this);

            cancel = (Button) view.findViewById(R.id.admin_cancel);
            cancel.setOnClickListener(this);
        }

        @Override
        public void onDestroy()
        {
            Log.d(TAG, "onDestroy()");
            super.onDestroy();
            if (loginControler != null && handler != null)
            {
                loginControler.removeHandler(handler);
                handler = null;
                loginControler = null;
            }
           // save the state in shared pref - memento
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.admin_signin:
                    // Check if the state is Free
                    // notify user Busy Stste or Processing state
                    doAdminSignInValidation();
                    break;

                case R.id.admin_cancel:
                    // Clear the state to free
                    getDialog().dismiss();
                    break;

                default:
                    break;
            }

        }

        private void doAdminSignInValidation()
        {
            if (username.getText().toString().length() == 0)
            {
                Toast.makeText(getActivity(), "User Id cannot be empty",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            else if (password.getText().toString().length() == 0)
            {
                Toast.makeText(getActivity(), "Password cannot be empty.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                model = new LoginDataModel();

                model.setUserId(username.getText().toString()); //    admin
                model.setPassword(password.getText().toString()); // admin

                // calls LoginController..
                loginControler = new LoginController( model ); // created a state thread in it..

                // view handler to addHandler
                loginControler.addHandler(handler);   //  Add handler to the BaseController Arraylist

                loginControler.handleMessage(LoginController.MESSAGE_LOGIN_ADMIN, model);
                //                                                 1,             obj
            }

        }

        @Override
        public boolean handleMessage(Message msg)
        {
            switch( msg.arg1) {

                case Login_Success :

                    Toast.makeText(getActivity(), "Success : ", Toast.LENGTH_SHORT).show();

                    SuccessCallBusinessLogic();

                    return true ;


                case Login_Failed:

                    LoginFailed();

                    return false ;


                case Login_STATE_BUSY:

                    Toast.makeText(getActivity(), "LoginBusy", Toast.LENGTH_SHORT).show();
                    // Turn the flag on to Switch off all controll buttons


                    return false ;

                case LOGIN_STATE_FREE:

                    // Bring back controll buttons to ready state..

                    return false ;

            }


            return false;
        }


    private void SuccessCallBusinessLogic() {

            // Not a good Idea  to call an activity in VIEW!!!
            // let controller do the job

        Log.d(TAG, "handleMessage()-->Success login");
        getDialog().dismiss();
        Intent intent = new Intent(getActivity(), AdminActivity.class);
        getActivity().startActivity(intent);
    }


    private void LoginFailed() {
        Toast.makeText(getActivity(), "Login failed..",
                Toast.LENGTH_SHORT).show();
        logCount++;

        if(logCount == 3 ){
            login.setEnabled(false);
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }





    }


