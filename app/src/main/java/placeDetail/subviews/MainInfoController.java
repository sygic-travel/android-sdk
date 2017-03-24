package placeDetail.subviews;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sygic.travel.sdkdemo.R;


import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import placeDetail.PlaceDetailReferenceUtils;
import placeDetail.fragment.PlaceDetailFragmentFactories;
import retrofit2.Call;

public class MainInfoController implements PlaceDetailSubview {
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
	private View.OnClickListener translateListener;
	private DoneCallback<JsonObject> doneCallback;
	private FailCallback failCallback;

	public MainInfoController (PlaceDetailSubviewModel model){
		mainInfoModel = (MainInfoModel) model;
	}

	@Override
	public void render(LinearLayout llContent, final Activity activity, PlaceDetailFragmentFactories factories) {
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

		tvTitle.setText(mainInfoModel.getTitle());

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
				PlaceDetailReferenceUtils.showUrl(activity, mainInfoModel.getPerexLink(), "", "");
			}
			@Override
			public void updateDrawState(TextPaint drawState) {
				super.updateDrawState(drawState);
				drawState.setColor(ContextCompat.getColor(activity, R.color.text_grey));
			}
		};
	}
}
