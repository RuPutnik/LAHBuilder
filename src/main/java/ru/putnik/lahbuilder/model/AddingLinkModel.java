package ru.putnik.lahbuilder.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.putnik.lahbuilder.link.*;

import java.util.List;

/**
 * Создано 13.01.2019 в 16:49
 */
public class AddingLinkModel {
    private String transferFunction="";
    public ObservableList<Link> getListLinks(){
        ObservableList<Link> list=FXCollections.observableArrayList();
        list.add(new AmplificationLink());
        list.add(new AperiodicLink1());
        list.add(new AperiodicLink2());
        list.add(new DifferentialLink());
        list.add(new IntegratingLink());
        list.add(new OscillatoryLink());

        return list;
    }
    public String formationTransferFunction(String function,Link newLink){
        System.out.println("Link: "+newLink.getNameLink());
        System.out.println("K = "+newLink.getValueK());
        System.out.println("T = "+newLink.getValueT());
        System.out.println("2TKsi = "+newLink.getValueT2Ksi());
        String linkStringFormat="";
        function=function+linkStringFormat;
        return function;
    };

    public void setTransferFunction(String transferFunction) {
        this.transferFunction = transferFunction;
    }

    public String getTransferFunction() {
        return transferFunction;
    }
}
