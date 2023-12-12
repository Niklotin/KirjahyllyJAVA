package Kirjahylly;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hoitaa lisätiedot ja kirjat luokat yhteen sulaan sopuun. Välittää tiedot ja yhteistyön.
 * Lukee ja kirjoittaa tiedostoon.
 * @author Niko
 * @version 10.8.2022
 * 
 */

public class Kirjahylly {
    
    private Kirjat kirjat = new Kirjat();
    private Lisatiedot lisatiedot = new Lisatiedot();
    
    /**
     * Maini
     */
    public static void main() {
        // 

    }
    /**
     * Lukkeepi tiedostot
     * @param nimi jonka avulla luetaan
     * @throws SailoException Jos ei onnistu lukeminen
     */
    
    public void lueTiedosto(String nimi) throws SailoException {
        kirjat = new Kirjat();
        lisatiedot = new Lisatiedot();
        
        setTiedosto(nimi);
        kirjat.lueTiedostosta();
        lisatiedot.lueTiedostosta();     
    }
    
    /**
     * Tekee uudet lisätiedot ja kirjat tiedostot uuteen hakemistoon
     * @param nimi hakemiston nimi
     */
    public void luoTiedostot(String nimi) {
        File kirjatTiedosto = new File (nimi + "/kirjat.dat");
        File lisatiedotTiedosto = new File (nimi + "/lisatiedot.dat");
        
        try {
            kirjatTiedosto.getParentFile().mkdirs();
            kirjatTiedosto.createNewFile();
            lisatiedotTiedosto.createNewFile();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Tallentaa (tai ainakin yrittää) kirjahyllyn tiedostoon
     * @throws SailoException Jos ei onnistu tallentaminen
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            kirjat.tallenna();
        }catch (SailoException ex) {
            virhe = ex.getMessage();
        }
        
        try {
            lisatiedot.tallenna();
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        if(!"".equals(virhe)) throw new SailoException(virhe);
    }
    
    /**
     * Asettaa teidostojen nimet
     * @param nimi nimi :=)
     */
    public void setTiedosto (String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemisto = "";
        if(!nimi.isEmpty()) hakemisto = nimi + "/";
        kirjat.setTiedosto(hakemisto + "kirjat.dat");
        lisatiedot.setTiedostonNimi(hakemisto + "lisatiedot.dat");
    }
    
    /**
     * Lisää uuden lisätiedon
     * @param tieto lisättävä tieto :=)
     */
    public void lisaa(Lisatieto tieto) {
        lisatiedot.lisaa(tieto);
    }
    
    /**
     * Lisää kirjan kirjataulukkoon
     * @param kirja lisättävä
     * @throws SailoException jos täynnä
     */
    public void lisaa(Kirja kirja) throws SailoException {
        kirjat.lisaa(kirja);
    }
    
    /**
     * Lisää tai korvaa kirjan
     * @param kirja lisättävänä
     * @throws SailoException Jos sattuu tietorakenne olemaan tuppasen täynnä.
     */
    public void korvaaTaiLisaa(Kirja kirja) throws SailoException {
        kirjat.korvaaTaiLisaa(kirja);
    }
    
     /**
      * Lisää tai korvaa lisätiedon
      * @param lisatieto lisättävä
      * @throws SailoException Jos sattu tieorakenen olemaan täynnä
      */
    public void korvaaTaiLisaa(Lisatieto lisatieto) throws SailoException {
        lisatiedot.korvaaTaiLisaa(lisatieto);
    }
    /**
     * Palauttaa indeksissä i olevan viitteen
     * @param i alkion paikka taulukossa
     * @return viite kirjaan
     */
    public Kirja annaKirja (int i) {
        return kirjat.annaKirja(i);
    }
    /**
     * Poistaa kirjan ja siihen liittyvät lisätiedot
     * @param kirja kirja
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Kirjahylly kirjahylly = new Kirjahylly();
     * Kirja k1 = new Kirja(), k2 = new Kirja(), k3 = new Kirja();
     * k1.kirjaaKirja(); k2.kirjaaKirja(); k3.kirjaaKirja();
     * kirjahylly.lisaa(k1); kirjahylly.lisaa(k2); kirjahylly.lisaa(k3);
     * kirjahylly.getKirjat() === 3;
     * kirjahylly.poistaKirja(k1);
     * kirjahylly.getKirjat() === 2;
     * </pre>
     */
    public void poistaKirja(Kirja kirja) {
        if(kirja == null) return;
        kirjat.poista(kirja.getKirjaID());
        lisatiedot.poistaKirjanLisatiedot(kirja.getKirjaID());
    }
    
    /**
     * Poistaa yksittäisen lisätiedon
     * @param lisatieto tämä se on se joka poistetaan
     */
    public void poistaLisatieto(Lisatieto lisatieto) {
        lisatiedot.poistaLisatieto(lisatieto.getLisatietoID());
    }
    
    /**
     * Palauttaa kirjojen määrän
     * @return kirjojen määrä
     */
    public int getKirjat() {
        return kirjat.getLKM();
    }
    
    /**
     * Etsii kirjoista ehdot täyttävät kirjat
     * @param ehto hakuehto jolla kirjoja haetaan
     * @param hakukentta minkä perusteella haetaan
     * @return lista ehdon täyttävistä kirjoista
     * @throws SailoException jos tulee probleemia
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.ArrayList;
     * Kirjahylly kirjahylly = new Kirjahylly();
     * Kirja k1 = new Kirja(); k1.parse("1|Internet     | Mikko Hyppönen | Otava     | Suomi| Kovakantinen| Muistelmat| 666| 2021|");
     * Kirja k2 = new Kirja(); k2.parse("2|Testi        | Kari Kirjailija| WSOY      | Suomi| Pokkari     | Fantasia  | 201| 2001|");
     * Kirja k3 = new Kirja(); k3.parse("3|Harry Potter | J.K.Rowling    | Bloomsbury| Suomi| Kovakantinen| Fantasia  | 352| 2002|");
     * kirjahylly.lisaa(k1); kirjahylly.lisaa(k2); kirjahylly.lisaa(k3);
     * ArrayList<Kirja> loytyneet = new ArrayList<Kirja>();
     * loytyneet = kirjahylly.haku("*ter*", 0);
     * loytyneet.size() === 2;
     * </pre>
     */
    public ArrayList<Kirja> haku(String ehto, int hakukentta) throws SailoException {
        return kirjat.haku(ehto, hakukentta);
    }
    
    /**
     * Hakee kirjan lisätiedot
     * @param kirja jonka tiedot haetaan
     * @return lista teidoista
     */
    public List<Lisatieto> annaLisatiedot(Kirja kirja) {
        return lisatiedot.annaTiedot(kirja.getKirjaID());
    }

}

