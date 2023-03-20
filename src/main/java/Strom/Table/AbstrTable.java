package Strom.Table;

import Strom.AbstLIFOFIFO.AbstrFifo;
import Strom.AbstLIFOFIFO.AbstrLifo;
import Strom.AbstLIFOFIFO.IAbstrLIFOFIFO;
import Strom.enun.eTypProhl;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V> {

    public AbstrTable() {
    }

    public static class Prvek<K, V> {
        public K key;
        public V value;
        Prvek<K, V> right;
        Prvek<K, V> left;
        private Prvek<K, V> prev;


        public Prvek(K key, V value, Prvek<K, V> right, Prvek<K, V> left, Prvek<K, V> prev) {
            this.key = key;
            this.value = value;
            this.right = right;
            this.left = left;
            this.prev = prev;
        }

    }

    private Prvek<K, V> root;


    @Override
    public void zrus() {
        root = null;

    }

    @Override
    public boolean jePrazdny() {
        return root == null;
    }

    @Override
    public V najdi(K key) throws BinStromException {

        if (jePrazdny()) {
            throw new BinStromException("Strom je prazdny");
        } else {
            Prvek<K, V> found = root;
            while (found != null) {
                int comp = key.compareTo(found.key);
                if (comp < 0) {
                    found = found.left;
                } else if (comp > 0) {
                    found = found.right;

                } else {
                    return found.value;
                }

            }
        }
        throw new BinStromException("Takovy prvek neexistuje ");
    }

    @Override
    public void vloz(K key, V value) throws BinStromException {
        if (jePrazdny()) {
            root = new Prvek<>(key, value, null, null, null);
        } else {
            Prvek<K, V> actual = root;
            while (true) {
                int compare = key.compareTo(actual.key);
                if (compare < 0) {
                    if (actual.left == null) {
                        actual.left = new Prvek<>(key, value, null, null, actual);
                        return;
                    }
                    actual = actual.left;
                } else  if(compare>0){
                    if (actual.right == null) {
                        actual.right = new Prvek<>(key, value, null, null, actual);
                        return;
                    }
                    actual = actual.right;
                }else {
                    throw new BinStromException("Chyba vlozeni");
                }

            }

        }


    }


    @Override
    public V odeber(K key) throws BinStromException {
        if (jePrazdny()) {
            throw new BinStromException("No elements inside");
        } else {
            Prvek<K, V> found = root;
            while (found != null) {
                int comp = key.compareTo(found.key);
                if (comp < 0) {
                    found = found.left;
                } else if (comp > 0) {
                    found = found.right;
                } else {
                    if (found.left == null && found.right == null) {
                        if (found.prev.right == found) {
                            found.prev.right = null;
                        } else if (found.prev.left == found) {
                            found.prev.left = null;
                        }
                        return found.value;
                    } else if (found.left == null) {
                        if (found.prev.right == found) {
                            found.prev.right = found.right;
                        } else if (found.prev.left == found) {
                            found.prev.left = found.right;
                        }
                        found.right.prev = found.prev;
                        return found.value;

                    } else if (found.right == null) {
                        if (found.prev.left == found) {
                            found.prev.left = found.left;
                        } else if (found.prev.right == found) {
                            found.prev.right = found.left;
                        }
                        found.left.prev = found.prev;
                        return found.value;
                    } else {
                        Prvek<K, V> help = found;
                        if (found.prev.left == found) {
                            found.prev.left = found.right;
                            found.right.prev = found.prev;
                        } else if (found.prev.right == found) {
                            found.prev.right = found.right;
                            found.right.prev = found.prev;
                        }
                        found = found.right;
                        while (found.left != null) {
                            found = found.left;
                        }
                        found.left = help.left;
                        found.left.prev = found;


                    }

                }

            }
        }
        return null;
    }


    @Override
    public Iterator<V> vytvorIterator(eTypProhl typ) {
        switch (typ) {
            case HLOUBKA -> {
                IAbstrLIFOFIFO<Prvek<K, V>> zasobnik = new AbstrLifo<>();
                zasobnik.vloz(root);
                return new Iterator<>() {
                    Prvek<K, V> actual = root;

                    @Override
                    public boolean hasNext() {
                        return !zasobnik.jePrazdny();
                    }

                    @Override
                    public V next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        while (actual != null && actual.left != null) {
                            zasobnik.vloz(actual.left);
                            actual = actual.left;
                        }

                        Prvek<K, V> element = zasobnik.odeber();

                        if (element.right != null) {
                            zasobnik.vloz(element.right);
                            actual = element.right;
                        }

                        return element.value;
                    }
                };
            }

            case SIRKA -> {
                IAbstrLIFOFIFO<Prvek<K, V>> fronta = new AbstrFifo<>();
                fronta.vloz(root);
                return new Iterator<V>() {
                    Prvek<K, V> actual = root;

                    @Override
                    public boolean hasNext() {
                        return actual != null && !fronta.jePrazdny();
                    }

                    @Override
                    public V next() {
                        actual = fronta.odeber();
                        if (actual.left != null) {
                            fronta.vloz(actual.left);
                        }
                        if (actual.right != null) {
                            fronta.vloz(actual.right);
                        }

                        return actual.value;
                    }
                };
            }
        }
        return null;
    }
    @Override
    public V najdiNejbliz(K key) {
        Prvek<K,V> act = root;
        int nejMensi = Integer.MAX_VALUE;
        V nejMen =null;
        while(act !=null){
            int comp = key.compareTo(act.key);
            int cislo = (int) Math.sqrt(Math.abs(comp*comp - nejMensi*nejMensi));
            if(cislo<nejMensi) {
                nejMensi = cislo;
                nejMen = act.value;
            }
            if(comp > 0) act = act.right;
            else if(comp < 0) act = act.left;
            else {
                return act.value;
            }
        }
        return nejMen;

    }
}
