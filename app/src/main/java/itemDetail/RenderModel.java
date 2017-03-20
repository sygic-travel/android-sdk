package itemDetail;

import com.tripomatic.contentProvider.db.pojo.Reference;
import com.tripomatic.ui.activity.itemDetail.subviews.AttributionModel;
import com.tripomatic.ui.activity.itemDetail.subviews.BasicExpandableElement;
import com.tripomatic.ui.activity.itemDetail.subviews.BookingModel;
import com.tripomatic.ui.activity.itemDetail.subviews.ItemDetailSubviewModel;
import com.tripomatic.ui.activity.itemDetail.subviews.LatLngModel;
import com.tripomatic.ui.activity.itemDetail.subviews.MainInfoModel;
import com.tripomatic.ui.activity.itemDetail.subviews.MultipleReferencesModel;
import com.tripomatic.ui.activity.itemDetail.subviews.SimpleDetailModel;
import com.tripomatic.ui.activity.itemDetail.subviews.TagsWrapperModel;
import com.tripomatic.utilities.references.ItemDetailReferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenderModel {
	public static final String REFERENCE = "reference";
	public static final String MULTIPLE_REFERENCES = "multipleReferences";
	public static final String PACKED_REFERENCES = "packedReferences";
	public static final String LINK = "link";
	public static final String EXPANDABLE_ELEMENT = "expandableElement";
	public static final String MAIN_LAYOUT = "mainLayout";
	public static final String TAGS_LAYOUT = "tagsLayout";
	public static final String BOOKING = "booking";
	public static final String ATTRIBUTION = "attribution";
	public static final String DIVIDER = "divider";
	public static final String EXTERNAL_LINKS = "externalLinks";
	private List<RenderModelEntry> data;

	public RenderModel(){
		data = new ArrayList<>();
	}

	public List<RenderModelEntry> getData() {
		return data;
	}

	public void setData(List<RenderModelEntry> data) {
		this.data = data;
	}

	public void addReference(Reference reference){
		data.add(new RenderModelEntry(REFERENCE, new MultipleReferencesModel(reference)));
	}

	public void addMultipleReferences(List<Reference> references){
		MultipleReferencesModel model = getMultipleReferencesModel(references);
		if(model != null) {
			data.add(new RenderModelEntry(MULTIPLE_REFERENCES, model));
		}
	}

	public void addTours(List<Reference> references) {
		MultipleReferencesModel model = getMultipleReferencesModel(references);
		if(model != null && references.get(0) != null) {
			if(references.get(0).getType().equals(ItemDetailReferenceUtils.TOUR_PLACE)) {
				if(references.size() == 1){
					data.add(new RenderModelEntry(REFERENCE, new MultipleReferencesModel(references.get(0))));
				} else {
					data.add(new RenderModelEntry(MULTIPLE_REFERENCES, model));
				}
			} else {
				data.add(new RenderModelEntry(PACKED_REFERENCES, model));
			}
		}
	}

	public void addBooking(BookingModel bookingModel){
		data.add(new RenderModelEntry(BOOKING, bookingModel));
	}

	public void addTags(List<String> tags){
		data.add(new RenderModelEntry(TAGS_LAYOUT, new TagsWrapperModel(tags)));
	}

	public void addMainInfo(MainInfoModel mainInfoModel){
		data.add(new RenderModelEntry(MAIN_LAYOUT, mainInfoModel));
	}

	public void addPackedReferences(List<Reference> references){
		MultipleReferencesModel model = getMultipleReferencesModel(references);
		if(model != null) {
			data.add(new RenderModelEntry(PACKED_REFERENCES, model));
		}
	}

	public void addSimpleReferenceLink(Reference reference){
		if(reference != null){
			data.add(new RenderModelEntry(LINK, new MultipleReferencesModel(reference)));
		}
	}

	public void addSimpleLink(String data, int type) {
		if(data != null) {
			this.data.add(new RenderModelEntry(LINK, new SimpleDetailModel(data, type)));
		}
	}


	private MultipleReferencesModel getMultipleReferencesModel(List<Reference> references){
		if(references != null && !references.isEmpty()) {
			Collections.sort(references, new ReferenceComparator());
			return new MultipleReferencesModel(references);
		} else {
			return null;
		}
	}

	public void addNavigationRow(boolean drive, double lat, double lng){
		data.add(new RenderModelEntry(LINK, new LatLngModel(drive, lat, lng)));
	}

	public void addBasicExpandableElement(String expandableText, String title, int iconId) {
		if(expandableText != null && title != null) {
			data.add(new RenderModelEntry(EXPANDABLE_ELEMENT, new BasicExpandableElement(title, expandableText, iconId)));
		}
	}

	public void addDuration(int duration, String text) {
		data.add(
			new RenderModelEntry(
				LINK,
				new SimpleDetailModel(
					text,
					ItemDetailSubviewModel.DURATION,
					duration
				)
			)
		);
	}

	public void addAttribution(AttributionModel model){
		if(model != null && model.getSource() != null && model.getAuthor() != null && model.getTitle() != null){
			data.add(new RenderModelEntry(ATTRIBUTION, model));
		}
	}

	public void addDivider(){
		data.add(new RenderModelEntry(DIVIDER, null));
	}

	public void addExternalLinks(List<Reference> externalLinks) {
		if(externalLinks.size() > 0) {
			data.add(new RenderModelEntry(EXTERNAL_LINKS, new MultipleReferencesModel(externalLinks)));
		}
	}
}
