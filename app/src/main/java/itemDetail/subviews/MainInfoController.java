package itemDetail.subviews;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sygic.travel.sdkdemo.R;


import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import itemDetail.ItemDetailReferenceUtils;
import itemDetail.fragment.ItemDetailFragmentFactories;
import itemDetail.toBeDeleted.PromisesManager;
import itemDetail.toBeDeleted.Utils;
import retrofit2.Call;

public class MainInfoController implements ItemDetailSubview {
	private static final String WIKIPEDIA = "wikipedia";
	public final String GOOGLE = "google";

	private TextView tvTitle, tvLocalTitle, tvPerex;
	private TextView tvMarker;
	private View rootView;
	private RelativeLayout rlTime;
	private TextView tvTime, tvDuration;
	private TextView tvNote;
	private TextView tvTranslate;
	private RelativeLayout rlNote;
	private LinearLayout llAtribution;
	private TextView tvPerexAtribution;

	private LinearLayout llHotelRating;
	private TextView tvEstimatedRating;
	private MainInfoModel mainInfoModel;
	private Call<JsonElement> translationCall;
	private PromisesManager promisesManager;
	private View.OnClickListener translateListener;
	private DoneCallback<JsonObject> doneCallback;
	private FailCallback failCallback;

	public MainInfoController (ItemDetailSubviewModel model){
		mainInfoModel = (MainInfoModel) model;
	}

	@Override
	public void render(LinearLayout llContent, final Activity activity, ItemDetailFragmentFactories factories) {
		rootView = activity.getLayoutInflater().inflate(R.layout.item_detail_main_info_layout, null);
		tvTitle = (TextView) rootView.findViewById(R.id.tv_item_title);
		tvLocalTitle = (TextView) rootView.findViewById(R.id.tv_local_title);
		tvPerex = (TextView) rootView.findViewById(R.id.tv_description);
		tvMarker = (TextView) rootView.findViewById(R.id.tv_item_marker);
		tvTranslate = (TextView) rootView.findViewById(R.id.tv_translate);
		llAtribution = (LinearLayout) rootView.findViewById(R.id.ll_attribution);
		tvPerexAtribution = (TextView) rootView.findViewById(R.id.tv_attribution_perex_provider);

		rlTime = (RelativeLayout) rootView.findViewById(R.id.rl_time_layout);
		tvDuration = (TextView) rootView.findViewById(R.id.tv_duration);
		tvTime = (TextView) rootView.findViewById(R.id.tv_time);
		tvNote = (TextView) rootView.findViewById(R.id.tv_note);
		rlNote = (RelativeLayout) rootView.findViewById(R.id.rl_note);

		llHotelRating = (LinearLayout) rootView.findViewById(R.id.ll_detail_hotel_rating);
		tvEstimatedRating = (TextView) rootView.findViewById(R.id.tv_estimated_rating);

		//translationCall = sygicTravel.getStApiCdn().getItemAutoTranslation(mainInfoModel.getGuid());
		//promisesManager = sygicTravel.getPromisesManager();
		checkTranslation(activity);

		tvTitle.setText(mainInfoModel.getTitle());
		if(mainInfoModel.getTitle().equals(mainInfoModel.getLocalTitle())){
			tvLocalTitle.setVisibility(View.GONE);
		} else {
			tvLocalTitle.setText(mainInfoModel.getLocalTitle());
		}

		if(mainInfoModel.isHotel()){
			renderHotelRating(activity);
		} else {
			llHotelRating.setVisibility(View.GONE);
			tvEstimatedRating.setVisibility(View.GONE);
		}

		tvMarker.setTypeface(factories.getTypeface());
		tvMarker.setText(mainInfoModel.getMarkerId());
		checkPerexAttributionAndShow(activity);

		llContent.addView(rootView);
	}

	private void checkPerexAttributionAndShow(final Activity activity){
		String wikipedia = activity.getString(R.string.item_detail_wikipedia);
		if(mainInfoModel.getPerexProvider() != null && mainInfoModel.getPerexProvider().equals(WIKIPEDIA)){
			String stringToSpan = new StringBuilder()
				.append(mainInfoModel.getPerex())
				.append(" (")
				.append(wikipedia)
				.append(")")
				.toString();

			SpannableString ss = new SpannableString(stringToSpan);

			ss.setSpan(
				getClickableSpan(activity),
				mainInfoModel.getPerex().length() + 2,
				mainInfoModel.getPerex().length() + 2 + wikipedia.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
			);

			tvPerex.setText(ss);
			tvPerex.setMovementMethod(LinkMovementMethod.getInstance());
			tvPerex.setHighlightColor(Color.TRANSPARENT);
		} else {
			tvPerex.setText(mainInfoModel.getPerex());
		}
	}

	private ClickableSpan getClickableSpan(final Activity activity){
		return new ClickableSpan() {
			@Override
			public void onClick(View textView) {
				ItemDetailReferenceUtils.showUrl(activity, mainInfoModel.getPerexLink(), "", "");
			}
			@Override
			public void updateDrawState(TextPaint drawState) {
				super.updateDrawState(drawState);
				drawState.setColor(ContextCompat.getColor(activity, R.color.text_grey));
			}
		};
	}

	private void checkTranslation(Activity activity){
		if(mainInfoModel.isTranslated() &&
			mainInfoModel.getPerexTranslationProvider() != null &&
			mainInfoModel.getPerexTranslationProvider().equals(GOOGLE)
		){
			llAtribution.setVisibility(View.VISIBLE);
		}
		if(!mainInfoModel.isTranslated()
			&& Utils.isOnline(activity)
			&& mainInfoModel.getPerex() != null
			&& !mainInfoModel.getPerex().isEmpty()
		){
			tvTranslate.setVisibility(View.VISIBLE);
			tvTranslate.setOnClickListener(getTranslateListener());
		}
	}

	private View.OnClickListener getTranslateListener(){
		if(translateListener == null){
			translateListener = new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					promisesManager
						.when(translationCall)
						.done(getDoneCallback())
						.fail(getFailCallback());
				}
			};
		}
		return translateListener;
	}

	private DoneCallback<JsonObject> getDoneCallback(){
		if(doneCallback == null) {
			doneCallback = new DoneCallback<JsonObject>() {
				@Override
				public void onDone(JsonObject result) {
					try {
						JsonObject translation = result.getAsJsonObject("translation");
						tvPerex.setText(translation.getAsJsonPrimitive("description").getAsString());
						String perexAtribution = result.getAsJsonPrimitive("provider").getAsString();
						if(perexAtribution != null && perexAtribution.equals(GOOGLE)){
							llAtribution.setVisibility(View.VISIBLE);
						}
						tvTranslate.setVisibility(View.GONE);
					} catch(Exception exception) {
						getFailCallback().onFail(exception);
					}
				}
			};
		}
		return doneCallback;
	}

	private FailCallback<Exception> getFailCallback(){
		if(failCallback == null){
			failCallback = new FailCallback<Exception>() {
				@Override
				public void onFail(Exception result) {
					Toast.makeText(
						tvTranslate.getContext(),
						R.string.translation_api_error,
						Toast.LENGTH_SHORT
					)
						.show();
				}
			};
		}
		return failCallback;
	}

	/*public void updateTimeNote(
		ItemDetailActivity activity,
		String userTimes,
		UserData userData,
		boolean inTrip,
		boolean today,
		int durationSeconds,
		View.OnClickListener onUserDataClickListener
	){
		if(
				userData.getStart() == null &&
				(
					durationSeconds == userData.getDuration() ||
					userData.getDuration() == 0
				) &&
				!inTrip
			) {
			rlTime.setVisibility(View.GONE);
		} else {
			String durationText;
			Duration duration;

			if(userData.getDuration() == 0){
				duration = new Duration(durationSeconds);
			} else {
				duration = new Duration(userData.getDuration());
			}
			durationText = duration.getShortFormattedDuration();


			rlTime.setVisibility(View.VISIBLE);
			if(userData.getStart() != null){
				showTime(activity.getString(R.string.item_detail_scheduled_time), userTimes);
			} else if(today){
				showTime(activity.getString(R.string.detail_today), durationText);
			} else if(inTrip){
				showTime(activity.getString(R.string.hotel_filters_in_trip), durationText);
			} else {
				showTime(activity.getString(R.string.duration), durationText);
			}
		}

		if(userData.getNote() == null){
			rlNote.setVisibility(View.GONE);
		} else {
			rlNote.setVisibility(View.VISIBLE);
			tvNote.setText(userData.getNote());
		}

		if(!mainInfoModel.isHotel()) {
			rlTime.setOnClickListener(onUserDataClickListener);
		}
		rlNote.setOnClickListener(onUserDataClickListener);
	}

	private void showTime(String text, String durationText) {
		tvTime.setText(text);
		if(mainInfoModel.isHotel()) {
			tvDuration.setText("");
		} else {
			tvDuration.setText(durationText);
		}
	}*/

	private void renderHotelRating(Activity activity) {
		if(mainInfoModel.getStars() > 0) {
			llHotelRating.setVisibility(View.VISIBLE);
			if(mainInfoModel.isEstimated()) {
				renderRating(activity, llHotelRating, mainInfoModel.getStars(), R.drawable.fake_star_hotel);
				tvEstimatedRating.setVisibility(View.VISIBLE);
			} else {
				renderRating(activity, llHotelRating, mainInfoModel.getStars(), R.drawable.hotel_star_rating);
				tvEstimatedRating.setVisibility(View.GONE);
			}
		} else {
			llHotelRating.setVisibility(View.GONE);
			tvEstimatedRating.setVisibility(View.GONE);
		}
	}

	private void renderRating(Activity activity, LinearLayout ratingBar, float rating, int starId){
		boolean addHalfStar = false;
		int numberOfStars = (int) rating;

		if(rating - (float) numberOfStars > 0f){
			addHalfStar = true;
		}

		for(int i = 0; i < numberOfStars; i++){
			ImageView star = new ImageView(activity);
			star.setImageResource(starId);
			star.setPadding(0, 0, Utils.dp2px(5, activity), 0);
			ratingBar.addView(star);
		}

		if(addHalfStar){
			ImageView halfStar = new ImageView(activity);
			halfStar.setImageResource(R.drawable.detail_star_half);
			ratingBar.addView(halfStar);
		}
	}
}
