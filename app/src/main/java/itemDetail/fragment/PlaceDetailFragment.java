package itemDetail.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sygic.travel.sdk.model.place.Detail;
import com.sygic.travel.sdk.model.place.Reference;
import com.sygic.travel.sdkdemo.R;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import itemDetail.ReferenceTypeComparator;
import itemDetail.RenderModel;
import itemDetail.TypedReferenceList;
import itemDetail.subviews.PlaceDetailSubviewModel;
import itemDetail.subviews.MainInfoModel;

import static itemDetail.PlaceDetailReferenceUtils.*;


public class PlaceDetailFragment extends Fragment {
	private Detail feature;
	private String guid;
	private PlaceDetailFragmentFactories factories;
	private Activity activity;
	private PlaceDetailFragmentRenderer renderer;
	private View rootView;

	public void setDependencies(
		Activity activity,
		PlaceDetailListener listener
	){
		this.factories = new PlaceDetailFragmentFactories(activity, this, listener);
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

	public void loadWithFeature(Detail feature){
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

		renderModel.addMainInfo(
			new MainInfoModel(
				feature.getName(),
				feature.getDescription().getText(),
				"picovina",//feature.getOriginalName(),
				factories.getMarkerMapper().getMarkerInfo(activity, feature.getMarker()).getCode(),
				guid,
				feature.getDescription().getTranslationProvider(),
				feature.getDescription().getProvider(),
				feature.getDescription().getUrl(),
				feature.getGuid().contains("hotel:"),
				false,//feature.isEstimated(),
				5,//feature.getStarRating(),
				true//feature.isTranslated()
			)
		);

		/*if(feature.getFodorsDescription() != null){
			renderModel.addSimpleLink(getString(R.string.detail_read_more), ItemDetailSubviewModel.FODORS);
		}*/

		renderModel.addDivider();

		/*if(feature.getGuid().contains("hotel:")) {
			BookingModel bookingModel = new BookingModel(
				(float) feature.getCustomerRating(),
				feature.getPrice(),
				feature.getGatewayUrl(),
				feature.getName()
			);
			renderModel.addBooking(bookingModel);
		}*/

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

		if(feature.getAdmission() != null) {
			renderModel.addBasicExpandableElement(
				feature.getAdmission(),
				getString(R.string.item_detail_admission),
				R.drawable.ic_admission
			);
		}

		if(feature.getOpeningHours() != null){
			renderModel.addBasicExpandableElement(
				feature.getOpeningHours(),
				getString(R.string.custom_place_hint_opening_hours),
				R.drawable.ic_opening_hours
			);
		}

		if(!guid.contains("hotel:")) {
			renderModel.addDuration(feature.getDuration(), getString(R.string.duration));
		}

		if(feature.getAddress() != null){
			renderModel.addBasicExpandableElement(
				feature.getAddress(),
				getString(R.string.custom_place_address),
				R.drawable.ic_address
			);
		}

		renderModel.addNavigationRow(true, feature.getLocation().getLat(), feature.getLocation().getLng());

		renderModel.addDivider();

		if(feature.getEmail() != null) {
			renderModel.addSimpleLink(feature.getEmail(), PlaceDetailSubviewModel.EMAIL);
		}

		if(feature.getPhone() != null){
			renderModel.addSimpleLink(feature.getPhone(), PlaceDetailSubviewModel.PHONE_NUMBER);
		}

		addSingleReference(renderModel, FACEBOOK, singleReferences);

		renderModel.addPackedReferences(passes);

		renderModel.addExternalLinks(externalLinks);

		renderModel.addDivider();

		renderModel.addSimpleLink(getString(R.string.add_note), PlaceDetailSubviewModel.ADD_NOTE);

		renderModel.addTags(feature.getTags());

		//renderModel.addAttribution(feature.getm());

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

	public Detail getFeature(){
		return feature;
	}

	public PlaceDetailFragmentRenderer getRenderer(){
		return renderer;
	}
}
