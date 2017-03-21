package itemDetail;


import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Duration {
	public static final int SECONDS_PER_DAY = 86400;
	public static final int SECONDS_PER_HOUR = 3600;
	public static final int SECONDS_PER_MINUTE = 60;
	public static final int MINUTES_PER_HOUR = 60;
	public static final int HOURS_PER_DAY = 24;
	public static final int DAY_HOURS_LIMIT = 48;
	public static final int DIGITAL_FORMAT_LENGTH = 5;

	private int durationInSeconds;

	public Duration(int durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	public Duration(String durationInHoursAndMinutes) {
		durationInSeconds = convertHoursAndMinutesToSeconds(durationInHoursAndMinutes);
	}

	public Duration(String startTimeOfDay, String endTimeOfDay) {
		int start = convertHoursAndMinutesToSeconds(startTimeOfDay);
		int end = convertHoursAndMinutesToSeconds(endTimeOfDay);

		durationInSeconds = end - start;
		if(durationInSeconds < 0){
			durationInSeconds += SECONDS_PER_DAY;
		}
	}

	private int convertHoursAndMinutesToSeconds(String durationInHoursAndMinutes) {
		if(durationInHoursAndMinutes == null || durationInHoursAndMinutes.equals("")){
			return 0;
		}

		try {
			if(durationInHoursAndMinutes.length() > DIGITAL_FORMAT_LENGTH) {
				durationInHoursAndMinutes = convertTo24HourFormat(durationInHoursAndMinutes);
			}

			int hours = 0, minutes = 0;
			Pattern p = Pattern.compile("([\\d]{1,2}).([\\d]{1,2}).*");
			Matcher m = p.matcher(durationInHoursAndMinutes);
			while (m.find()) {
				hours = Integer.parseInt(m.group(1));
				minutes = Integer.parseInt(m.group(2));
			}

			return (hours * SECONDS_PER_HOUR) + (minutes * SECONDS_PER_MINUTE);
		} catch(Exception exception){
			return 0;
		}
	}

	private String convertTo24HourFormat(String durationInHoursAndMinutes) {
		Pattern p = Pattern.compile(".*([\\d]{1,2}:[\\d]{1,2}).*");
		Matcher m = p.matcher(durationInHoursAndMinutes);
		String pureTime = "";
		while(m.find()){
			pureTime = m.group(1);
		}

		String[] parts = pureTime.split(" ");
		String[] timeParts = parts[0].split(":");

		int hours = Integer.parseInt(timeParts[0]);
		if(parts[1].startsWith("p") || parts[1].startsWith("P")){
			hours += 12;
		}
		return hours + ":" + timeParts[1];
	}

	public String getDigitalClockFormat(){
		String formattedTime;
		int hours = durationInSeconds / SECONDS_PER_HOUR;
		int minutes = (durationInSeconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;

		formattedTime = (hours < 10 ? ("0" + hours) : hours) +	":" + (minutes < 10 ? ("0" + minutes) : minutes);

		return formattedTime;
	}

	public String getLongFormattedDuration(){
		String formattedTime;
		formattedTime = getShortFormattedDuration();
		int seconds = (durationInSeconds % SECONDS_PER_HOUR) % SECONDS_PER_MINUTE;
		if(seconds > 0){
			formattedTime += " " + seconds + " s";
		}
		return formattedTime;
	}

	public String getShortFormattedDuration() {
		String formattedTime;
		if(durationInSeconds >= SECONDS_PER_HOUR){
			int hours = durationInSeconds / SECONDS_PER_HOUR;
			float minutes;
			int seconds = durationInSeconds % SECONDS_PER_HOUR;

			if(seconds >= SECONDS_PER_MINUTE){
				minutes = (float) seconds / (float) SECONDS_PER_MINUTE;
				formattedTime = hours + " h " + Math.round(minutes) + " min";
			} else {
				formattedTime = hours + " h";
			}
		} else {
			float minutes = 1f;
			if(durationInSeconds >= SECONDS_PER_MINUTE){
				minutes = (float) durationInSeconds / (float) SECONDS_PER_MINUTE;
			}
			formattedTime = Math.round(minutes) + " min";
		}
		return formattedTime;
	}

	public String getShortFormattedDurationFullNameUnit() {
		String formattedTime;
		if(durationInSeconds >= (SECONDS_PER_HOUR * HOURS_PER_DAY) ){
			int days = durationInSeconds / SECONDS_PER_HOUR / HOURS_PER_DAY;
			if(days == 1){
				formattedTime = days + " day";
			}else{
				formattedTime = days + " days";
			}
		} else if(durationInSeconds >= SECONDS_PER_HOUR){
			int hours = durationInSeconds / SECONDS_PER_HOUR;
			float minutes;
			int seconds = durationInSeconds % SECONDS_PER_HOUR;
			if(hours == 1){
				formattedTime = hours + " hour";
			}else{
				formattedTime = hours + " hours";
			}

			if(seconds >= SECONDS_PER_MINUTE){
				minutes = (float) seconds / (float) SECONDS_PER_MINUTE;
				formattedTime += " " + Math.round(minutes) + " minutes";
			}

		} else {
			float minutes = 1f;
			if(durationInSeconds >= SECONDS_PER_MINUTE){
				minutes = (float) durationInSeconds / (float) SECONDS_PER_MINUTE;
			}
			if(minutes < 2f){
				formattedTime = Math.round(minutes) + " minute";
			}else{
				formattedTime = Math.round(minutes) + " minutes";
			}

		}
		return formattedTime;
	}

	public int getDurationInHours() {
		float durationInHours = (float) durationInSeconds / (float) SECONDS_PER_HOUR;
		return Math.round(durationInHours);
	}

	public int getDurationInSeconds() {
		return durationInSeconds;
	}

	public int getBookableActivityDuration() {
		float durationInHours = (float) durationInSeconds / (float) SECONDS_PER_HOUR;
		return Math.round(durationInHours);
	}

	public int getHours(){
		return durationInSeconds / SECONDS_PER_HOUR;
	}

	public int getMinutes(){
		return (durationInSeconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;
	}
}
