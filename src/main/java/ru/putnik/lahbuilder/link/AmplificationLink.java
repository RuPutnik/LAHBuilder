package ru.putnik.lahbuilder.link;

/**
 * Создано 13.01.2019 в 16:15
 */
public class AmplificationLink extends Link {
    private double valueK;
    public AmplificationLink(double k){
        valueK=k;
    }


    public static String getNameLink() {
        return "Усилительное звено";
    }

    @Override
    public double getValueK(){
        return valueK;
    }

    @Override
    public double getValueT() {
        return 0;
    }

    @Override
    public double getValueKsi() {
        return 0;
    }
}
