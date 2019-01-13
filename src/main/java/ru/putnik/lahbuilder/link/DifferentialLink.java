package ru.putnik.lahbuilder.link;

/**
 * Создано 13.01.2019 в 16:13
 */
public class DifferentialLink extends Link {
    public DifferentialLink(double t, double k){
        valueT=t;
        valueK=k;
    }
    public DifferentialLink(double t){
        valueT=t;
    }
    public static String getNameLink() {
        return "Дифференцирующее звено";
    }

    @Override
    public double getValueT() {
        return valueT;
    }

    @Override
    public double getValueKsi() {
        return 0;
    }

    @Override
    public double getValueK(){
        return valueK;
    }
}
