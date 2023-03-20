package Strom.Table;

import Strom.enun.eTypProhl;

import java.util.Iterator;

public interface IAbstrTable <K extends Comparable<K>,V> {
    void zrus();
    boolean jePrazdny();
    V najdi(K key) throws BinStromException;
    void vloz(K key, V value) throws BinStromException;
    V odeber(K key) throws BinStromException;
    Iterator vytvorIterator(eTypProhl typ);

    V najdiNejbliz(K key);
}
