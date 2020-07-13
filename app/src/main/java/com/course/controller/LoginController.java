package com.course.controller;

public class LoginController extends BaseController{

    public static final int MESSAGE_LOGIN_ADMIN            = 1;
    public static final int MESSAGE_ADMIN_CHANGE_PASSWWORD = 2;


    private Object          model;
    private ControllerState messageState;

    protected void setMessageState(ControllerState messageState)
    {
        if (this.messageState != null)
        {
            this.messageState.dispose();
        }
        this.messageState = messageState;
    }

    public LoginController(Object model)
    {
        this.model = model; // LoginDataModel
        // Tell the LOginState to talk back to us..
        messageState = new LoginState(this);  // make it a point to hold the current login state in DETAIL
    }


    // When ever the state changes...Controler here can receive / read all states..

    public Object getModel()
    {
        return model;
    }

    @Override
    public boolean handleMessage(int what)
    {
        return messageState.handleMessage(what);
    }

    @Override
    public boolean handleMessage(int what, Object data)
    {
        // LoginState
        return messageState.handleMessage(what, data);
        // messsageState is loginState object..
    }

}
