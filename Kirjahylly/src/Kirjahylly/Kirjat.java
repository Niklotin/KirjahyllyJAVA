package Kirjahylly;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import fi.jyu.mit.ohj2.WildChars;


/**
 * Kirjarekisteri, lisää ja poistaa kirjan, osaa etsiä, kirjoittaa ja lukee tiedostot.
 * @author Niko
 * @version 9.8.2022
 *
 */

public class Kirjat {
    
    private static final int MAX_KIRJAT = 5;
    private int lkm = 0;
    private Kirja[] alkiot;
    private String tiedostonNimi = "";
    private boolean muutettu = false;
    
    /**
     * Alustaa kirjataulukon
     */
    public Kirjat() {
        alkiot = new Kirja[MAX_KIRJAT];
    }
    
    /**
     * Palauttaa kirjojen määrän
     *      * @return kirjojen määrä
     */
    public int getLKM() {
        return lkm;
    }
    
    /**
     * Tarkistaa taulukon koon
     * @return taulukon koko
     */
    public int taulukonKoko() {
        return alkiot.length;
    }
    /**
     * Hakee kirjan indeksin taulukosta
     * @param id haettavan kirjan tunniste
     * @return Haettavan kirjan paikka taulukosta, jos ei onnistu niin palautetaan -1
     */
    public int etsiID(int id) {
        for (int i = 0; i <lkm; i++)
            if(id==alkiot[i].getKirjaID()) return i;
        return -1;
    }
    
    /**
     * Kasvattaa taulukon kokoa yhdellä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Kirjat kirjat = new Kirjat();
     * Kirja kirja1 = new Kirja();
     * Kirja kirja2 = new Kirja();
     * kirjat.lisaa(kirja1); kirjat.lisaa(kirja1); kirjat.lisaa(kirja1); kirjat.lisaa(kirja1); kirjat.lisaa(kirja1);
     * kirjat.taulukonKoko() === 5;
     * kirjat.lisaa(kirja2);
     * kirjat.taulukonKoko() === 6;
     */
    public void kasvataTaulukkoa() {
        Kirja[] kasvatettuTaulukko = new Kirja[alkiot.length+1];
        
        for (int i = 0; i < alkiot.length; i++) {
            kasvatettuTaulukko[i] = alkiot[i];
        }
        alkiot = kasvatettuTaulukko;
    }
    
    /**
     * Lisää kirjan taulukkoon 
     * @param kirja kirja
     * @throws SailoException Nurisee jos taulukko on täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     *      Kirjat kirjat = new Kirjat();
     *      Kirja kirja1 = new Kirja(), kirja2 = new Kirja();
     *      kirjat.getLKM() === 0;
     *      kirjat.lisaa(kirja1); kirjat.getLKM() === 1;
     *      kirjat.lisaa(kirja2); kirjat.getLKM() === 2;
     *      kirjat.lisaa(kirja1); kirjat.getLKM() === 3;
     *      kirjat.annaKirja(0) === kirja1;
     *      kirjat.annaKirja(1) === kirja2;
     *      kirjat.annaKirja(2) === kirja1;
     *      kirjat.annaKirja(1) == kirja1 === false;
     *      kirjat.annaKirja(1) == kirja2 === true;
     *      kirjat.annaKirja(3) === kirja1; #THROWS IndexOutOfBoundsException
     *      kirjat.lisaa(kirja1); kirjat.getLKM() === 4;
     *      kirjat.lisaa(kirja1); kirjat.getLKM() === 5;
     *      Kirja kirja6 = new Kirja();
     *      kirjat.lisaa(kirja6); kirjat.getLKM() === 6;
     *      kirjat.taulukonKoko() === 6;
     *      kirjat.lisaa(kirja6); kirjat.lisaa(kirja6); kirjat.lisaa(kirja6);
     *      kirjat.lisaa(kirja6); kirjat.lisaa(kirja6);
     * </pre>
     */
    public void lisaa(Kirja kirja) throws SailoException {

        if(alkiot.length <= lkm) kasvataTaulukkoa();
        if(alkiot.length <= lkm) throw new SailoException("Liikaa kirjoja!");

        alkiot[lkm] = kirja;
        lkm++;
        muutettu = true;
    }
    
    /**
     * Poistaa kirjan taulukosta
     * @param ID kirjan tunnistenumero
     */
    public void poista(int ID) {
        int haettuID = etsiID(ID);
        if(haettuID < 0) return;
        lkm--;
        for(int i = haettuID; i <lkm; i++)
            alkiot[i] = alkiot [i++];
        alkiot[lkm] = null;
        muutettu = true;
    }
    
    /**
     * Korvaa samalla tunnisteella olevan kirjan, jos ei ole samalla tunnisteella olevaa niin lisää kirjan.
     * @param kirja Korvattava kirja
     * @throws SailoException jos ei mahdu
     */
    public void korvaaTaiLisaa(Kirja kirja) throws SailoException {
        int kirjanID = kirja.getKirjaID();
        
        for(int i=0; i<lkm; i++) {
            if(alkiot[i].getKirjaID() == kirjanID) {
                alkiot[i] = kirja;
                muutettu = true;
                return;
            }
        }
        lisaa(kirja);
    }
    
    /**
     * Antaa paikasta i kirjan
     * @param i kirjan paikka
     * @return kirjan viite
     * @throws IndexOutOfBoundsException Jos yritetään hakea kirjaa paikasta jota ei ole olemassa niin huudellaan perään.
     */
    public Kirja annaKirja(int i) throws IndexOutOfBoundsException {
        if(i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Virheellinen index paikassa: " + i);
        return alkiot[i];
    }
    
    /**
     * Haku kirjoille
     * @param ehto Syötetty hakuehto
     * @param kentta Millä kentällä haetaan
     * @return Lista kirjoista jotka täyttää haun(ei se vuh vuh)
     */
    public ArrayList<Kirja> haku(String ehto, int kentta) {
        ArrayList<Kirja> palautettavat = new ArrayList<>();
        
        String jokeri = "*";
        if(ehto.length() > 0)jokeri=ehto;
        
        for(int i = 0; i<lkm; i++)
        {
            if(WildChars.onkoSamat(alkiot[i].getKentat(kentta), jokeri))
            {
                palautettavat.add(alkiot[i]);
            }
        }
        
        return palautettavat;
    }
    
    /**
     * Palauttaa tallennettavan tiedoston nimen
     * @return tiedoston nimi johon tallennetaan
     */
    public String getTiedosto() {
        return tiedostonNimi;
    }
    
    /**
     * Asettaa tallenstiedostolle uuden nimen
     * @param uusiNimi uusi nimi tiedoostolle
     */
    public void setTiedosto(String uusiNimi) {
        tiedostonNimi = uusiNimi;
    }
    
    /**
     * Lukee tiedostosta
     * @param hakemisto Tiedoston hakemistto
     * @throws SailoException Jos menee pieleen niin poikkeusta pukkaa
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        setTiedosto(hakemisto);
        try(Scanner file = new Scanner(new FileInputStream(getTiedosto()))){
            while(file.hasNext()) {
            String s = "";
            s = file.nextLine();
            Kirja kirja = new Kirja();
            kirja.parse(s);
            lisaa(kirja);
            }
        
        muutettu = false;
        file.close();
    } catch (FileNotFoundException e) {
        throw new SailoException("Tiedoston" + getTiedosto() +" luku epäonnistui.");
    }
    }
    
    /**
     * Luetaan tiedostosta
     * @throws SailoException heitellään poikkeuksia
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedosto());
    }
    
    /**
     * Tallentaa setit tiedostoon
     * @param tiedNim Tiedoston nimi johon tallennetaan
     * @throws SailoException Jos epäonnistuu
     */
    public void tallenna(String tiedNim) throws SailoException {
        if(!muutettu) return;
        File tied = new File(tiedNim);
        try(PrintStream printti = new PrintStream(new FileOutputStream(tied, false)))
        {
            for(int i = 0; i < getLKM(); i++) {
                Kirja kirja = annaKirja(i);
                printti.println(kirja.toString());
            }
            
        } catch (FileNotFoundException e) {
            throw new SailoException ("Tiedosto " + tied.getAbsolutePath() + " ei aukea");
            
        }
        muutettu = false;
    }
    
    /**
     * Tallentaa setit tiedostoon
     * @throws SailoException jos ei pelaa
     */
    public void tallenna() throws SailoException {
        tallenna(tiedostonNimi);
    }

    
    
    
    
    /**
     * Pääohjelma kirjat-luokalle
     */
    public static void main()
   {
        Kirjat kirjat = new Kirjat();
    
        try {
            kirjat.lueTiedostosta("Kirjahylly");
        } catch(SailoException e) {
            System.err.println(e.getMessage());
        }
        
        Kirja kirja1 = new Kirja();
        Kirja kirja2 = new Kirja();
        kirja1.kirjaaKirja();
        kirja2.kirjaaKirja();
        kirja1.taytaTiedot();
        kirja2.taytaTiedot();
        
        try {
            kirjat.lisaa(kirja1);
            kirjat.lisaa(kirja2);
            
            for(int i = 0; i < kirjat.getLKM(); i++) {
                Kirja kirja = kirjat.annaKirja(i);
                System.out.println("Indeksi = " + i);
                kirja.tulosta(System.out);
            }
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
        
        try { kirjat.tallenna("Kirjahylly");
        } catch(SailoException e) {
            e.printStackTrace();
        }

    }


}
