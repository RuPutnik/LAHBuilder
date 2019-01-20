package ru.putnik.lahbuilder.link;

/**
 * Создано 13.01.2019 в 16:12
 */
public class OscillatoryLink extends Link{
    public OscillatoryLink(double t, double t2ksi, double k){
        valueT=t;
        valueT2Ksi=t2ksi;
        valueK=k;
    }
    public OscillatoryLink(double t, double t2ksi){
        valueT=t;
        valueT2Ksi=t2ksi;
    }
    public OscillatoryLink(){}
    @Override
    public String getNameLink() {
        return "Колебательное звено";
    }

    @Override
    public double getValueT() {
        return valueT;
    }

    @Override
    public double getValueT2Ksi() {
        return valueT2Ksi;
    }

    @Override
    public double getValueK(){
        return valueK;
    }

    @Override
    public int getIncline() {
        return -40;
    }
}
