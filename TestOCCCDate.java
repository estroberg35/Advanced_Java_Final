/**
 * @author Ethan Stroberg
 * @version 1.0
 * OCCCDate Homework
 * CS 2463, OCCC sp25
 * last edited on 4/15/25
 */
import java.util.Scanner;
import java.io.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
public class TestOCCCDate {
	public static void main (String [] args) {
		
//		How many dates were processed? 
		int linecount = 0;
		try (Scanner scanner = new Scanner(new File("OCCCDateDataFile.txt"))) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();  // Read the line and discard it
                linecount++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		System.out.println("Number of dates: " + linecount);
		
//		 How many dates threw an OCCCDateException?
		int exceptionCount = 0;
        try (Scanner scanner = new Scanner(new File("OCCCDateDataFile.txt"))) {
            while (scanner.hasNextLine()) {
                int day = scanner.nextInt();
                int month = scanner.nextInt();
                int year = scanner.nextInt();
                try {
                	OCCCDate date = new OCCCDate(day, month, year);
                    date.checkDate();
                } 
                catch (InvalidOCCCDateException e) {
                    exceptionCount++;
                    //System.out.println("Exception for date: " + date + " → " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        }
        System.out.println("Total invalid dates: " + exceptionCount);
		
//		 How many of these dates were in July?
        int julyDates = 0;
        try (Scanner scanner = new Scanner (new File("OCCCDateDataFile.txt"))) {
        	while (scanner.hasNextLine()) {
        		int day = scanner.nextInt();
                int month = scanner.nextInt();
                int year = scanner.nextInt();
                GregorianCalendar cal = new GregorianCalendar(year, month - 1, day);
                if (cal.get(Calendar.MONTH) == Calendar.JULY) {
                	julyDates++;
                }
        	}
        }
        catch (FileNotFoundException e) {
        	System.err.println("File not found");
        }
        System.out.println("July dates: " + julyDates);
		
		
//		 How many of these dates were on a Tuesday?
        int tuesdays = 0;
        try (Scanner scanner = new Scanner (new File("OCCCDateDataFile.txt"))) {
        	while (scanner.hasNextLine()) {
        		int day = scanner.nextInt();
                int month = scanner.nextInt();
                int year = scanner.nextInt();
                GregorianCalendar cal = new GregorianCalendar(year, month - 1, day);
                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                	tuesdays++;
                }
        	}
        }
        catch (FileNotFoundException e) {
        	System.err.println("File not found");
        }
        System.out.println("Tuesdays: " + tuesdays);
		
		
//		TODO How many of these dates were prior to the beginning of the 21st Century?
        int pre21st = 0;
        try (Scanner scanner = new Scanner (new File("OCCCDateDataFile.txt"))) {
        	while (scanner.hasNextLine()) {
        		int day = scanner.nextInt();
                int month = scanner.nextInt();
                int year = scanner.nextInt();
                GregorianCalendar cal = new GregorianCalendar(year, month - 1, day);
                if (cal.get(Calendar.YEAR) < 2001) {
                	pre21st++;
                }
        	}
        }
        catch (FileNotFoundException e) {
        	System.err.println("File not found");
        }
        System.out.println("Dates pre-21st century: " + pre21st);
		

//      EVERYTHING BELOW HERE IS THE HOMEWORK ASSIGNMENT FOR UNIT 6
//		Scanner s = new Scanner(System.in);
//		System.out.println("Each date is set to display as specified for that bullet on the homework submission screen \n");
//		System.out.println("The first four dates will use a try catch block so that exceptions can be caught and the program can keep moving.  The last date will not use a try catch block");
//		
//		System.out.println("Please enter a date in the form [day month year], numerically and separated by spaces");
//		// Set your output format to US style dates showing the name of the month and also display the day of the week
//		
//		try {
//			OCCCDate date1 = new OCCCDate(s.nextInt(), s.nextInt() ,s.nextInt());
//			date1.setDateFormat(OCCCDate.FORMAT_US);
//			date1.setStyleFormat(OCCCDate.STYLE_NAMES);
//			date1.setDayName(OCCCDate.SHOW_DAY_NAME);
//			System.out.println(date1.toString());
//			System.out.println();
//		}
//		catch (InvalidOCCCDateException ex){
//			System.out.println("Exception: " + ex.getMessage());
//		}
//		
//		try {
//			OCCCDate date2 = new OCCCDate(s.nextInt(), s.nextInt() ,s.nextInt());
//			date2.setDateFormat(OCCCDate.FORMAT_US);
//			date2.setStyleFormat(OCCCDate.STYLE_NAMES);
//			date2.setDayName(OCCCDate.SHOW_DAY_NAME);
//			System.out.println(date2.toString());
//			System.out.println();
//		}
//		catch (InvalidOCCCDateException ex){
//			System.out.println("Exception: " + ex.getMessage());
//		}
//		
//		try {
//			OCCCDate date3 = new OCCCDate(s.nextInt(), s.nextInt() ,s.nextInt());
//			date3.setDateFormat(OCCCDate.FORMAT_US);
//			date3.setStyleFormat(OCCCDate.STYLE_NAMES);
//			date3.setDayName(OCCCDate.SHOW_DAY_NAME);
//			System.out.println(date3.toString());
//			System.out.println();
//		}
//		catch (InvalidOCCCDateException ex){
//			System.out.println("Exception: " + ex.getMessage());
//		}
//		
//		try {
//			OCCCDate date4 = new OCCCDate(s.nextInt(), s.nextInt() ,s.nextInt());
//			date4.setDateFormat(OCCCDate.FORMAT_US);
//			date4.setStyleFormat(OCCCDate.STYLE_NAMES);
//			date4.setDayName(OCCCDate.SHOW_DAY_NAME);
//			System.out.println(date4.toString());
//			System.out.println();
//		}
//		catch (InvalidOCCCDateException ex){
//			System.out.println("Exception: " + ex.getMessage());
//		}
//		
//		OCCCDate date5 = new OCCCDate(s.nextInt(), s.nextInt() ,s.nextInt());
//		date5.setDateFormat(OCCCDate.FORMAT_US);
//		date5.setStyleFormat(OCCCDate.STYLE_NAMES);
//		date5.setDayName(OCCCDate.SHOW_DAY_NAME);
//		System.out.println(date5.toString());
//		System.out.println();
//		
//		
//		s.close();
//		
	}

}
