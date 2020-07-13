package com.course.activities;

import course.com.capp.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

public class SplashScreen extends Activity implements AnimationListener {

    Animation animFadeIn;

    TextView splashtext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        flashScreen();
    }

    private void flashScreen()
    {
        splashtext = (TextView) findViewById(R.id.SPLASH_TEXT);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        animFadeIn.setAnimationListener(this);

        splashtext.startAnimation(animFadeIn);
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        Intent intent = new Intent(getApplicationContext(), SignUpSignIn.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();

    }

    @Override
    public void onAnimationRepeat(Animation animation)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation)
    {


    }

}





