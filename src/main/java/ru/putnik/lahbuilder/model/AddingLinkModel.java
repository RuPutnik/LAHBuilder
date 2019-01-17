package ru.putnik.lahbuilder.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.putnik.lahbuilder.link.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Создано 13.01.2019 в 16:49
 */
public class AddingLinkModel {
    private String transferFunction="";
    public static ObservableList<Link> getListLinks(){
        ObservableList<Link> list=FXCollections.observableArrayList();
        list.add(new AmplificationLink());
        list.add(new AperiodicLink1());
        list.add(new AperiodicLink2());
        list.add(new DifferentialLink());
        list.add(new DifferentialLink1());
        list.add(new IntegratingLink());
        list.add(new OscillatoryLink());

        return list;
    }

    public static String formationFunction(ObservableList<Link> linksList) {
        StringBuilder finishFunction= new StringBuilder();
        String[] numerators;
        String[] denominators;

        int countNum=0,countDen=0;

        for (Link aLinksList : linksList) {
            if (aLinksList instanceof AperiodicLink1) countDen++;
            if (aLinksList instanceof AperiodicLink2 || aLinksList instanceof OscillatoryLink) countDen++;
            if (aLinksList instanceof DifferentialLink1) countNum++;
        }

        numerators=new String[countNum];
        denominators=new String[countDen];

        for (int l=0;l<numerators.length;l++){
            numerators[l]="";
        }
        for (int l=0;l<denominators.length;l++){
            denominators[l]="";
        }

        double k=1;
        int countDifferentialLinks=0;
        int countIntegrationLinks=0;
        int a=0,b=0;

        for (int c=0;c<MainModel.getListLinks().size();c++){
            if(MainModel.getListLinks().get(c) instanceof IntegratingLink){
                countIntegrationLinks++;
            }else if(MainModel.getListLinks().get(c) instanceof DifferentialLink){
                countDifferentialLinks++;
            }
        }
        for (Link l:MainModel.getListLinks()){
            k=k*l.getValueK();
        }
        for (Link link : linksList) {
            if (link instanceof AperiodicLink1) {
                denominators[b] = "(" + link.getValueT() + "s + 1)";
                b++;
            }
            if (link instanceof AperiodicLink2 || link instanceof OscillatoryLink) {
                DecimalFormat df=new DecimalFormat("#.###");
                df.setRoundingMode(RoundingMode.CEILING);
                double t2=Math.pow(link.getValueT(), 2);
                double fixT2=Double.parseDouble(df.format(t2).replace(",","."));

                denominators[b] = "(" + fixT2 + "s² + " + link.getValueT2Ksi() + "s + 1)";
                b++;
            }
            if (link instanceof DifferentialLink1) {
                numerators[a] = "(" + link.getValueT() + "s + 1)";
                a++;
            }
        }
        if(countDifferentialLinks>1) {
            finishFunction = new StringBuilder(k + "s^" + countDifferentialLinks);
        }else if(countDifferentialLinks==1){
            finishFunction = new StringBuilder(k + "s");
        }else if(countDifferentialLinks==0){
            finishFunction = new StringBuilder(k + "");
        }

        for (String numerator : numerators) {
            finishFunction.append(numerator);
        }
        if(countDen>0) {
            finishFunction.append("/");

            if (countIntegrationLinks > 1) {
                finishFunction.append("s^").append(countIntegrationLinks);
            } else if (countIntegrationLinks == 1) {
                finishFunction.append("s");
            }
            for (String denominator : denominators) {
                finishFunction.append(denominator);
            }
        }

        return finishFunction.toString();

    }

    public void setTransferFunction(String transferFunction) {
        this.transferFunction = transferFunction;
    }

    public String getTransferFunction() {
        return transferFunction;
    }
}
