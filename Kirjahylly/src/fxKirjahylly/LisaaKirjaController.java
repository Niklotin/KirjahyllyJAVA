package fxKirjahylly;

import java.net.URL;
import java.util.ResourceBundle;

import Kirjahylly.Kirja;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Kirjan lisäys
 * @author Niko
 * @version 9.8.2022
 *
 */

public class LisaaKirjaController implements ModalControllerInterface<Kirja>, Initializable {
    
    private Kirja kirjaKohdalla;
    @FXML private TextField labelVirhe;
    @FXML private TextField editNimi;
    @FXML private TextField editKirjailija;
    @FXML private TextField editJulkaisija;
    @FXML private TextField editKieli;
    @FXML private TextField editTyyli;
    @FXML private TextField editGenre;
    @FXML private TextField editSivut;
    @FXML private TextField editJulkaisu;
    private TextField[] muutokset;
    
    @FXML private void handlePeru() {
        kirjaKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    
    @FXML private void handleTallenna() {
        if(kirjaKohdalla != null && kirjaKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Tyhjä kenttä, aseta nimi!");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();        
    }
    
    /**
     * Alustaa TextField taulukon ja kuuntelee muutoksia
     */
    private void alusta() {
        muutokset = new TextField[] {editNimi, editKirjailija, editJulkaisija, editKieli,
                editTyyli, editGenre, editSivut, editJulkaisu};
        
        int i = 0;
        for(TextField muutos : muutokset) {
            final int x = i++;
            muutos.setOnKeyReleased(e -> teeKirjaanMuutos(x, (TextField) (e.getSource())));
        }
    }
    /**
     * Käsittelee kenttiin tulleet muutokset ja ilmoittaa virheestä, jos sellaisia sattuu tekemään
     * @param x kentän numero
     * @param muutos muutos kentässä
     */
    private void teeKirjaanMuutos(int x, TextField muutos) {
        if (kirjaKohdalla == null) return;
        
        String s = muutos.getText(); 
        String virhe = null;
        
        virhe = kirjaKohdalla.setKentat(x, s);
        
        if(virhe==null) {
            Dialogs.setToolTipText(muutos, "");
            muutos.getStyleClass().removeAll("Error");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(muutos, virhe);
            muutos.getStyleClass().add("Error");
            naytaVirhe(virhe);
        }
    }
    
    
    /**
     * Kirjantietojen muokkausdialhoi
     * @param modalitystage stage jolle modaalisia
     * @param oletusTieto kirja jonka tietoja muutetaan
     * @return null jos painetaan peruuta ja muuten palautetaan tietue
     */
    public static Kirja kysyKirja(Stage modalitystage, Kirja oletusTieto) {
        return ModalController.showModal(
                KirjahyllyGUIController.class
                        .getResource("LisaaKirjaDialog.fxml"),
                "Muokkaa kirjaa", modalitystage, oletusTieto);
    }
    /**
     * Näyttää kirjan
     * @param muutokset tehdyt muutokset
     * @param kirja kirja
     */
    public static void naytaKirja(TextField[] muutokset, Kirja kirja) {
        if (kirja == null)
            return;
        for (int i = 0; i < muutokset.length; i++) {
            muutokset[i].setText(kirja.getKentat(i));
        }
    }
    
    /**
     * Näyttää kirjan tiedot editkentissä
     * @param kirja kirja jonka tiedot näytetään
     */
    private void naytaKirja(Kirja kirja) {
        naytaKirja(muutokset, kirja);
    }
    
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }



    @Override
    public Kirja getResult() {
        return kirjaKohdalla;
    }


    @Override
    public void handleShown() {
        editNimi.requestFocus();        
    }


    @Override
    public void setDefault(Kirja oletus) {
        kirjaKohdalla = oletus;
        naytaKirja(kirjaKohdalla);
    }
    


}
