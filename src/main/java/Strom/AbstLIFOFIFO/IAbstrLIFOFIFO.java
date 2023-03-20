package Strom.AbstLIFOFIFO;

import java.util.Iterator;

public interface IAbstrLIFOFIFO<T>  {
    void zrus();
    boolean jePrazdny();
    void vloz(T data);
    T odeber() ;


    Iterator<T> vytvorIterator();
}
