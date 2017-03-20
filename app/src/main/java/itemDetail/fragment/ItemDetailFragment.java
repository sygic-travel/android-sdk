package itemDetail.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tripomatic.R;
import com.tripomatic.contentProvider.db.pojo.Feature;
import com.tripomatic.contentProvider.db.pojo.HotelDetail;
import com.tripomatic.contentProvider.db.pojo.PlaceDetail;
import com.tripomatic.contentProvider.db.pojo.Reference;
import com.tripomatic.ui.activity.itemDetail.ReferenceTypeComparator;
import com.tripomatic.ui.activity.itemDetail.RenderModel;
import com.tripomatic.ui.activity.itemDetail.TypedReferenceList;
import com.tripomatic.ui.activity.itemDetail.subviews.BookingModel;
import com.tripomatic.ui.activity.itemDetail.subviews.ItemDetailSubviewModel;
import com.tripomatic.ui.activity.itemDetail.subviews.MainInfoModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.FACEBOOK;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.OFFICIAL;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.PARKING;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.PASS;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.RENT;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.SIMPLE_LINK;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.TABLE;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.TICKET;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.TOUR;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.TRANSFER;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.WIKI;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.isListableType;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.normalizeReferenceType;

public class ItemDetailFragment extends Fragment {
	private Feature feature;
	private String guid;
	private ItemDetailFragmentFactories factories;
	private Activity activity;
	private FragmentRenderer renderer;
	private View rootView;

	public void setDependencies(
		Activity activity,
		ItemDetailListener listener
	){
		this.factories = new ItemDetailFragmentFactories(activity, this, listener);
		this.activity = activity;
		renderer = factories.getRenderer();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.item_detail_fragment, container, false);

		prepareAndShowContent();

		return rootView;
	}
	public void loadWithGuid(String guid){

	}

	public void loadWithFeature(Feature feature){
		this.feature = feature;
		guid = feature.getGuid();
	}

	private void prepareAndShowContent(){
		Map<String, List<Reference>> processedReferences = new LinkedHashMap<>();
		List<Reference> singleReferences = new ArrayList<>();
		List<Reference> passes = new ArrayList<>();
		List<Reference> externalLinks = new ArrayList<>();
		List<TypedReferenceList> preparedReferences = new ArrayList<>();


		RenderModel renderModel = new RenderModel();
		String type;
		Boolean isListable;
		PlaceDetail placeDetail = feature.getPlaceDetail();
		HotelDetail hotelDetail = feature.getHotelDetail();

		renderModel.addMainInfo(
			new MainInfoModel(
				feature.getName(),
				feature.getText(),
				feature.getOriginalName(),
				factories.getMarkerMapper().getMarkerInfo(activity, feature.getMarker()).getCode(),
				guid,
				feature.getPerexTranslationProvider(),
				feature.getPerexProvider(),
				feature.getPerexLink(),
				feature.getGuid().contains("hotel:"),
				feature.isEstimated(),
				feature.getStarRating(),
				feature.isTranslated()
			)
		);

		if(feature.getFodorsDescription() != null){
			renderModel.addSimpleLink(getString(R.string.detail_read_more), ItemDetailSubviewModel.FODORS);
		}

		renderModel.addDivider();

		if(feature.getGuid().contains("hotel:")) {
			BookingModel bookingModel = new BookingModel(
				(float) feature.getCustomerRating(),
				feature.getPriceValue(),
				feature.getGatewayUrl(),
				feature.getName()
			);
			renderModel.addBooking(bookingModel);
		}

		if(feature.getReferences() != null) {

			for(Reference reference : feature.getReferences()) {
				type = normalizeReferenceType(reference.getType());
				isListable = isListableType(type);
				if(isListable != null) {
					if(isListable) {
						if(type.equals(RENT)) {
							type = reference.getType();
						}

						if(processedReferences.get(type) == null) {
							List<Reference> references = new ArrayList<>();
							references.add(reference);
							processedReferences.put(type, references);
						} else {
							processedReferences.get(type).add(reference);
						}
					} else {
						singleReferences.add(reference);
					}
				}
			}
		}

		for(Map.Entry<String, List<Reference>> entry : processedReferences.entrySet()) {
			preparedReferences.add(new TypedReferenceList(entry.getValue(), entry.getKey()));
		}
		Collections.sort(preparedReferences, new ReferenceTypeComparator());

		for(TypedReferenceList entry : preparedReferences){
			String listType = entry.getType();
			if(listType.equals(TOUR)){
				renderModel.addTours(entry.getReferenceList());
			} else if(
				normalizeReferenceType(listType).equals(RENT) ||
					listType.equals(TABLE) ||
					listType.equals(TRANSFER) ||
					listType.equals(TICKET) ||
					listType.equals(PARKING))
			{
				if(entry.getReferenceList().size() == 1){
					renderModel.addReference(entry.getReferenceList().get(0));
				} else {
					renderModel.addMultipleReferences(entry.getReferenceList());
				}
			} else if(listType.equals(PASS)) {
				passes = entry.getReferenceList();
			} else if(listType.equals(SIMPLE_LINK)){
				externalLinks = entry.getReferenceList();
			}
			renderModel.addDivider();
		}


		addSingleReference(renderModel, WIKI, singleReferences);
		addSingleReference(renderModel, OFFICIAL, singleReferences);

		renderModel.addDivider();

		if(placeDetail != null) {
			renderModel.addBasicExpandableElement(
				placeDetail.getAdmission(),
				getString(R.string.item_detail_admission),
				R.drawable.ic_admission
			);
			renderModel.addBasicExpandableElement(
				placeDetail.getOpeningHours(),
				getString(R.string.custom_place_hint_opening_hours),
				R.drawable.ic_opening_hours
			);
		}

		if(!guid.contains("hotel:")) {
			renderModel.addDuration(feature.getDuration(), getString(R.string.duration));
		}

		if(placeDetail != null){
			renderModel.addBasicExpandableElement(
				placeDetail.getAddress(),
				getString(R.string.custom_place_address),
				R.drawable.ic_address
			);
		}

		if(hotelDetail != null){
			renderModel.addBasicExpandableElement(
				hotelDetail.getAddress(),
				getString(R.string.custom_place_address),
				R.drawable.ic_address
			);
		}

		renderModel.addNavigationRow(true, feature.getLat(), feature.getLng());

		renderModel.addDivider();

		if(placeDetail != null) {
			renderModel.addSimpleLink(placeDetail.getEmail(), ItemDetailSubviewModel.EMAIL);
			renderModel.addSimpleLink(placeDetail.getPhone(), ItemDetailSubviewModel.PHONE_NUMBER);
		}

		addSingleReference(renderModel, FACEBOOK, singleReferences);

		renderModel.addPackedReferences(passes);

		renderModel.addExternalLinks(externalLinks);

		renderModel.addDivider();

		renderModel.addSimpleLink(getString(R.string.add_note), ItemDetailSubviewModel.ADD_NOTE);

		renderModel.addTags(feature.getTagsValues());

		renderModel.addAttribution(feature.getAttributionModel());

		activity.runOnUiThread(
			renderer.getRenderContent(
				activity,
				renderModel,
				factories,
				(LinearLayout) rootView.findViewById(R.id.ll_content)
			)
		);

	}

	private void addSingleReference(RenderModel renderModel, String type, List<Reference> references) {
		for(Reference reference : references){
			if(normalizeReferenceType(reference.getType()).equals(type)){
				renderModel.addSimpleReferenceLink(reference);
				break;
			}
		}
	}

	public Feature getFeature(){
		return feature;
	}

	public FragmentRenderer getRenderer(){
		return renderer;
	}
}
