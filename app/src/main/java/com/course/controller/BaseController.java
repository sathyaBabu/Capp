package com.course.controller;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseController {
    // ToDO : Store all threads into it..
    // connected to loginControler.. in FRAGMENTS AdminLoginDialog

    private final List<Handler> baseHandlerList = new ArrayList<Handler>(); // 4

    abstract public boolean handleMessage(int what, Object data);

    public boolean handleMessage(int what)
    {
        return handleMessage(what, null);
    }

    public void dispose()
    {
    }

    // This is called from AdminValidation
    // AdminLoginDialog fragments thread is regestered here..
    public final void addHandler(Handler handler)
    {
        baseHandlerList.add(handler);
    }

    public final void removeHandler(Handler handler)
    {
        baseHandlerList.remove(handler);
    }


    protected final void notifyHandlers(int what, int arg1, int arg2, Object obj)
    {
        if (!baseHandlerList.isEmpty())
        {
            for (Handler handler : baseHandlerList)
            {
                // ADminLoginDialog..
                Message msg = Message.obtain(handler, what, arg1, arg2, obj);
                msg.sendToTarget();
            }
        }
    }


}
