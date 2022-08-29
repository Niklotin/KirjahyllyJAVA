package fxKirjahylly;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * @author Niko
 * Aloitus sivun controller
 * @version 9.8.2022
 *
 */
public class AloitusController implements ModalControllerInterface<String> {
    
    @FXML private TextField kirjahyllynNimi;
    private String vastaus = null;
    
    
    @FXML private void handleCancel() {
        vastaus = null;
        ModalController.closeStage(kirjahyllynNimi);
    }
    

    @FXML private void handleOK() {
        vastaus = kirjahyllynNimi.getText();
        ModalController.closeStage(kirjahyllynNimi);
    }
    

    @Override
    public String getResult() {
        return vastaus;
    }
    

    @Override
    public void handleShown() {
        kirjahyllynNimi.requestFocus();
        
    }
    

    @Override
    public void setDefault(String oletus) {
        kirjahyllynNimi.setText(oletus);
    }        
    
    
    /**
     * Kysymysikkuna kirjahyllylle
     * @param modalityStage modaalisuus
     * @param oletus oletusnimi hyllylle
     * @return null cancelilla
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(AloitusController.class.getResource("AloitusView.fxml"), "Avaa kirjahylly", modalityStage, oletus); 
    }
}
