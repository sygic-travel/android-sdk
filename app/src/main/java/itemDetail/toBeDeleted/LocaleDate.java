package itemDetail.toBeDeleted;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LocaleDate {

	private android.app.Activity activity;

	public LocaleDate(android.app.Activity activity){
		this.activity = activity;
	}

	private SimpleDateFormat getSimpleDateFormat(String originalFormat){
		return new SimpleDateFormat(originalFormat, Locale.ROOT);
	}

	public String getLocaleTime(String originalTime, String originalFormat){
		try {
			DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(activity);
			return dateFormat.format(getDate(originalTime, originalFormat));
		} catch (ParseException e) {
			return originalTime;
		}
	}

	public String getLocaleDate(String originalDate, String originalFormat) {
		try {
			DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(activity);
			return dateFormat.format(getDate(originalDate, originalFormat));
		} catch (ParseException e) {
			Calendar date = new Calendar(originalDate);
			return date.get(Calendar.DAY_OF_MONTH) + " " + date.getShortMonthName();
		}
	}

	private Date getDate(String originalDate, String originalFormat) throws ParseException {
		return getSimpleDateFormat(originalFormat).parse(originalDate);
	}
}
