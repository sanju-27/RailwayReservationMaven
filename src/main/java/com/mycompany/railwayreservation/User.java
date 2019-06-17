package com.mycompany.railwayreservation;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    String name, phone;
    int age, uID;
    List<Pnr> bookedPnr;

    /**
     *
     * @param uID
     * @param name
     * @param phone
     * @param age
     */
    public User(int uID, String name, String phone, int age) {
        this.name = name;
        this.uID = uID;
        this.phone = phone;
        this.age = age;
        bookedPnr = new ArrayList<>();
    }

    User(int uid) {
        this.uID = uid; //To change body of generated methods, choose Tools | Templates.
    }

    public Pnr bookTicket(Train tr, int count, int tID, boolean tat, String from, String to) {
        Ticket temp = new Ticket(tID);
        Pnr p=null;
        if (tat == false) {
            if (tr.available.contains(temp)) {
                Ticket x = tr.available.get(tr.available.indexOf(temp));
                    p = new Pnr(tr, this, x, count, from, to);
                if (x.book(count)) {
                    p.status = p.status.concat("CNF");
                    bookedPnr.add(p);
                    tr.available.set(tr.available.indexOf(temp), x);
                } else {
//                    Pnr p = new Pnr(tr, this, x, count);
                    p.status = p.status.concat("WL");
                    bookedPnr.add(p);
                    //                tr.available.set(tr.available.indexOf(temp), x);
                    tr.waitList.add(p);
                }
                return p;
            } 
        } else if (tr.tatkal.contains(temp)) {
            Ticket x = tr.tatkal.get(tr.tatkal.indexOf(temp));
            if (x.book(count)) {
                p = new Pnr(tr, this, x, count, from, to);
                p.status = p.status.concat("TKL");
                bookedPnr.add(p);
                tr.tatkal.set(tr.tatkal.indexOf(temp), x);

            } else {
                p = new Pnr(tr, this, x, count, from, to);
                p.status = p.status.concat("WTKL");
                bookedPnr.add(p);
                //                tr.tatkal.set(tr.tatkal.indexOf(temp), x);
                tr.tWaitList.add(p);
            }
        } //kax
            return p;
    }

    @Override
    public String toString() {
        return "User{\n" +"uID=" + (uID+1) + "name=" + name + ", phone=" + phone + ", age=" + age +  ",\nbookedPnr=" + bookedPnr + "\n}";
    }
    

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(uID).append(",").append(name).append(",").append(phone).append(",").append(age).append("\n");
//        for (Pnr pnr : bookedPnr) {
//            sb.append(pnr.pnrNo).append(",");
//            
//        }
//        return sb.toString();
//    }
    public void writeFile()
    {
        String fname = "C:/Users/Administrator/Desktop/Reservation/users/"+this.uID+".txt";
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fname));
            bw.write(this.toString());
            bw.close();
        } catch (IOException ex) {
            System.err.println("Exception at User"+ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.uID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return this.uID == other.uID;
    }

}
