package Strom.Pamatky;

import Strom.Model.GPS;
import Strom.Model.Zamek;
import Strom.Table.AbstrTable;
import Strom.Table.BinStromException;
import Strom.Table.IAbstrTable;
import Strom.enun.eTypKey;
import Strom.enun.eTypProhl;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Pamatky implements IPamatky {

    private IAbstrTable<Zamek, Zamek>bvs;
    private eTypKey actTyp;

    public Pamatky(IAbstrTable<Zamek, Zamek> bvs, eTypKey actTyp) {
        this.bvs = new AbstrTable<>();
        this.actTyp = eTypKey.NAZEV;
    }
    public Pamatky() {
        this.bvs = new AbstrTable<>();
        this.actTyp = eTypKey.GPS;

    }


    @Override
    public int importDatZTXT(String soubor) throws BinStromException {
        int pocet=0;
        try{
            Scanner scanner = new Scanner(new File(soubor));

            while (scanner.hasNext()){
                pocet++;
                String[]result=scanner.nextLine().split("\\s+");
                Zamek zamek = new Zamek(pocet, result[0],new GPS(gps(result[1]+result[2]),gps(result[3]+result[4])), actTyp);
                System.out.println(zamek);
                bvs.vloz(zamek,zamek);
            }


        } catch (FileNotFoundException e) {
            error("File was not found");
        }

        return pocet;
    }

    @Override
    public void vlozZamek(Zamek zamek) throws BinStromException {

        bvs.vloz(zamek,zamek);
    }

    @Override
    public Zamek najdiZamek(String klic) throws BinStromException {
        switch (actTyp){
            case NAZEV -> {
                return bvs.najdi(new Zamek(0,klic,null,actTyp));
            }
            case GPS -> {
                String[] gps= klic.trim().split(" ");
                return bvs.najdi(new Zamek(0,null,new GPS(Integer.parseInt(gps[0]),Integer.parseInt(gps[1])),actTyp));
            }
        }
        return null;


    }

    @Override
    public Zamek odeberZamek(String klic) throws BinStromException {
        switch (actTyp){
            case GPS ->{
                String[]gps=klic.trim().split(" ");
                return bvs.odeber(new Zamek(0,null,new GPS(Integer.parseInt(gps[0]),Integer.parseInt(gps[1])),actTyp));
            }
            case NAZEV -> {
                return bvs.odeber(new Zamek(0,klic,null,actTyp));
            }
        }
        return null;
    }

    @Override
    public void zrus() {bvs.zrus();}

    @Override
    public void prebuduj()throws BinStromException {
        Iterator<Zamek>iterator= bvs.vytvorIterator(eTypProhl.HLOUBKA);
        List<Zamek> zamekList=new ArrayList<>();
        while (iterator.hasNext()){
            Zamek zamek=iterator.next();
            zamek.setTyp(actTyp);
            zamekList.add(zamek);
        }
        Comparator<Zamek>zamekComparator=null;
        switch (actTyp){
            case NAZEV -> {
               zamekComparator= Comparator.comparing(Zamek::getNazev);
            }
            case GPS -> {
                zamekComparator=Comparator.comparing(Zamek::getGps);
            }
        }
        zamekList.sort(zamekComparator);
        bvs.zrus();
        prebuduj(zamekList);
    }
    private void prebuduj(List<Zamek> list) throws BinStromException {
        if (list.size() == 0) {
            return;
        }
        if (list.size() == 1) {
            vlozZamek(list.get(0));
            return;
        }
        int mid = list.size()/2;
        vlozZamek(list.get(mid));
        prebuduj(list.subList(0, mid));
        prebuduj(list.subList(mid +1, list.size()));
    }

    @Override
    public void nastavKlic(eTypKey typ) {
        switch (typ){
            case GPS -> actTyp=eTypKey.GPS;
            case NAZEV -> actTyp=eTypKey.NAZEV;
        }

    }

    @Override
    public Zamek najdiNejbliz(String klic) {
        if(actTyp==eTypKey.GPS){
            String[]gps=klic.trim().split(" ");
            return bvs.najdiNejbliz(new Zamek(0,null,new GPS(Integer.parseInt(gps[0]),Integer.parseInt(gps[1])),actTyp));
        }

        return null;
    }

    @Override
    public Iterator vytvorIterator(eTypProhl typ) {
        return bvs.vytvorIterator(typ);
    }
    private void error(String message) {
        new Alert(Alert.AlertType.ERROR,message, ButtonType.OK).show();
    }
    private int gps(String line){
        line = line.substring(1);
        String[]gps=line.split("\\.");
        return Integer.parseInt(String.valueOf(gps[0]+gps[1].charAt(0)));
    }
    public eTypKey getActualKlic(){
        return actTyp;
    }

}
