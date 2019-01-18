package ru.putnik.lahbuilder;

import ru.putnik.lahbuilder.link.Link;

import java.util.Comparator;

/**
 * Создано 18.01.2019 в 18:38
 */
public class ComparatorLink implements Comparator<Link> {
    @Override
    public int compare(Link o1, Link o2) {
        return Double.compare(1 / o1.getValueT(), 1 / o2.getValueT());
    }
}
