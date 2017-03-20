package itemDetail;

import com.tripomatic.contentProvider.db.pojo.Reference;

import java.util.List;

import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.*;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.PASS;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.RENT;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.TABLE;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.TICKET;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.TOUR;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.TRANSFER;
import static com.tripomatic.utilities.references.ItemDetailReferenceUtils.normalizeReferenceType;

public class TypedReferenceList {
	private List<Reference> referenceList;
	private String type;

	public TypedReferenceList(List<Reference> referenceList, String type) {
		this.referenceList = referenceList;
		this.type = type;
	}

	public List<Reference> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<Reference> referenceList) {
		this.referenceList = referenceList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIntegerTypePriority(){
		if(type.equals(TICKET)){
			return 0;
		} else if(type.equals(TABLE)){
			return 1;
		} else if(type.equals(TOUR)){
			return 2;
		} else if(type.equals(TRANSFER)){
			return 3;
		} else if(normalizeReferenceType(type).equals(RENT)){
			return 4;
		} else if(type.equals(PASS)){
			return 5;
		} else {
			return 999;
		}
	}
}
