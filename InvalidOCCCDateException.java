/**
 * @author Ethan Stroberg
 * @version 1.0
 * OCCCDate Exceptions Homework
 * CS 2463, OCCC sp25
 * last edited on 4/15/25
 */
public class InvalidOCCCDateException extends IllegalArgumentException {
	// this class will throw the exception if the date input does not match the Gregorian calendar date
	public InvalidOCCCDateException() {
		super("Invalid Date Entered \n");
	}
}
