package Kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kirja luokka kirjastoa varten
 * 
 * 
 * 
 * @author Niko
 * @version 8.8.2022
 *
 */


public class Kirja implements Cloneable {
    
    private String kirjanNimi = "";
    private String kirjailijanNimi = "";
    private String kirjanJulkaisija = "";
    private String kirjanKieli = "";
    private String kirjanTyyli = "";
    private String kirjanGenre = "";
    private String kirjanSivut = "";
    private String kirjanJulkaisu= "";
    private String numeroRegex = "\\d+";
    private int kirjaID;
    private static int seuraavaID = 1;
    
    
    /**
     * Alustaa kirjan
     */
    public Kirja() {
        /**
         * Hymynaama :)
         */
    }
    
    /**
     * Kopioi kirjan
     * @return Kopioitu kirja
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     *  Kirja kirja = new Kirja();
     *  kirja.parse("Internet | Mikko Hyppönen ");
     *  Kirja klooni = kirja.clone();
     *  klooni.toString() === kirja.toString();
     *  kirja.parse("Näin opit leipomaan! | Naapurin Hans");
     *  kirja.toString().equals(klooni.toString()) === false;
     *  
     */
    @Override
    public Kirja clone() throws CloneNotSupportedException {
       Kirja klooni;
       klooni = (Kirja) super.clone();
       return klooni;
    }
    
    /**
     * Antaa kirjalle tunnistenumeron 
     * @param id kirjalle annettava tunniste
     */
    public void setID(int id ) {
        kirjaID = id;
        if(kirjaID >= seuraavaID) seuraavaID = kirjaID + 1;
    }
    
    /**
     * Palauttaa kirjan tunnistenumeron
     * @return kirjan tunnistenumero
     */
    public int getKirjaID() {
        return this.kirjaID;
    }
    
    /**
     * Palauttaa kirjan nimen
     * @return kirjan nimi
     */
    public String getNimi( ) {
        return kirjanNimi;
    }
    
    /**
     * Kirjaa kirjalle tunnistenumeron
     * @return kirjan tunnnistenumero
     * @example
     * <pre name="test">
     * Kirja ekaKirja = new Kirja();
     * Kirja tokaKirja = new Kirja();
     * int k1, k2;
     * ekaKirja.kirjaaKirja();
     * tokaKirja.kirjaaKirja();
     * k1 = ekaKirja.getID();
     * k2 = tokaKirja.getID();
     * k1 === k2-1;
     * 
     */
    public int kirjaaKirja() {
        this.kirjaID = seuraavaID;
        seuraavaID++;
        return this.kirjaID;
    }
    
    /**
     * 
     * Testitietoja kirjalle
     */
    
    public void taytaTiedot() {
        this.kirjanNimi = "Kirjan nimi";
        this.kirjailijanNimi ="Kirjailijan nimi";
        this.kirjanJulkaisija = "Julkaisijan nimi";
        this.kirjanKieli = "Kirjan kieli";
        this.kirjanTyyli = "Kirjan tyyli";
        this.kirjanGenre = "Kirjan genre";
        this.kirjanSivut = "Kirjan sivujen määrä";
        this.kirjanJulkaisu = "Kirjan julkaisu pvm";
    }
    
    /**
     * 
     * @param rivi Rivi jolta tiedot haetaan
     * 
     */
    
    public void parse(String rivi) {
        StringBuilder lankaRakentaja = new StringBuilder(rivi);
        setID(Mjonot.erota(lankaRakentaja, '|', getKirjaID()));
        kirjanNimi = Mjonot.erota(lankaRakentaja, '|', kirjanNimi);
        kirjailijanNimi = Mjonot.erota(lankaRakentaja, '|', kirjailijanNimi);
        kirjanJulkaisija = Mjonot.erota(lankaRakentaja, '|', kirjanJulkaisija);
        kirjanKieli = Mjonot.erota(lankaRakentaja, '|', kirjanKieli);
        kirjanTyyli = Mjonot.erota(lankaRakentaja, '|', kirjanTyyli);
        kirjanGenre = Mjonot.erota(lankaRakentaja, '|', kirjanGenre);
        kirjanSivut = Mjonot.erota(lankaRakentaja, '|', kirjanSivut);
        kirjanJulkaisu = Mjonot.erota(lankaRakentaja, '|', kirjanJulkaisu);
    }
    
    @Override
    public String toString() {
        return "" + getKirjaID() + '|' + kirjanNimi + '|' + kirjailijanNimi + '|'  + kirjanJulkaisija + '|'  + kirjanKieli + '|' +  kirjanTyyli + '|'  + kirjanGenre + '|'  + kirjanSivut + '|' + kirjanJulkaisu + '|';
    }
    
    /**
     * Tulostaa kirjan tiedot
     * @param ulos Ulostulon titetovirta
     */
    public void tulosta(PrintStream ulos) {
        ulos.println(kirjaID);
        ulos.println(kirjanNimi + " " + kirjailijanNimi);
        ulos.println(kirjanJulkaisija + " " + kirjanJulkaisu);
        ulos.println(kirjanSivut + " " + kirjanKieli);
        ulos.println(kirjanGenre + " " + kirjanKieli);
        
        
    }
    
    /**
     * Tulostaa kirjan tiedot
     * @param ulos Ulostulon tietovrita
     */
  public void tulosta(OutputStream ulos) {
      tulosta(new PrintStream(ulos));
  }
  
  /**
   * Asettaa kentän tiedot
   * 
   * 
   * @param s kentän sisältö
   * @param i kentän paikkatieto
   * @return nullia kaikille jos hommat jiirissä, virheviestit numerokentille jos ei jiirissä.
   * <pre name="test">
   * Kirja kirja = new Kirja();
   * kirja.setKentat(0, "Kirjan nimi");
   * kirja.getKentat(0) === "Kirjan nimi";
   * kirja.setKentat(4, "nauris");
   * kirja.getKentat(4) === "nauris";
   * kirja.setKentat(6, "247");
   * kirja.getKentat(6) === "247";
   */
  public String setKentat(int i, String s) {
      String trimmattu = s.trim();
      
      switch(i) {
      case 0: kirjanNimi = trimmattu; return null;
      case 1: kirjailijanNimi = trimmattu; return null;
      case 2: kirjanJulkaisija = trimmattu; return null;
      case 3: kirjanKieli = trimmattu; return null;
      case 4: kirjanTyyli = trimmattu; return null;
      case 5: kirjanGenre = trimmattu; return null;
      case 6: return tarkistaNumerot(trimmattu, "sivu"); //kirjanSivut
      case 7: return tarkistaNumerot(trimmattu, "vuosi"); //Julkaisuvuosi
      default: return "Eipä ollu kenttää";
      }
  }
  
  /**
   * 
   * @param i kentän paikkatieto
   * @return nimet kielet jnejne
   * Testattu ylemmässä testissä.
   */
  
  public String getKentat(int i) {
      switch(i) {
      case 0: return "" + kirjanNimi;
      case 1: return "" + kirjailijanNimi;
      case 2: return "" + kirjanJulkaisija;
      case 3: return "" + kirjanKieli;
      case 4: return "" + kirjanTyyli;
      case 5: return "" + kirjanGenre;
      case 6: return "" + kirjanSivut;
      case 7: return "" + kirjanJulkaisu;
      default: return "";
      }
  }
  
  
  /**
   * Tarkistaa ettei vaan käyttäjä menisi laittamaan positiivisille numeroille tarkoitettuihin kenttiin kirjaimia tai negatiivisiä lukuja.
   * @param s numero stringinä
   * @param sivuVaiVuosi erottaja sille kumpaan dataan tallennetaan, ei ehkä paras ratkaisu.
   * @return virheviesti jos sattuu hupsiskupsis, null jos kaikki on hyvin.
   * <pre name="test">
   * Kirja kirja = new Kirja();
   * kirja.tarkistaNumerot("-200", "vuosi") === "Tähän kenttään voi asettaa vain positiivisia numeroita";
   * kirja.tarkistaNumerot("Haha olen hupsuttelija ja yritän laittaa kirjaimia numerokenttään hehee", "sivu") === "Tähän kenttään voi asettaa vain positiivisia numeroita";
   * kirja.tarkistaNumerot("200", "sivu") === null;
   * </pre>
   */
  public String tarkistaNumerot(String s, String sivuVaiVuosi)
  {
      if(!Pattern.matches(numeroRegex, s)) return "Tähän kenttään voi asettaa vain positiivisia numeroita";
      if(sivuVaiVuosi == "sivu") kirjanSivut = s;
      if(sivuVaiVuosi == "vuosi") kirjanJulkaisu = s;
      return null;
  }
    
    
    
    /**
     * maini
     */
    public static void main() {
        // Tyhjä
    }

}

