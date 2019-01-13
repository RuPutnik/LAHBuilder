package ru.putnik.lahbuilder.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.putnik.lahbuilder.link.*;

import java.util.List;

/**
 * Создано 13.01.2019 в 16:49
 */
public class AddingLinkModel {
    public ObservableList<String> getListNamesLinks(){
        ObservableList<String> list=FXCollections.observableArrayList();
        list.add(AmplificationLink.getNameLink());
        list.add(AperiodicLink1.getNameLink());
        list.add(AperiodicLink2.getNameLink());
        list.add(DifferentialLink.getNameLink());
        list.add(IntegratingLink.getNameLink());
        list.add(OscillatoryLink.getNameLink());

        return list;
    }
}
