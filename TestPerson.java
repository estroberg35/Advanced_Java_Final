/**
 * @author Ethan Stroberg
 * @version 1.0
 * Person Homework
 * CS 2463, OCCC sp25
 * last edited on 3/13/25
 */
import java.util.Scanner;
import java.util.*;
import java.io.*;
public class TestPerson{
	  public static void main(String [] args){

		// for simplicity this demo uses only a portion of our Person hierarchy
		// Person is constructed with just a first and last name
		// OCCCPerson (the child of Person in this demo) is a first name, last name, and ID number (string)
		  
	    // Make an array (for simplicity) of Person
	    // note that since OCCCPerson is a Person, it holds those as well
		
		  // make some rp's so that the OCCC people actually function
		RegisteredPerson rp1 = new RegisteredPerson("Ethan", "Stroberg", "gov123");
		RegisteredPerson rp2 = new RegisteredPerson("Emma", "van Werkhoven", "gov456");
		RegisteredPerson rp3 = new RegisteredPerson("Brent", "Jenkins", "gov789");
		
	    Person [] p = new Person[6];
	    
	    p[0] = new Person("George", "Washington");
	    p[1] = new Person("Abraham", "Lincoln");
	    p[2] = new OCCCPerson(rp1, "B00001234");
	    p[3] = new OCCCPerson(rp2, "B00003323");
	    
	    Person p4 = new Person("James", "Carter");
	    OCCCPerson p5 = new OCCCPerson(rp3, "B00004234");

	    p[4] = p4;
	    p[5] = p5;

	    // Display them on the screen to make sure it all worked

	    for(int i = 0; i < p.length; ++i){
	      System.out.println(p[i].toString());
	    }

	    // Now dump them to a file and read them back in

	    System.out.print("Please enter file name: ");
	    Scanner s = new Scanner(System.in);
	    String fileName = s.next();
	    
	    System.out.println("Dumping objects to " + fileName + "...");

	    try{
	      FileOutputStream   fout = new FileOutputStream(fileName);
	      ObjectOutputStream oout = new ObjectOutputStream(fout);

	      for(int i = 0; i < p.length; ++i){
	        oout.writeObject(p[i]);
	      }
	    }
	    catch(IOException e){
	      System.out.println("OH NO BAD THINGS HAPPEN");
	      System.out.println(e.toString());
	    }

	    System.out.println("Now we read them back in...");

	    Person [] q = new Person[6];

	    try{
	      FileInputStream   fin = new FileInputStream(fileName);
	      ObjectInputStream oin = new ObjectInputStream(fin);
	      Object o;
	      for(int i = 0; i < q.length; ++i){
	        o = oin.readObject();
	        System.out.println(o.getClass());

	        // here we have to figure out what kind of Person we have and write them to the array with the appropriate type cast.
	        // for code like this always start at the bottom of the inheritance chain and work your way up

	        if (o.getClass().equals(OCCCPerson.class)){
	          System.out.println("Got me an OCCC Person");
	          q[i] = (OCCCPerson) o;
	        }
	        else{
	          System.out.println("This is a Person");
	          q[i] = (Person) o;
	        }
	      }
	    }
	    catch(Exception e){
	      System.out.println("INPUT ERROR");
	      System.out.println(e.toString());
	    }

	    for(int i = 0; i < q.length; ++i){
	      System.out.println(q[i].toString());
	    }
	  }
}
	
	
	
	
	
	
//	public static void main (String [] args) {
//		Scanner s = new Scanner(System.in);
////		Prompt the user for the data for a Person (first and last name)
//		System.out.println("Please enter a person (first and last name)");
//		
////		Create and Display that person (using toString)
//		Person p1 = new Person(s.next(), s.next());
//		System.out.println(p1.toString());
//		System.out.println();
//		
////		Prompt the user for the data for a RegisteredPerson (first name, last name, and government ID)
//		System.out.println("Please enter a government ID to make this person a RegisteredPerson");
//
////		Create and Display that RegisteredPerson (using toString)
//		RegisteredPerson rp1 = new RegisteredPerson(p1, s.next());
//		System.out.println(rp1.toString());
//		System.out.println();
//		
////		Prompt the user for the data for an OCCCPerson (first name, last name, government ID, and student ID)
//		System.out.println("Please enter a student ID to make this person an OCCCPerson)");
//		
////		Create and Display that OCCCPerson (using toString)
//		OCCCPerson op1 = new OCCCPerson(rp1, s.next());
//		System.out.println(op1.toString());
//		System.out.println();
//		
////		Prompt the user for a government ID, then create a new RegisteredPerson using that ID and your existing Person
//		System.out.println("Please enter a government ID");
//		
////		Display that RegisteredPerson (using toString)
//		RegisteredPerson rp2 = new RegisteredPerson(op1, s.next());
//		System.out.println(rp2.toString());
//		System.out.println();
//		
////		Prompt the user for a student ID, then create a new OCCCPerson using that ID and the newly-created RegisteredPerson
//		System.out.println("Please enter a student ID");
//		OCCCPerson op2 = new OCCCPerson(rp2, s.next());
//		
////		Display that OCCCPerson (using toString)
//		System.out.println(op2.toString());
//		System.out.println();
//		
//		s.close();
//	}

