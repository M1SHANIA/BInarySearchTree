package Strom.Pamatky;

import Strom.Model.Zamek;
import Strom.Table.BinStromException;
import Strom.enun.eTypKey;
import Strom.enun.eTypProhl;
import java.util.Iterator;

public interface IPamatky {
    int importDatZTXT(String soubor) throws BinStromException;

       void vlozZamek(Zamek zamek) throws BinStromException;
    Zamek najdiZamek(String klic) throws BinStromException;
    Zamek odeberZamek(String klic) throws BinStromException;
    void zrus();
    void prebuduj() throws BinStromException;
    void nastavKlic(eTypKey typ);
    Zamek najdiNejbliz(String klic);

    Iterator vytvorIterator(eTypProhl typ);
}
