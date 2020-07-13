package com.course.controller;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.util.Log;

import com.course.model.LoginDataModel;
import com.course.utils.Constants;
import com.course.utils.ApplicationClass;

public class LoginState implements ControllerState {

    private static final String TAG = LoginState.class.getSimpleName();
    private HandlerThread workerThread;

    private LoginController controller;
    private Object model;
    private ContentResolver contentResolver;
    private Handler workerHandler;

    protected Handler getWorkerHander()
    {
        return workerHandler;
    }

    // called from the LoginController
    public LoginState(LoginController controller)
    {
        this.controller = controller;
        this.model = controller.getModel();

        // Lets start a thread in login state..
        // Since it take a long time
        // Lets craete an Message Archi here to handle the server
        // request to down load
        // state of down load
        // then pass the dat to the controller
        // controller in turn wil pass the data to Fragment view..

        /// BRING STATE MACHINE HERE - REffer MVC _THREADS state( For participants )

        workerThread = new HandlerThread("Unlocked Save Thread");
        workerThread.start();


        workerHandler = new Handler(workerThread.getLooper());   // Looper.prepare() & Looper.loop();
    }

    @Override
    public boolean handleMessage(int what)
    {
        return handleMessage(what, null);
    }

    @Override
    public boolean handleMessage(int what, Object model)
    {

        // Set State to Busy..
        // Send a Busy notification to View

        this.model = model;
        switch (what)
        {
            case LoginController.MESSAGE_LOGIN_ADMIN:   // 1
                return autheniticateAdmin();

                case LoginController.MESSAGE_ADMIN_CHANGE_PASSWWORD:   // 2
                return changeAdminPassword();

            default:
                break;
        }
        return false;
    }
    @Override
    public void dispose()
    {
        workerThread.getLooper().quit();

    }




    private boolean changeAdminPassword()
    {
        final LoginDataModel mainModel = (LoginDataModel) model;
        workerHandler.post(new Runnable()
        {

            @Override
            public void run()
            {
                if (mainModel.getOldPassword() != null
                        && mainModel.getPassword() != null)
                {
                    SharedPreferences prefs = PreferenceManager
                            .getDefaultSharedPreferences(ApplicationClass
                                    .getContext());

                    String serverAdminPwd = prefs.getString(
                            Constants.ADMIN_PASSWORD_PREFERENCE, null);

                    if (serverAdminPwd != null)
                    {
                        if (serverAdminPwd.equals(mainModel.getOldPassword()))
                        {
                            Log.d(TAG, "changeAdminPassword()--Success..");
                            prefs.edit()
                                    .putString(
                                            Constants.ADMIN_PASSWORD_PREFERENCE,
                                            mainModel.getPassword()).commit();
                            controller
                                    .notifyHandlers(

                                           LoginController.MESSAGE_ADMIN_CHANGE_PASSWWORD,
                                            1, 0, mainModel);

                        }
                        else
                        {
                            Log.e(TAG, "changeAdminPassword()--Failure..");

                            controller
                                    .notifyHandlers(
                                            LoginController.MESSAGE_ADMIN_CHANGE_PASSWWORD,
                                            0, 0, model);
                        }
                    }

                }

                // set state to free...
                //

            }

        });
        return true;

    }

    private boolean autheniticateAdmin()
    {
        final LoginDataModel mainModel = (LoginDataModel) model;

        workerHandler.post(new Runnable()
        {
            // java.lang.NullPointerException: Attempt to invoke virtual method
            // 'java.lang.String android.content.Context.getPackageName()'
            // on a null object reference


            @Override
            public void run()
            {
                if (mainModel.getUserId() != null
                        && mainModel.getPassword() != null)
                {

                   // getting conneted to server...

                    SharedPreferences prefs = PreferenceManager
                            .getDefaultSharedPreferences(ApplicationClass
                                    .getContext());
                    String serverAdminId = prefs.getString(
                            Constants.ADMIN_NAME_PREFERENCE, null);
                    String serverAdminPwd = prefs.getString(
                            Constants.ADMIN_PASSWORD_PREFERENCE, null);

                     Log.d("tag","Admin id : "+serverAdminId+" PW : "+serverAdminPwd);
                    if (serverAdminId != null && serverAdminPwd != null)
                    {
                        if (serverAdminPwd.equals(mainModel.getPassword())
                                && serverAdminId.equals(mainModel.getUserId())) {
                            Log.d(TAG, "autheniticateAdmin()--Success..");


                           // set the state to FREE


                            controller.notifyHandlers(
                                    LoginController.MESSAGE_LOGIN_ADMIN, 1, 0,
                                    model);
                            // arg1 = 1 success 0 = failure 2 = Busy


                        }
                        else
                        {
                            Log.e(TAG, "autheniticateAdmin()--Failure..");
                            controller.notifyHandlers(
                                    LoginController.MESSAGE_LOGIN_ADMIN, 0, 0,
                                    model);
                        }
                    }

                }

            }

        });
        return true;

    }



}
