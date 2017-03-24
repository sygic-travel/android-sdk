package placeDetail;

import android.content.Context;
import android.support.v4.content.ContextCompat;


import com.sygic.travel.sdkdemo.R;

import java.util.HashMap;
import java.util.Map;

public class MarkerMapper {
	public static final String DISCOVERING = "discovering";
	public static final String SLEEPING = "sleeping";
	public static final String SIGHTSEEING = "sightseeing";
	public static final String RELAXING = "relaxing";
	public static final String HIKING = "hiking";
	public static final String GOING_OUT = "going_out";
	public static final String PLAYING = "playing";
	public static final String SPORTS = "sports";
	public static final String NO_CATEGORY = "no_category";
	public static final String OTHER = "other";
	public static final String EATING = "eating";
	public static final String TRAVELING = "traveling";
	public static final String SHOPPING = "shopping";
	public static final String DEFAULT = "default";

	public static final String ICONFONT_PATH = "fonts/icomoon.ttf";

	public static final String NO_CATEGORY_VALUE = "\ue95d";
	
	private Map<String, Character> markers = new HashMap<>();
	private Map<String, Integer> colorIds = new HashMap<>();

	public MarkerMapper() {
		init();
	}

	private void init(){
		String markersKeys[] = new String[]{
			"playing",
			"playing:playground",
			"playing:playground:indoor",
			"playing:park",
			"playing:park:water",
			"playing:park:theme",
			"playing:park:theme:disney",
			"traveling",
			"traveling:taxi_stand",
			"traveling:station",
			"traveling:station:train",
			"traveling:station:train:tram",
			"traveling:station:subway",
			"traveling:station:bus",
			"traveling:parking",
			"traveling:fuel",
			"traveling:fuel:car_charging",
			"traveling:ferry_terminal",
			"traveling:car+rental",
			"traveling:cable_car",
			"traveling:airport",
			"traveling:airport:helipad",
			"traveling:airport:airfield",
			"sports",
			"sports:winter:ice_rink",
			"sports:winter:ice_hockey",
			"sports:tennis",
			"sports:stadium",
			"sports:pool",
			"sports:pool:outdoor",
			"sports:pool:indoor",
			"sports:horse",
			"sports:golf",
			"sports:golf:minigolf",
			"sports:fitness",
			"sports:field",
			"sports:field:soccer",
			"sports:centre",
			"sports:bycicle_rental",
			"sports:bat",
			"sleeping",
			"sleeping:hotel",
			"sleeping:hotel:ryokan",
			"sleeping:hostel",
			"sleeping:campsite",
			"sleeping:apartment",
			"sightseeing",
			"sightseeing:tower",
			"sightseeing:sculpture",
			"sightseeing:ruins",
			"sightseeing:place_of_worship:church",
			"sightseeing:palace",
			"sightseeing:monument",
			"sightseeing:mill",
			"sightseeing:memorial",
			"sightseeing:marina",
			"sightseeing:lighthouse",
			"sightseeing:library",
			"sightseeing:fountain",
			"sightseeing:fort",
			"sightseeing:cheateau",
			"sightseeing:castle",
			"sightseeing:brewery",
			"sightseeing:art:artwork",
			"sightseeing:architecture:modern",
			"sightseeing:archeological:site",
			"shopping",
			"shopping:toys",
			"shopping:supermarket:tesco",
			"shopping:supermarket:lidl",
			"shopping:shoes",
			"shopping:market",
			"shopping:market:fish",
			"shopping:kiosk",
			"shopping:jewelery",
			"shopping:ice_cream",
			"shopping:florist",
			"shopping:electronics",
			"shopping:electronics:apple",
			"shopping:deli",
			"shopping:deli:candy",
			"shopping:convenience_store",
			"shopping:clothes",
			"shopping:centre",
			"shopping:butcher",
			"shopping:bookshop",
			"shopping:bakery",
			"relaxing",
			"relaxing:spa",
			"relaxing:sauna",
			"relaxing:park",
			"relaxing:park:garden",
			"relaxing:beach",
			"other",
			"other:toilets",
			"other:school",
			"other:post:office",
			"other:post:box",
			"other:place_of_worship",
			"other:place_of_worship:temple",
			"other:place_of_worship:synagogue",
			"other:place_of_worship:shrine",
			"other:place_of_worship:mosque",
			"other:place_of_worship:monastery",
			"other:place_of_worship:church",
			"other:place_of_worship:chapel",
			"other:pharmacy",
			"other:information",
			"other:information:office",
			"other:information:guidepost",
			"other:information:board",
			"other:hairdresser",
			"other:emergency:police",
			"other:emergency:fire",
			"other:drinking_water",
			"other:dog_park",
			"other:doctor",
			"other:doctor:hospital",
			"other:dentist",
			"other:cemetery",
			"other:bank",
			"other:atm",
			"hiking",
			"hiking:waterfall",
			"hiking:volcano",
			"hiking:valley",
			"hiking:rock",
			"hiking:picnic_site",
			"hiking:park",
			"hiking:park:nature",
			"hiking:mountains",
			"hiking:lake",
			"hiking:hill",
			"hiking:forest",
			"hiking:cave",
			"hiking:bbq",
			"going_out",
			"going_out:wine_bar",
			"going_out:pub",
			"going_out:opera",
			"going_out:club:music",
			"going_out:club:dance",
			"going_out:circus",
			"going_out:cinema",
			"going_out:casino",
			"going_out:cabaret",
			"going_out:bar",
			"eating",
			"eating:restaurant",
			"eating:restaurant:pizza",
			"eating:restaurant:pizza:pizza_hut",
			"eating:restaurant:mexican",
			"eating:restaurant:italian",
			"eating:restaurant:fastfood",
			"eating:restaurant:fastfood:tacos:taco_bell",
			"eating:restaurant:fastfood:mcdonalds",
			"eating:restaurant::fastfood:kfc",
			"eating:restaurant:burgers",
			"eating:restaurant:burgers:burger_king",
			"eating:restaurant:asian",
			"eating:cafe",
			"eating:cafe:starbucks",
			"discovering",
			"discovering:zoo",
			"discovering:zoo:safari",
			"discovering:zoo:aquarium",
			"discovering:zoo:aquarium:sea_world",
			"discovering:university",
			"discovering:theatre",
			"discovering:planetarium",
			"discovering:observatory",
			"discovering:museum",
			"discovering:garden:tropical",
			"discovering:garden:botanical",
			"discovering:gallery",
			"discovering:art_centre",
			"destination:village",
			"destination:town",
			"destination:state",
			"destination:settlement",
			"destination:region",
			"destination:neighbourhood",
			"destination:municipality",
			"destination:locality",
			"destination:island",
			"destination:hamlet",
			"destination:county",
			"destination:country",
			"destination:continent",
			"destination:city",
			"destination:archipelago"
		};

		char markerValue = 0xe900;
		for(String markersKey : markersKeys) {
			markers.put(markersKey, markerValue);
			markerValue++;
		}

		markers.put(NO_CATEGORY, NO_CATEGORY_VALUE.charAt(0));
		markers.put(DEFAULT, NO_CATEGORY_VALUE.charAt(0));

		colorIds.put(DISCOVERING, R.color.discovering);
		colorIds.put(EATING, R.color.eating);
		colorIds.put(SLEEPING, R.color.sleeping);
		colorIds.put(SIGHTSEEING, R.color.sightseeing);
		colorIds.put(RELAXING, R.color.relaxing);
		colorIds.put(HIKING, R.color.hiking);
		colorIds.put(GOING_OUT, R.color.going_out);
		colorIds.put(PLAYING, R.color.playing);
		colorIds.put(SPORTS, R.color.sports);
		colorIds.put(NO_CATEGORY, R.color.other);
		colorIds.put(OTHER, R.color.other);
		colorIds.put(TRAVELING, R.color.traveling);
		colorIds.put(SHOPPING, R.color.shopping);
	}

	public MarkerIconInfo getMarkerInfo(Context context, String key){
		MarkerIconInfo iconInfo = new MarkerIconInfo();
		if(key == null){
			iconInfo.setCode(NO_CATEGORY_VALUE);
			iconInfo.setColor(colorIds.get(NO_CATEGORY));
			return iconInfo;
		}

		String code = getCode(key);
		if(code == null){
			iconInfo.setCode(NO_CATEGORY_VALUE);
		} else {
			iconInfo.setCode(code);
		}
		
		iconInfo.setColor(getColor(context, key));
		
		return iconInfo;
	}

	private String getCode(String key){
		String markerId = key;
		Character ret;

		while((ret = markers.get(markerId)) == null){
			int lastIndexOfColon = markerId.lastIndexOf(":");
			if(lastIndexOfColon >= 0){
				markerId = markerId.substring(0, lastIndexOfColon);
			} else {
				break;
			}
		}
		if(ret != null) {
			return Character.toString(ret);
		} else {
			return NO_CATEGORY_VALUE;
		}
	}

	public int getColor(Context context, String key){
		if(key == null){
			return ContextCompat.getColor(context, colorIds.get(NO_CATEGORY));
		}
		String firstSegment = key.split(":")[0];

		Integer colorId = colorIds.get(firstSegment);
		if(colorId == null){
			colorId = colorIds.get(NO_CATEGORY);
		}
		return ContextCompat.getColor(context, colorId);
	}
}
