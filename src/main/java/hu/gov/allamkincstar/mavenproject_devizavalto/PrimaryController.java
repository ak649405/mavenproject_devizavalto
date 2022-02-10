package hu.gov.allamkincstar.mavenproject_devizavalto;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {
    
    private float fizetendo;
    private java.util.List<String> arfolyamok1 = new ArrayList<>();
  
    @FXML
    private TextField valtando1;
    @FXML
    private ComboBox<String> combobox1;
    @FXML
    private Button button1;
    @FXML
    private TextField fizetendo1;
    
    @FXML
    private TextField tf_xmlvalaszto;
    @FXML
    private AnchorPane ap_xmlvalaszto;
    @FXML
    private Button bt_xmlvalaszto;
    @FXML
    private Button bt_web;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //váltó gomb tilt
        button1.setDisable(true);
        
        bt_web.setOnAction( new EventHandler <ActionEvent>() {
        @Override           
        public void handle(ActionEvent e){
            
            arfolyamok1 = DevizaValto.webBeolvas();
            for (String a : arfolyamok1 ) {
                combobox1.getItems().add(a);
                System.out.println(a) ;           
            }
            combobox1.setValue("EUR - 1.00");
            button1.setDisable(false);
        }
        });

        bt_xmlvalaszto.setOnAction( new EventHandler <ActionEvent>() {
        @Override           
        public void handle(ActionEvent e){
            
            //System.out.println( "Beviteli mező üres!" );
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("XML file");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML files", "*.xmls", "*.xml"),
                new FileChooser.ExtensionFilter("All files", "*.*")
            );

            String initialDirPath = System.getProperty("user.dir");
            //String initialDirPath = System.getProperty("c:\\users\\antalk\\NetBeansProjects\\mavenproject_file_letolt_valaszt\\");
            File xmlFile = new File(initialDirPath);
            fileChooser.setInitialDirectory(xmlFile);
            Stage stage = (Stage) ap_xmlvalaszto.getScene().getWindow(); 
            xmlFile = fileChooser.showOpenDialog(stage);


            if ( xmlFile != null ){ 
                tf_xmlvalaszto.setText( " "+xmlFile ) ;
                
                arfolyamok1 = DevizaValto.xmlBeolvas();
                for (String a : arfolyamok1 ) {
                    combobox1.getItems().add(a);
                    System.out.println(a) ;           
                }
                 // Set the default value.
                combobox1.setValue("EUR - 1.00");
                button1.setDisable(false);
            }

            }

        });
          
        

        button1.setOnAction( new EventHandler <ActionEvent>() {
        @Override           
        public void handle(ActionEvent e){
                System.out.println("Árfolyam_button: "+combobox1.getValue());
                // fizetendo = DevizaValto.valtas( 10 ,combobox1.getValue() );
                if      ( combobox1.getValue() == null ){
                 System.out.println( "Combobox mező üres!" );
                 // Set the default value.
                 combobox1.setValue("EUR - 1.00");
                }

                if      ( valtando1.getText().isEmpty() ){
                 System.out.println( "Beviteli mező üres!" );
                 fizetendo1.clear();
                }
                else {
                  fizetendo = DevizaValto.valtas( Float.valueOf(valtando1.getText()) ,combobox1.getValue() );
                  fizetendo1.setText(String.valueOf( Math.round(fizetendo) ) );
                }
               
           }
        });

        combobox1.setOnAction( new EventHandler <ActionEvent>() {
        @Override
        public void handle(ActionEvent e){
                  System.out.println("Árfolyam: "+combobox1.getValue());
                  fizetendo1.clear();
           }
        });
                 

        }

}
