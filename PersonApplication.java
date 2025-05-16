/**
 * @author Ethan Stroberg
 * @version 2.0
 * -> based on original person GUI (v1.0)
 * Person Application Final Project
 * CS 2463, OCCC sp25
 * last edited on 5/15/25
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
public class PersonApplication extends JFrame implements ActionListener{
	// front end GUI for person application --> ability to create, save, load, edit, delete person objects with OCCC Dates attached (birthdates)
	
	JMenuItem fileMenu_new, fileMenu_open, fileMenu_save, fileMenu_saveAs, fileMenu_exit, fileMenu_view, helpMenu_about;
	JComboBox<Person> personMenu;
	JRadioButton person, registeredPerson, occcPerson;
	ButtonGroup personGroup;
	JButton submit, edit, delete;
	JPanel editPanel, instructionPanel, topPanel, personTypePanel, inputPanel, typeButtonPanel, birthPanel, contentsPanel; 
	JTextField firstName, lastName, govID, studID, birthDate, birthMonth, birthYear;
	Font smallFont = new Font("Arial", Font.PLAIN, 18);
	Font bigFont = new Font("Arial", Font.PLAIN, 24);
	
	private File currentSaveFile = null; // this guy will store the save file name so that once we saveAs, the normal save button will save to the same file
	public Person[] people = new Person[10]; // array to store our names for the final demo video
	public int size = 0; // start the array size at 0
	public int editIndex = -1;
	public boolean isModified = false; // boolean to determine if we modified the contents
	
	public static void main(String[] args) {
		PersonApplication pa = new PersonApplication();
	}
	
	public PersonApplication() {
		super("Person Application");
		setSize(750,750);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitPersonApplication();
			}
		});
		

		// make the menu bar
		JMenuBar bar = new JMenuBar();
		
		// make the file menu and new, open, save, save as, and exit options (also with a view "main" page to see the contents of the file)
		JMenu fileMenu = new JMenu("File");
		
		fileMenu_view = new JMenuItem("View");
		fileMenu_view.addActionListener(this);
		
		fileMenu_new = new JMenuItem("New");
		fileMenu_new.addActionListener(this);
		
		fileMenu_open = new JMenuItem("Open...");
		fileMenu_open.addActionListener(this);
		
		fileMenu_save = new JMenuItem("Save");
		fileMenu_save.addActionListener(this);
		
		fileMenu_saveAs = new JMenuItem("Save As...");
		fileMenu_saveAs.addActionListener(this);
		
		fileMenu_exit = new JMenuItem("Exit");
		fileMenu_exit.addActionListener(this);
		
		// add the options to the file menu
		fileMenu.add(fileMenu_view);
		fileMenu.add(fileMenu_new);
		fileMenu.add(fileMenu_open);
		fileMenu.add(fileMenu_save);
		fileMenu.add(fileMenu_saveAs);
		fileMenu.add(fileMenu_exit);
		bar.add(fileMenu);
		
		// make the help menu with an about option
		JMenu helpMenu = new JMenu("Help");
		
		helpMenu_about = new JMenuItem("About");
		helpMenu_about.addActionListener(this);

		// add the about option to the help menu
		helpMenu.add(helpMenu_about);
		bar.add(Box.createHorizontalGlue());
		bar.add(helpMenu);
		
		// set the bar and make it appear
		setJMenuBar(bar);
		
		// construct new person buttons and make sure you can only select one option
		person = new JRadioButton("Person");
		registeredPerson = new JRadioButton("Registered Person");
		occcPerson = new JRadioButton("OCCC Person");
		
		person.setFont(smallFont);
		registeredPerson.setFont(smallFont);
		occcPerson.setFont(smallFont);
		
		person.addActionListener(e -> updateInputFields());
		registeredPerson.addActionListener(e -> updateInputFields());
		occcPerson.addActionListener(e -> updateInputFields());
		
		personGroup = new ButtonGroup();
		personGroup.add(person);
		personGroup.add(registeredPerson);
		personGroup.add(occcPerson);
		
		// organize JPanels
		personTypePanel = new JPanel(new GridLayout(4,1));
		instructionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		typeButtonPanel = new JPanel(new GridLayout(1, 3));
		editPanel = new JPanel(new GridLayout(1, 3));
		
		// dropdown and edit and delete
		personMenu = new JComboBox<>(people);
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		
		edit.addActionListener(e -> editPerson());
		delete.addActionListener(e -> deletePerson());
		
		editPanel.add(personMenu);
		editPanel.add(edit);
		editPanel.add(delete);
		
		personTypePanel.add(editPanel);
		
		// instruction panel
		JLabel instructions = new JLabel("<html><div style='text-align: center;'>Please select person type, and then enter the corresponding information"
				+ "<br>Press Submit when done with that person"
				+ "<br>THIS DOES NOT SAVE YOUR PERSON"
				+ "<br>It prepares your list to be saved when you hit save!"
				+ "<br>Birthdate is optional, but highly recommended!</div></html>");
		instructions.setFont(smallFont);
		instructionPanel.add(instructions);
		personTypePanel.add(instructionPanel);
		
		// panel for choosing person type
		typeButtonPanel.add(person);
		typeButtonPanel.add(registeredPerson);
		typeButtonPanel.add(occcPerson);
		
		personTypePanel.add(typeButtonPanel);
		submit = new JButton("Submit");
		submit.addActionListener(e -> updateArray());

		personTypePanel.add(submit);
		
		// make the panel invisible until you hit new so that nothing overlaps
		personTypePanel.setVisible(false);
		add(personTypePanel, BorderLayout.NORTH);		
		
		loadSaveFile(); // on startup, load the saved filepath
						// if we are reopening the app and have already save-as'd a file, this will load that name so that the 'save' method automatically saves there
		
		setVisible(true);

	}
	
	public void updateArray() {
		// each time submit is pressed, save that person object to the array so that when the user saves, we save all the people they created at once
		// check if all fields are complete, if they are, proceed
		if (readyToSave()) {
			Person p = null;
			RegisteredPerson rp = null;
			
			String first = firstName.getText();
			String last = lastName.getText();
			String gov = govID != null ? govID.getText() : "";
			String stud = studID != null ? studID.getText() : "";
			String day = birthDate != null ? birthDate.getText() : "";
			String month = birthMonth != null ? birthMonth.getText() : "";
			String year = birthYear != null ? birthYear.getText() : "";
			
			// set appropriate actions depending on the type of person created
			if (occcPerson.isSelected()) {
				// had to first create the rp and convert due to how occcPerson is structured in the class
				rp = new RegisteredPerson(first, last, gov);
				p = new OCCCPerson(rp, stud);
				if (!birthYear.getText().equals("") && !birthMonth.getText().equals("") && !birthDate.getText().equals("")) {
					try { 
						p.setBirthdate(month, day, year);
						birthYear.setText("");
						birthMonth.setText("");
						birthDate.setText("");
					}
					catch (InvalidOCCCDateException e) {
						JOptionPane.showMessageDialog(this, "Invalid date, please re-enter the birthdate");
						birthYear.setText("");
						birthMonth.setText("");
						birthDate.setText("");
						return;
					}
					
				}
				firstName.setText("");
				lastName.setText("");
				govID.setText("");
				studID.setText("");
			}
			else if (registeredPerson.isSelected()) {
				p = new RegisteredPerson(first, last, gov);
				if (birthYear != null && birthMonth != null && birthDate != null) {
					try { 
						p.setBirthdate(month, day, year);
						birthYear.setText("");
						birthMonth.setText("");
						birthDate.setText("");
					}
					catch (InvalidOCCCDateException e) {
						JOptionPane.showMessageDialog(this, "Invalid date, please re-enter the birthdate");
						birthYear.setText("");
						birthMonth.setText("");
						birthDate.setText("");
						return;
					}
					
				}
				firstName.setText("");
				lastName.setText("");
				govID.setText("");
			}
			else if (person.isSelected()) {
				p = new Person(first, last);
				if (birthYear != null && birthMonth != null && birthDate != null) {
					try { 
						p.setBirthdate(month, day, year);
						birthYear.setText("");
						birthMonth.setText("");
						birthDate.setText("");
					}
					catch (InvalidOCCCDateException e) {
						JOptionPane.showMessageDialog(this, "Invalid date, please re-enter the birthdate");
						birthYear.setText("");
						birthMonth.setText("");
						birthDate.setText("");
						return;
					}
					
				}
				firstName.setText("");
				lastName.setText("");
			}
			
			// if we wind up here due to editing, the edit index will be some positive integer, so this will let us know to put the edited person on top of the old one
			if (editIndex != -1) {
				people[editIndex] = p;
				editIndex = -1; // reset the edit index
			}
			else { // if we aren't editing, then put the Person in the next open spot in the array
				for (int j = 0; j < people.length; j++) {
					if (people[j] == null) {
						people[j] = p;
						size++;
						break;
					}
				}
			}
			// make sure to update dropdown as you add people
			updateDropdown();
			isModified = true; // we changed the list
			personGroup.clearSelection();
			
		}
		// otherwise, the user didn't fully input something, display a dialogue to let them know to go back and finish creating their person
		// figured I would also stop the user here to prevent the "we have a half created person" saving issue in the first place
		else {
			JOptionPane.showMessageDialog(this, "Please make sure you have entered all required information for this person!");
		}
		

	}
	
	public void showContents() { // this will be the "main" page where the user can go to see the contents of their persons
		// hide the other stuff that would be on the screen so that this is a page with only the contents
		if (inputPanel != null) {
			inputPanel.setVisible(false);
	    }
		
	    if (personTypePanel != null) {
	    	personTypePanel.setVisible(false);
	    }
	    
	    // initialize contentsPanel, make it vertical so each person gets their own line
	    contentsPanel = new JPanel();
	    contentsPanel.setLayout(new BoxLayout(contentsPanel, BoxLayout.Y_AXIS));
	    
	    // make a header that tells you what you're looking at (contents of the file you loaded into the dropdown)
	    JLabel header = new JLabel("People in File:");
	    header.setFont(bigFont);
	    contentsPanel.add(header);

	    // if a slot in the dropdown isn't blank, add them to this view page
	    for (int i = 0; i < people.length; i++) {
	        if (people[i] != null) {
	            JLabel personLabel = new JLabel(people[i].toString());
	            personLabel.setFont(smallFont);
	            contentsPanel.add(personLabel);
	        }
	    }
	    this.add(contentsPanel, BorderLayout.CENTER);

	    // make sure everything refreshes properly
	    revalidate();
	    repaint();
	}
	
	public void actionPerformed(ActionEvent e) { // assign each button appropriately
		if (e.getSource() == fileMenu_view ) {
			showContents();
		}
		if (e.getSource() == fileMenu_new) {
			createNewPerson();
		}
		if (e.getSource() == fileMenu_open) {
			openPerson();
		}
		if (e.getSource() == fileMenu_save) {
			savePerson();
		}
		if (e.getSource() == fileMenu_saveAs) {
			savePersonAs();
		}
		if (e.getSource() == fileMenu_exit) {
			exitPersonApplication();
		}
		if (e.getSource() == helpMenu_about) {
			displayAbout();
		}
	}
	
	public void updateInputFields() { // update the textbox inputs based on whether person, registeredperson, or occcperson is selected
		// clear out old panels every time this is called so that we can update them and not make a mess
		if (inputPanel != null) {
			this.remove(inputPanel);
			inputPanel = null;
		}
		
		// create the input jpanel and make it a vertical box layout
		inputPanel = new JPanel();
		birthPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		birthPanel.setLayout(new BoxLayout(birthPanel, BoxLayout.X_AXIS));
		
		// add all the birthdate stuff to a horizontal row --> this applies to every type of person so it can come first with no conditional
		birthPanel.add(new JLabel("month"));
		birthMonth = new JTextField();
		birthPanel.add(birthMonth);
		
		birthPanel.add(new JLabel("date"));
		birthDate = new JTextField();
		birthPanel.add(birthDate);
		
		birthPanel.add(new JLabel("year"));
		birthYear = new JTextField();
		birthPanel.add(birthYear);
		
		inputPanel.add(birthPanel);
		
		// depending on which button is pressed, show the appropriate input fields for the user to enter
	    if (person.isSelected()) {
	        inputPanel.add(new JLabel("First Name:"));
	        firstName = new JTextField(); // textField so the user can write in the app
	        inputPanel.add(firstName);

	        inputPanel.add(new JLabel("Last Name:"));
	        lastName = new JTextField();
	        inputPanel.add(lastName);
	    } 
	    else if (registeredPerson.isSelected()) {
	        inputPanel.add(new JLabel("First Name:"));
	        firstName = new JTextField();
	        inputPanel.add(firstName);

	        inputPanel.add(new JLabel("Last Name:"));
	        lastName = new JTextField();
	        inputPanel.add(lastName);

	        inputPanel.add(new JLabel("Government ID:"));
	        govID = new JTextField();
	        inputPanel.add(govID);
	    } 
	    else if (occcPerson.isSelected()) {
	        inputPanel.add(new JLabel("First Name:"));
	        firstName = new JTextField();
	        inputPanel.add(firstName);

	        inputPanel.add(new JLabel("Last Name:"));
	        lastName = new JTextField();
	        inputPanel.add(lastName);

	        inputPanel.add(new JLabel("Government ID:"));
	        govID = new JTextField();
	        inputPanel.add(govID);

	        inputPanel.add(new JLabel("Student ID:"));
	        studID = new JTextField();
	        inputPanel.add(studID);
	    }
	    this.add(inputPanel, BorderLayout.CENTER);

	    // make sure everything displays correctly, call revalidate and repaint to make sure everything is updated
	    revalidate();
	    repaint();
	    
	}
	
	public void createNewPerson() { // make the person options visible and call the updateinputfields method
		if (contentsPanel != null) {
			this.remove(contentsPanel);
		    contentsPanel = null;
		}
		if (isModified == true) { // per the final intructions/steps, call the "would you like to save" pop up if we hit new without saving

			int result = JOptionPane.showConfirmDialog(
				    null,
				    "Would you like to save before creating a new person?",
				    "Save first?",
				    JOptionPane.YES_NO_CANCEL_OPTION
				);

				if (result == JOptionPane.YES_OPTION) {
				    savePerson();
				    personTypePanel.setVisible(true);
				    inputPanel.setVisible(true);
					updateInputFields();
					revalidate();
				    repaint();
				} 
				else if (result == JOptionPane.NO_OPTION) {
					personTypePanel.setVisible(true);
					inputPanel.setVisible(true);
					updateInputFields();
					revalidate();
				    repaint();
				}
				// if the user hits cancel, nothing happens and the pop up closes

		}
		else { // if nothing was modified, we have nothing to worry about here and can just createNewPerson
			personTypePanel.setVisible(true);
			inputPanel.setVisible(true);
			updateInputFields();
		}
	}
	
	public void openPerson() { // open an existing saved person
		JFileChooser fChooser = new JFileChooser();
		int result = fChooser.showOpenDialog(this); // show the OPEN file screen
		
		if (result == JFileChooser.APPROVE_OPTION) { // if the user selects a file...
			File file = fChooser.getSelectedFile();
			
			try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file))) {
				people = (Person[]) oin.readObject(); // read the objects into the person array
				size = 0;
				for (Person p : people) { 
					if (p != null) {
						size++; // increase size to the amount of people stored
					}
				}
				// make sure these people get thrown in the dropdown
				updateDropdown();
			}
			catch (IOException | ClassNotFoundException e) {
				JOptionPane.showMessageDialog(this,  "Error");
			}
			isModified = false;
		}
	}
	
	public void savePerson() { // save people to the current working directory.  File will automatically be named
		// check if we are ready to save, proceed if true, lock the option and tell the user to finish input fields if false
		if (readyToSave()) {
			if (currentSaveFile == null) {
				String fname = "person_data";
				File file = new File(fname);
				
				try (ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file))) {
					oout.writeObject(people); // save the whole array
				}
				catch (IOException e) {
					JOptionPane.showMessageDialog(this, "Error saving");
				}
				isModified = false;
			}
			else {
				File file = currentSaveFile;
				try (ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file))) {
					oout.writeObject(people);
				}
				catch (IOException e) {
					JOptionPane.showMessageDialog(this,  "Error saving");
				}
				isModified = false;
			}
		}
			
		else {
			JOptionPane.showMessageDialog(this,  "Please make sure all input fields are complete!");
		}
		
	}
	
	public void savePersonAs() { // save people as a specific file name	
		// check if we are ready to save, proceed if true, lock the option and tell the user to finish input fields if false
		if (readyToSave()) {
			JFileChooser fChooser = new JFileChooser(); // will allow user to choose where to save a file
			int result = fChooser.showSaveDialog(this); // opens the dialog to save the person file
			if (result == JFileChooser.APPROVE_OPTION ) { //approve_option means the user hit save and we move forward
				File file = fChooser.getSelectedFile();
				
				try (ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file))) { 
					oout.writeObject(people); // saveAs the whole Person array
					currentSaveFile = file; // make the current save file name the name the user chooses
					saveFilePath(currentSaveFile);
					isModified = false;
				}
				catch (IOException e) {
					JOptionPane.showMessageDialog(this,"error saving file");
				}
				
			}
		}
		
		else {
			JOptionPane.showMessageDialog(this, "Please make sure all input fields are complete!");
		}
		
	}
	
	public void exitPersonApplication() { // exit the app
		if (isModified == true) {

			int result = JOptionPane.showConfirmDialog(
				    null,
				    "Would you like to save before exiting the program?",
				    "Exit PersonApplication.java",
				    JOptionPane.YES_NO_CANCEL_OPTION
				);

				if (result == JOptionPane.YES_OPTION) {
				    savePerson();
				    System.exit(0);
				} 
				else if (result == JOptionPane.NO_OPTION) {
				    System.exit(0);
				}
				// if the user hits cancel, nothing happens and the pop up closes

		}
		else { // if nothing was modified, we have nothing to worry about here and can just close the app
			dispose();
			System.exit(0);
		}
	}
	
	public void displayAbout() { // display some information about the program (capabilities/uses)
		JOptionPane.showMessageDialog(this, "This is my final project for Advanced Java!  You can use this program to create and manage a list of various types of people."
				+ "\nWhen you click 'new', you can choose what type of person you would like to create and then enter the corresponding information.  "
				+ "\nYou then click 'submit' to add that person to a list of people to be saved when you click either 'save' or 'save as'.  "
				+ "\nOnce you click one of the save options, your list will save and you can reopen this list of people and edit or delete entries.");
	}
	
	public void updateDropdown() { // populate the dropdown with a list of people
		if (personMenu == null) { // if it's null it would throw an exception, so return
			return;
		}
		personMenu.removeAllItems(); // clear before re-populating to make sure we don't make a mess with overlapping stuff
		for (Person p : people) {
			if (p!= null) {
				personMenu.addItem(p);
			}
		}
	}
	
	public void editPerson() {
		// we will bring up the persons info and populate the fields with it, and remove their old version from the array
		// we pretty much take them out, change em, and throw em back in
		int i = personMenu.getSelectedIndex();
		if (i == -1 || people[i] == null) {
			return;
		}
		
		Person p = people[i]; //get first and last info -- applies to all person types
		
		if (p instanceof OCCCPerson) {
	        occcPerson.setSelected(true);
	        updateInputFields(); // bring up studentID and govID in the text fields for OP
	        OCCCPerson occc = (OCCCPerson) p; 
	        govID.setText(occc.getGovernmentID());
	        studID.setText(occc.getStudentID());
	    } 
		else if (p instanceof RegisteredPerson) {
	        registeredPerson.setSelected(true);
	        updateInputFields(); // bring up gov ID in the text field for RP
	        RegisteredPerson rp = (RegisteredPerson) p;
	        govID.setText(rp.getGovernmentID());
	    }
		else if (p instanceof Person) {
	        person.setSelected(true);
	        updateInputFields(); // load input fields for Person
	    }
		firstName.setText(p.getFirstName());
		lastName.setText(p.getLastName());
		// all relevant text fields should pull up the data for the selected person, that way we can easily tweak it
		
		editIndex = i; // set the edit index for use in the updateArray method
		isModified = true;
	}
	
	public void deletePerson() {
		Person p = (Person) personMenu.getSelectedItem(); // the person we want to delete is the one in the dropdown
		if (p == null) { // if nothing is there there's nothing to delete, do nothing
			return;
		}
		for (int i = 0; i < size; i++) {
			if (people[i].equals(p)) { // when the person in the array equals the person in the dropdown, we've found our target
				for (int j = i; j < size - 1; j++) { // move every person to the right of the target one index left, erasing the one we want deleted
					people[j] = people[j + 1];
				}
				people[size-1] = null; // there will be a duplicate at the end since we shifted left, this will get rid of whatever is in that spot
				size--; //shrink the size appropriately
				break;
			}
		}
		updateDropdown();
		isModified = true;
	}
	
	public boolean readyToSave() {
		// this will check if we are ready to save.  If any field is empty for that variant of a person object, return false.  Otherwise, we are ready, return true
		// this method will effectively "lock" the save and save as options until the person is done by preventing the user from submitting an entry to the list if incomplete
		if (!person.isSelected() && !registeredPerson.isSelected() && !occcPerson.isSelected()) {
	        return true;
	    }
		if (person.isSelected()) {
			if (firstName.getText().isEmpty() || lastName.getText().isEmpty()) {
				return false;
			}
	   
	    } 
	    else if (registeredPerson.isSelected()) {
	    	if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || govID.getText().isEmpty()) {
				return false;
			}
	
	    } 
	    else if (occcPerson.isSelected()) {
	    	if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || govID.getText().isEmpty() || studID.getText().isEmpty()) {
				return false;
			}
	 
	    }
		return true;
	}

	public void saveFilePath(File file) { // this will save the location of the saveAs filepath in another little helper file
		// this will allow the file to be saved to even if the app is closed and reopened
		try (FileWriter writer = new FileWriter("filepath.txt")) {
			writer.write(file.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("Error saving the file containing the filepath"); // just print this to console if we have an issue
		}
	}
	
	public void loadSaveFile() { // this method will recall the location of the saveAs saved file so if we click "save" even after reopening, it still saves to that file
		File pathFile = new File("filepath.txt");
		if (pathFile.exists()) { // if this file exists, it has the name of the file we want to save to
			try (Scanner scanner = new Scanner(pathFile)) {
				if (scanner.hasNextLine()) {
					String path = scanner.nextLine().trim();
					File file = new File(path);
					if (file.exists()) {
						currentSaveFile = file; // make the current save file we want to save to the default
					}
				}
			} catch (IOException e) {
				System.out.println("Could not load file containing the filepath."); // just print this to console if we have an issue
			}
		}
	}
}
