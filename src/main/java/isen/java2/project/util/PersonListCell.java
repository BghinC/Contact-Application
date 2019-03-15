package isen.java2.project.util;

import java.io.File;

import isen.java2.project.model.Person;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class PersonListCell extends ListCell<Person> {
	
	private final GridPane gridPane = new GridPane();
    private final Label nameLabel = new Label();
    private final Label nicknameLabel = new Label();
    private final ImageView personIcon = new ImageView(); 
    private final AnchorPane content = new AnchorPane(); 
  
    public PersonListCell() { 
    	personIcon.setFitWidth(75); 
    	personIcon.setPreserveRatio(true); 
        GridPane.setConstraints(personIcon, 0, 0, 1, 3); 
        GridPane.setValignment(personIcon, VPos.TOP); 
        // 
        nameLabel.setStyle("-fx-font-weight: bold;"); 
        GridPane.setConstraints(nameLabel, 2, 1); 
        //
        GridPane.setConstraints(nicknameLabel, 2, 2); 
        //         
        gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true)); 
        gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true)); 
        gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true)); 
        gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true)); 
        gridPane.setHgap(6); 
        gridPane.setVgap(6); 
        gridPane.getChildren().setAll(personIcon, nameLabel, nicknameLabel); 
        AnchorPane.setTopAnchor(gridPane, 0d); 
        AnchorPane.setLeftAnchor(gridPane, 0d); 
        AnchorPane.setBottomAnchor(gridPane, 0d); 
        AnchorPane.setRightAnchor(gridPane, 0d); 
        content.getChildren().add(gridPane); 
    }
  
  
    @Override
    protected void updateItem(Person item, boolean empty) { 
        super.updateItem(item, empty);
        setGraphic(null); 
        setText(null);
        setContentDisplay(ContentDisplay.LEFT);
        if (!empty && item != null) { 
            nameLabel.setText(item.getFirstname() + " " + item.getLastname());
            if(item.getNickname() != null) {
            	nicknameLabel.setText(item.getNickname());
            }
            if(item.getAvatarPath() != null) {
            	File image = new File(item.getAvatarPath());
            	if(image.exists()) {
                	personIcon.setImage(new Image(image.toURI().toString()));
                }else {
                	personIcon.setImage(new Image("/isen/contactApp/img/person.png"));
                }
            }
            setText(null); 
            setGraphic(content); 
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
        } 
    } 
}
