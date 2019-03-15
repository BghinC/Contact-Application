package isen.java2.project.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import javax.swing.JFileChooser;

import isen.java2.project.db.daos.PersonDao;
import isen.java2.project.model.Person;
import isen.java2.project.service.StageService;

public class MainLayoutController {

	public void closeApplication() {
		StageService.closeStage();
	}

	/*
	public void gotoHome() {
		StageService.showView(ViewService.getView("HomeScreen"));
	}
	*/
	
	public void exportContacts() throws IOException{
		JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("Select directory");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);

	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	
	    	PersonDao pDao = new PersonDao();
	    	List<Person> listToExport = pDao.listPeople();
	    	
	    	String contactPath;
	    	File f;
	    	FileOutputStream fop;
	    	
	    	for (int i = 0; i < listToExport.size(); i++) {
	    		Person currentPerson = listToExport.get(i);
	    		contactPath = chooser.getSelectedFile() + "\\" + currentPerson.getFirstname() + " " + currentPerson.getLastname() + ".vcf";
	    		f=new File(contactPath);
	    		fop = new FileOutputStream(f);
	    		if(f.exists()) {
	    			/*
	    			 * Define vCard information
	    			 */
	    			String str="BEGIN:VCARD\n" + 
	    	    			"VERSION:4.0\n" +
	    	    	    	"N:"+currentPerson.getLastname()+";"+ currentPerson.getFirstname() +";;;\n" +
	    	    	    	"FN:"+ currentPerson.getFirstname() + " " + currentPerson.getLastname() +"\n";
	    			
	    			//Nickname
	    			if(currentPerson.getNickname() != null) {
	    				str += "NICKNAME:"+ currentPerson.getNickname() +"\n";
	    			}
	    			/*
	    			 * Image
	    			 * (Conversion --> base64)
	    			 */
	    			File fileImage = new File(currentPerson.getAvatarPath());
	    			if(fileImage.exists()) {
	    				FileInputStream imageInFile = new FileInputStream(fileImage);
		    			byte imageData[] = new byte[(int) fileImage.length()];
		    			imageInFile.read(imageData);
		    			String base64Image = Base64.getEncoder().encodeToString(imageData);
		    			str += "PHOTO;ENCODING=BASE64;TYPE=JPEG:"+ base64Image +"\n";
		    			imageInFile.close();
	    			}
	    			//Phonenumber
	    			if(currentPerson.getPhonenumber() != null) {
	    				System.out.println(currentPerson.getPhonenumber());
	    				str += "TEL;TYPE=HOME;VALUE=uri:tel:"+ currentPerson.getPhonenumber() +"\n";
	    			}
	    			//Email
	    			if(currentPerson.getEmailaddress() != null) {
	    				str += "EMAIL:"+ currentPerson.getEmailaddress() +"\n";
	    			}
	    			//Address
	    			if(currentPerson.getAddress() != null) {
	    				str += "ADR:;;"+ currentPerson.getAddress().getNumber() + " " + currentPerson.getAddress().getStreet() +";"+currentPerson.getAddress().getCity()+";;"+currentPerson.getAddress().getPostalCode()+"\n";
	    			}
	    			//Birthday
	    			if(currentPerson.getBirthdate() != null) {
	    				str += "BDAY:"+currentPerson.getBirthdate().toString().replace("-","")+"\n";
	    			}
	    	    	
	    			str += "REV:"+ LocalDate.now()+"\n"+ 
	    					"END:VCARD";
	    	    	    	
	    	    	fop.write(str.getBytes());
	    	    	fop.flush();
	    	    	fop.close();
	    	    	System.out.println("The data has been written");
	    		}else {
	 	    	   	System.out.println("This file does not exist");
		    	}
	    	}
	    } else {
	    	System.out.println("No directory selected ");
	    }
	}
	/*
	 * Not implemented
	 * To do : refresh list after delete all contacts
	 
	@FXML
	public void deleteAllContacts() throws IOException {
		//Delete all contacts
		PersonDao pDao = new PersonDao();
		pDao.deleteTable();
		//Delete also all addresses
		AddressDao aDao = new AddressDao();
		aDao.deleteTable();
	}
	*/
}
