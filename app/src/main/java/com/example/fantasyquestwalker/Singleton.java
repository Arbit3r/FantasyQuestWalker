package com.example.fantasyquestwalker;
import java.util.*;
public class Singleton {
    private static final Singleton ourInstance = new Singleton();
    private ArrayList<Journey> matkat;

    public static Singleton getInstance() {
        return ourInstance;
    }
    private Singleton() {
        matkat = new ArrayList<Journey>();
        matkat.add(new Journey("Mordor", 2863));
    }
    public ArrayList<Journey> getMatkat(){
        return matkat;
    }
    public Journey getMatkat(int i){
        return matkat.get(i);

    }
}
