package itemDetail.toBeDeleted;

import android.content.Context;
import android.text.format.DateFormat;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Calendar extends GregorianCalendar {
	public static final String DEFAULT_MAX_DATE = "2037-12-31";
	public static final String DEFAULT_MIN_DATE = "1970-1-1";


	public Calendar(){
		this(
			Calendar.getInstance().get(Calendar.YEAR) + "-" +
			(Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" +
			Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
		);
	}

	public Calendar(String date) {
		if(date == null || date.equals("")) {
			set(
				Calendar.getInstance().get(Calendar.YEAR),
				Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
			);
		} else {
			String[] parts = date.split("-");
			if(parts.length < 3) {
				throw new InvalidParameterException("Accepts YYYY-mm-dd format! Got '" + date + "'!");
			}
			set(
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1]) - 1,
				Integer.parseInt(parts[2])
			);
			setFirstDayOfWeek(MONDAY);
		}
	}

	public String getDayOfWeek() {
		return String.valueOf(DateFormat.format("EEEE", this));
	}

	public String getShortDayOfWeek() {
		return String.valueOf(DateFormat.format("EE", this));
	}

	public String getMonthName(){
		return String.valueOf(DateFormat.format("MMMM", this));
	}

	public String getShortMonthName(){
		return String.valueOf(DateFormat.format("MMM", this));
	}

	public String toDashStringFormat(){
		StringBuilder dateString = new StringBuilder();
		dateString.append(get(YEAR));
		dateString.append("-");
		dateString.append(String.format("%02d", get(MONTH) + 1));
		dateString.append("-");
		dateString.append(String.format("%02d", get(DAY_OF_MONTH)));
		return dateString.toString();

	}

	public Calendar getNewDateFromCurrent(int days){
		Calendar end = (Calendar) this.clone();
		end.set(
			DAY_OF_MONTH,
			this.get(DAY_OF_MONTH) + days
		);
		return end;
	}

	public int getDuration(Calendar date){
		if(
			get(YEAR) == date.get(YEAR) &&
			get(MONTH) == date.get(MONTH) &&
			get(DAY_OF_MONTH) > date.get(DAY_OF_MONTH)
		){
			return -1;
		}
		long millisTo = date.getTimeInMillis();
		long millisFrom = getTimeInMillis();
		long millis = millisTo - millisFrom;
		float wholeDaysCount = (float)millis / 86400000f;
		return Math.round(wholeDaysCount + 1f);
	}

	public boolean dateEquals(java.util.Calendar date){
		return(
			get(DAY_OF_MONTH) == date.get(DAY_OF_MONTH) &&
			get(MONTH) == date.get(MONTH) &&
			get(YEAR) == date.get(YEAR)
		);
	}

	public boolean isToday(){
		java.util.Calendar today = getInstance();

		return(
			get(DAY_OF_MONTH) == today.get(DAY_OF_MONTH) &&
			get(MONTH) == today.get(MONTH) &&
			get(YEAR) == today.get(YEAR)
		);
	}

	public boolean isYesterday(){
		java.util.Calendar yesterday = getInstance();
		yesterday.add(DAY_OF_MONTH, -1);

		return(
			get(DAY_OF_MONTH) == yesterday.get(DAY_OF_MONTH) &&
			get(MONTH) == yesterday.get(MONTH) &&
			get(YEAR) == yesterday.get(YEAR)
		);
	}

	public boolean isTomorrow(){
		java.util.Calendar tomorrow = getInstance();
		tomorrow.add(DAY_OF_MONTH, +1);

		return(
			get(DAY_OF_MONTH) == tomorrow.get(DAY_OF_MONTH) &&
			get(MONTH) == tomorrow.get(MONTH) &&
			get(YEAR) == tomorrow.get(YEAR)
		);
	}

	public boolean isBefore(java.util.Calendar date){
		if(get(YEAR) > date.get(YEAR)){
			return false;
		} else if(get(YEAR) < date.get(YEAR)){
			return true;
		} else {
			if(get(MONTH) > date.get(MONTH)){
				return false;
			} else if(get(MONTH) < date.get(MONTH)){
				return true;
			} else {
				if(get(DAY_OF_MONTH) > date.get(DAY_OF_MONTH)){
					return false;
				} else {
					return get(DAY_OF_MONTH) < date.get(DAY_OF_MONTH);
				}
			}
		}
	}

	public boolean isBeforeByNumDays(java.util.Calendar date, int numDays){
		java.util.Calendar calendar = new Calendar();
		calendar.setTime(date.getTime());
		calendar.add(DAY_OF_MONTH, numDays);
		return isBefore(calendar);
	}

	public static String getLocaleDateFormat(Context context, String date, SimpleDateFormat sdfInput){
		Date localeDate;
		try {
			localeDate = sdfInput.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return DateFormat.getMediumDateFormat(context).format(localeDate);
	}
}
