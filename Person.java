/**
 * @author Ethan Stroberg
 * @version 3.0
 * Person Homework
 * CS 2463, OCCC sp25
 * last edited on 5/13/25
 */
import java.io.Serializable;
// implement serializable so we can save stuff
public class Person extends OCCCDate implements Serializable {
	public String firstName;
	public String lastName;
	public OCCCDate birthdate;
	public Person(String firstName, String lastName) {
		this.firstName = new String(firstName);
		this.lastName = new String(lastName);
	}
	// copy constructor
	public Person (Person p) {
		firstName = p.firstName;
		lastName = p.lastName;
	}
	
	public void setBirthdate(String month, String day, String year) {
		int birthDate = Integer.parseInt(day);
		int birthMonth = Integer.parseInt(month);
		int birthYear = Integer.parseInt(year);
		this.birthdate = new OCCCDate(birthDate, birthMonth, birthYear);
		this.birthdate.setDayName(HIDE_DAY_NAME);
		this.birthdate.setDateFormat(FORMAT_US);
		this.birthdate.setStyleFormat(STYLE_NUMBERS);
	}
	
	public OCCCDate getBirthDate() {
		return birthdate;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	// we override the usual toString from java.lang.object
	@Override
	public String toString() {
		return lastName + ", " + firstName + " (" + birthdate + ")";
	}
	
	public boolean equals(Person p) {
		return firstName.equalsIgnoreCase(p.firstName) && lastName.equalsIgnoreCase(p.lastName);
	}
	
	public void eat() {
		System.out.println( getClass().getName() + " " + toString() + " is eating!" );
	}
	
	public void sleep() {
		System.out.println( getClass().getName() + " " + toString() + " is sleeping!" );
	}
	
	public void play () {
		System.out.println( getClass().getName() + " " + toString() + " is playing!" );
	}
	
	public void run () {
		System.out.println( getClass().getName() + " " + toString() + " is running!" );
	}
}
