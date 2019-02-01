package ru.putnik.lahbuilder.model;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import ru.putnik.lahbuilder.LinkComparator;
import ru.putnik.lahbuilder.link.*;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Создано 08.01.2019 в 1:17
 */
public class MainModel {
    private static ArrayList<Link> listLinks=new ArrayList<>();
    private XYChart.Series<Double,Double> ser;

    public void deleteLink(ObservableList<Link> list,int numberElement){
        if(list!=null&&!list.isEmpty()){
            list.remove(numberElement);
        }
        if(listLinks!=null&&!listLinks.isEmpty()){
            listLinks.remove(numberElement);
        }
    }
    private ArrayList<Link> decompositionAperiodicLink2(ArrayList<Link> listLinks){
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
        lineChart.getData().clear();
        double lowFreq=0.1;//левая граница шкалы
        double upperFreq=1000;//правая граница шкалы
        ser=new XYChart.Series<>();
        ArrayList<Link> finalListLinks=new ArrayList<>(listLinks);
        finalListLinks=decompositionAperiodicLink2(finalListLinks);
        double[] cornerFrequency;
        double[] valueAmplitude;
        int m=0,n=0;
        double k=1;
        int incline;
        double value_20lgk;
        for (Link finalListLink : finalListLinks) {
            k = k * finalListLink.getValueK();
        }
        for (int a=0;a<finalListLinks.size();a++){
            if(finalListLinks.get(a) instanceof IntegratingLink){
                n++;
                finalListLinks.remove(a);
                a--;
            }else if(finalListLinks.get(a) instanceof DifferentialLink){
                m++;
                finalListLinks.remove(a);
                a--;
            }
            else if(finalListLinks.get(a) instanceof AmplificationLink){
                finalListLinks.remove(a);
                a--;
            }
        }

        cornerFrequency=new double[finalListLinks.size()+2];
        valueAmplitude=new double[cornerFrequency.length];
        cornerFrequency[0]=lowFreq;
        cornerFrequency[cornerFrequency.length-1]=upperFreq;
        finalListLinks.sort(new LinkComparator());
        int b=1;
        for(Link l:finalListLinks){
            cornerFrequency[b]=(1/l.getValueT());
            b++;
        }

        cornerFrequency[cornerFrequency.length-1]=upperFreq;
        incline=20*(m-n);
        value_20lgk=20*Math.log10(k);
        valueAmplitude[0]=value_20lgk-incline*-Math.log10(lowFreq);

       for(int a=0;a<valueAmplitude.length-1;a++){
            valueAmplitude[a+1]=valueAmplitude[a]+incline*(Math.log10(cornerFrequency[a+1]/cornerFrequency[a]));
            if(a<finalListLinks.size()) {
                incline = incline + finalListLinks.get(a).getIncline();
            }
        }
        System.out.println("-------------------");
        System.out.println(Arrays.toString(cornerFrequency));
        System.out.println(Arrays.toString(valueAmplitude));


        for (int a=0;a<valueAmplitude.length;a++){
            ser.getData().add(new XYChart.Data<>(cornerFrequency[a],valueAmplitude[a]));
        }
        lineChart.getData().add(ser);
    }

    static ArrayList<Link> getListLinks() {
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
