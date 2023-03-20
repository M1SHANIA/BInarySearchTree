package Strom.AbstLIFOFIFO;

import Strom.DoubleList.AbstrDoubleList;
import Strom.DoubleList.IAbstrDoubleList;
import Strom.DoubleList.LinkedListException;

import java.util.Iterator;

public class AbstrFifo<T> implements IAbstrLIFOFIFO<T>{
 IAbstrDoubleList<T> list;
    public AbstrFifo() {
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
    public T odeber() {

        try {
            return list.odeberPrvni();
        } catch (LinkedListException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Iterator<T> vytvorIterator() {
        return list.iterator();
    }


}
