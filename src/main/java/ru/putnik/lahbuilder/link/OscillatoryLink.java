package ru.putnik.lahbuilder.link;

/**
 * Создано 13.01.2019 в 16:12
 */
public class OscillatoryLink extends Link{
    public OscillatoryLink(double t, double t2ksi, double k){
        valueT=t;
        valueKsi=t2ksi/(2*t);
        valueK=k;
    }
    public OscillatoryLink(double t, double t2ksi){
        valueT=t;
        valueKsi=t2ksi/(2*t);
    }

    @Override
    public String getNameLink() {
        return "Колебательное звено";
    }

    @Override
    public double getValueT() {
        return valueT;
    }

    @Override
    public double getValueKsi() {
        return valueKsi;
    }

    @Override
    public double getValueK(){
        return valueK;
    }
}
