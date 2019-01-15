package ru.putnik.lahbuilder.link;

/**
 * Создано 13.01.2019 в 16:13
 */
public class IntegratingLink extends Link{
    public IntegratingLink(double k){
        valueK=k;
    }
    public IntegratingLink(){}

    @Override
    public String getNameLink() {
        return "Интегрирующее звено";
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

    @Override
    public double getIncline() {
        return 0;
    }
}
