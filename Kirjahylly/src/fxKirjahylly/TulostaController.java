package fxKirjahylly;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;

/**
 * Hoitaa tulostamisen
 * @author Niko
 * @version 12.8.2022
 */
public class TulostaController implements ModalControllerInterface<String> {
    
    @FXML private Button okButton;
    @FXML private TextArea tulostusAlue;
    
    
    @FXML private void handleOK() {
        ModalController.closeStage(okButton);
    }
    
    
    /**
     * Hoitaa tulostamisen
     */
    @FXML private void handleTulosta() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(null)) {
            WebEngine webEngine = new WebEngine();
            webEngine.loadContent("<pre>" + tulostusAlue.getText() + "</pre>");
            webEngine.print(job);
            job.endJob();
        }
    }
    

    @Override
    public String getResult() {
        // Auto-generated method stub
        return null;
    }

    
    @Override
    public void handleShown() {
        // Auto-generated method stub  
    }

    
    @Override
    public void setDefault(String oletus) {
        tulostusAlue.setText(oletus);
    }
    

    /**
     * @return alue johon tulostetaan
     */
    public TextArea getTextArea() {
        return tulostusAlue;
    }

    
    /**
     * Luo ja näyttää tulostusalueessa tekstin
     * @param tulostus tulostettava teksti
     * @return tulostuscontroller jos tarvitaan pyytää lisää tietoa
     */
    public static TulostaController tulosta(String tulostus) {
        TulostaController tulostaCtrl = ModalController.showModeless(TulostaController.class.getResource("TulostaView.fxml"), "Tulostus", tulostus);
        return tulostaCtrl;
    }
}
