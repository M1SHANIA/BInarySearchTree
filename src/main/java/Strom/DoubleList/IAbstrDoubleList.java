package Strom.DoubleList;

import java.util.Iterator;

public interface IAbstrDoubleList <T> extends Iterable<T> {
    void zrus();

        boolean jePrazdny();

        void vlozPrvni(T data);

        void vlozNaslednika(T data) throws LinkedListException;

        void vlozPosledni(T data);

        void vlozPredchudce(T data) throws LinkedListException;

        T zpristupniAktualni() throws LinkedListException;

        T zpristupniPrvni() throws LinkedListException;

        T zpristupniPosledni() throws LinkedListException;

        T zpristupniNaslednika() throws LinkedListException;

        T zpristupniPredchudce() throws LinkedListException;

        T odeberAktualni() throws LinkedListException;

        T odeberPrvni() throws LinkedListException;

        T odeberPosledni() throws LinkedListException;

        T odeberNaslednika() throws LinkedListException;

        T odeberPredchudce() throws LinkedListException;

    }


