package ru.putnik.lahbuilder.link;

/**
 * Создано 06.01.2019 в 21:42
 */
public abstract class Link implements Cloneable{
    protected double valueT;
    protected double valueT2Ksi;//for oscillatory
    protected double valueK=1;

    public Link(){}

    public abstract String getNameLink();

    public abstract double getValueT();

    public abstract double getValueT2Ksi();

    public abstract double getValueK();

    public void setValueT(double valueT) {
        this.valueT = valueT;
    }

    public void setValueT2Ksi(double valueT2Ksi) {
        this.valueT2Ksi = valueT2Ksi;
    }

    public void setValueK(double valueK) {
        this.valueK = valueK;
    }

    @Override
    public Link clone(){
        try {
            return (Link)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return getNameLink();
    }
}
