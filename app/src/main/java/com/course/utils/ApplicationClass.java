
package com.course.utils;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

import course.com.capp.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class ApplicationClass extends Application
{

    private static Context context;
    private static DisplayImageOptions options;

    public static Context getContext()
    {

        return context;
    }

    @Override
    public void onCreate()
    {


        // TODO Auto-generated method stub
        super.onCreate();
        context = getApplicationContext();

        SharedPreferences mPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        mPreferences.edit().putString(Constants.ADMIN_NAME_PREFERENCE, "admin").apply();
        mPreferences.edit().putString(Constants.ADMIN_PASSWORD_PREFERENCE, "admin").apply();

        initImageLoader(getApplicationContext());

    }

    public static void initImageLoader(Context context)
    {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context,
                "capp");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 1)
                .threadPoolSize(5).discCache(new UnlimitedDiscCache(cacheDir))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs()
                .denyCacheImageMultipleSizesInMemory().build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true)
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .build();

    }

    public static DisplayImageOptions getDisplayOptions()
    {
        return options;
    }

}
