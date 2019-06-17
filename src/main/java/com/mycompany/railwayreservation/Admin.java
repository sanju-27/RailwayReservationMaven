package com.mycompany.railwayreservation;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class Admin implements Serializable{
    private static final long serialVersionUID = 1L;
     static final String fname = "C:/Users/Administrator/Desktop/Reservation/admin/save.txt";
//    static final String fname = "C:\\Users\\Sanju\\Documents\\GitHub\\RailwayReservation\\src\\save.txt";
    List<Train> trains = new ArrayList<>();
    List<User> users = new ArrayList<>();

    boolean addUser(User user) {
        return this.users.add(user);
    }

    boolean addTrain(Train train) {
        train.writeFile();
        return this.trains.add(train);
    }

    public void writeFile() {

        try {
            FileOutputStream f = new FileOutputStream(new File(fname));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(this);
//			o.writeObject(p2);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public static Object readFile() {
        Object o = null;
        try {
            FileInputStream fi = new FileInputStream(new File(fname));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            o = oi.readObject();
            oi.close();
            fi.close();
               
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
			
			e.printStackTrace();
	}
        return o;
    }
}
