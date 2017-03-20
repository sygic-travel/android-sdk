package itemDetail.subviews;

public interface ItemDetailSubviewModel {
	int REFERENCE = 0;
	int MULTIPLE_REFERENCES = 1;
	int PHONE_NUMBER = 2;
	int EMAIL = 3;
	int BASIC_EXPANDABLE = 4;
	int MAIN_LAYOUT = 5;
	int TAGS = 6;
	int ADD_NOTE = 7;
	int DURATION = 8;
	int LAT_LNG_DRIVE = 9;
	int LAT_LNG_WALK = 10;
	int FODORS = 11;
	int BOOKING = 12;
	int TOUR = 13;
	int ATTRIBUTION = 14;


	int getType();
}
