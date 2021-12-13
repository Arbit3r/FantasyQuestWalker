package com.example.fantasyquestwalker;

public class Journey {
    private String nimi;
    private int matka;

        public Journey(String nimi, int matka){
            this.nimi = nimi;
            this.matka = matka;
        }

    public String getNimi() { return nimi; }

    public int getMatka(){ return matka; }

    @Override
    public String toString(){ return nimi; }
}
