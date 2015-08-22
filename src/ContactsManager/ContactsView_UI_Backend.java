package ContactsManager;
/*
 * Name: Harsha Deep Reddy V
 * 
 * Date: 13 Sep 2014 
 * Purpose of this module: The main UI backend file for the fxml file. This handles all the listeners 
 *                         and settings for the controls in the fxml file. 
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ContactsView_UI_Backend {
    
    //Defining all the fields required .. 
    //Please note they are all fx:id not the CSS style sheet id
    @FXML private TableView<Person> tableView;
    @FXML private TableColumn nameColumn;
    @FXML private TableColumn phoneColumn;
    @FXML private ProgressBar progressBar;
    
    @FXML private TextFieldLimited firstNameField;
    @FXML private TextFieldLimited lastNameField;
    @FXML private TextFieldLimited middleNameField;
    @FXML private TextFieldLimited genderField;
    @FXML private TextFieldLimited address1Field;
    @FXML private TextFieldLimited address2Field;
    @FXML private TextFieldLimited cityField;
    @FXML private TextFieldLimited stateField;
    @FXML private TextFieldLimited countryField;
    @FXML private TextFieldLimited emailField;
    @FXML private TextFieldLimited zipField;
    @FXML private TextFieldLimited phoneField;
    
    @FXML private Label titleLabel;
    
    @FXML private Label firstNameLabel;
    @FXML private Label genderLabel;
    @FXML private Label address1Label;
    @FXML private Label address2Label;
    @FXML private Label cityLabel;
    @FXML private Label stateLabel;
    @FXML private Label zipLabel;
    @FXML private Label phoneLabel;
    @FXML private Label countryLabel;
    @FXML private Label emailLabel;
    
    @FXML private VBox fieldsVBox;
    @FXML private VBox labelsVBox;
    
    @FXML private Button addPersonBtn;
    @FXML private Button editPersonBtn;
    @FXML private Button deletePersonBtn;
    
    @FXML private Label errorMessageLabel;
    
    //this variable maintains the current selected index if persent.
    public static int tableSelectedIndex = 0;
    private Boolean isDataSafe = true;
    private Boolean isEditing = false;
    private String[] requiredMessage = {"","","","","","",""};
    
    private boolean isNameSafe = true;
    private boolean isFNameSafe = true;
    private boolean isLNameSafe = true;
    private boolean isAddressSafe = true;
    private boolean isCitySafe = true;
    private boolean isStateSafe = true;
    private boolean isCountrySafe = true;
    private boolean isZipSafe = true;
    private boolean isPhoneSafe = true;
    private boolean isEmailSafe = true;
    private boolean isGenderSafe = true;
    
    
    //The main list for maintiaining Person objects
    public static ObservableList<Person> data = FXCollections.observableArrayList();
    
    //This is the main intialize function that is called 
    //when the app is created.
    @FXML
    private void initialize() {

    //Setting the lengths of fields to conform with the requriement of the assignment
    firstNameField.setMaxlength(20);
    lastNameField.setMaxlength(20);
    middleNameField.setMaxlength(1);
    genderField.setMaxlength(1);
    address1Field.setMaxlength(35);
    address2Field.setMaxlength(35);
    cityField.setMaxlength(25);
    stateField.setMaxlength(2);
    zipField.setMaxlength(9);
    phoneField.setMaxlength(21);   
    countryField.setMaxlength(20);
    emailField.setMaxlength(100);
        
    //Setting table column widths
    nameColumn.minWidthProperty().bind(tableView.widthProperty().divide(2));
    phoneColumn.minWidthProperty().bind(tableView.widthProperty().divide(2)); 
        
    //Reading file andd checking if any data is present
    Controller.readFile();
    if(data.size() > 0)
    {
        tableView.setItems(data);
        tableView.getSelectionModel().selectFirst();
        tableMouseClick(null);
    }
    else
    {
        //if no data is present then disable the edit and delete buttons.
        editPersonBtn.setDisable(true);
        deletePersonBtn.setDisable(true);
    }
    
    //Set this variable indicatin data is not safe for saving.
    isDataSafe = true;
    progressBar.setProgress(0);
    
    //Validate first name, if it is invalid append to error message.
    //If valid make the variable for the field safe and continue.
    firstNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {            
            firstNameField.setStyle("-fx-border-color: none;-fx-border-width:0");
            if(!newValue) {
                //Name checks
                if(firstNameField.getText() == null || firstNameField.getText().length() == 0)
                {
                    firstNameField.setStyle("-fx-border-color: red;-fx-border-width:2");            
                    isFNameSafe = false;
                    requiredMessage[0] = "Firstname,";
                }
                else
                {
                    requiredMessage[0] = "";
                    isFNameSafe = true;
                }
                incrementProgressBar();                
            }
        }
    });
    
    //Validate last name, if it is invalid append to error message.
    //If valid make the variable for the field safe and continue.
    lastNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {            
            lastNameField.setStyle("-fx-border-color: none;-fx-border-width:0");
            if(!newValue) {          
                if(lastNameField.getText() == null || lastNameField.getText().length() == 0)
                {
                    lastNameField.setStyle("-fx-border-color: red;-fx-border-width:2");
                    isLNameSafe = false;
                    requiredMessage[1]= "Lastname,";
                }
                else
                    {
                    requiredMessage[1] = "";
                    isLNameSafe = true;
                    }
                incrementProgressBar();
            }
        }
    });
    
    //Validate full name, if it is invalid append to error message.
    //If valid make the variable for the field safe and continue.
    middleNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(isFNameSafe)
                firstNameField.setStyle("-fx-border-color: none;-fx-border-width:0");
            if(isLNameSafe)
                lastNameField.setStyle("-fx-border-color: none;-fx-border-width:0");
            middleNameField.setStyle("-fx-border-color: none;-fx-border-width:0");
            if(!newValue) {   
                isNameSafe = true;
                //Check whether the name already exists in the data list.
                for(Person person: data)
                {
                    if(person.getFirstName().equals(firstNameField.getText()) && person.getMiddleInitial().equals(middleNameField.getText())
                            && person.getLastName().equals(lastNameField.getText()))
                    {
                        //Set border to red and highlight the field.
                        firstNameField.setStyle("-fx-border-color: red;-fx-border-width:2");
                        lastNameField.setStyle("-fx-border-color: red;-fx-border-width:2");
                        middleNameField.setStyle("-fx-border-color: red;-fx-border-width:2");

                        isNameSafe = false;
                        
                        if(isEditing)
                        {
                            //If there is editing going on we have to check if the current index 
                            //selected is equal to the data in the fields then we can treat 
                            //data as safe
                            if(data.get(tableSelectedIndex).getFirstName().equals(firstNameField.getText()) &&
                                  data.get(tableSelectedIndex).getLastName().equals(lastNameField.getText()) &&
                                    data.get(tableSelectedIndex).getMiddleInitial().equals(middleNameField.getText())
                                    )
                            {
                                isNameSafe = true;
                                firstNameField.setStyle("-fx-border-color: none;-fx-border-width:0");
                                lastNameField.setStyle("-fx-border-color: none;-fx-border-width:0");
                                middleNameField.setStyle("-fx-border-color: none;-fx-border-width:0");
                            }
                        }
                    }
                }
                
                incrementProgressBar();                
            }
        }
    });
    
    //Validate city, if it is invalid append to error message.
    //If valid make the variable for the field safe and continue.
    cityField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {            
            cityField.setStyle("-fx-border-color: none;-fx-border-width:0");            
            if(!newValue) {
                //City check
                if(cityField.getText() == null || cityField.getText().length() == 0)
                {
                    cityField.setStyle("-fx-border-color: red;-fx-border-width:2");
                    isCitySafe = false;
                    requiredMessage[2]= "City,";
                }
                else
                    {
                    requiredMessage[2] = "";
                    isCitySafe = true;
                    }
                    incrementProgressBar();                
                }
        }
    });
    
    //Validate address 1, if it is invalid append to error message.
    //If valid make the variable for the field safe and continue.
    address1Field.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {            
            address1Field.setStyle("-fx-border-color: none;-fx-border-width:0");            
            if(!newValue) {
                //Address1 check
                if(address1Field.getText() == null || address1Field.getText().length() == 0)
                {
                    address1Field.setStyle("-fx-border-color: red;-fx-border-width:2");
                    isAddressSafe = false;
                    requiredMessage[3]= "Address1,";
                }
                else
                    {
                    requiredMessage[3] = "";
                    isAddressSafe = true;
                    }
                    incrementProgressBar();                
                }
        }
    });
    
    //Validate state, if it is invalid append to error message.
    //If valid make the variable for the field safe and continue.
    stateField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {            
            stateField.setStyle("-fx-border-color: none;-fx-border-width:0");
            if(!newValue) {
                //State check
                if(stateField.getText() == null || stateField.getText().length() == 0)
                {
                    stateField.setStyle("-fx-border-color: red;-fx-border-width:2");
                    isStateSafe = false;
                    requiredMessage[4]= "State,";
                }
                else
                    {
                    requiredMessage[4] = "";
                    isStateSafe = true;
                    }
                incrementProgressBar();                
            }
        }
    });
    
    //Validate country, if it is invalid append to error message.
    //If valid make the variable for the field safe and continue.
    countryField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {            
            countryField.setStyle("-fx-border-color: none;-fx-border-width:0");
            if(!newValue) {
                //Country check
                if(countryField.getText() == null || countryField.getText().length() == 0)
                {
                    countryField.setStyle("-fx-border-color: red;-fx-border-width:2");
                    isCountrySafe = false;
                    requiredMessage[5]= "Country,";
                }
                else
                    {
                    requiredMessage[5] = "";
                    isCountrySafe = true;
                    }
                incrementProgressBar();                
            }
        }
    });
    
    //Validate zip, if it is invalid append to error message.
    //If valid make the variable for the field safe and continue.
    zipField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {            
            zipField.setStyle("-fx-border-color: none;-fx-border-width:0");
            if(!newValue) {   
                //Zip Check
                if(zipField.getText() == null || zipField.getText().length() == 0)
                {
                    zipField.setStyle("-fx-border-color: red;-fx-border-width:2");
                    isZipSafe = false;
                    requiredMessage[6]= "Zip,";
                }
                else
                    {
                    requiredMessage[6] = "";
                    isZipSafe = true;
                    }
                incrementProgressBar();                
            }
        }
    });
    
    //Validate phone, if it is invalid append to error message.
    //If valid make the variable for the field safe and continue.
    phoneField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {            
            phoneField.setStyle("-fx-border-color: none;-fx-border-width:0");
            if(!newValue) {   
                String phoneNumber = phoneField.getText();
                //Phone check
                if(phoneNumber == null || phoneNumber.length() == 0)
                {
                    phoneField.setStyle("-fx-border-color: red;-fx-border-width:2");
                    isPhoneSafe = false;
                }
                else if(phoneNumber.length() >0)
                {
                    //Initialize reg ex for phone number. 
                    String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
                    CharSequence inputStr = phoneNumber;
                    Pattern pattern = Pattern.compile(expression);
                    Matcher matcher = pattern.matcher(inputStr);
                    if(!matcher.matches()){
                        phoneField.setStyle("-fx-border-color: red;-fx-border-width:2");
                        isPhoneSafe=false;                         
                        }
                    else
                        isPhoneSafe = true;
                }
                else
                    isPhoneSafe = true;
                incrementProgressBar();                
            }
        }
    });
    
    //Validate email, if it is invalid append to error message.
    //If valid make the variable for the field safe and continue.
    emailField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            emailField.setStyle("-fx-border-color: none;-fx-border-width:0");
            if(!newValue) {
                //Email check
                if(emailField.getText() == null || emailField.getText().length() == 0)
                {
                    emailField.setStyle("-fx-border-color: red;-fx-border-width:2");
                    isEmailSafe = false;
                }
                else if(emailField.getText().length() >0)
                {
                    //Initialize reg ex for phone number. 
                    String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
                    CharSequence inputStr = emailField.getText();
                    Pattern pattern = Pattern.compile(expression);
                    Matcher matcher = pattern.matcher(inputStr);
                    if(!matcher.matches()){
                        emailField.setStyle("-fx-border-color: red;-fx-border-width:2");
                        isEmailSafe=false;
                        }
                    else
                        isEmailSafe = true;
                }
                else
                    isEmailSafe = true;
                incrementProgressBar();                
            }
        }
    });
    
    //Validate gender, if it is invalid append to error message.
    //If valid make the variable for the field safe and continue.
    genderField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {           
            genderField.setStyle("-fx-border-color: none;-fx-border-width:0");
            if(!newValue) {
                //Gender Checks
                if(genderField.getText() == null || genderField.getText().length() == 0 
                        || !(genderField.getText().toLowerCase().equals("m") || genderField.getText().toLowerCase().equals("f")))
                {
                    genderField.setStyle("-fx-border-color: red;-fx-border-width:2");
                    isGenderSafe = false;                    
                }
                else
                    isGenderSafe = true;
                incrementProgressBar();
            }
        }
    });
    
    fieldsVBox.setOnKeyPressed(fieldsKeyListener);
    editPersonBtn.setOnKeyPressed(editKeyListener);
    addPersonBtn.setOnKeyPressed(addKeyListener);
    deletePersonBtn.setOnKeyPressed(deleteKeyListener);
    tableView.setOnKeyPressed(tableKeyListener);
    }  
    
    //Event handler for saving data when enter is pressed
    //and to cancel when escape is pressed
    private EventHandler<KeyEvent> fieldsKeyListener = new EventHandler<KeyEvent>() {
    @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.ENTER) {
                
                fieldsVBox.requestFocus();
                
                savePerson(null);
                event.consume();
            } 
            else if(event.getCode() == KeyCode.ESCAPE ) {
                cancelEdit(null);
                event.consume();
            } 
                
        }
    };
    
    //Event handler for the case when enter key is pressed on the edit button
    private EventHandler<KeyEvent> editKeyListener = new EventHandler<KeyEvent>() {
    @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.ENTER) {
                fieldsVBox.requestFocus();
                editPerson(null);
                event.consume();
            } 
                
        }
    };
    
    //Event handler for the case when enter key is pressed on the add button
    private EventHandler<KeyEvent> addKeyListener = new EventHandler<KeyEvent>() {
    @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.ENTER) {
                addPerson(null);
                event.consume();
            } 
                
        }
    };
    
    //Event handler for the case when enter key is pressed on the delete button
    private EventHandler<KeyEvent> deleteKeyListener = new EventHandler<KeyEvent>() {
    @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.ENTER) {
                deletePerson(null);
                event.consume();
            } 
                
        }
    };
    
    //Event handler for the case when up and down arrows are used on the table. 
    private EventHandler<KeyEvent> tableKeyListener = new EventHandler<KeyEvent>() {
    @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.DOWN) {
                tableSelectedIndex = tableView.getSelectionModel().getSelectedIndex();
                if(tableSelectedIndex < data.size() - 1)
                {
                    tableSelectedIndex++;
                    tableView.getSelectionModel().select(tableSelectedIndex);
                    updateLabels();
                }
                event.consume();
            }             
            else if(event.getCode() == KeyCode.UP) {
                tableSelectedIndex = tableView.getSelectionModel().getSelectedIndex();
                if(tableSelectedIndex > 0)
                {
                    tableSelectedIndex--;
                    tableView.getSelectionModel().select(tableSelectedIndex);
                    updateLabels();
                }
                event.consume();
            }    
        }
    };
    
    //Code to increment progres bar and set appropriate error messages from the validation done above
    private void incrementProgressBar() {
        progressBar.setProgress(0);
        if(firstNameField.getText().length()>0 && isFNameSafe)
            progressBar.setProgress(progressBar.getProgress() + 0.1);
        if(lastNameField.getText().length()>0 && isLNameSafe)
            progressBar.setProgress(progressBar.getProgress() + 0.1);
        if(address1Field.getText().length()>0 )
            progressBar.setProgress(progressBar.getProgress() + 0.1);
        if(cityField.getText().length()>0 && isCitySafe)
            progressBar.setProgress(progressBar.getProgress() + 0.1);
        if(countryField.getText().length()>0 && isCountrySafe)
            progressBar.setProgress(progressBar.getProgress() + 0.1);
        if(stateField.getText().length()>0 && isStateSafe)
            progressBar.setProgress(progressBar.getProgress() + 0.1);
        if(zipField.getText().length()>0 && isZipSafe)
            progressBar.setProgress(progressBar.getProgress() + 0.1);
        if(phoneField.getText().length()>0 && isPhoneSafe)
            progressBar.setProgress(progressBar.getProgress() + 0.1);
        if(genderField.getText().length()>0 && isGenderSafe)
            progressBar.setProgress(progressBar.getProgress() + 0.1);
        if(emailField.getText().length()>0 && isEmailSafe)
            progressBar.setProgress(progressBar.getProgress() + 0.1);

        isDataSafe = isNameSafe && isFNameSafe && isLNameSafe && isAddressSafe && isCitySafe && isCountrySafe && isStateSafe && isZipSafe
                && isPhoneSafe && isGenderSafe && isEmailSafe;
        
        errorMessageLabel.setText("");
        if(!isNameSafe)
            errorMessageLabel.setText(errorMessageLabel.getText() + "* A person with similar name is already in your contacts\n");
        if(!isPhoneSafe)
            errorMessageLabel.setText(errorMessageLabel.getText() + "* Invalid or Empty phone number\n");
        if(!isEmailSafe)
            errorMessageLabel.setText(errorMessageLabel.getText() + "* Invalid or Empty email.");
        if(!isGenderSafe)
            errorMessageLabel.setText(errorMessageLabel.getText() + "* Only M or F accepted in Gender field.");
        
        String finalRequiredMessage = requiredMessage[0] + requiredMessage[1] +requiredMessage[2]+requiredMessage[3]
                +requiredMessage[4] +requiredMessage[5] + requiredMessage[6];
        //Display the error message
        if(!isDataSafe)
        {
            if(finalRequiredMessage.length() > 2)
                errorMessageLabel.setText(errorMessageLabel.getText() + "\n* "+ finalRequiredMessage.substring(0, finalRequiredMessage.length()-1) +"\n  cannot be empty.");
        }
        }
    
    //The listener when the Add button is clicked.
    @FXML
    protected void addPerson(ActionEvent event) {
        titleLabel.setText("ADD MODE");
        progressBar.setProgress(0);
        isDataSafe = true;
        //clear any previous settings and warnings
        clearFields();
        removeErrorStyling();
        //Hide the labels and make visible the textfields
        labelsVBox.setVisible(false);
        fieldsVBox.setVisible(true);
        //Disable the table until the entry is done
        tableView.setDisable(true);        
        tableSelectedIndex = -1;
    }
    
    //The listener when the Edit button is clicked.
    @FXML
    protected void editPerson(ActionEvent event) {
        titleLabel.setText("EDIT MODE");        
        //clear any previous settings and warnings
        updateFields();        
        isDataSafe = true;
        removeErrorStyling();
        incrementProgressBar();
        
        isEditing = true;
        if(data.size() >= tableSelectedIndex)
        {
            //Hide the labels and make visible the textfields
            labelsVBox.setVisible(false);
            fieldsVBox.setVisible(true);
            //Disable the table until the entry is done
            tableView.setDisable(true);
        }
    }
    
    //The listener when the Save button is clicked.
    @FXML
    protected void savePerson(ActionEvent event) { 
        //clear any previous settings and warnings
        data = tableView.getItems();                
        validateFields();
        if(isDataSafe) //Check if data is safe for editing.
        {
        removeErrorStyling();
        if(tableSelectedIndex >=0) 
        //Always check for proper selected index which
        //means the data is being edited.
        {
            //update the object in the data list
            data.set(tableSelectedIndex, new Person(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    middleNameField.getText(),
                    address1Field.getText(),
                    address2Field.getText(),
                    cityField.getText(),
                    stateField.getText(),
                    countryField.getText(),
                    zipField.getText(),
                    phoneField.getText(),
                    emailField.getText(),
                    genderField.getText()
            ));
        }
        else
        {
            //here the data is being added to the data list
            tableSelectedIndex = data.size();
            //add object to data list
            data.add(tableSelectedIndex, new Person(
                firstNameField.getText(),
                lastNameField.getText(),
                middleNameField.getText(),
                address1Field.getText(),
                address2Field.getText(),
                cityField.getText(),
                stateField.getText(),
                countryField.getText(),
                zipField.getText(),
                phoneField.getText(),
                emailField.getText(),
                genderField.getText()
            ));
            
            //Enable the edit and delete buttons (if they were disabled).
            editPersonBtn.setDisable(false);
            deletePersonBtn.setDisable(false);
        }
        
        //Complete the necessary activites to write to file and update necessary fields.
        Controller.writeToFile();
        updateLabels();
        titleLabel.setText("CONTACTS MANAGER");
        tableView.getSelectionModel().select(tableSelectedIndex);
        isEditing = false;
        fieldsVBox.setVisible(false);
        labelsVBox.setVisible(true);
        tableView.setDisable(false);
        }
    }
    
    //The listener when the Delete button is clicked.
    @FXML
    protected void deletePerson(ActionEvent event) {         
        //Remove the obejct from the data list
        data.remove(tableSelectedIndex);
        if(data.size() > 0)
            //if there is more than 1 object in data list then delete it and select the first row
        {
            tableView.getSelectionModel().selectFirst();    
            tableMouseClick(null);
        }
        else
        {
            //delete the last object from the list and clear any fields
            clearLabels();
            editPersonBtn.setDisable(true);
            deletePersonBtn.setDisable(true);
        }
        titleLabel.setText("CONTACTS MANAGER");
        Controller.writeToFile();
    }
    
    //The listener when the cancel button is clicked.
    @FXML
    protected void cancelEdit(ActionEvent event) { 
        
        //Reverts back any prgress that was happening
        //and clears all the fields that were previously set.
        if(tableSelectedIndex < 0)
        {
            tableSelectedIndex = tableView.getSelectionModel().getSelectedIndex();            
        }
        isEditing = false;
        fieldsVBox.setVisible(false);
        labelsVBox.setVisible(true);
        tableView.setDisable(false);
        titleLabel.setText("CONTACTS MANAGER");
    }
    
    //The listener when a row in the table is clicked.
    @FXML
    protected void tableMouseClick(final MouseEvent event) {
        if(data.size() >0) //Always check for valid size of data list
        {
            //get the selected index and update the requried fields.
            tableSelectedIndex = tableView.getSelectionModel().getSelectedIndex();
            updateLabels();
        }
    }
    
    //The method used to update the textfields in the app
    private void updateFields()
    {
        if(data.size() >= tableSelectedIndex) //Check for valid table selected index
        {
            firstNameField.setText(data.get(tableSelectedIndex).getFirstName());
            lastNameField.setText(data.get(tableSelectedIndex).getLastName());
            middleNameField.setText(data.get(tableSelectedIndex).getMiddleInitial());
            address1Field.setText(data.get(tableSelectedIndex).getAddressLine1());
            address2Field.setText(data.get(tableSelectedIndex).getAddressLine2());
            cityField.setText(data.get(tableSelectedIndex).getCity());
            stateField.setText(data.get(tableSelectedIndex).getState());
            zipField.setText(data.get(tableSelectedIndex).getZipcode());
            phoneField.setText(data.get(tableSelectedIndex).getPhoneNumber());
            genderField.setText(data.get(tableSelectedIndex).getGender());
            countryField.setText(data.get(tableSelectedIndex).getCountry());
            emailField.setText(data.get(tableSelectedIndex).getEmail());
        }
    }
    
    //The method used to clear the textfields in the app
    private void clearFields()
    {
        firstNameField.setText("");
        lastNameField.setText("");
        middleNameField.setText("");
        address1Field.setText("");
        address2Field.setText("");
        cityField.setText("");
        stateField.setText("");
        zipField.setText("");
        phoneField.setText("");
        genderField.setText("");
        countryField.setText("");
        emailField.setText("");
        //clear any previous error messages
        requiredMessage[0]="";isNameSafe=true;isFNameSafe=true;
        requiredMessage[1]="";isLNameSafe=true;
        requiredMessage[2]="";isAddressSafe=true;
        requiredMessage[3]="";isCitySafe=true;
        requiredMessage[4]="";isStateSafe=true;
        requiredMessage[5]="";isZipSafe=true;
        requiredMessage[6]="";isCountrySafe=true;
        isPhoneSafe=true;isEmailSafe=true;isGenderSafe=true;
        errorMessageLabel.setText("");
    }
    
    //The method used to clear the labels in the right pane in the app
    private void clearLabels()
    {
        firstNameLabel.setText("");
        address1Label.setText("");
        address2Label.setText("");
        cityLabel.setText("");
        stateLabel.setText("");
        zipLabel.setText("");
        phoneLabel.setText("");
        genderLabel.setText("");
        countryLabel.setText("");
        emailLabel.setText("");
        //clear any previous error messages
        requiredMessage[0]="";isNameSafe=true;isFNameSafe=true;
        requiredMessage[1]="";isLNameSafe=true;
        requiredMessage[2]="";isAddressSafe=true;
        requiredMessage[3]="";isCitySafe=true;
        requiredMessage[4]="";isStateSafe=true;
        requiredMessage[5]="";isZipSafe=true;
        requiredMessage[6]="";isCountrySafe=true;
        isPhoneSafe=true;isEmailSafe=true;isGenderSafe=true;
        errorMessageLabel.setText("");
    }
    
    //The method used to update the labels in the right pane in the app
    private void updateLabels()
    {
        if(data.size() >= tableSelectedIndex)
        {
            String name = "";
            if(data.get(tableSelectedIndex).getFirstName().length() > 25 || 
                    data.get(tableSelectedIndex).getLastName().length() > 25 )
            {
                name = data.get(tableSelectedIndex).getFirstName() +
            " " + data.get(tableSelectedIndex).getMiddleInitial() + "\n" + data.get(tableSelectedIndex).getLastName();
            }
            else
            {
                name = data.get(tableSelectedIndex).getFirstName() +
            " " + data.get(tableSelectedIndex).getMiddleInitial() + " " + data.get(tableSelectedIndex).getLastName();
            }
            
            firstNameLabel.setText(name);
            
            if(data.get(tableSelectedIndex).getAddressLine1().length() > 25)
            {
                String address1 = data.get(tableSelectedIndex).getAddressLine1();
                address1Label.setText(address1.substring(0,25) + "\n" + address1.substring(25,address1.length()));
            }
            else
                address1Label.setText(data.get(tableSelectedIndex).getAddressLine1());
            
            if(data.get(tableSelectedIndex).getAddressLine2().length() > 25)
            {
                String address2 = data.get(tableSelectedIndex).getAddressLine2();
                address2Label.setText(address2.substring(0,25) + "\n" + address2.substring(25,address2.length()));
            }
            else
                address2Label.setText(data.get(tableSelectedIndex).getAddressLine2());
            
            cityLabel.setText(data.get(tableSelectedIndex).getCity());
            stateLabel.setText(data.get(tableSelectedIndex).getState());
            zipLabel.setText(data.get(tableSelectedIndex).getZipcode());
            phoneLabel.setText(data.get(tableSelectedIndex).getPhoneNumber());
            countryLabel.setText(data.get(tableSelectedIndex).getCountry());
            
            String email = data.get(tableSelectedIndex).getEmail();
            if(email.length() > 25 && email.length() <=50)
            {
                labelsVBox.setStyle("-fx-font-size:1em");
                emailLabel.setText(email.substring(0,25) + "\n" + email.substring(25, email.length()) );
            }
            else if(email.length() > 50 && email.length()<=75)
            {
                labelsVBox.setStyle("-fx-font-size:1em");
                emailLabel.setText(email.substring(0,25) + "\n" +
                        email.substring(25,50) + "\n" + email.substring(50, email.length()) );
            }
            else if(email.length() > 75 && email.length()<=100)
            {
                labelsVBox.setStyle("-fx-font-size:0.82em");
                emailLabel.setText(email.substring(0,33) + "\n" +
                        email.substring(33,66) + "\n" + email.substring(66, email.length()) );
            }
            else
            {
                labelsVBox.setStyle("-fx-font-size:1em");
                emailLabel.setText(data.get(tableSelectedIndex).getEmail());                
            }
            
            //Convert single digit gender to human readable format as Male and Female.
            if(data.get(tableSelectedIndex).getGender().toLowerCase().equals("m"))
                genderLabel.setText("Male");
            else
                genderLabel.setText("Female");
        }
    }
    
    //Remove any styling for errors that were set previously.
    private void removeErrorStyling()
    {
            firstNameField.setStyle("-fx-border-color: none;");
            lastNameField.setStyle("-fx-border-color: none;");
            middleNameField.setStyle("-fx-border-color: none;");
            address1Field.setStyle("-fx-border-color: none;");
            cityField.setStyle("-fx-border-color: none;");
            stateField.setStyle("-fx-border-color: none;");
            zipField.setStyle("-fx-border-color: none;");
            phoneField.setStyle("-fx-border-color: none;");
            genderField.setStyle("-fx-border-color: none;"); 
            countryField.setStyle("-fx-border-color: none;"); 
            emailField.setStyle("-fx-border-color: none;"); 
            //Clear the error warnings too
            requiredMessage[0]="";isNameSafe=true;isFNameSafe=true;
            requiredMessage[1]="";isLNameSafe=true;
            requiredMessage[2]="";isAddressSafe=true;
            requiredMessage[3]="";isCitySafe=true;
            requiredMessage[4]="";isStateSafe=true;
            requiredMessage[5]="";isZipSafe=true;
            requiredMessage[6]="";isCountrySafe=true;
            isPhoneSafe=true;isEmailSafe=true;isGenderSafe=true;
            errorMessageLabel.setText("");
    }
    
     //This is method used validate any errors the ocure during data input
    //This is the final barrier for any checks happening in the input. 
    private void validateFields() {
        //Set this variable indicatin data is not safe for saving.
        isDataSafe = true;
        
        errorMessageLabel.setText("");
        
        //Check whether the name already exists in the data list.
        for(Person person: data)
        {
            if(person.getFirstName().equals(firstNameField.getText()) && person.getMiddleInitial().equals(middleNameField.getText())
                    && person.getLastName().equals(lastNameField.getText()))
            {
                //Set border to red and highlight the field.
                firstNameField.setStyle("-fx-border-color: red;-fx-border-width:2");
                lastNameField.setStyle("-fx-border-color: red;-fx-border-width:2");
                middleNameField.setStyle("-fx-border-color: red;-fx-border-width:2");
                
                isDataSafe = false;
                errorMessageLabel.setText("* A person with similar name is already in your contacts");
                if(isEditing)
                {
                    //If there is editing going on we have to check if the current index 
                    //selected is equal to the data in the fields then we can treat 
                    //data as safe
                    if(data.get(tableSelectedIndex).getFirstName().equals(firstNameField.getText()) &&
                          data.get(tableSelectedIndex).getLastName().equals(lastNameField.getText()) &&
                            data.get(tableSelectedIndex).getMiddleInitial().equals(middleNameField.getText())
                            )
                    {
                        isDataSafe = true;
                        removeErrorStyling();
                    }
                }
            }
        }
        
        /* Phone Number formats: (nnn)nnn-nnnn; nnnnnnnnnn; nnn-nnn-nnnn 
        ^\\(? : May start with an option "(" . 
        (\\d{3}): Followed by 3 digits. 
        \\)? : May have an optional ")"  
        [- ]? : May have an optional "-" after the first 3 digits or after optional ) character.  
        (\\d{3}) : Followed by 3 digits.  
         [- ]? : May have another optional "-" after numeric digits. 
         (\\d{4})$ : ends with four digits. 
 
         Examples: Matches following phone numbers: 
         (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890 
        */  
        
        String phoneNumber = phoneField.getText();
        //Phone check
        if(phoneNumber == null || phoneNumber.length() == 0)
        {
            phoneField.setStyle("-fx-border-color: red;-fx-border-width:2");
            isDataSafe = false;
            errorMessageLabel.setText(errorMessageLabel.getText() + "* Empty or invalid phone.");
        }
        else if(phoneNumber.length() >0)
        {
            //Initialize reg ex for phone number. 
            String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
            CharSequence inputStr = phoneNumber;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            if(!matcher.matches()){
                phoneField.setStyle("-fx-border-color: red;-fx-border-width:2");
                isDataSafe=false; 
                errorMessageLabel.setText(errorMessageLabel.getText() + "* Empty or invalid phone. ");
                }
        }
        
        //Email check
        if(emailField.getText() == null || emailField.getText().length() == 0)
        {
            emailField.setStyle("-fx-border-color: red;-fx-border-width:2");
            isDataSafe = false;
            errorMessageLabel.setText(errorMessageLabel.getText() + "* Invalid or empty email.");
        }
        else if(emailField.getText().length() >0)
        {
            //Initialize reg ex for phone number. 
            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr = emailField.getText();
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            if(!matcher.matches()){
                emailField.setStyle("-fx-border-color: red;-fx-border-width:2");
                isDataSafe=false;
                errorMessageLabel.setText(errorMessageLabel.getText() + "* Invalid or empty email.");
                }
        }
        
        //Gender Checks
        if(genderField.getText() == null || genderField.getText().length() == 0 
                || !(genderField.getText().toLowerCase().equals("m") || genderField.getText().toLowerCase().equals("f")))
        {
            genderField.setStyle("-fx-border-color: red;-fx-border-width:2");
            isDataSafe = false;
            errorMessageLabel.setText(errorMessageLabel.getText() + "\n* Only M or F accepted in Gender field.");
        }
        
        //Name checks
        if(firstNameField.getText() == null || firstNameField.getText().length() == 0)
        {
            firstNameField.setStyle("-fx-border-color: red;-fx-border-width:2");            
            isDataSafe = false;
            requiredMessage[0] = "Firstname,";
        }
        
        if(lastNameField.getText() == null || lastNameField.getText().length() == 0)
        {
            lastNameField.setStyle("-fx-border-color: red;-fx-border-width:2");
            isDataSafe = false;
            requiredMessage [1]= "Lastname,";
        }
        
        //Address check
        if(address1Field.getText() == null || address1Field.getText().length() == 0)
        {
            address1Field.setStyle("-fx-border-color: red;-fx-border-width:2");
            isDataSafe = false;
            requiredMessage [2]= "Address1,";
        }
        
        //City check
        if(cityField.getText() == null || cityField.getText().length() == 0)
        {
            cityField.setStyle("-fx-border-color: red;-fx-border-width:2");
            isDataSafe = false;
            requiredMessage [3]= "City,";
        }
        
        //State check
        if(stateField.getText() == null || stateField.getText().length() == 0)
        {
            stateField.setStyle("-fx-border-color: red;-fx-border-width:2");
            isDataSafe = false;
            requiredMessage[4] = "State,";
        }
        
        //Zip Check
        if(zipField.getText() == null || zipField.getText().length() == 0)
        {
            zipField.setStyle("-fx-border-color: red;-fx-border-width:2");
            isDataSafe = false;
            requiredMessage[5] = "Zip,";
        }
        
        //Country check
        if(countryField.getText() == null || countryField.getText().length() == 0)
        {
            countryField.setStyle("-fx-border-color: red;-fx-border-width:2");
            isCountrySafe = false;
            requiredMessage[6]= "Country,";
        }
        
        String finalRequiredMessage = requiredMessage[0] + requiredMessage[1] +requiredMessage[2]+requiredMessage[3]
                +requiredMessage[4] +requiredMessage[5] + requiredMessage[6];
        //Display the error message
        if(!isDataSafe)
        {
            if(finalRequiredMessage.length() > 2)
                errorMessageLabel.setText(errorMessageLabel.getText() + "\n* "+ finalRequiredMessage.substring(0, finalRequiredMessage.length()-1) +"\n  cannot be empty.");
        }
        
    }
    
}
