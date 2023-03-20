package Strom.AbstrHeap;

import java.util.Iterator;

public interface IAbstrHeap {
    void vybuduj();
    void prebuduj();
    void zrus();
    boolean jePrazdny();
    void vloz();
    void odeberMax();
    void zpristupniMax();
    Iterator vytvorIterator();



}
