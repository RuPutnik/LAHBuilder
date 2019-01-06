package ru.putnik.lahbuilder;

/**
 * Создано 06.01.2019 в 21:42
 */
public class Link {
    private Type typeLink;
    private double valueT;
    private double valueKsi;//for oscillatory

    public Link(Type nameLink, double valueT) {
        this.typeLink = nameLink;
        this.valueT = valueT;
    }

    public Link(Type nameLink, double valueT, double valueKsi) {
        this.typeLink = nameLink;
        this.valueT = valueT;
        this.valueKsi = valueKsi;
    }

    public String getNameLink() {
        return typeLink.toString();
    }

    public void setNameLink(Type nameLink) {
        this.typeLink = nameLink;
    }

    public double getValueT() {
        return valueT;
    }

    public void setValueT(double valueT) {
        this.valueT = valueT;
    }

    public double getValueKsi() {
        return valueKsi;
    }

    public void setValueKsi(double valueKsi) {
        this.valueKsi = valueKsi;
    }

    public enum Type{
        Апериодическое1, Апериодическое2, Колебательное, Интегрирующее, Дифференцирующее, Усилитель;

        @Override
        public String toString() {
            if(this==Апериодическое1){
                return "Апериодическое 1-го порядка";
            }else if(this==Апериодическое2){
                return "Апериодическое 2-го порядка";
            }
            return super.toString();
        }
    }
}
