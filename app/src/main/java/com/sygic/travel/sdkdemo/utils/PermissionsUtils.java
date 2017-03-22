package com.sygic.travel.sdkdemo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.sygic.travel.sdkdemo.R;

public class PermissionsUtils {
	public static final int REQUEST_LOCATION = 0;
	public static final int NO_EXPLANATION = -1;

	public final String[] PERMISSION_LOCATION = new String[]{
		Manifest.permission.ACCESS_FINE_LOCATION,
		Manifest.permission.ACCESS_COARSE_LOCATION
	};

	private View vMain;

	public PermissionsUtils(@NonNull View vMain) {
		this.vMain = vMain;
	}

	public boolean verifyPermissions(int[] grantResults) {
		if(grantResults.length < 1) {
			return false;
		}

		for(int result : grantResults) {
			if(result != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		}
		return true;
	}

	public boolean isPermitted(Context context, String... permissions) {
		for(int i = 0; i < permissions.length; i++) {
			try {
				if(ActivityCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
					return false;
				}
			} catch(Exception exception) {
				return false;
			}
		}
		return true;
	}

	public void requestLocationPermission(Activity activity) {
		ActivityCompat.requestPermissions(activity, PERMISSION_LOCATION, REQUEST_LOCATION);
	}

	public void showExplanationSnackbar(final Activity activity, String permissionType) {
		if(!ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionType)) {
			Snackbar
				.make(vMain, getExplanationId(permissionType), Snackbar.LENGTH_LONG)
//				.setActionTextColor(ContextCompat.getColor(activity, R.color.snackbar_action_color))
				.setAction(R.string.settings, new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						try {
							Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
							intent.setData(Uri.parse("package:" + activity.getPackageName()));
							activity.startActivity(intent);
						} catch(ActivityNotFoundException e) {
							Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
							activity.startActivity(intent);

						}
					}
				})
				.show();
		}
	}

	public void showGrantedSnackBar() {
		Snackbar.make(vMain, R.string.permissions_granted, Snackbar.LENGTH_LONG).show();
	}

	private int getExplanationId(String permissionType) {
		switch(permissionType) {
			case Manifest.permission.ACCESS_FINE_LOCATION:
			case Manifest.permission.ACCESS_COARSE_LOCATION:
				return R.string.permissions_location_explanation;
			default:
				return R.string.need_permissions;

		}
	}

}
