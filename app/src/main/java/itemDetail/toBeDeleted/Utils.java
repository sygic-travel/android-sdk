package itemDetail.toBeDeleted;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;


import com.sygic.travel.sdkdemo.R;

import java.io.File;
import java.util.List;


/**
 * Created by Lukas on 04.04.2016.
 */
public class Utils {
	public static final String VIEW_PAGER_POSITION = "viewPagerPosition";
	public static final String RES_STRING = "string";
	public static final String RES_DRAWABLE = "drawable";
	public static final String RES_COLOR = "color";
	public static final String ANONYMOUS_USER_NAME = "Anonymous user";
	public static final String ANONYMOUS_USER_KEY = "anonymous_user";

	public static final String DEST_TYPE_RES_PREFIX = "dt_";

	public static final long DAY_IN_MILLIS = 86400000;
	public static final String keys[] = new String[]{"sightseeing", "shopping", "eating", "playing", "traveling", "going_out", "hiking", "sports", "relaxing", "discovering"};

	public static void setVisibility(int visibility, View...views) {
		if (views != null) {
			for (int i = 0; i < views.length; i++) {
				views[i].setVisibility(visibility);
			}
		}
	}

	public static void toast(Context context, int stringId, int duration) {
		Toast.makeText(context, stringId, duration == 1 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
	}

	public static void toast(Context context, String msg, int duration) {
		Toast.makeText(context, msg, duration == 1 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
	}

	public static int levenshteinDistance(CharSequence lhs, CharSequence rhs) {
		int len0 = lhs.length() + 1;
		int len1 = rhs.length() + 1;

		// the array of distances
		int[] cost = new int[len0];
		int[] newcost = new int[len0];

		// initial cost of skipping prefix in String s0
		for (int i = 0; i < len0; i++) cost[i] = i;

		// dynamically computing the array of distances

		// transformation cost for each letter in s1
		for (int j = 1; j < len1; j++) {
			// initial cost of skipping prefix in String s1
			newcost[0] = j;

			// transformation cost for each letter in s0
			for(int i = 1; i < len0; i++) {
				// matching current letters in both strings
				int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

				// computing cost for each transformation
				int cost_replace = cost[i - 1] + match;
				int cost_insert  = cost[i] + 1;
				int cost_delete  = newcost[i - 1] + 1;

				// keep minimum cost
				newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
			}

			// swap cost/newcost arrays
			int[] swap = cost;
			cost = newcost;
			newcost = swap;
		}

		// the distance is the cost for transforming all letters in both strings
		return cost[len0 - 1];
	}

	public static String removeTypeFromGuid(String guid){
		String split [] = guid.split(":");
		return split[1];
	}

	/**
	 * @param context context
	 * @param name string resource name
	 * @return demanded string or defaultValue if the string with name doesn't exist
	 */
	public static String getStringResByName(Context context, String name, String defaultValue){
		int id = getResIdentifier(context, name, RES_STRING);
		if(id != 0){
			return context.getString(id);
		} else {
			return  defaultValue;
		}
	}

	/**
	 * @param context context
	 * @param name resource name
	 * @param resType resource type (string, drawable, etc.)
	 * @return integer representing demanded identifier or 0 if resource with given name doesn't exist
	 */
	public static int getResIdentifier(Context context, String name, String resType){
		try{
			return context.getResources().getIdentifier(
				name,
				resType,
				context.getPackageName()
			);
		} catch(Exception exception){
			return 0;
		}
	}

	public static String capitalize(String string){
		if(!TextUtils.isEmpty(string)) {
			if(string.length() > 1) {
				return string.substring(0, 1).toUpperCase() +
					string.substring(1).toLowerCase();
			} else {
				return string.toUpperCase();
			}
		} else {
			return "";
		}
	}

	public static boolean isConnectionMobile(Context context){
		return connectionTypeEquals(context, ConnectivityManager.TYPE_MOBILE);
	}

	public static boolean isConnectionWifi(Context context){
		return connectionTypeEquals(context, ConnectivityManager.TYPE_WIFI);
	}

	private static boolean connectionTypeEquals(Context context, int connectionType){
		ConnectivityManager connectivityManager =
			(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		return connectivityManager.getActiveNetworkInfo().getType() == connectionType;
	}



	public static boolean isTablet(Context context){
		return isTablet(context.getResources());
	}

	public static boolean isTablet(Resources resources){
		return resources.getBoolean(R.bool.is_tablet_device);
	}

	public static boolean isLandscape(Context context){
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	public static int getViewPagerPosition(Bundle savedInstanceState){
		int position;
		if(savedInstanceState != null) {
			position = savedInstanceState.getInt(VIEW_PAGER_POSITION, 0);
		} else {
			position = 0;
		}
		return position;
	}

	public static boolean isLandscape(Resources resources){
		return resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

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

	public static long getCurrentTimestamp() {
		return System.currentTimeMillis() / 1000;
	}


	public static boolean isEmailValid(String email) {
		return email.matches("[^\\s]+@[^\\s]+\\.[^\\s]+");
	}

	public static int dp2px(int dp, Activity activity) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
			activity.getResources().getDisplayMetrics());
	}

	public static int getNavigationDrawerItemTextColor(Context context) {
		return ContextCompat.getColor(context, R.color.main_black);
	}

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
}
