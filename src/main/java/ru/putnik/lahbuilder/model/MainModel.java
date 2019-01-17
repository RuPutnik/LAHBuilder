package ru.putnik.lahbuilder.model;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ListView;
import ru.putnik.lahbuilder.link.AperiodicLink1;
import ru.putnik.lahbuilder.link.AperiodicLink2;
import ru.putnik.lahbuilder.link.Link;


import java.math.RoundingMode;
import java.text.DecimalFormat;
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
    public ArrayList<Link> decompositionAperiodicLink2(ArrayList<Link> listLinks){
        for (int n=0;n<listLinks.size();n++){
            if(listLinks.get(n) instanceof AperiodicLink2){
                Link l=listLinks.get(n);
                listLinks.remove(n);
                double a=rounding(Math.pow(l.getValueT(),2));
                double b=l.getValueT2Ksi();
                double c=1;
                double D=rounding(Math.pow(b,2)-4*a*c);
                double k=l.getValueK();
                Link l1,l2;

                if(D>0) {
                    double t1 = rounding(-1.0/((-b - Math.sqrt(D)) / (2 * a)));
                    double t2 = rounding(-1.0/((-b + Math.sqrt(D)) / (2 * a)));
                    k=k/a;
                    k=k/((-b - Math.sqrt(D)) / (2 * a));
                    k=k/((-b + Math.sqrt(D)) / (2 * a));
                    k=rounding(k);
                    l1=new AperiodicLink1(t1,k);
                    l2=new AperiodicLink1(t2,1);
                    listLinks.add(l1);
                    listLinks.add(l2);
                }else if(D==0){
                    double t=rounding(-1.0/(-b/(2*a)));
                    k=k/a;
                    k=k/Math.pow(-b/(2*a),2);
                    k=rounding(k);
                    l1=new AperiodicLink1(t,k);
                    l2=new AperiodicLink1(t,1);
                    listLinks.add(l1);
                    listLinks.add(l2);
                }
            }
        }
        return listLinks;
    }
    public void buildLAH(LineChart<Double,Double> lineChart){
        ArrayList<Link> finalListLinks=new ArrayList<>(listLinks);
        for(Link l:finalListLinks){
            System.out.println(l.getNameLink());
            System.out.println(l.getValueK());
            System.out.println(l.getValueT());
            System.out.println(l.getValueT2Ksi());
        }
        finalListLinks=decompositionAperiodicLink2(finalListLinks);
        System.out.println("-------------------------------");
        for(Link l:finalListLinks){
            System.out.println(l.getNameLink());
            System.out.println(l.getValueK());
            System.out.println(l.getValueT());
            System.out.println(l.getValueT2Ksi());
        }


    }


    public static ArrayList<Link> getListLinks() {
        return listLinks;
    }
    public static void addLink(Link link){
        listLinks.add(link);
    }
    public double rounding(double number){
        DecimalFormat df=new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);

        return Double.parseDouble(df.format(number).replace(",","."));
    }


}
