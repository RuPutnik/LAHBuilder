package ru.putnik.lahbuilder.link;

/**
 * Создано 13.01.2019 в 22:14
 */
public class DifferentialLink extends Link {
    public DifferentialLink(double k){
        valueK=k;
    }
    public DifferentialLink(){}
    @Override
    public String getNameLink() {
        return "Дифференцирующее звено";
    }

    @Override
    public double getValueT() {
        return 0;
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
