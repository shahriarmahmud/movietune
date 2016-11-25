package com.techcoderz.ruchira.utills;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.techcoderz.ruchira.R;
import com.techcoderz.ruchira.activity.RuchiraActivity;


public class ViewUtils {

    private static void launchFragmentKeepingInBackStack(Context context, Fragment fragmentToLaunch, String fragmentTag) {
        FragmentManager supportFragmentManager = ((RuchiraActivity) context).getSupportFragmentManager();
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_body, fragmentToLaunch, fragmentTag)
                .addToBackStack(null)
                .commit();
    }

    public static void launchFragmentKeepingInBackStack(Context context, Fragment fragmentToLaunch) {
        Log.e("ViewUtil", fragmentToLaunch.getClass().getName());
        launchFragmentKeepingInBackStack(context, fragmentToLaunch, fragmentToLaunch.getClass().getName());
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static boolean isPointOutOfView(View w, float x, float y) {
        return x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom();
    }
}
