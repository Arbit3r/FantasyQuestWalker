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
        matkat.add(new Journey("500 miles and 500 more", 1609));
        matkat.add(new Journey("Journey to the West", 5794));
        matkat.add(new Journey("Route 66", 3944));
        matkat.add(new Journey("The Orient Express", 2989));
        matkat.add(new Journey("Run through Finland", 1850));
        matkat.add(new Journey("Marathon", 42));
    }
    public ArrayList<Journey> getMatkat(){
        return matkat;
    }
    public Journey getMatkat(int i){
        return matkat.get(i);

    }
}
