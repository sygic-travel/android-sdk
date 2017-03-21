package itemDetail.subviews;

import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sygic.travel.sdkdemo.R;

import itemDetail.ItemDetailReferenceUtils;
import itemDetail.ReviewScoreRating;
import itemDetail.fragment.ItemDetailFragmentFactories;


public class BookingController implements ItemDetailSubview {
	private Button btnBook;
	private TextView tvPrice, tvRating, tvRatingValue, tvPriceGuarantee;
	private View rootView;
	private RelativeLayout llScoreLayout;
	private BookingModel bookingModel;

	public BookingController (ItemDetailSubviewModel model){
		bookingModel = (BookingModel)model;
	}

	@Override
	public void render(LinearLayout llContent, final Activity activity, ItemDetailFragmentFactories factories) {
		rootView = activity.getLayoutInflater().inflate(R.layout.booking_layout, null);
		tvPrice = (TextView) rootView.findViewById(R.id.tv_price);
		tvRating = (TextView) rootView.findViewById(R.id.tv_detail_score_rating);
		tvRatingValue = (TextView) rootView.findViewById(R.id.tv_detail_score_rating_value);
		btnBook = (Button) rootView.findViewById(R.id.btn_book);
		llScoreLayout = (RelativeLayout) rootView.findViewById(R.id.rl_detail_score_rating);
		tvPriceGuarantee = (TextView) rootView.findViewById(R.id.price_guarantee);
		tvPriceGuarantee.setMovementMethod(LinkMovementMethod.getInstance());

		//tvPrice.setText(" " + CurrenciesLoader.getValueInCurrencyString(bookingModel.getPrice()));
		tvPrice.setText(" " + (bookingModel.getPrice()));
		btnBook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ItemDetailReferenceUtils.showUrl(
					activity,
					bookingModel.getUrl(),
					bookingModel.getName(),
					null
				);
			}
		});

		renderHotelReviewScoreRating(bookingModel.getRating(), activity);
		llContent.addView(rootView);
	}

	private void renderHotelReviewScoreRating(float reviewScore, Activity activity) {
		if(reviewScore > 0f) {
			ReviewScoreRating rating = new ReviewScoreRating(reviewScore);
			llScoreLayout.setVisibility(View.VISIBLE);
			tvRatingValue.setText(String.valueOf(reviewScore));
			tvRating.setText(rating.getCategoryTag(activity.getResources()).toUpperCase());
		} else {
			llScoreLayout.setVisibility(View.GONE);
		}
	}
}
