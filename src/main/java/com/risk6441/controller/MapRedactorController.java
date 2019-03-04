package com.risk6441.controller;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Country;
import com.risk6441.entity.Map;
import com.risk6441.exception.InvalidMap;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.maputilities.CommonMapUtilities;
import com.risk6441.maputilities.MapOperations;
import com.risk6441.maputilities.MapVerifier;
import com.risk6441.maputilities.MapWriter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This class is a controller for the MapEditor layout.
 * @author Hardik
 * @author Dolly
 * @author Deep
 * @author Jemish
 * @author Raj
 *
 */
public class MapRedactorController implements Initializable{

	/**
	 * The map map object 
	 * {@link Map}
	 */
	private Map map;

	
	private File file;
	@FXML
    private Button btnDelCoun;

    @FXML
    private TextField txtContControlVal;

    @FXML
    private Label tctAuthorLabel1211;

    @FXML
    private ListView<Country> counList;

    @FXML
    private ListView<Country> adjCounList;

    @FXML
    private Label lblCounname;

    @FXML
    private Button btnUpdateCont;

    @FXML
    private Label lblCounList;

    @FXML
    private Label lblAdjCoun;

    @FXML
    private Label tctAuthorLabel2;

    @FXML
    private Label tctAuthorLabel1;

    @FXML
    private Button btnAddCoun;

    @FXML
    private ListView<Continent> contList;

    @FXML
    private Button btnDelCont;

    @FXML
    private TextField txtImage;

    @FXML
    private Label lblWrap;

    @FXML
    private ComboBox<Country> comboAdjCoun;

    @FXML
    private Button btnDltAdjCoun;

    @FXML
    private Label lblScroll;

    @FXML
    private Button btnExit;

    @FXML
    private Label lblImage;

    @FXML
    private TextField txtContName;

    @FXML
    private Label lblAuthor;

    @FXML
    private Label tctAuthorLabel121;

    @FXML
    private Label lblAdjCount;

    @FXML
    private Label lblWarn;

    @FXML
    private Label labelContDetail;

    @FXML
    private Label lblAuthor1;

    @FXML
    private TextField txtCounName;

    @FXML
    private Label lblSelectedCont;

    @FXML
    private Button btnAddCont;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtScroll;

    @FXML
    private TextField txtWarn;

    @FXML
    private Label lblContList;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtYCo;

    @FXML
    private TextField txtXCo;

    @FXML
    private Button btnUpdateCoun;

    @FXML
    private TextArea txtAreaMsg;

    @FXML
    private TextField txtWrap;

    /**
	 * This is parameterized constructor used in case of editing existing map.
	 * @param map Existing map which is passed.
	 * @param file Map file in which it'll be saved.
	 */
	public MapRedactorController(Map map, File file) {
		this.map = map;
		this.file = file;
	}
	
	
	
	/**
	 * This is a default constructor which doesn't take any parameters.
	 */
	public MapRedactorController() {
		
	}
	
	
	 /**
     * This method deletes country from the continent.
     * @param event When the user clicks the delete country button, this event is triggered and passed to the method.
     */
    @FXML
    void deleteCountry(ActionEvent event) {
    	Continent continent = contList.getSelectionModel().getSelectedItem();
		Country country = counList.getSelectionModel().getSelectedItem();
		
		if(continent!=null && continent.getCountries().size() == 1) {
			CommonMapUtilities.printMessage(txtAreaMsg, "Continent has only one country, hence it can't be removed.");
			CommonMapUtilities.alertBox("Error", "Continent has only one country, hence it can't be removed.", "Error");
			return;
		}
		//now iterate and make a hash set
		Set<Country> fromCounToBeRemoved = new HashSet<>();
		
		if(country!=null) {
			for(Country adjCoun : country.getAdjacentCountries()) {
				if(adjCoun.getAdjacentCountries().size()==1) {
					CommonMapUtilities.printMessage(txtAreaMsg, adjCoun.getName()+" has only one neighbour "+country
							+ " , hence It can't be removed. OR add another country as its neighbour and remove "+country+".");
					CommonMapUtilities.alertBox("Error", adjCoun.getName()+" has only one neighbour "+country
							+ " , hence It can't be removed. OR add another country as its neighbour and remove "+country+".", "Error");
					return;
				}else {
					fromCounToBeRemoved.add(adjCoun);
				}
			}
		}
		
		for(Country t : fromCounToBeRemoved) {
			t.getAdjacentCountries().remove(country);
		}
		
		counList.getItems().remove(country);
		continent.getCountries().remove(country);
		CommonMapUtilities.disableControls(btnDelCoun,btnUpdateCoun);
		CommonMapUtilities.clearTextArea(txtCounName,txtXCo,txtYCo);
		CommonMapUtilities.printMessage(txtAreaMsg, "Removed Successfully : Country :"+country);
    
    }
    
    
    
    /**
     * This method adds a Country to the continent.
     * @param event When the user clicks the add country button, this event is triggered and passed to the method.
     */
    @FXML
    void addCountry(ActionEvent event) {
    	if(StringUtils.isEmpty(txtCounName.getText())) {
    		CommonMapUtilities.alertBox("Error", "Country Name Can't be empty.", "Map is not valid.");
    		return;
    	}
    	
    	Country adjCoun = comboAdjCoun.getSelectionModel().getSelectedItem();
    	Continent continent = contList.getSelectionModel().getSelectedItem();
    	
    	Country country = null;
    	try {
    		country = MapOperations.addCountry(map, txtCounName.getText(), txtXCo.getText(), txtYCo.getText(),
    				adjCoun, continent);
    	}catch (InvalidMap e) {
    		CommonMapUtilities.alertBox("Error", e.getMessage(), "Map is not valid.");
    		return;
		}
    	
    	//add countries to continent
    	continent = MapOperations.mapCountryToContinent(continent, country);
    	comboAdjCoun.getItems().add(country);
    	counList.getItems().add(country);
    	CommonMapUtilities.clearTextArea(txtCounName,txtXCo,txtYCo);
    }

    
    /**
     * This method updates countries.
     * @param event When the user clicks the update country button, this event is triggered and passed to the method.
     * @throws InvalidMap InvalidMapException if any error occurs
     */
    @FXML
    void updateCountry(ActionEvent event) {
    	if(StringUtils.isEmpty(txtCounName.getText())) {
    		CommonMapUtilities.alertBox("Error", "Country Name Can't be empty.", "Error");
    		return;
    	}
    	
    	Country country = counList.getSelectionModel().getSelectedItem();
    	Country adjCoun = comboAdjCoun.getSelectionModel().getSelectedItem();
    	
    	if(country.equals(adjCoun)) {
    		CommonMapUtilities.printMessage(txtAreaMsg, "Country can'be its own neighbour.");
    		return;
    	}
    	
    	try {
    		country = MapOperations.updateCountry(country, map, txtCounName.getText(),txtXCo.getText(), txtYCo.getText(), adjCoun);
		}catch(InvalidMap e) {
			CommonMapUtilities.alertBox("Error", e.getMessage(), "Map is not valid.");
			return;
		}
    	counList.refresh();
    	CommonMapUtilities.enableControls(btnAddCoun,txtCounName);
    	CommonMapUtilities.clearTextArea(txtCounName,txtXCo,txtYCo);
    	showAdjCountryOfCounInList(country);
    
    }
    
    
    /**
	 * This method is used to obtain the adjacent countries of a given country
	 * @param coun Requires country object whose adjacent countries have to be displayed
	 */
	public void showAdjCountryOfCounInList(Country coun) {
		adjCounList.getItems().clear();
		try {
			for (Country t : coun.getAdjacentCountries()) {
				adjCounList.getItems().add(t);
			}
		}catch (Exception e) {
		}
	}
    
	
    /**
     * This method deletes adjacent countries
     * @param event When the user clicks the delete adjacent country button, this event is triggered and passed to the method.
     */
    @FXML
    void deleteAdjCountry(ActionEvent event) {
    	Country coun = counList.getSelectionModel().getSelectedItem();
    	Country adjCoun = adjCounList.getSelectionModel().getSelectedItem();
    	
    	if(coun!=null && adjCoun!=null) {
    		if(coun.getAdjacentCountries().size() <= 1) {
    			CommonMapUtilities.printMessage(txtAreaMsg, "There should be at least one adjacent country.");
    			CommonMapUtilities.alertBox("Error", "There should be at least one adjacent country.", "Error");
    		}else {
    			//remove reference from both...adjacency relationship is mutual 
    			coun.getAdjacentCountries().remove(adjCoun);
    			adjCoun.getAdjacentCountries().remove(coun); 
    			
    			adjCounList.getItems().remove(adjCoun);
    			CommonMapUtilities.printMessage(txtAreaMsg, "Removed Successfully : Adjacent Country "+adjCoun.getName());
    		}
    	}
    }
    
    /**
     * This method validates the map. If it is valid then save it else show error.
     * @param event When the user clicks the save map button, this event is triggered and passed to the method.
     */
    @FXML
    void saveMap(ActionEvent event) {
    	map.getMapData().put("image",txtImage.getText());
    	map.getMapData().put("author",txtAuthor.getText());
    	map.getMapData().put("scroll",txtScroll.getText());
    	map.getMapData().put("wrap",txtWrap.getText());
    	map.getMapData().put("warn",txtWarn.getText());
    	System.out.println("Map Save Clicked");
    	try {
    		MapVerifier.verifyMap(map);
        }
    	catch(InvalidMap e)
    	{
    		e.printStackTrace();
    		CommonMapUtilities.alertBox("Error", e.getMessage(), "Map is not valid.");
    		return;
    	}

    	
    	MapWriter write = new MapWriter();
    	
    	if(file==null) {
    		file = CommonMapUtilities.saveMapFile();
    	}
    	write.writeMapFile(map, file);
    
    }

    /**
     * This method exits the program when the button is clicked.
     * @param event When the user clicks the exit button, this event is triggered and passed to the method.
     */
    @FXML
    void exitBtnClick(ActionEvent event) {
    	Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
    
    }
    
    /**
	 * This method adds the continent to the map
	 * @param event event object containing details regarding origin of the event
	 */
    @FXML
    void addContinent(ActionEvent event) {
    	if(StringUtils.isEmpty(txtContName.getText())) {
    		CommonMapUtilities.alertBox("Error", "Continent Name Can't be empty.", "Map is not valid.");
    		return;
    	}
		
		Continent cnt;
		try {
			cnt = MapOperations.addContinent(map, txtContName.getText(), txtContControlVal.getText());
		}catch(InvalidMap e) {
			CommonMapUtilities.alertBox("Error", e.getMessage(), "Error");
			return;
		}
		
		if (contList == null) {
			contList = new ListView<Continent>();
		}
		
		map.getContinents().add(cnt);
		contList.getItems().add(cnt);
		
		//clear the textboxes
		CommonMapUtilities.clearTextArea(txtContName,txtContControlVal);
    }
    
    /**
     * This method deletes continent from the map.
     * @param event When the user clicks the delete continent button, this event is triggered and passed to the method.
     */
    @FXML
    void deleteContinent(ActionEvent event) {
    	Continent continent = contList.getSelectionModel().getSelectedItem();
    	
    	if(continent!=null && continent.getCountries().size()>0) {
    		CommonMapUtilities.printMessage(txtAreaMsg, "Delete its countries first "+continent.getCountries());
    	}else {
    		map.getContinents().remove(continent);
    		contList.getItems().remove(continent);
    		CommonMapUtilities.printMessage(txtAreaMsg, "Removed Successfully : Continent :"+continent);
    		CommonMapUtilities.enableControls(txtContName,btnAddCont);
        	CommonMapUtilities.clearTextArea(txtContName,txtContControlVal);
    	}

    }
    

    /**
     * This method updates continent details.
     * @param event When the user clicks the update continent button, this event is triggered and passed to the method.
     * @throws InvalidMap InvalidMapException if any error occurs
     */
    @FXML
    void updateContinent(ActionEvent event) {
    	if(StringUtils.isEmpty(txtContName.getText())) {
    		CommonMapUtilities.alertBox("Error", "Continent Name Can't be empty.", "Error");
    		return;
    	}
    	
    	try {
    		MapOperations.updateContinent(contList.getSelectionModel().getSelectedItem(), map , txtContName.getText(),txtContControlVal.getText());
		}catch(InvalidMap e) {
			CommonMapUtilities.alertBox("Error", e.getMessage(), "Map is not valid.");
			return;
		}
    	lblSelectedCont.setText(contList.getSelectionModel().getSelectedItem().getName());
    	contList.refresh();
    	CommonMapUtilities.enableControls(btnAddCont,txtContName);
    	CommonMapUtilities.clearTextArea(txtContName,txtContControlVal);
    
    }
    /*
	 * This method intializes the Map Editor with default values.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Intializer Called");
	
		CommonMapUtilities.disableControls(btnAddCoun,btnUpdateCont,btnDelCont,btnUpdateCoun,btnDelCoun,txtCounName,txtXCo,txtYCo,comboAdjCoun);
		comboAdjCoun.getItems().add(null);
		CommonMapUtilities.disableControls(btnDltAdjCoun);
		
		
		if (this.map == null) {
			map = new Map();
			
			txtAuthor.setText("author");
			txtImage.setText("image");
			txtScroll.setText("scroll");
			txtWarn.setText("warn");
			txtWrap.setText("wrap");
			
		} else {
			//for loading existing map and editing
			parseMapData();
		}			
		
		contList.setCellFactory(param -> new ListCell<Continent>() {
		    @Override
		    protected void updateItem(Continent item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || item == null || item.getName() == null) {
		            setText(null);
		        } else {
		            setText(item.getName());
		        }
		    }
		});
		
		counList.setCellFactory(param -> new ListCell<Country>() {
		    @Override
		    protected void updateItem(Country item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || item == null || item.getName() == null) {
		            setText(null);
		        } else {
		            setText(item.getName());
		        }
		    }
		});
		
		adjCounList.setCellFactory(param -> new ListCell<Country>() {
		    @Override
		    protected void updateItem(Country item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || item == null || item.getName() == null) {
		            setText(null);
		        } else {
		            setText(item.getName());
		        }
		    }
		});
		
		comboAdjCoun.setCellFactory(param -> new ListCell<Country>() {
		    @Override
		    protected void updateItem(Country item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || item == null || item.getName() == null) {
		            setText(null);
		        } else {
		            setText(item.getName());
		        }
		    }
		});
		
		
		contList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				onClickContList();
			}
		});
		
		
		counList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				onClickCounList();
			}
		});
		
		adjCounList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event) {
				CommonMapUtilities.enableControls(btnDltAdjCoun);
			}; 
		});
		
	}
	
	/**
	 * This method parses map data and stores in the map file
	 */
	public void parseMapData() {
	
		System.out.println("parseMapData Called");
		txtAuthor.setText(map.getMapData().get("author"));
		txtImage.setText(map.getMapData().get("image"));
		txtScroll.setText(map.getMapData().get("scroll"));
		txtWarn.setText(map.getMapData().get("warn"));
		txtWrap.setText(map.getMapData().get("wrap"));
		
		for(Continent continent : map.getContinents()) {
			contList.getItems().add(continent);
			for(Country country : continent.getCountries()) {
				comboAdjCoun.getItems().add(country);
			}
		}
			
	}
	
	/**
	 * This method is to perform operations on the selected continent like update, delete etc
	 */
	public void onClickContList() {
		Continent cnt = contList.getSelectionModel().getSelectedItem();
		txtContName.setText(cnt.getName());
		txtContControlVal.setText(String.valueOf(cnt.getValue()));
		lblSelectedCont.setText(cnt.getName());
		
		CommonMapUtilities.disableControls(btnAddCont,btnDltAdjCoun, txtXCo, txtYCo, comboAdjCoun,btnAddCoun, btnUpdateCoun, btnDelCoun);
		CommonMapUtilities.clearTextArea(txtCounName, txtXCo, txtYCo);
		CommonMapUtilities.enableControls(txtCounName,btnDelCont,btnUpdateCont,btnAddCoun,txtXCo, txtYCo, comboAdjCoun);
		
		adjCounList.getItems().clear();
		//show countries in the country list
		showCountryOfContInList(contList.getSelectionModel().getSelectedItem());
		
		refreshComboBox();
	}
	
	/**
	 * This method refreshes the combobox
	 */
	private void refreshComboBox() {
	    	comboAdjCoun.getItems().clear();
	    	comboAdjCoun.getItems().add(null);
	    	List<Country> countryList = GameUtilities.getCountryList(map);
	    	for(int i=0; i<countryList.size(); i++)
	    	{
	    		comboAdjCoun.getItems().add(countryList.get(i));          
	    	}  
	}


	/**
	 * This displays the countries in the selected Continent.
	 * @param continent Continent whose countries are to be printed.
	 */
	public void showCountryOfContInList(Continent continent) {
		counList.getItems().clear();
		if (continent != null && continent.getCountries() != null)  {
			for (Country t : continent.getCountries()) {
				counList.getItems().add(t);
			}
		}
	}
	
	/**
	 * This method is to perform operations on the selected Country like update, delete etc
	 */
	public void onClickCounList() {
		Country coun = counList.getSelectionModel().getSelectedItem();
		txtCounName.setText(coun.getName());
		txtXCo.setText(String.valueOf(coun.getxCoordinate()));
		txtYCo.setText(String.valueOf(coun.getyCoordinate()));
		
		CommonMapUtilities.disableControls(btnAddCoun,btnDltAdjCoun);
		CommonMapUtilities.clearTextArea(txtContName, txtContControlVal);
		CommonMapUtilities.enableControls(txtContName,btnAddCont, btnUpdateCoun, btnDelCoun);
		
		//show countries in the country list
		showAdjCountryOfCounInList(counList.getSelectionModel().getSelectedItem());
	}
	

}
