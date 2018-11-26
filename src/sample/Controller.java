package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    //GUI ELEMENTS
    @FXML AnchorPane modsID;
    @FXML CheckBox isX;
    @FXML CheckBox isY;
    @FXML Spinner amntSpinner;
    SpinnerValueFactory<Integer> vf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9, 1);
    @FXML ListView itemList;
    @FXML TextField total;
    @FXML Label modLabel;

    //CURRENT TRANSACTION LIST
    private ArrayList<Item> currentTransaction = new ArrayList<>();

    //UNIVERSAL CONSTANTS
    private Item select = null;
    private boolean editDeleteBoolean = false;
    private int itemIndex;

    //GETS ANCHORPOINT ELEMENT ID, THEN GETS CORRESPONDING ITEM CONSTANT, GOES TO MODIFY METHOD
    public void itemSelection(MouseEvent e) {
        AnchorPane ap = (AnchorPane) e.getTarget();
        String id = ap.getId();

        switch(id){
            case("bA"):
                select = itemsEnum.bA.getItem();
                break;
            case("bB"):
                select = itemsEnum.bB.getItem();
                break;
            case("bC"):
                select = itemsEnum.bC.getItem();
                break;
            case("bD"):
                select = itemsEnum.bD.getItem();
                break;
            case("bE"):
                select = itemsEnum.bE.getItem();
                break;
            case("bF"):
                select = itemsEnum.bF.getItem();
                break;
        }

        modify(select);
    }

    //MODIFICATIONS WINDOW
    public void modify(Item select){
        try{
            modsID.setVisible(true);
            modLabel.setText(select.getName());
            isX.setSelected(select.isModX());
            isY.setSelected(select.isModY());

            vf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,9,select.getAmount());
            amntSpinner.setValueFactory(vf);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //ADDS TO TRANSACTIONS ARRAY LIST
    public void modsAccept(MouseEvent e) {
        String name = select.getName();
        double cost = select.getCost();
        boolean X = isX.isSelected();
        boolean Y = isY.isSelected();
        int amnt = (int) amntSpinner.getValue();

        if(editDeleteBoolean){
            itemIndex = itemList.getSelectionModel().getSelectedIndex();
            itemDelete();
            editDeleteBoolean=false;
        }

        currentTransaction.add(new Item(name,cost,X,Y,amnt));
        updateList();

        modsID.setVisible(false);
    }

    //LISTENS TO LISTVIEW INDEX AND DELETES SELECTION
    public void itemDelete(){
        try {
            currentTransaction.remove(itemIndex);
            itemIndex = -1;
            updateList();
        } catch (Exception e){
            System.out.println("No item selected");
        }
    }

    //CLOSES MODS WINDOW
    public void modsCancel(MouseEvent e) {
        modsID.setVisible(false);
        updateList();
    }

    //LISTENS TO LISTVIEW SELECTION AND USES MODIFY METHOD ON SELECTION
    public void itemEdit(MouseEvent e){
        try {
            int index = itemList.getSelectionModel().getSelectedIndex();
            select = currentTransaction.get(index);
            editDeleteBoolean=true;
            modify(select);

        } catch (Exception x) {
            System.out.println("No Item Selected");
        }
    }

    //METHOD CLEARS AND UPDATES LISTVIEW
    public void updateList(){
        double totalcost = 0.0;

        //ItemList
        itemList.getItems().clear();
        for(Item x : currentTransaction){
            String str = x.getName()+" x"+x.getAmount()+" = "+x.getTotalCost();
            if(x.isModX()) str+="\nmod X : +2";
            if(x.isModY()) str += "\nmod Y: +3";

            totalcost += x.getTotalCost();
            itemList.getItems().add(str);
        }

        //Total
        //totalcost = Math.round(totalcost*100)/100;
        total.setText(String.valueOf(totalcost));
    }

    //FINALIZE
    public void transactionFinalize(MouseEvent e){
        try{
            double totalcost = 0.0;
            FileWriter fw = new FileWriter("Receipt.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("RECEIPT");
            bw.newLine();
            bw.write("=============");
            bw.newLine();
            for(Item x : currentTransaction){
                bw.write(x.getName()+" x"+x.getAmount()+" = "+Math.round(x.getTotalCost()*100)/100);
                bw.newLine();
                if(x.isModX()) {bw.write("   modifier X. +2"); bw.newLine();}
                if(x.isModY()) {bw.write("   modifier Y. +3"); bw.newLine();}
                bw.newLine();

                totalcost += x.getTotalCost();
            }

            bw.write("------------"); bw.newLine();
            bw.write("Total: "+String.valueOf(totalcost));
            bw.close();

            File file = new File("Receipt.txt");
            if(Desktop.isDesktopSupported()){
                Desktop.getDesktop().edit(file);
            }

            Platform.exit();

        } catch (Exception z) {
            System.out.println("Error in transaction finalization");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Sets up Spinner
        amntSpinner.setValueFactory(vf);
        amntSpinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);

        //turns off mods scene on start up
        modsID.setVisible(false);

    }
}
