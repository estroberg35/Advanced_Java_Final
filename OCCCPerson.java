/**
 * @author Ethan Stroberg
 * @version 1.0
 * Person Homework
 * CS 2463, OCCC sp25
 * last edited on 3/13/25
 */
public class OCCCPerson extends RegisteredPerson {
	public String studentID;
	
	public OCCCPerson(RegisteredPerson p, String studentID) {
		super(p.getFirstName(), p.getLastName(), p.getGovernmentID());
		this.studentID = studentID;
	}
	
	public OCCCPerson(OCCCPerson p) {
		super(p);
	}
	
	@Override
	public String getGovernmentID() {
		return super.getGovernmentID();
	}
	
	public String getStudentID() {
		return studentID;
	}
	
	public boolean equals(OCCCPerson p) {
		return super.equals(p) && studentID.equalsIgnoreCase(p.studentID);
	}
	@Override
	public boolean equals(RegisteredPerson p) {
		return super.equals(p);
	}
	@Override
	public boolean equals(Person p) {
		return super.equals(p); //if you hover over equals, this one is the one that only compares names (for now)
	}
	@Override
	public String toString() {
		return super.toString() + " [" + studentID + "]";
	}

}
