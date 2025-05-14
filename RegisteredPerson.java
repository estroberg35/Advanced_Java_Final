/**
 * @author Ethan Stroberg
 * @version 1.0
 * Person Homework
 * CS 2463, OCCC sp25
 * last edited on 3/13/25
 */
public class RegisteredPerson extends Person {
	public String govID;
	
	public RegisteredPerson (String firstName, String lastName, String govID) {
		super(firstName, lastName);
		this.govID = govID;
	}
	
	public RegisteredPerson(Person p, String govID) {
		super(p);
		this.govID = govID;
	}
	
	public RegisteredPerson(RegisteredPerson p) {
		super(p);
	}
	
	public String getGovernmentID() {
		return govID;
	}
	
	public boolean equals(RegisteredPerson p) {
		return super.equals(p) && govID.equalsIgnoreCase(p.govID);
		// toString overrides the one in Person and should compare the one listed below
	}
	@Override
	public boolean equals(Person p) {
		return super.equals(p); // call the parent equals method to just check the names but not the govID
	}
	@Override
	public String toString() {
		return super.toString() + " [" + govID + "]";
	}
}
