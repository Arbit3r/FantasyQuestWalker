package com.example.fantasyquestwalker;

public class Journey {
    private String nimi;
    private float matka;

        public Journey(String nimi, float matka){
            this.nimi = nimi;
            this.matka = matka;
        }

    public String getNimi() { return nimi; }

    public float getMatka(){ return matka; }

    @Override
    public String toString(){ return nimi; }
}
