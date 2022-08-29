package fxKirjahylly;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Kirjahylly.Kirja;
import Kirjahylly.Kirjahylly;
import Kirjahylly.Lisatieto;
import Kirjahylly.SailoException;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.TextAreaOutputStream;



/**
 * Kirjahyllyn GUI Controller
 * @author Niko
 * @version 9.8.2022
 *
 */
public class KirjahyllyGUIController implements Initializable {
    
    @FXML private ListChooser<Kirja> chooserKirjat;
    @FXML private ListChooser<Lisatieto> chooserLisatieto;
    @FXML private ComboBoxChooser<String> hakuKentat;
    @FXML private TextField hakuehto;
    @FXML private TextField editNimi;
    @FXML private TextField editKirjailija;
    @FXML private TextField editJulkaisija;
    @FXML private TextField editKieli;
    @FXML private TextField editTyyli;
    @FXML private TextField editGenre;
    @FXML private TextField editSivut;
    @FXML private TextField editJulkaisu;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    @FXML void handleHaku() {
        haku(0);
    }
    @FXML private void handleAvaa() {
        avaa();
    }

    @FXML private void handleLisaaKirja() {
        uusiKirja();
    }

    @FXML private void handleLisaaLisatieto() {
        uusiLisatieto();
    }   

    @FXML private void handleLopeta() {
        Platform.exit();
    }

    @FXML private void handleMuokkaaKirja() {
        muokkaaKirja();
    }

    @FXML private void handleMuokkaaLisatieto() {
        muokkaaLisatieto();
    }

    @FXML private void handlePoistaKirja() {
        poistaKirja();
    }

    @FXML private void handlePoistaLisatieto() {
        poistaLisatieto();
    }

    @FXML private void handleTallenna() {
        tallenna();
    }


    @FXML private void handleTulosta() {
       TulostaController tulostusControl = TulostaController.tulosta(null);
       tulostaKirjat(tulostusControl.getTextArea());
    }
    
    
    // ------------ Handlerit ylös, loput alas :=)
    
    private Kirjahylly kirjahylly;
    private Kirja kirjaKohdalla;
    private Lisatieto lisatietoKohdalla;
    private String kirjahyllynNimi = "Itäsiiven kirjahylly";
    private TextField[] muutokset;
    
    /**
     * Alustaa valitsimet, kuunetlijat ja tekstikentät
     */
    private void alusta() {
        chooserKirjat.clear();
        chooserKirjat.addSelectionListener(e -> naytaKirja());
        chooserLisatieto.addSelectionListener(e -> lisatietoKohdalla());
        chooserKirjat.setOnMouseClicked(e -> {if (e.getClickCount() > 1) muokkaaKirja(); });
        chooserLisatieto.setOnMouseClicked(e -> {if (e.getClickCount() > 1) muokkaaLisatieto(); }); 
        muutokset = new TextField[] {editNimi, editKirjailija, editJulkaisija, editKieli,
                                 editTyyli, editGenre, editSivut, editJulkaisu};
    }
    
    /**
     * Hakee kirjat
     * @param kirjanID kirjaidid
     */
    
    private void haku(int kirjanID) {
        int kirjaid = kirjanID;
        int index = 0;
        ArrayList<Kirja> kirjat;
        if(kirjaid == 0) {
            Kirja kohdalla = kirjaKohdalla;
            if(kohdalla != null) kirjaid = kohdalla.getID();
        }
        
        int kentanNumero = hakuKentat.getSelectionModel().getSelectedIndex();
        String haku = hakuehto.getText();
        
        if (haku.indexOf('*') < 0) haku = "*" + haku + "*";
        chooserKirjat.clear();
        try {
            kirjat = kirjahylly.haku(haku, kentanNumero);
            for(int i = 0; i < kirjat.size(); i++) {
                Kirja kirja = kirjat.get(i);
                if(kirja.getID() == kirjaid) index = i;
                chooserKirjat.add(kirja.getNimi(), kirja);
            }
        } catch (SailoException se) {
            Dialogs.showMessageDialog("Kirjojen haku ongelma!!!");
        }
        chooserKirjat.setSelectedIndex(index);
    }
    
    /**
     * Asettaa valitun lisätiedon kohdalla olevaksi lisäteidksi
     */
    private void lisatietoKohdalla() {
        lisatietoKohdalla = chooserLisatieto.getSelectedObject();
    }
    
    /**
     * Näyttää valitun kirjan
     */
    private void naytaKirja() {
        kirjaKohdalla = chooserKirjat.getSelectedObject();
        if(kirjaKohdalla == null) return;
        
        LisaaKirjaController.naytaKirja(muutokset, kirjaKohdalla);
        naytaLisatiedot(kirjaKohdalla);
    }
    
    /**
     * Näyttää kirjan lisätiedot
     * @param kirja kirja
     */
    private void naytaLisatiedot (Kirja kirja) {
        chooserLisatieto.clear();
        if(kirja == null) return;
        List<Lisatieto> lisatiedot = kirjahylly.annaLisatiedot(kirja);
        if(lisatiedot.size() == 0) return;
        for(Lisatieto tieto : lisatiedot) {
            chooserLisatieto.add(tieto.getNimi(), tieto);
        }
        
    }
    
    /**
     * Avaa muokkausdialogin kirjalle ja hoitaa muokkauksen
     */
    
    private void muokkaaKirja() {
        if (kirjaKohdalla == null) return;
        Kirja kirja;
        
        try {
            kirja = kirjaKohdalla.clone();
            kirja = LisaaKirjaController.kysyKirja(null, kirja);
            if (kirja == null) return;
            kirjahylly.korvaaTaiLisaa(kirja);
            haku(kirja.getID());
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage() + " kloonaus ei onnistu");
        } catch (SailoException se) {
            System.err.println(se.getMessage() + " Ongelmia tietorakenteessa");
        }
    }
    
    private void muokkaaLisatieto() {
        if(lisatietoKohdalla == null) return;
        Lisatieto lisatieto;
        
        try {
            lisatieto = lisatietoKohdalla.clone();
            lisatieto = LisaaLisatietoController.kysyLisatieto(null,lisatieto);
            if(lisatieto == null) return;
            kirjahylly.korvaaTaiLisaa(lisatieto);
            naytaLisatiedot(kirjaKohdalla);
        } catch(CloneNotSupportedException ce) {
            System.err.println(ce.getMessage() + " kloonaus ongelma");
        } catch (SailoException se) {
            System.err.println(se.getMessage() + " tietorakenne ongelma");
        }
    }
    
    /**
     * Poistaa kirjan
     */
    private void poistaKirja() {
        Kirja kirja = kirjaKohdalla;
        if (kirja == null) return;
        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko Kirja: " + kirja.getNimi(), "Kyllä", "Ei")) return;
        kirjahylly.poistaKirja(kirja);
        int index = chooserKirjat.getSelectedIndex();
        haku(0);
        chooserKirjat.setSelectedIndex(index);
        if (chooserKirjat.getSelectedIndex() < 0) {
            for (TextField field : muutokset) {
                field.clear();
            }
        }
    }
    
    /**
     * Poistaa lisätiedon
     */
    private void poistaLisatieto() {
        if (lisatietoKohdalla == null) return;
        kirjahylly.poistaLisatieto(lisatietoKohdalla);
        naytaLisatiedot(kirjaKohdalla);
    }
    
    /**
     * Lukee valitun nimen tiedoston ja alustaa kirjaston tiedot
     * @param nimi tiedoston nimi
     * @return true jos kaikki menee mallikkaasti ja false jos jotain menee pieleen
     */
    public Boolean lueTiedosto(String nimi) {
        File tiedosto = new File(nimi + "/kirjat.dat");
        if (!tiedosto.exists()) {
            if (!Dialogs.showQuestionDialog("Ei löydy", "Luodaanko uusi kirjasto: " + nimi, "Kyllä", "Ei")) {
                return false;
            }
        }
        
        kirjahyllynNimi = nimi;
        kirjahylly.luoTiedostot(kirjahyllynNimi);
        try {
            kirjahylly.lueTiedosto(nimi);
            haku(0);
            return true;
        } catch (SailoException e) {
            haku(0);
            String virhe = e.getMessage();
            if (virhe != null)
                Dialogs.showMessageDialog(virhe);
            return false;
        }
    }
    
    private String tallenna() {
        try {
            kirjahylly.tallenna();
            return null;
        } catch (SailoException se) {
            Dialogs.showMessageDialog("Tallennusvirhe! " + se.getMessage());
            return se.getMessage();
        }
    }
    
    /**
     * Lisää kirjahyllyyn kirjan
     */
    private void uusiKirja() {
        try {
            Kirja uusi = new Kirja();
            uusi = LisaaKirjaController.kysyKirja(null, uusi);
            if (uusi == null) return;
            uusi.kirjaaKirja();
            kirjahylly.lisaa(uusi);
            haku(uusi.getID());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Kirjan luominen ei onnistunut! " + e.getMessage());
        }
    }
    /**
     * Lisää uuden lisätiedon kirjalle
     */
    private void uusiLisatieto() {
        if (kirjaKohdalla == null) return;
        Lisatieto uusi = new Lisatieto();
        uusi = LisaaLisatietoController.kysyLisatieto(null, uusi);
        if(uusi == null) return;
        uusi.kirjaa();
        uusi.setKirjanID(kirjaKohdalla.getID());
        kirjahylly.lisaa(uusi);
        naytaLisatiedot(kirjaKohdalla);
    }
    
    /**
     * Asettaa kirjahyllyn
     * @param kirjahylly hylly jota käytetään
     */
    public void setKirjahylly (Kirjahylly kirjahylly) {
        this.kirjahylly = kirjahylly;
    }
    
    /**
     * Jos kirjasto ei löydy, tyhjennetään kentät ja tehdään uusi hylly
     * @return true jos onnistuu ja false jos ei onnistu
     */
    public boolean avaa() {
        chooserKirjat.clear();
        chooserLisatieto.clear();
        for(TextField kentta : muutokset) {
            kentta.clear();
        }
        String uusinimi = AloitusController.kysyNimi(null, kirjahyllynNimi);
        if (uusinimi == null) return false;
        return lueTiedosto(uusinimi);
    }
    
    /**
     * Tulostaa kirjat valitsimesta
     * @param tulostus alue jonne tulostetaan
     */
    private void tulostaKirjat(TextArea tulostus) {
        try (PrintStream ps = TextAreaOutputStream.getTextPrintStream(tulostus)) {
            ps.println("Tulostetaan kirjat");
            for(Kirja kirja : chooserKirjat.getObjects()) {
                tulosta(ps, kirja);
                ps.println(" ");
            }
        }
    }
    
    /**
     *  Tulostaa yhden kirjan tiedot
     *  @param ps tietovirta johon tulostetaan
     *  @param kirja jonka tiedot tulostetaan
     */
    private void tulosta(PrintStream ps, Kirja kirja) {
        ps.println("///////////////////////////////////////////////////");
        kirja.tulosta(ps);

        List<Lisatieto> kirjanLisatiedot = kirjahylly.annaLisatiedot(kirja);
        for (Lisatieto tieto : kirjanLisatiedot) {
            tieto.tulosta(ps);
        }
    }
	
}
