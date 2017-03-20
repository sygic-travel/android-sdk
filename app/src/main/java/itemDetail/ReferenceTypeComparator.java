package itemDetail;

import java.util.Comparator;

public class ReferenceTypeComparator implements Comparator<TypedReferenceList> {
	@Override
	public int compare(TypedReferenceList references1, TypedReferenceList references2) {
		if(references1.getIntegerTypePriority() > references2.getIntegerTypePriority()){
			return 1;
		} else if(references1.getIntegerTypePriority() < references2.getIntegerTypePriority()){
			return -1;
		} else {
			return 0;
		}
	}
}
