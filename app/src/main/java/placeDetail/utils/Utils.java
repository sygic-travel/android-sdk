package placeDetail.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;


import com.sygic.travel.sdkdemo.R;


/**
 * Created by Lukas on 04.04.2016.
 */
public class Utils {

	public static boolean isOnline(Context context) {
		final ConnectivityManager connectivityManager =
			(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnectedOrConnecting();
	}

	public static Spanned fromHtml(String text) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return Html.fromHtml(text, 0);
		} else {
			return Html.fromHtml(text);
		}
	}

	public static int dp2px(int dp, Activity activity) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
			activity.getResources().getDisplayMetrics());
	}

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public static ActionBar setToolbar(AppCompatActivity activity){
		return setToolbar(true, activity);
	}

	public static ActionBar setToolbar(boolean updatePadding, AppCompatActivity activity){
		Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
		if(toolbar != null) {
			activity.setSupportActionBar(toolbar);
		}

		if(updatePadding) {
			toolbar.setPadding(0, Utils.getStatusBarHeight(activity), 0, 0);
		}

		return activity.getSupportActionBar();
	}
}
