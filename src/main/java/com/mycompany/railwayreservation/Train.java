package com.mycompany.railwayreservation;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Train implements Serializable{
    private static final long serialVersionUID = 1L;
    int trainNo;
    List<Ticket> available = new ArrayList<>();
//    Map<User, Pnr> waitList = new LinkedHashMap<>();
    List<Pnr> waitList = new ArrayList<>();
    List<Pnr> tWaitList = new ArrayList<>();
    List<Pnr> names = new ArrayList<>();
    List<Ticket> tatkal = new ArrayList<>();
    Map<String,String> route = new HashMap<>();

    public Train(int trainNo) {
        this.trainNo = trainNo;
    }

    @Override
    public String toString() {
        return "Train{\n" + "trainNo=" + trainNo + ", available=" + available + ", waitList=" + waitList + ", tWaitList=" + tWaitList + ", names=" + names + ", tatkal=" + tatkal + ", route=" + route + '\n'+'}';
    }

   

    public void setAvailable(List<Ticket> available) {
        this.available.addAll(available);
    }

    public void setRoute(Map<String, String> route) {
        this.route = route;
    }

    public void setTatkal(List<Ticket> tatkal) {
        this.tatkal.addAll(tatkal);
    }
    public String getAvaiable()
    {
        StringBuilder sb = new StringBuilder();
        for (Ticket ticket : available) {
            sb.append(ticket.toString()); 
        }
        return sb.toString();
    }
    public String getWaitlist()
    {
        StringBuilder sb = new StringBuilder();
        for (Pnr p : waitList) {
            sb.append(p.toString());
        }
        return sb.toString();
    }
    public String getTWaitlist()
    {
        StringBuilder sb = new StringBuilder();
        for (Pnr p : tWaitList) {
            sb.append(p.toString());
        }
        return sb.toString();
    }
    public String getNames()
    {
        StringBuilder sb = new StringBuilder();
        for (Pnr p : names) {
            sb.append(p.toString());
        }
        return sb.toString();
    }
    public String getTatkal()
    {
        StringBuilder sb = new StringBuilder();
        for (Ticket ticket : tatkal) {
            sb.append(ticket.toString()); 
        }
        return sb.toString();
    }
    public void writeFile()
    {
        String fAvailable = "C:/Users/Administrator/Desktop/Reservation/trains/"+this.trainNo;
        String fWaitlist = "C:/Users/Administrator/Desktop/Reservation/trains/"+this.trainNo;
        String fTWaitlist = "C:/Users/Administrator/Desktop/Reservation/trains/"+this.trainNo;
        String fNames = "C:/Users/Administrator/Desktop/Reservation/trains/"+this.trainNo;
        String fTatkal = "C:/Users/Administrator/Desktop/Reservation/trains/"+this.trainNo;
        BufferedWriter bw;
//        FileWriter fw = null;
        try
        {
            new File(fAvailable).mkdirs();
            bw = new BufferedWriter(new FileWriter(fNames+"/num.txt"));
            bw.write(this.trainNo);
            bw.close();
            bw = new BufferedWriter(new FileWriter(fAvailable+"/available.txt"));
            bw.write(this.getAvaiable());
            bw.close();
            bw = new BufferedWriter(new FileWriter(fNames+"/names.txt"));
            bw.write(this.getNames());
            bw.close();
            bw = new BufferedWriter(new FileWriter(fTWaitlist+"/twaitlist.txt"));
            bw.write(this.getTWaitlist());
            bw.close();
            bw = new BufferedWriter(new FileWriter(fTatkal+"/tatkal.txt"));
            bw.write(this.getTatkal());
            bw.close();
            bw = new BufferedWriter(new FileWriter(fWaitlist+"/waitlist.txt"));
            bw.write(this.getWaitlist());
            bw.close();
        } catch (IOException ex) {
            System.err.println("Caught exception when writing to file at Train"+ex);
        }
        
    }

    @Override
    public boolean equals(Object obj) {
        if(this.getClass()!=obj.getClass())return false;
        if(obj==null)return false;
        if(obj==this)return true;
        Train t = (Train)obj;
        return this.trainNo == t.trainNo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.trainNo;
        return hash;
    }
}
