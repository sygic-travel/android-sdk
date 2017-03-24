package placeDetail;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;


import com.sygic.travel.sdk.model.place.Reference;
import com.sygic.travel.sdkdemo.R;

import java.util.Collections;

public class PlaceDetailReferenceUtils {
	public static final String
		TICKET = "ticket",
		TOUR = "tour",
		TABLE = "table",
		TRANSFER = "transfer",
		RENT = "rent",
		ROOM = "room",
		WIKI = "wiki",
		FACEBOOK = "facebook",
		OFFICIAL = "official",
		PASS = "pass",
		SIMPLE_LINK = "simpleLink",
		PARKING = "parking";
	public static final String BOOK_TABLE = "book:table";
	public static final String BUY_TICKET = "buy:ticket";
	public static final String BOOK_ROOM = "book:room";
	public static final String LINK_OFFICIAL = "link:official";
	public static final String LINK_FACEBOOK = "link:facebook";
	public static final String LINK = "link";
	public static final String TOUR_PLACE = "tour:place";

	public static final String RENT_TYPE_BIKE = "bike";
	public static final String RENT_TYPE_BOAT = "boat";
	public static final String RENT_TYPE_CAR = "car";
	public static final String RENT_TYPE_SCOOTER = "scooter";
	public static final String RENT_TYPE_SKI = "ski";

	private static final String BESTSELLER = "bestseller";
	private static final String INSTANT_CONFIRMATION = "instant_confirmation";
	private static final String MOBILE_VOUCHER = "mobile_voucher";
	private static final String PRIVATE = "private";
	private static final String SKIP_THE_LINE = "skip_the_line";
	private static final int MAX_RATING = 6;
	public static final String DETAIL = "Detail";
	public static final String MENU = "Menu";
	public static final String TOURS = "Tours";
	private static String userId = null;
	private static final String GYG = "get_your_guide";
	private static final String ANDROID = "Android";
	private static final String CMP_PARAM = "cmp";
	private static final String SUBPUID_PARAM = "SUBPUID";

	private static final String VIATOR = "viator";


	public static String normalizeReferenceType(String type){
		if(type.equals(BOOK_TABLE)){
			return TABLE;
		} else if(type.equals(BUY_TICKET)){
			return TICKET;
		} else if(type.contains(TRANSFER)){
			return TRANSFER;
		} else if(type.contains(TOUR)){
			return TOUR;
		} else if(type.contains(RENT)){
			return RENT;
		} else if(type.equals(BOOK_ROOM)){
			return ROOM;
		} else if(type.contains(PARKING)){
			return PARKING;
		} else if(type.equals(WIKI)){
			return WIKI;
		} else if(type.equals(LINK_OFFICIAL)){
			return OFFICIAL;
		} else if(type.equals(LINK_FACEBOOK)){
			return FACEBOOK;
		} else if(type.contains(PASS)){
			return PASS;
		} else if(type.contains(LINK)){
			return SIMPLE_LINK;
		} else {
			return "";
		}
	}

	public static Boolean isListableType(String type){
		switch(type){
			case TABLE:
			case TOUR:
			case TICKET:
			case TRANSFER:
			case RENT:
			case PASS:
			case PARKING:
			case SIMPLE_LINK:{
				return true;
			}

			case ROOM:
			case WIKI:
			case FACEBOOK:
			case OFFICIAL:{
				return false;
			}
			default:{
				return null;
			}
		}
	}

	public static void showReferenceUrl(
		Activity activity,
		ReferenceWrapper reference,
		String campaign
	) {
		showReferenceUrl(activity, reference, campaign, "");
	}

	public static void showReferenceUrl(
		Activity activity,
		ReferenceWrapper reference,
		String campaign,
		String dirUrl
	) {
		String url = checkSupplierAndAddUrlParam(
			reference.getUrl(),
			reference.getSupplier(),
			campaign
		);

		showUrl(activity, url, reference.getTitle(), dirUrl);

	}

	private static String checkSupplierAndAddUrlParam(
		String url,
		String supplier,
		String campaign
	){
		if(supplier == null){
			return url;
		}

		if(supplier.equals(GYG)){
			String cmp = ANDROID + "_" + campaign + "_" + userId;
			Uri uri = Uri.parse(url).buildUpon().appendQueryParameter(CMP_PARAM, cmp).build();
			return uri.toString();
		} else if(supplier.equals(VIATOR)){
			String subpuid = ANDROID + "_" + campaign;
			Uri uri = Uri.parse(url).buildUpon().appendQueryParameter(SUBPUID_PARAM, subpuid).build();
			return uri.toString();
		}
		return url;
	}

	public static void showUrl(Activity activity, String url, String title, String dirUrl){
		Intent intent = new Intent(activity, TripReferenceActivity.class);
		intent.putExtra(TripReferenceActivity.REFERENCE_DIR_PATH, dirUrl);
		intent.putExtra(TripReferenceActivity.REFERENCE_URL, url);
		intent.putExtra(TripReferenceActivity.REFERENCE_TITLE, title);
		activity.startActivity(intent);
	}

	public static void renderReferenceFlags(Reference reference, TextView tvFlags, Resources resources){
		StringBuilder builder = new StringBuilder();
		String processedFlag;
		Collections.sort(reference.getFlags());
		int addedFlags = 0;
		if(reference.getFlags() != null && reference.getFlags().size() > 0) {
			for(int i = 0 ; i < reference.getFlags().size() ; i++){
				processedFlag = getReferenceFlagTitle(reference.getFlags().get(i), resources);
				if(processedFlag != null) {
					addedFlags++;
					builder.append(processedFlag);
					if(i < reference.getFlags().size() - 1){
						builder.append(" \u2022 ");
					}
				}
			}
			tvFlags.setText(builder.toString());
		}

		if(addedFlags > 0){
			tvFlags.setVisibility(View.VISIBLE);
		} else {
			tvFlags.setVisibility(View.GONE);
		}
	}
	
	private static String getReferenceFlagTitle(String input, Resources resources){
		switch(input){
			case BESTSELLER:{
				return resources.getString(R.string.item_detail_bestsellser);
			}

			case INSTANT_CONFIRMATION:{
				return resources.getString(R.string.item_detail_instant_confirmation);
			}

			case MOBILE_VOUCHER:{
				return resources.getString(R.string.item_detail_mobile_voucher);
			}

			case PRIVATE:{
				return resources.getString(R.string.item_detail_private);
			}

			case SKIP_THE_LINE:{
				return resources.getString(R.string.item_detail_skip_line);
			}
		}
		return null;
	}
}
