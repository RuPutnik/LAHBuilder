package ru.putnik.lahbuilder.link;

/**
 * Создано 13.01.2019 в 16:13
 */
public class DifferentialLink1 extends Link {
    public DifferentialLink1(double t, double k){
        valueT=t;
        valueK=k;
    }
    public DifferentialLink1(double t){
        valueT=t;
    }
    public DifferentialLink1(){}
    @Override
    public String getNameLink() {
        return "Дифференцирующее звено 1-го порядка";
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
