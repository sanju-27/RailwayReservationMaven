package com.mycompany.railwayreservation;


import java.io.Serializable;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Pnr implements Serializable{
    private static final long serialVersionUID = 1L;
    String pnrNo;
    Train myTrain;
    String status;
    User uName;
    Ticket t;
    double bill;
    int count;
    String from, to;

    public Pnr(Train myTrain, User uName, Ticket t, int count, String from, String to) {
        this.myTrain = myTrain;
        this.uName = uName;
        this.from = from;
        this.to = to;
//        this.status = status;
        this.t = t;
        this.bill = count * (this.t.price);
        this.count = count;
        this.status = "";
        this.pnrNo = "Tr" + myTrain.trainNo + "U" + uName.name.charAt(0) + uName.name.charAt(1) + uName.name.charAt(2) + "Ti" + t.type;
    }
    
    public Pnr(User uname)
    {
        this.uName = uname;
    }

    public double getBill() {
        return this.bill;
    }

    public double getRefund() {

        double refund = this.getBill();
        if (this.status.equalsIgnoreCase("CNF")) {
            if (this.uName.age > 65) {
                refund = this.bill - this.t.cancelPrice * 0.9;
            } else {
                refund = this.bill - this.t.cancelPrice;
            }
        } else if (this.status.equalsIgnoreCase("WL")) {
            refund = this.bill;
        } else if (this.status.equals("TKL")) {
            refund = 0;
        }
        this.status = "CAN";
        return refund;
    }

    public void cancel() {
        double refund = 0;
        switch (this.status) {
            case "WL":
                if (!(this.myTrain.waitList.remove(this))) {
                    System.out.println("PNR number not found");
                    return;
                }
                refund = this.getRefund();
                break;
            case "WTKL":
                if (!(this.myTrain.tWaitList.remove(this))) {
                    System.out.println("PNR number not found");
                    return;
                }
                refund = this.getRefund();
                break;
            case "CNF":
                if (!(this.myTrain.names.remove(this))) {
                    System.out.println("PNR number not found");
                    return;
                }
                if (!(this.myTrain.waitList.isEmpty())) {
                    Pnr p = this.myTrain.waitList.get(0);
                    p.status = "CNF";
                    this.myTrain.names.add(p);
                    this.myTrain.waitList.remove(0);

                }
                refund = this.getRefund();
                break;
            case "TKL":
                if (!(this.myTrain.names.remove(this))) {
                    System.out.println("PNR number not found");
                    return;
                }
                if (!(this.myTrain.waitList.isEmpty())) {
                    Pnr p = this.myTrain.tWaitList.get(0);
                    p.status = "TKL";
                    this.myTrain.names.add(p);
                    this.myTrain.tWaitList.remove(0);

                }
                refund = this.getRefund();
                break;
            default:
                break;
        }

        System.out.println("PNR number : " + this.pnrNo + "\nStatus : CAN [Cancelled]\nRefunded amount : " + refund);
    }

    @Override
    public String toString() {
        return myTrain.trainNo + "," + uName.uID + "," + status + "," + bill + "," + count+"\n";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.uName);
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
        final Pnr other = (Pnr) obj;
        return other.uName.uID==this.uName.uID;
    }

}
