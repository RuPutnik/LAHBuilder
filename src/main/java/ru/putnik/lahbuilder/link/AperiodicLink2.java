package ru.putnik.lahbuilder.link;

/**
 * Создано 13.01.2019 в 16:12
 */
public class AperiodicLink2 extends Link {
    public AperiodicLink2(double t,double t2ksi,double k){
        valueT=t;
        valueKsi=t2ksi/(2*t);
        valueK=k;
    }
    public AperiodicLink2(double t,double t2ksi){
        valueT=t;
        valueKsi=t2ksi/(2*t);
    }
    @Override
    public String getNameLink() {
        return "Апериодическое звено 2-го порядка";
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
