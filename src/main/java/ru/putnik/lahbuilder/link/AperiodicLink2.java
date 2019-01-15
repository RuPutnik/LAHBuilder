package ru.putnik.lahbuilder.link;

/**
 * Создано 13.01.2019 в 16:12
 */
public class AperiodicLink2 extends Link {
    public AperiodicLink2(double t,double t2ksi,double k){
        valueT=t;
        valueT2Ksi=t2ksi;
        valueK=k;
    }
    public AperiodicLink2(double t,double t2ksi){
        valueT=t;
        valueT2Ksi=t2ksi;
    }
    public AperiodicLink2(){}
    @Override
    public String getNameLink() {
        return "Апериодическое звено 2-го порядка";
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
    public double getIncline() {
        return -40;
    }
}
