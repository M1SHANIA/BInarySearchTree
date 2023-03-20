package Strom.AbstLIFOFIFO;

import Strom.DoubleList.AbstrDoubleList;
import Strom.DoubleList.IAbstrDoubleList;
import Strom.DoubleList.LinkedListException;

import java.util.Iterator;

public class AbstrLifo<T> implements IAbstrLIFOFIFO<T>{
    IAbstrDoubleList<T> list;
    public AbstrLifo() {
        list=new AbstrDoubleList<>();
    }
    @Override
    public void zrus() {
        list.zrus();

    }

    @Override
    public boolean jePrazdny() {
        return list.jePrazdny();
    }

    @Override
    public void vloz(T data) {
        list.vlozPosledni(data);

    }

    @Override
    public T odeber()  {
        try {
            return list.odeberPosledni();
        } catch (LinkedListException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator vytvorIterator() {
        return list.iterator();
    }


}
