/**
 * @author Ethan Stroberg
 * @version 1.1
 * OCCCDate Homework
 * CS 2463, OCCC sp25
 * last edited on 5/13/25
 */
import java.util.GregorianCalendar;
import java.util.Locale;
import java.io.Serializable;

public class OCCCDate implements Serializable {
	private int dayOfMonth;
	private int monthOfYear;
	private int year;
	private GregorianCalendar gc;
	private boolean dateFormat = FORMAT_US; //default is DATE_FORMAT_US
	private boolean dateStyle = STYLE_NUMBERS; // default is DATE_STYLE_NUMBERS
	private boolean dateDayName = SHOW_DAY_NAME; // default is SHOW_DAY_NAME
	
	public static final boolean FORMAT_US = true;
	public static final boolean FORMAT_EURO = false;
	public static final boolean STYLE_NUMBERS = true;
	public static final boolean STYLE_NAMES = false;
	public static final boolean SHOW_DAY_NAME = true;
	public static final boolean HIDE_DAY_NAME = false;
	
	public OCCCDate() { //default constructor, uses current date and time
		gc = new GregorianCalendar();
		this.dayOfMonth = gc.get(GregorianCalendar.DAY_OF_MONTH);
		// make sure to adjust month by 1 since gregorian calendar is 0 index for months
		this.monthOfYear = gc.get(GregorianCalendar.MONTH) + 1; 
		this.year = gc.get(GregorianCalendar.YEAR);
		if (checkDate()) {
			throw new InvalidOCCCDateException();
		}
	}
	
	public OCCCDate(int day, int month, int year) { // constructor
		this.dayOfMonth = day;
		this.monthOfYear = month;
		this.year = year;
		gc = new GregorianCalendar(this.year, this.monthOfYear - 1, this.dayOfMonth);
		// check the date -- if it returns true, throw the invalid date exception
		if (checkDate()) {
			throw new InvalidOCCCDateException();
		}

	}
	
	public OCCCDate(GregorianCalendar gc) {
		this.dayOfMonth = gc.get(GregorianCalendar.DAY_OF_MONTH);
		this.monthOfYear = gc.get(GregorianCalendar.MONTH) + 1;
		this.year = gc.get(GregorianCalendar.YEAR);
		if (checkDate()) {
			throw new InvalidOCCCDateException();
		}
	}
	
	public OCCCDate(OCCCDate d) { // copy constructor
		this.dayOfMonth = d.getDayofMonth();
		this.monthOfYear = d.getMonthNumber();
		this.year = d.getYear();
		gc = new GregorianCalendar(this.year, this.monthOfYear - 1, this.dayOfMonth);
	}
	
	public int getDayofMonth() { //1, 2, 3
		return gc.get(GregorianCalendar.DAY_OF_MONTH);
	}
	
	public String getDayName() { //Sunday, Monday etc --> since we want the name, we do display name and need the LONG (whole name vs shortened version)
		return gc.getDisplayName(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.LONG, Locale.getDefault());
	}
	
	public int getMonthNumber() { //1, 2, 3
		return gc.get(GregorianCalendar.MONTH) + 1;
	}
	
	public String getMonthName() { // January, February etc
		// we need the month in this GregorianCalendar class method, but also need to specify we want the whole name (LONG)
		return gc.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, Locale.getDefault());
	}
	
	public int getYear() { // Gregorian Year, like 2020
		return gc.get(GregorianCalendar.YEAR);
	}
	
	public void setDateFormat(boolean df) {
		// default is true (US), only set to EURO is set to false
		if (df == FORMAT_EURO) {
			this.dateFormat = FORMAT_EURO;
		}
	}
	
	public void setStyleFormat(boolean sf) {
		// default is true (numbers), only set to names if set to false
		if (sf == STYLE_NAMES) { 
			this.dateStyle = STYLE_NAMES;
		}
	}
	
	public void setDayName(boolean nf) {
		// default is show day name (true), only set to hide if set to false
		if (nf == HIDE_DAY_NAME) {
			this.dateDayName = HIDE_DAY_NAME;
		}
	}
	
	public int getDifferenceInYears() { // difference in years between this OCCCDate and now
		// a negative answer indicates that "this" date is in the past
		// a positive answer indicates that "this" date is in the future
		return getDifferenceInYears(new OCCCDate());
	}
	
	public int getDifferenceInYears(OCCCDate d) { //difference in years between this date and d
		// a negative answer indicates that "this" date is after d
		// a positive answer indicates that "this" date is before d
		return this.year - d.getYear();
	}
	
	public boolean equals(OCCCDate dob) {
		return (this.year == dob.getYear()) && (this.dayOfMonth == dob.getDayofMonth()) && (this.monthOfYear == dob.getMonthNumber());
	}
	
	@Override
	public String toString() { 
		// output according to what the user has specified
		// check format, style, and whether or not to show the day name
		String output = "";
		// deal with having the day name in the string or not
		// start with an empty string, either throw the name and a comma up front or not
		if (this.dateDayName == SHOW_DAY_NAME) {
			output += getDayName() + ", ";
		}
		// if we want month names, proceed down this branch and choose US or EURO
		if (this.dateStyle == STYLE_NAMES) {
			if (this.dateFormat == FORMAT_US) {
				// US format mm/dd/yyyy or monthName, dd, yyyy
				output += getMonthName() + " " + getDayofMonth() + ", " + getYear();
			}
			else {
				// Euro format dd/mm/yyyy or dd monthName yyyy
				output += getDayofMonth() + " " + getMonthName() + " " + getYear();
			}
		}
		else { // otherwise, the style is NUMBERS, proceed to choose US or EURO
			if (this.dateFormat == FORMAT_US) {
				// US format mm/dd/yyyy or monthName, dd, yyyy
				output += getMonthNumber() + "/" + getDayofMonth() +  "/" + getYear();
			}
			else {
				// Euro format dd/mm/yyyy or dd monthName yyyy
				output += getDayofMonth() + "/" + getMonthNumber() + "/" + getYear();
			}
		}
		
		return output;
	}
	
	public boolean checkDate() { // check if a date is valid, set to true to throw exception in the constructor
		// if OCCCDate != Gregorian Calendar object --> throw InvalidOCCCDateException
		// if any component of occc day != any component of gc, return true, which will throw the exception in the OCCCDate constructor
		if (this.dayOfMonth != getDayofMonth()) {
			return true;
		}
		if (this.monthOfYear != getMonthNumber()) {
			return true;
		}
		if (this.year != getYear()) {
			return true;
		}
		// if none of these triggered a return true, then the date is a-ok, return false
		return false;
	}


}
