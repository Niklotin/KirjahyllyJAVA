package Kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Osaa lisätietojen kentät
 * Hanskaa merkkijonojen pyörittelyt.
 * @author Niko
 * @version 9.8.2022
 *
 */

public class Lisatieto implements Cloneable {
    
    private String nimi = "";
    private String sisalto = "";
    private int tietoID;
    private int kirjaID;
    private static int seuraavaID = 1;
    
    
    /**
     * Alustaa uuden lisätiedon
     */
    public Lisatieto() {
        //Hymynaama :=)
    }
    
    /**
     * Kopioi lisätiedon
     * @return kopoioitu lisätieto
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     * Lisatieto tieto = new Lisatieto();
     * tieto.parse("6| 2| Lisätieto kirjasta| Lisätiedon sisältä");
     * Lisatieto kopio = tieto.clone();
     * kopio.toString() === tieto.toString();
     * tieto.parse("6|2|BLÖÖÖÖ|BLÄÄÄÄ");
     * kopio.toString().equals(tieto.toString()) === false;
     * </pre>
     */
    @Override
    public Lisatieto clone() throws CloneNotSupportedException {
        Lisatieto klooni;
        klooni = (Lisatieto) super.clone();
        return klooni;
    }
    
    /**
     * Asettaa lisätiedolle tunnisteen ja tarkistaa tunnisteen suuruuden.
     * @param id tunnistenumero
     */
    public void setID(int id) {
        tietoID = id;
        if(tietoID >= seuraavaID)seuraavaID = tietoID + 1;
    }
    /**
     * Asettaa kirjalle tunnisteen
     * @param id tunniste
     */
    public void setKirjanID(int id) {
        kirjaID = id;
    }

    
    /**
     * Hakee lisätiedon numerotunnisteen
     * @return lisätiedon numerotunniste
     */
    public int getLisatietoID() {
        return tietoID;
    }
    
    /**
     * Hakee kirjan tunnisteen
     * @return kirjan tunniste
     */
    public int getKirjanID() {
        return kirjaID;
    }
    
    /**
     * Hakee lisätiedon nimen
     * @return lisätiedon nimi
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * Hakee lisätiedon sisällön
     * @return lisätiedon sisältö
     */
    public String getSisalto() {
        return sisalto;
    }
    
    /**
     * Asettaa lisätiedon nimen
     * @param s lisätiedon nimi
     */
    public void setNimi(String s) {
        nimi = s;
    }
    
    /**
     * Asettaa lisätiedon sisällön
     * @param s lisätiedon sisältö
     */
    public void setSisalto(String s) {
        sisalto = s;
    }
    
    /**
     * Antaa lisätiedolle seuraavan tunnistenumeron
     * @return Lisätiedon tunnistenumero
     */
    public int kirjaa() {
        this.tietoID = seuraavaID;
        seuraavaID++;
        return this.kirjaID;
    }
    
    /**
     * Tulostaa tietovirtaan
     * @param ulos tietovirta
     */
    public void tulosta(PrintStream ulos) {
        ulos.println(nimi + "\n" + sisalto);
    }
    
    /**
     * Tulostaa tietovirtaan
     * @param ulos tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream ulos) {
        tulosta(new PrintStream(ulos));
    }
    
    /**
     * Täyttää lisätiedon
     * @param id tunnistenumero
     */
    public void taytaLisatiedot(int id) {
        this.kirjaID = id;
        nimi = "Tiedon nimi";
        sisalto ="Tässä sinulle sisältöä siitä saat ja siitä";
    }
    
    /**
     * Alustaa lisätiedon kirjalle
     * @param kirjaID kirja johon lisätieto lisätään
     * @example
     * <pre name="test">
     * Lisatieto tieto = new Lisatieto(3);
     * tieto.getKirjanID() === 3;
     * </pre>
     */
    public Lisatieto(int kirjaID) {
        this.kirjaID = kirjaID;
    }
    
    /**
     * Nappaa lisätiedot tolppamerkillä erotelluista merkkijonoista
     * @param rivi rivi mistä haetaan
     * @example
     * <pre name="test">
     * Lisatieto lisatieto = new Lisatieto();
     * lisatieto.parse("6 | 9 | Hyllymbyvor | Rynkkäjynks |");
     * lisatieto.getLisatietoID() === 6;
     * lisatieto.toString().startsWith("6|9|Hyllymbyvor|Rynkkäjynks|") === true;
     * lisatieto.kirjaa();
     * int x = lisatieto.getLisatietoID();
     * lisatieto.parse("" + (x+10));
     * lisatieto.kirjaa();
     * lisatieto.getLisatietoID() === x+11;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder lankaRakentaja = new StringBuilder(rivi);
        setID(Mjonot.erota(lankaRakentaja, '|', getLisatietoID()));
        kirjaID = Mjonot.erota(lankaRakentaja, '|', getKirjanID());
        nimi = Mjonot.erota(lankaRakentaja, '|', nimi);
        sisalto = Mjonot.erota(lankaRakentaja, '|', sisalto);      
    }
    
    @Override
    public String toString() {
        return "" + getLisatietoID() + "|" + getKirjanID() + '|' + nimi + '|' + sisalto + '|';
    }
    
    /**
     * Pääohjelma testaamista varten
     */
    public static void main() {
        Lisatieto testiTieto = new Lisatieto();
        testiTieto.taytaLisatiedot(1);
        testiTieto.tulosta(System.out);

    }

}
