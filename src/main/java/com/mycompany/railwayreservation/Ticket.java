package com.mycompany.railwayreservation;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Ticket implements Serializable{
    private static final long serialVersionUID = 1L;
    int tID;
    String type;
    double price;
    int count;
    double cancelPrice;
    
    public Ticket(int tID, String type, double price, int count, double cancelPrice)
    {
        this.tID = tID;
        this.type = type;
        this.cancelPrice = cancelPrice;
        this.count = count;
        this.price = price;
    }

//    public Ticket(String type) {
//        this.type = type;
//    }

    public Ticket(int tID) {
        this.tID = tID;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        Ticket t = (Ticket)obj;
        return this.tID == t.tID;

//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17*hash + (this.tID != 0 ? this.tID : 0);
        return hash;
        
//To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean book(int n)
    {
        if(this.count<n)
            return false;
        else
            this.count-=n;
        return true;
    }    

    @Override
    public String toString() {
        return  tID + "," + type + "," + price + "," + count + "," + cancelPrice+"\n";
    }
    
}
