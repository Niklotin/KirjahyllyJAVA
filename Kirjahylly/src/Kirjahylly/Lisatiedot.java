package Kirjahylly;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Lisätietorekisteri, lisää ja poistaa, etsii ja lajittelee, lukee ja kirjoittaa.
 * @author Niko
 * @version 9.8.2022
 *
 */

public class Lisatiedot {
    
    private ArrayList<Lisatieto> alkiot = new ArrayList<Lisatieto>();
    private String tiedostonNimi = "";
    private boolean muutettu = false;
    
    
    /**
     * Alustus
     */
    public Lisatiedot() {
        //Hymynaama : ^)
    }
    
    /**
     * Asettaa tiedoston nimen
     * @param nimi tiedoston
     */
    public void setTiedostonNimi(String nimi) {
        tiedostonNimi = nimi;
    }
    
    /**
     * Palauttaa käytettävän tiedoston nimen
     * @return tiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonNimi;
    }
    
    /**
     * Lisää uuden lisätiedon
     * @param tieto lisättävä tieto
     * @example
     * <pre name="test">
     *   #import java.util.*;
     *   Lisatiedot tiedot = new Lisatiedot();
     *   Lisatieto tieto1 = new Lisatieto(2); tiedot.lisaa(tieto1);
     *   Lisatieto tieto2 = new Lisatieto(1); tiedot.lisaa(tieto2); 
     *   Lisatieto tieto3 = new Lisatieto(2); tiedot.lisaa(tieto3); 
     *   Lisatieto tieto4 = new Lisatieto(1); tiedot.lisaa(tieto4); 
     *   Lisatieto tieto5 = new Lisatieto(2); tiedot.lisaa(tieto5); 
     *   Lisatieto tieto6 = new Lisatieto(5); tiedot.lisaa(tieto6); 
     *   List<Lisatieto> loytyneet;
     *   loytyneet = tiedot.annaTiedot(3);
     *   loytyneet.size() === 0;
     *   loytyneet = tiedot.annaTiedot(1);
     *   loytyneet.size() === 2;
     *   loytyneet.get(0) == tieto2 === true;
     *   loytyneet.get(1) == tieto4 === true; 
     *   loytyneet = tiedot.annaTiedot(5);
     *   loytyneet.size() === 1;
     *   loytyneet.get(0) == tieto6 === true; 
     * 
     * </pre>
     */
    public void lisaa(Lisatieto tieto) {
        alkiot.add(tieto);
        muutettu = true;
    }
    
    /**
     * Korvaa lisätiedon muutetulla tiedolla, tai lisää jos ei löydy
     * @param lisatieto Lisätieto
     * @example
     * <pre name="test">
     * Lisatiedot lisatiedot = new Lisatiedot();
     * Lisatieto tieto11 = new Lisatieto(2); lisatiedot.lisaa(tieto11);
     * Lisatieto tieto22 = new Lisatieto(2); lisatiedot.lisaa(tieto22);
     * tieto11.parse("1  |2  |testi1 |hyllymbyvör");
     * tieto22.parse("2  |2  |testi2 |miumau");
     * Lisatieto tieto33 = new Lisatieto(2);
     * tieto33.parse("3  |2  |testi3 |Pepputemppu");
     * Lisatieto tieto44 = new Lisatieto(2);
     * tieto44.parse("1  |2  |testi4 |Hymynaama");
     * List<Lisatieto> hitit;
     * hitit = lisatiedot.annaTiedot(2);
     * hitit.get(0).getNimi() === "testi1";
     * lisatiedot.korvaaTaiLisaa(tieto33);
     * hitit = lisatiedot.annaTiedot(2);
     * hitit.size() === 3;
     * lisatiedot.korvaaTaiLisaa(tieto44);
     * hitit = lisatiedot.annaTiedot(2);
     * hitit.get(0).getNimi() === "testi4";
     * </pre>
     */
    public void korvaaTaiLisaa(Lisatieto lisatieto) {
        int id = lisatieto.getLisatietoID();
        
        for(Lisatieto tieto : alkiot) {
            if(tieto.getLisatietoID() == id) {
                alkiot.remove(tieto);
                alkiot.add(0, lisatieto);
                muutettu = true;
                return;
            }
        }
        lisaa(lisatieto);
    }
    
    /**
     * Poistaa lisätiedot kirjalta
     * @param kirjaID kirja jonka lisätiedot poistetaan
     * @example
     * <pre name="test">
     * #import java.util.*;
     *   Lisatiedot lisatiedot = new Lisatiedot();
     *   Lisatieto tieto11 = new Lisatieto(2); lisatiedot.lisaa(tieto11);
     *   Lisatieto tieto22 = new Lisatieto(1); lisatiedot.lisaa(tieto22); 
     *   Lisatieto tieto33 = new Lisatieto(2); lisatiedot.lisaa(tieto33); 
     *   Lisatieto tieto44 = new Lisatieto(1); lisatiedot.lisaa(tieto44); 
     *   Lisatieto tieto55 = new Lisatieto(2); lisatiedot.lisaa(tieto55); 
     *   Lisatieto tieto66 = new Lisatieto(5); lisatiedot.lisaa(tieto66); 
     *   List<Lisatieto> loytyneet;
     *   loytyneet = lisatiedot.annaTiedot(1);
     *   loytyneet.size() === 2;
     *   lisatiedot.poistaKirjanLisatiedot(1);
     *   loytyneet = lisatiedot.annaTiedot(1);
     *   loytyneet.size() === 0;
     *   loytyneet = lisatiedot.annaTiedot(5);
     *   loytyneet.size() === 1;
     *   lisatiedot.poistaKirjanLisatiedot(5);
     *   loytyneet = lisatiedot.annaTiedot(5);
     *   loytyneet.size() === 0;
     * </pre>
     */
    public void poistaKirjanLisatiedot(int kirjaID) {
        int n = 0;
        
        for(Iterator<Lisatieto> it = alkiot.iterator(); it.hasNext(); ) {
            Lisatieto tieto = it.next();
            if(tieto.getKirjanID() == kirjaID) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
    }
    
    /**
     * Poistaa yksittäisen lisätiedon
     * @param tietoID Lisätiedon id
     * @example
     * <pre name="test">
     * #import java.util.*;
     * Lisatiedot lisatiedot = new Lisatiedot();
     * Lisatieto tieto11 = new Lisatieto(2); lisatiedot.lisaa(tieto11);
     * Lisatieto tieto22 = new Lisatieto(1); lisatiedot.lisaa(tieto22);
     * Lisatieto tieto33 = new Lisatieto(2); lisatiedot.lisaa(tieto33);
     * tieto11.parse("4 |2| lisatietoinen | priipryy ");
     * tieto22.parse("5 |2| lisatietoinen2| priipryyrpöö ");
     * tieto33.parse("3 |2| lisatietoinen3| prööpriipryy ");
     * List <Lisatieto> loytyneet;
     * loytyneet = lisatiedot.annaTiedot(2);
     * loytyneet.size() === 3;
     * lisatiedot.poistaLisatieto(5);
     * loytyneet = lisatiedot.annaTiedot(2);
     * loytyneet.size() === 2;
     * lisatiedot.poistaLisatieto(3);
     * loytyneet = lisatiedot.annaTiedot(2);
     * loytyneet.size() === 1;
     * </pre>
     */
    public void poistaLisatieto (int tietoID) {
        for(Lisatieto tieto : alkiot) {
            if(tieto.getLisatietoID() == tietoID) {
                alkiot.remove(tieto);
                muutettu = true;
                return;
            }
        }
    }
    
    /**
     * Etsii lisätiedot kirjoille
     * @param id kirjan ID
     * @return lisätiedot listana
     * @example
     * <pre name="test">
     *   #import java.util.*;
     *   Lisatiedot lisatiedot = new Lisatiedot();
     *   Lisatieto tieto11 = new Lisatieto(2); lisatiedot.lisaa(tieto11);
     *   Lisatieto tieto22 = new Lisatieto(1); lisatiedot.lisaa(tieto22); 
     *   Lisatieto tieto33 = new Lisatieto(2); lisatiedot.lisaa(tieto33); 
     *   Lisatieto tieto44 = new Lisatieto(1); lisatiedot.lisaa(tieto44); 
     *   Lisatieto tieto55 = new Lisatieto(2); lisatiedot.lisaa(tieto55); 
     *   Lisatieto tieto66 = new Lisatieto(5); lisatiedot.lisaa(tieto66); 
     *   List<Lisatieto> loytyneet;
     *   loytyneet = lisatiedot.annaTiedot(3);
     *   loytyneet.size() === 0;
     *   loytyneet = lisatiedot.annaTiedot(1);
     *   loytyneet.size() === 2;
     *   loytyneet.get(0) == tieto22 === true;
     *   loytyneet.get(1) == tieto44 === true; 
     *   loytyneet = lisatiedot.annaTiedot(5);
     *   loytyneet.size() === 1;
     *   loytyneet.get(0) == tieto66 === true; 
     * </pre>
     */
    public ArrayList<Lisatieto> annaTiedot(int id) {
        ArrayList<Lisatieto> tiedot = new ArrayList<Lisatieto>();
        
        for(Lisatieto tieto : alkiot) {
            if (tieto.getKirjanID() == id) tiedot.add(tieto);
        }
        return tiedot;
    }
    
    /**
     * Tallentaa tiedoston
     * @throws SailoException jos ei onnistu
     */
    public void tallenna() throws SailoException {
        tallenna(tiedostonNimi);
    }
    /**
     * Tallentaa tiedostoon.
     * @param tiedNim Tiedostoon nimi
     * @throws SailoException jos tallennus epäonnistuu.
     */
    public void tallenna(String tiedNim) throws SailoException {
        if(!muutettu) return;
        File tied = new File(tiedNim);
        
        try(PrintStream fout = new PrintStream (new FileOutputStream(tied, false))) {
            for(Lisatieto tieto: alkiot) {
                fout.println(tieto.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException ("Tiedosto " + tied.getAbsolutePath() + " ei aukea.");
        }
        muutettu = false;
    }
    
    /**
     * Lukee lisätiedot tiedostosta
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos sattuu hupsiskupsis
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * #import java.util.ArrayList;
     * #import java.util.Iterator;
     *  Lisatiedot lisatiedot = new Lisatiedot();
     *  Lisatieto tieto1 = new Lisatieto(); tieto1.taytaLisatiedot(2);
     *  Lisatieto tieto2 = new Lisatieto(); tieto2.taytaLisatiedot(1);
     *  Lisatieto tieto3 = new Lisatieto(); tieto3.taytaLisatiedot(2); 
     *  Lisatieto tieto4 = new Lisatieto(); tieto4.taytaLisatiedot(1); 
     *  Lisatieto tieto5 = new Lisatieto(); tieto5.taytaLisatiedot(2); 
     *  String tiedNimi = "testitiedot.dat";
     *  File ftied = new File(tiedNimi);
     *  ftied.delete();
     *  lisatiedot.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  lisatiedot.lisaa(tieto1);
     *  lisatiedot.lisaa(tieto2);
     *  lisatiedot.lisaa(tieto3);
     *  lisatiedot.lisaa(tieto4);
     *  lisatiedot.lisaa(tieto5);
     *  lisatiedot.tallenna();
     *  lisatiedot = new Lisatiedot();
     *  lisatiedot.lueTiedostosta(tiedNimi);
     *  ArrayList<Lisatieto> tiet = lisatiedot.annaTiedot(2);
     *  Iterator<Lisatieto> i = tiet.iterator();
     *  i.next().toString() === tieto1.toString();
     *  i.next().toString() === tieto3.toString();
     *  i.next().toString() === tieto5.toString();
     *  i.hasNext() === false;
     *  lisatiedot.lisaa(tieto5);
     *  lisatiedot.tallenna();
     *  ftied.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        setTiedostonNimi(hakemisto);
        
        try(Scanner miumau = new Scanner (new FileInputStream(getTiedostonNimi()))) {
            while(miumau.hasNext()) {
                String s = "";
                s = miumau.nextLine();
                Lisatieto lisatieto = new Lisatieto();
                lisatieto.parse(s);
                lisaa(lisatieto);
            }
            muutettu = false;
            miumau.close();
        } catch (FileNotFoundException e) {
            throw new SailoException("Jokin meni pieleen, tiedoston " + getTiedostonNimi() + " lukeminen ei onnistu");
        }
    }
    
    /**
     * Luetaan tiedostosta
     * @throws SailoException heittelee täm tämmöisen jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonNimi());
    }
    
    /**
     * Pääohjelma
     */
    public static void main() {
        // TODO Auto-generated method stub

    }

}
