package ru.putnik.lahbuilder.model;

import javafx.collections.ObservableList;
import ru.putnik.lahbuilder.link.Link;

import java.util.ArrayList;

/**
 * Создано 08.01.2019 в 1:17
 */
public class MainModel {
    private static ArrayList<Link> listLinks=new ArrayList<>();

    public void deleteLink(ObservableList<Link> list,int numberElement){
        if(list!=null&&!list.isEmpty()){
            list.remove(numberElement);
        }
        if(listLinks!=null&&!listLinks.isEmpty()){
            listLinks.remove(numberElement);
        }
    }



    public static ArrayList<Link> getListLinks() {
        return listLinks;
    }
    public static void addLink(Link link){
        listLinks.add(link);
    }


}
