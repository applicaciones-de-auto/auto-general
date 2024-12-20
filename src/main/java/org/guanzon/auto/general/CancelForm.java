/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.general;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.guanzon.appdriver.agent.ShowMessageFX;
import org.guanzon.appdriver.base.GRider;

/**
 *
 * @author Arsiela
 * Date Created: 05-20-2023
 */
public class CancelForm {
    private double xOffset = 0;
    private double yOffset = 0;
    private String psAction = "";
    private String psType = "";
    public boolean bState = false;
    
    public void setAction(String fsAction, String fsType){
        psAction = fsAction;
        psType = fsType;
    }
    
    /*CANCELLATION FORM REMARKS*/
//    public boolean loadCancelWindow(GRider oApp, String sSourceNox, String sTransNox, String sSourceCD ) throws SQLException{
    public boolean loadCancelWindow(GRider oApp, String sTransNox, String sSourceCD ) throws SQLException{
       
        try {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("CancelForm.fxml"));

            CancelFormController loControl = new CancelFormController();
            loControl.setGRider(oApp);
//            loControl.setsSourceNox(sSourceNox);
            loControl.setsSourceCD(sSourceCD);
            loControl.setTransNo(sTransNox);
            loControl.setAction(psAction);
            loControl.setType(psType);
            fxmlLoader.setController(loControl);
            
            //load the main interface
            Parent parent = fxmlLoader.load();
            parent.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });

            //set the main interface as the scene
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("");
            stage.showAndWait();
            //get the cancellation action
            bState = loControl.setState();
            
        } catch (IOException e) {
            e.printStackTrace();
            ShowMessageFX.Warning(e.getMessage(), "Warning", null);
            System.exit(1);
        }
        return bState ; 
    }
     
}
