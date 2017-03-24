package placeDetail.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.MailTo;
import android.net.Uri;
import android.view.View;

import placeDetail.PlaceDetailConstants;
import placeDetail.MarkerMapper;
import placeDetail.referenceList.ReferencesListActivity;

public class PlaceDetailFragmentFactories {
	private final PlaceDetailListener listener;
	private Activity activity;
	private Typeface typeface;
	private PlaceDetailFragment fragment;
	private View.OnClickListener userDataActivityListener;
	private MarkerMapper markerMapper;
	private PlaceDetailFragmentRenderer fragmentRenderer;

	public PlaceDetailFragmentFactories(Activity activity, PlaceDetailFragment fragment, PlaceDetailListener listener) {
		this.activity = activity;
		this.fragment = fragment;
		this.listener = listener;
	}

	public Typeface getTypeface() {
		if(typeface == null){
			typeface = Typeface.createFromAsset(activity.getAssets(), MarkerMapper.ICONFONT_PATH);
		}
		return typeface;
	}

	public View.OnClickListener getOnReferenceListClickListener(){

		return new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(activity, ReferencesListActivity.class);
				intent.putExtra(PlaceDetailConstants.FEATURE_TITLE, fragment.getFeature().getName());
				intent.putExtra(PlaceDetailConstants.GUID, fragment.getFeature().getGuid());
				activity.startActivity(intent);
			}
		};
	}

	public View.OnClickListener getOnPhoneClickListener(final String phone) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String phoneUri = phone;
					if(!phoneUri.startsWith("tel:")){
						phoneUri = "tel:" + phoneUri;
					}
					Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneUri));
					activity.startActivity(phoneIntent);
				} catch(Exception exception){
				}
			}
		};
	}

	public View.OnClickListener getUserDataActivityListener(){
		if(userDataActivityListener == null){
			userDataActivityListener = new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					listener.onUserDataClick();
				}
			};
		}
		return userDataActivityListener;
	}

	public View.OnClickListener getOnEmailClickListener(final String email) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					startEmailContent(email, "", "");
				} catch(Exception exception){
				}
			}
		};
	}

	public void startEmailContent(String email, String subject, String message){
		try {
			String mailtoUrl = email;
			if(!mailtoUrl.startsWith("mailto:")){
				mailtoUrl = "mailto:" + mailtoUrl;
			}
			MailTo mailTo = MailTo.parse(mailtoUrl);
			Intent mailIntent = getEmailIntent(mailTo.getTo(), subject, message, mailTo.getCc());
			activity.startActivity(mailIntent);
		} catch(Exception exception){
			//Crashlytics.logException(exception);
			//Crashlytics.log(activity.getTitle() + " - no email intent");
		}
	}

	public Intent getEmailIntent(String address, String subject, String body, String cc) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
		intent.putExtra(Intent.EXTRA_TEXT, body);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_CC, cc);
		intent.setType("message/rfc822");
		return intent;
	}

	public MarkerMapper getMarkerMapper(){
		if(markerMapper == null) {
			markerMapper = new MarkerMapper();
		}
		return markerMapper;
	}

	public PlaceDetailFragmentRenderer getRenderer() {
		if(fragmentRenderer == null){
			fragmentRenderer = new PlaceDetailFragmentRenderer();
		}
		return fragmentRenderer;
	}

	public Runnable getAfterRenderRunnable(){
		return new Runnable() {
			@Override
			public void run() {
				listener.onRenderFinished();
			}
		};
	}
}
