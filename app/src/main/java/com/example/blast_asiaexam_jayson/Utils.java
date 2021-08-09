package com.example.blast_asiaexam_jayson;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.load.model.Model;
import com.example.blast_asiaexam_jayson.Model.Items;
import com.example.blast_asiaexam_jayson.R;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static MainActivity activity;

    public static void applyStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(color == R.color.color_white){
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else{
                View decorView = activity.getWindow().getDecorView();
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(GetColorFromResource(activity, color));
        }
    }

    public static int GetColorFromResource(Context context, int resID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return context.getColor(resID);
        else
            return context.getResources().getColor(resID);
    }

    public static Drawable GetDrawableFromResource(Context context, int resID){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            return context.getResources().getDrawable(resID, null);
        } else {
            return context.getResources().getDrawable(resID);
        }
    }

    @SuppressLint("ResourceType")
    public static void DisplayFragment(FragmentActivity context, Fragment fragment, String tagName, boolean isBackStack){
        try {
            FragmentManager fragmentManager = context.getSupportFragmentManager();
            boolean fragmentPopped = fragmentManager.popBackStackImmediate(tagName, 0);
            if (!fragmentPopped && fragmentManager.findFragmentByTag(tagName) == null) {
                FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment, tagName);
                if(isBackStack){fragmentTransaction.addToBackStack(tagName);}
                fragmentTransaction.commitAllowingStateLoss();
            }
        } catch (IllegalStateException e){
            Log.e("TRAP", e.getLocalizedMessage());
        } catch (Exception e){

        }
    }

    public static void swipeRefreshStyle(SwipeRefreshLayout refreshLayout, Context context){
        int c1 = GetColorFromResource(context, R.color.color_ed7161);
        int c2 = GetColorFromResource(context, R.color.color_efce4a);
        int c3 = GetColorFromResource(context, R.color.color_white);
        refreshLayout.setColorSchemeColors(c1, c2, c3);
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity.getActiveNetworkInfo() != null
                && connectivity.getActiveNetworkInfo().isAvailable()
                && connectivity.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.e("NETWORK", "Internet Connection Not Present");
            return false;
        }
    }

    //test
    public void test(){
        //test again
    }
}
