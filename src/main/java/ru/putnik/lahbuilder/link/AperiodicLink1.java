package ru.putnik.lahbuilder.link;

/**
 * Создано 13.01.2019 в 16:11
 */
public class AperiodicLink1 extends Link{
    public AperiodicLink1(double t,double k){
        valueT=t;
        valueK=k;
    }
    public AperiodicLink1(double t){
        valueT=t;
    }
    public AperiodicLink1(){}
    @Override
    public String getNameLink() {
        return "Апериодическое звено 1-го порядка";
    }

    @Override
    public double getValueT() {
        return valueT;
    }

    @Override
    public double getValueT2Ksi() {
        return 0;
    }

    @Override
    public double getValueK(){
        return valueK;
    }
}
