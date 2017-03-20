package itemDetail.subviews;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sygic.travel.sdk.model.place.Reference;
import com.sygic.travel.sdkdemo.R;

import itemDetail.fragment.ItemDetailFragmentFactories;


public class ReferenceController implements ItemDetailSubview{
	protected Reference reference;
	private TextView tvFlags, tvDescPrice, btnAction;
	protected View referenceView;

	public ReferenceController(ItemDetailSubviewModel reference) {
		this.reference = ((MultipleReferencesModel)reference).getReference();
	}

	@Override
	public void render(LinearLayout llContent, Activity activity, ItemDetailFragmentFactories factories){
		referenceView = activity.getLayoutInflater().inflate(R.layout.reference_layout, null);

		initUiElements();
		llContent.addView(referenceView);
		renderReference(activity);

	}

	protected void initUiElements(){
		tvFlags = (TextView) referenceView.findViewById(R.id.tv_flags);
		tvDescPrice = (TextView) referenceView.findViewById(R.id.tv_desc_price);
		btnAction = (Button) referenceView.findViewById(R.id.btn_action);

	}

	protected void renderReference(
		final Activity activity
	){
		String mainType = normalizeReferenceType(reference.getType());
		String dashFrom = " - " + activity.getString(R.string.detail_from) + " ";
		String priceWithCurrency = CurrenciesLoader.getValueInCurrencyString(reference.getPrice());
		String price = reference.getPrice() == 0 ? "" : dashFrom + priceWithCurrency;


		tvDescPrice.setText(reference.getTitle() + price, TextView.BufferType.SPANNABLE);
		Spannable title = (Spannable) tvDescPrice.getText();
		title.setSpan(new StyleSpan(Typeface.BOLD), 0, reference.getTitle().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		if(reference.getPrice() != 0) {
			title.setSpan(
				new ForegroundColorSpan(ContextCompat.getColor(activity, R.color.st_blue)),
				reference.getTitle().length() + dashFrom.length(),
				reference.getTitle().length() + dashFrom.length() + priceWithCurrency.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
			);
		}

		renderReferenceFlags(reference, tvFlags, activity.getResources());
		btnAction.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				showReferenceUrl(
					activity,
					new ReferenceWrapper(reference),
					ItemDetailReferenceUtils.DETAIL
				);
			}
		});

		setActionButtonText(mainType, activity.getResources());
	}

	private void setActionButtonText(String mainType, Resources resources){
		switch(mainType){
			case TICKET: {
				btnAction.setText(R.string.item_detail_buy_ticket);
				break;
			}

			case TABLE: {
				btnAction.setText(R.string.item_detail_book_table);
				break;
			}

			case TOUR: {
				btnAction.setText(R.string.item_detail_book_tour);
				break;
			}

			case TRANSFER: {
				btnAction.setText(R.string.item_detail_book_transfer);
				break;
			}

			case RENT: {
				btnAction.setText(getRentButtonText(reference.getType(), resources));
				break;
			}

			case PARKING: {
				btnAction.setText(R.string.book_parking);
				break;
			}

			default: {
				break;
			}
		}
	}

	private String getRentButtonText(String referenceType, Resources resources){
		String referenceTypeSplit[] = referenceType.split(":");
		if(referenceTypeSplit.length <= 1) {
			return resources.getString(R.string.item_detail_rent);
		}

		switch(referenceTypeSplit[1]){
			case RENT_TYPE_BIKE:
				return resources.getString(R.string.item_detail_rent_bike);
			case RENT_TYPE_BOAT:
				return resources.getString(R.string.item_detail_rent_boat);
			case RENT_TYPE_CAR:
				return resources.getString(R.string.item_detail_rent_car);
			case RENT_TYPE_SCOOTER:
				return resources.getString(R.string.item_detail_rent_scooter);
			case RENT_TYPE_SKI:
				return resources.getString(R.string.item_detail_rent_skis);
			default:
				return resources.getString(R.string.item_detail_rent);
		}
	}

}
