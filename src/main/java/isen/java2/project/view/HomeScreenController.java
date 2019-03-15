package isen.java2.project.view;

import java.io.File;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

import isen.java2.project.db.daos.AddressDao;
import isen.java2.project.db.daos.PersonDao;
import isen.java2.project.model.Address;
import isen.java2.project.model.Person;
import isen.java2.project.service.PersonService;
import isen.java2.project.service.StageService;
import isen.java2.project.util.PersonValueFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class HomeScreenController {
	
	@FXML
	public ListView<Person> peopleList;
	@FXML
	public Text textFirstname;
	@FXML
	public Text textLastname;
	@FXML
	public Text textNickname;
	@FXML
	public Text textPhonenumber;
	@FXML
	public Text textAddress;
	@FXML
	public Text textEmailaddress;
	@FXML
	public Text textBirthdate;
	@FXML
	public AnchorPane detailPane;
	@FXML
	public AnchorPane informationPane;
	/*
	 * Add Person
	 */
	@FXML
	public Button button_cancel_add;
	@FXML
	public Button button_save_add;
	/*
	 * Modify View
	 */
	@FXML
	public AnchorPane modifyPane;
	@FXML
	public Button button_cancel_modify;
	@FXML
	public Button button_save_modify;
	@FXML
	public TextField textFieldFirstname;
	@FXML
	public TextField textFieldLastname;
	@FXML
	public TextField textFieldNickname;
	@FXML
	public TextField textFieldPhonenumber;
	@FXML
	public TextField textFieldAddressNumber;
	@FXML
	public TextField textFieldAddressStreet;
	@FXML
	public TextField textFieldAddressPostalCode;
	@FXML
	public TextField textFieldAddressCity;
	@FXML
	public TextField textFieldEmailaddress;
	@FXML
	public DatePicker datePickerBirthdate;
	@FXML
	public Button button_changeAvatar;
	@FXML
	public ImageView imageViewNewAvatar;
	public String pathNewAvatar;
	/*
	 * Buttons
	 */
	@FXML
	public Button button_add;
	@FXML
	public Button button_delete;
	@FXML
	public Button button_modify;
	
	
	public void refreshList() {
		peopleList.refresh();
		peopleList.getSelectionModel().clearSelection();
	}
	
	public void populateList() {
		peopleList.setItems(PersonService.getPeople());
		
		/*
		 * Sort list alphabetically
		 */
		peopleList.getItems().sort(new Comparator<Person>() {
			
			@Override
			public int compare(Person p1, Person p2) {
				return p1.getLastname().toUpperCase().compareTo(p2.getLastname().toUpperCase());
			}
		});
		
		refreshList();
	}
	
	public void initialize() {
		
		peopleList.setCellFactory(new PersonValueFactory());
		populateList();

		/*
		 * Listener on item list
		 */
		peopleList.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Person>() {
					@Override
					public void changed(ObservableValue<? extends Person> observable, Person oldValue,
							Person newValue) {
						
						/*
						 * Remove all information
						 */
						removeContentText();
						removeContentTextField();
						
						/*
						* To handle click on an other item when modifying or adding
						* Decided to understand it like a cancellation
						*/
						if(modifyPane.isVisible() && button_cancel_modify.isVisible()) {
							handleCancelModifyButton();
						}else if(modifyPane.isVisible() && button_cancel_add.isVisible()) {
							handleCancelAddButton();
						}
						
						/*
						* Show person details
						*/
						if(newValue != null) {
							showPersonDetails(newValue);
						}
					}
				}
		);
		
		/*
		 * Restrict textFieldAddress (number & postal code) to be only numeric
		 */
		textFieldAddressNumber.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue != null && !newValue.matches("\\d*")) {
		        	textFieldAddressNumber.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		textFieldAddressPostalCode.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue != null && !newValue.matches("\\d*")) {
		        	textFieldAddressPostalCode.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
	}
	
	public void showPersonDetails(Person person) {
		initializeText(person);
	}
	
	@FXML
    private void handleAddButton() {
		System.out.println("Firstname and Lastname cannot be empty");
		if(!modifyPane.isVisible()) {
			Person tmpPerson = new Person(null,"","","","",null,"",null,"");
			peopleList.getItems().add(tmpPerson);
			
			int indexNewPerson = peopleList.getItems().size() - 1;
			peopleList.getSelectionModel().select(indexNewPerson);
			peopleList.getFocusModel().focus(indexNewPerson);
			peopleList.scrollTo(indexNewPerson);
			
			changeToAddView();
		}
	}
	
	@FXML
    private void handleModifyButton() {
		int selectedIndex = peopleList.getSelectionModel().getSelectedIndex();
		
		if(selectedIndex >= 0) {
			changeToModifyView();
			initalizeTextField(peopleList.getItems().get(selectedIndex));
		}
	}
	
	@FXML
	private void handleSaveAddButton() {
		if(textFieldFirstname.getText() == null || textFieldLastname.getText() == null){
			
		}else {
			int selectedIndex = peopleList.getSelectionModel().getSelectedIndex();
			Person newPerson = peopleList.getItems().get(selectedIndex);
			
			String firstname = textFieldFirstname.getText();
			newPerson.setFirstname(firstname);
			
			String lastname = textFieldLastname.getText();
			newPerson.setLastname(lastname);
			
			
			//Handle all optional information
			if(textFieldNickname.getText() != null) {
				String nickname = textFieldNickname.getText();
				newPerson.setNickname(nickname);
			}
			if(textFieldPhonenumber.getText() != null) {
				String phonenumber = textFieldPhonenumber.getText();
				newPerson.setPhonenumber(phonenumber);
			}
			if(textFieldEmailaddress.getText() != null) {
				String emailaddress = textFieldEmailaddress.getText();
				newPerson.setEmailaddress(emailaddress);
			}
			if(datePickerBirthdate.getValue() != null) {
				LocalDate birthdate = datePickerBirthdate.getValue();
				newPerson.setBirthdate(birthdate);
			}
			if(pathNewAvatar != null) {
				String avatarPath = pathNewAvatar.substring(6);
				newPerson.setAvatarPath(avatarPath);
			}
			Integer addressNumber = null, addressPostalCode = null;
			String addressStreet = "", addressCity = "";
			if(textFieldAddressNumber.getText() != null) {
				addressNumber = Integer.parseInt(textFieldAddressNumber.getText());
			}
			if(textFieldAddressPostalCode.getText() != null) {
				addressPostalCode = Integer.parseInt(textFieldAddressPostalCode.getText());
			}
			if(textFieldAddressStreet.getText() != null) {
				addressStreet = textFieldAddressStreet.getText();
			}
			if(textFieldAddressCity.getText() != null) {
				addressCity = textFieldAddressCity.getText();
			}
			if(addressNumber != null && !addressStreet.isEmpty() && addressPostalCode != null && !addressCity.isEmpty()) {
				Address newAddress = new Address(null,addressNumber,addressStreet,addressPostalCode,addressCity);
				newPerson.setAddress(newAddress);
				
				AddressDao addressDao = new AddressDao();
				
				//If the new address is not in database, add it
				if(!addressDao.isAddressExist(newAddress)) {
					addressDao.addAddress(newAddress);
				}
			}else if (addressNumber != null || !addressStreet.isEmpty() || addressPostalCode != null || !addressCity.isEmpty()) {
				System.out.println("Address has to be complete to be saved");
			}
			
			
			PersonDao pDao = new PersonDao();
			pDao.addPerson(newPerson);
			
			initializeText(newPerson);
			
			changeToDetailsView();
			populateList();
		}
	}
	
	@FXML
    private void handleCancelAddButton() {
		changeToDetailsView();
		peopleList.getItems().remove(peopleList.getItems().size() - 1);
	}
	
	@FXML
    private void handleSaveModifyButton() {
		if(textFieldFirstname.getText() == null || textFieldLastname.getText() == null){
			
		}else {
			int selectedIndex = peopleList.getSelectionModel().getSelectedIndex();
			Person updatedPerson = peopleList.getItems().get(selectedIndex);
			
			String firstname = textFieldFirstname.getText();
			updatedPerson.setFirstname(firstname);
			
			String lastname = textFieldLastname.getText();
			updatedPerson.setLastname(lastname);
			
			
			//Handle all optional information
			if(textFieldNickname.getText() != null) {
				String nickname = textFieldNickname.getText();
				updatedPerson.setNickname(nickname);
			}
			if(textFieldPhonenumber.getText() != null) {
				String phonenumber = textFieldPhonenumber.getText();
				updatedPerson.setPhonenumber(phonenumber);
			}
			if(textFieldEmailaddress.getText() != null) {
				String emailaddress = textFieldEmailaddress.getText();
				updatedPerson.setEmailaddress(emailaddress);
			}
			if(datePickerBirthdate.getValue() != null) {
				LocalDate birthdate = datePickerBirthdate.getValue();
				updatedPerson.setBirthdate(birthdate);
			}
			if(pathNewAvatar != null) {
				String avatarPath = pathNewAvatar.substring(6);
				updatedPerson.setAvatarPath(avatarPath);
			}
			Integer addressNumber = null, addressPostalCode = null;
			String addressStreet = "", addressCity = "";
			if(textFieldAddressNumber.getText() != null) {
				addressNumber = Integer.parseInt(textFieldAddressNumber.getText());
			}
			if(textFieldAddressPostalCode.getText() != null) {
				addressPostalCode = Integer.parseInt(textFieldAddressPostalCode.getText());
			}
			if(textFieldAddressStreet.getText() != null) {
				addressStreet = textFieldAddressStreet.getText();
			}
			if(textFieldAddressCity.getText() != null) {
				addressCity = textFieldAddressCity.getText();
			}
			if(addressNumber != null && !addressStreet.isEmpty() && addressPostalCode != null && !addressCity.isEmpty()) {
				Address newAddress = new Address(null,addressNumber,addressStreet,addressPostalCode,addressCity);
				updatedPerson.setAddress(newAddress);
				
				AddressDao addressDao = new AddressDao();
				
				//If the new address is not in database, add it
				if(!addressDao.isAddressExist(newAddress)) {
					addressDao.addAddress(newAddress);
				}
			}else if (addressNumber != null || !addressStreet.isEmpty() || addressPostalCode != null || !addressCity.isEmpty()) {
				System.out.println("Address has to be complete to be saved");
			}
			
			initializeText(updatedPerson);
			
			PersonDao pDao = new PersonDao();
			pDao.updatePerson(updatedPerson);
			
			changeToDetailsView();
			populateList();
		}
	}
	
	@FXML
    private void handleCancelModifyButton() {
		int selectedIndex = peopleList.getSelectionModel().getSelectedIndex();
		
		if(selectedIndex >= 0) {
			changeToDetailsView();
		}
	}
	
	@FXML
    private void handleDeleteButton() {
		int selectedIndex = peopleList.getSelectionModel().getSelectedIndex();
		
		//If delete person who's not in database
		if(peopleList.getItems().get(selectedIndex).getFirstname().equals("")) {
			handleCancelAddButton();
		}else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Select");
	        alert.setHeaderText("Are you sure to remove " + 
	        					peopleList.getItems().get(selectedIndex).getFirstname() + " " + 
	        					peopleList.getItems().get(selectedIndex).getLastname() +
	        					" from your contacts ? ");
	 
	        ButtonType yes = new ButtonType("Yes");
	        ButtonType no = new ButtonType("No");
	 
	        // Remove default ButtonTypes
	        alert.getButtonTypes().clear();
	 
	        alert.getButtonTypes().addAll(yes, no);
	 
	        // option != null.
	        Optional<ButtonType> option = alert.showAndWait();
	 
	        if (option.get() == yes && selectedIndex >= 0) {
	        	PersonDao pDao = new PersonDao();
	    		pDao.deletePerson(peopleList.getItems().get(selectedIndex));
	        	peopleList.getItems().remove(selectedIndex);
	        }
		}
    }
	
	@FXML
    private void handleChangeAvatarButton() {
		Stage mainStage = StageService.getPrimaryStage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a new avatar");
		fileChooser.getExtensionFilters().add(
		         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		 File selectedFile = fileChooser.showOpenDialog(mainStage);
		 if (selectedFile != null) {
			 pathNewAvatar = selectedFile.toURI().toString();
			 imageViewNewAvatar.setImage(new Image(pathNewAvatar));
		 }
	}
	
	private void changeToModifyView() {
		button_save_modify.setVisible(true);
		modifyPane.setVisible(true);
		button_cancel_modify.setVisible(true);
		button_modify.setVisible(false);
		informationPane.setVisible(false);
	}
	
	private void changeToAddView() {
		button_save_add.setVisible(true);
		modifyPane.setVisible(true);
		button_cancel_add.setVisible(true);
		button_modify.setVisible(false);
		informationPane.setVisible(false);
	}
	
	private void initializeText(Person person) {
		textFirstname.setText(person.getFirstname());
		textLastname.setText(person.getLastname());
		if(person.getNickname() != null) {
			textNickname.setText(person.getNickname());
		}
		if(person.getPhonenumber() != null) {
			textPhonenumber.setText(person.getPhonenumber());
		}
		if(person.getAddress() != null) {
			textAddress.setText(person.getAddress().getAddressFormat());
		}
		if(person.getEmailaddress() != null) {
			textEmailaddress.setText(person.getEmailaddress());
		}
		if(person.getBirthdate() != null) {
			LocalDate birthdate = person.getBirthdate();
			
			String[] months = new String[] {
		            "Janvier",
		            "Février",
		            "Mars",
		            "Avril",
		            "Mai",
		            "Juin",
		            "Juillet",
		            "Août",
		            "Septembre",
		            "Octobre",
		            "Novembre",
		            "Décembre"};
			
			textBirthdate.setText(birthdate.getDayOfMonth() + " " + months[birthdate.getMonthValue() - 1] + " " + birthdate.getYear());
			
		}
	}
	
	private void removeContentText() {
		textFirstname.setText(null);
		textLastname.setText(null);
		textNickname.setText(null);
		textPhonenumber.setText(null);
		textAddress.setText(null);
		textEmailaddress.setText(null);
		textBirthdate.setText(null);
	}
	
	private void initalizeTextField(Person person) {
		textFieldFirstname.setText(person.getFirstname());
		textFieldLastname.setText(person.getLastname());
		
		if(person.getNickname() != null) {
			textFieldNickname.setText(person.getNickname());
		}
		if(person.getPhonenumber() != null) {
			textFieldPhonenumber.setText(person.getPhonenumber());
		}
		if(person.getAddress() != null) {
			if(person.getAddress().getNumber() != 0) {
				textFieldAddressNumber.setText(Integer.toString(person.getAddress().getNumber()));
			}
			if(person.getAddress().getStreet() != null) {
				textFieldAddressStreet.setText(person.getAddress().getStreet());
			}
			if(person.getAddress().getPostalCode() != 0) {
				textFieldAddressPostalCode.setText(Integer.toString(person.getAddress().getPostalCode()));
			}
			if(person.getAddress().getCity() != null) {
				textFieldAddressCity.setText(person.getAddress().getCity());
			}
		}
		if(person.getEmailaddress() != null) {
			textFieldEmailaddress.setText(person.getEmailaddress());
		}
		if(person.getBirthdate() != null) {
			datePickerBirthdate.setValue(person.getBirthdate());
		}
		if(person.getAvatarPath() != null) {
			imageViewNewAvatar.setImage(new Image(new File(person.getAvatarPath()).toURI().toString()));
		}
	}
	
	private void removeContentTextField() {
		textFieldFirstname.setText(null);
		textFieldLastname.setText(null);
		textFieldNickname.setText(null);
		textFieldPhonenumber.setText(null);
		textFieldAddressNumber.setText(null);
		textFieldAddressNumber.setPromptText("n°");
		textFieldAddressStreet.setText(null);
		textFieldAddressStreet.setPromptText("street");
		textFieldAddressPostalCode.setText(null);
		textFieldAddressPostalCode.setPromptText("postal code");
		textFieldAddressCity.setText(null);
		textFieldAddressCity.setPromptText("city");
		textFieldEmailaddress.setText(null);
		datePickerBirthdate.setValue(null);
		imageViewNewAvatar.setImage(null);
	}
	
	private void changeToDetailsView() {
		button_save_modify.setVisible(false);
		button_save_add.setVisible(false);
		modifyPane.setVisible(false);
		button_cancel_modify.setVisible(false);
		button_cancel_add.setVisible(false);
		button_modify.setVisible(true);
		informationPane.setVisible(true);
	}

}
