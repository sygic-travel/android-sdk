package itemDetail.subviews;


import com.sygic.travel.sdk.model.place.Reference;

import java.util.List;

/**
 * Represents packed references to be shown in ReferencesListActivity.
 * Attribute reference is always first item in list to be eventualy shown as CTA
 */
public class MultipleReferencesModel implements ItemDetailSubviewModel{
	private Reference reference;
	private List<Reference> otherReferences;
	private int type;

	public MultipleReferencesModel(Reference reference){
		this.reference = reference;
		type = ItemDetailSubviewModel.REFERENCE;
	}

	public MultipleReferencesModel(List<Reference> otherReferences) {
		this.otherReferences = otherReferences;
		reference = otherReferences.get(0);
		type = MULTIPLE_REFERENCES;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public List<Reference> getOtherReferences() {
		return otherReferences;
	}

	public void setOtherReferences(List<Reference> otherReferences) {
		this.otherReferences = otherReferences;
	}

	@Override
	public int getType() {
		return type;
	}
}
