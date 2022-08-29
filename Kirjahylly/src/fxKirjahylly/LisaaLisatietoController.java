package fxKirjahylly;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Kirjahylly.Lisatieto;

/**
 * Lisätietojen lisääminen jne
 * @author Niko
 * @version 10.8.2022
 */
public class LisaaLisatietoController implements ModalControllerInterface<Lisatieto>, Initializable {
    
    @FXML private TextField nimi;
    @FXML private TextArea sisalto;
    private Lisatieto lisatietoKohdalla;

    
    @FXML private void handlePeru() {
        lisatietoKohdalla = null;
        ModalController.closeStage(nimi);
    }

    /**
     * Asettaa uudet tekstit kommentin kenttiin ja sulkee ikkunan
     */
    @FXML private void handleTallenna() {
        lisatietoKohdalla.setNimi(nimi.getText());       
        lisatietoKohdalla.setSisalto(sisalto.getText());
        ModalController.closeStage(nimi);
    }

    
    @Override
    public Lisatieto getResult() {
        return lisatietoKohdalla;
    }

    
    @Override
    public void handleShown() {
        nimi.requestFocus();
        
    }
    

    @Override
    public void setDefault(Lisatieto oletus) {
        lisatietoKohdalla = oletus;
        naytaLisatieto(lisatietoKohdalla);
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }
    
     
    
    /**
     * Näyttää lisätiedon 
     * @param lisatieto muokattava
     */
    public void naytaLisatieto(Lisatieto lisatieto) {
        if (lisatieto == null) return;
        nimi.setText(lisatieto.getNimi());
        sisalto.setText(lisatieto.getSisalto());
        }
    
    
    /**
     * Luodaan kommentin tietojen kysymys tai muokkausdialogi ja palautetaan sama tietue.
     * @param modalitystage stage jolle ollaan modaalisia null == sovellukselle
     * @param oletusTieto kommentti jonka tietoja käsitellään
     * @return null jos painetaan peru muuten tietue
     */
    public static Lisatieto kysyLisatieto(Stage modalitystage, Lisatieto oletusTieto) {
        return ModalController.showModal(KirjahyllyGUIController.class.getResource("LisaaLisatietoDialog.fxml"), "Lisatieto", modalitystage, oletusTieto);
    }

}