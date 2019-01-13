package ru.putnik.lahbuilder.link;

/**
 * Создано 06.01.2019 в 21:42
 */
public abstract class Link {
    protected double valueT;
    protected double valueKsi;//for oscillatory
    protected double valueK=1;

    public static String getNameLink(){
        return "";
    }

    public abstract double getValueT();

    public abstract double getValueKsi();

    public abstract double getValueK();
}
