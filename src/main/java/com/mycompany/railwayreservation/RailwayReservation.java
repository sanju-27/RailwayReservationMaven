package com.mycompany.railwayreservation;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class RailwayReservation {
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    // static void writeFile(String fname, String str) {
    //     try {
    //         BufferedWriter bw = new BufferedWriter(new FileWriter(fname, true));
    //         bw.write(str);
    //     } catch (IOException ex) {
    //         System.err.println("Caught exception" + ex);
    //     }

    // }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Admin admin = (Admin) Admin.readFile();
        if (admin == null)
            admin = new Admin();
        // String userFile = "C:/Users/Administrator/Desktop/userFile";
        int id = 3;
        do {
            System.out.print("Welcome to Railway Reservation\nEnter ID / Enter -1 to exit: ");
            id = Integer.parseInt(br.readLine());
            if (id == 0) {
                int ch1;
                do {
                    System.out.println("Hello Admin\n1.User\n2.Trains\n3.Logout");
                    ch1 = Integer.parseInt(br.readLine());
                    if (ch1 == 1) {
                        int ch2;
                        do {
                        System.out.println("1.Add\n2.View\n3.Count\n4.Exit");
                            ch2 = Integer.parseInt(br.readLine());
                            switch (ch2) {
                            case 1:
                                System.out.print("Enter Name: ");
                                String name = br.readLine();
                                System.out.print("Enter Phone number: ");
                                String ph = br.readLine();
                                System.out.print("Entetr age: ");
                                int age = Integer.parseInt(br.readLine());
                                admin.users.add(new User(admin.users.size(), name, ph, age));
                                break;
                            case 2:
                                System.out.print("Enter UID: ");
                                int uid = Integer.parseInt(br.readLine());
                                if(admin.users.isEmpty())
                                    System.out.println("No existing users");
                                if(!admin.users.contains(new User(uid-1)))
                                    System.out.println("User Not Found");
                                System.out.println(admin.users.get(uid-1).toString());
                                break;
                            case 3:
                                System.out.println("Total number of users: " + admin.users.size());
                            }
                        } while (ch2 < 4);
                    }
                    if (ch1 == 2) {
                        int ch2;
                        do {
                        System.out.println("1.Add\n2.View\n3.Count\n4.Exit");
                            ch2 = Integer.parseInt(br.readLine());
                            switch (ch2) {
                            case 1:
                                System.out.print("Enter Train number: ");
                                int num = Integer.parseInt(br.readLine());
                                Train t = new Train(num);
                                List<Ticket> gen = new ArrayList<>();
                                List<Ticket> tat = new ArrayList<>();
                                System.out.print("Enter Type count: ");
                                int n = Integer.parseInt(br.readLine());
                                System.out.println("TID\tType\tGeneral\tTatkal\tPrice\tCancel Fee");
                                for (int i = 0; i < n; i++) {
                                    String[] s = br.readLine().split("\t");
                                    Ticket gt = new Ticket(Integer.parseInt(s[0]), s[1], Double.valueOf(s[4]),
                                            Integer.parseInt(s[2]), Double.valueOf(s[5]));
                                    Ticket tt = new Ticket(Integer.parseInt(s[0]), s[3], Double.valueOf(s[4]),
                                            Integer.parseInt(s[2]), Double.valueOf(s[5]));
                                    gen.add(gt);
                                    tat.add(tt);
                                }
                                Map<String, String> mp = new HashMap<>();
                                System.out.println("Enter Route Details\n");
                                System.out.print("Number of Stops: ");
                                n = Integer.parseInt(br.readLine());
                                System.out.println("Place\tTime");
                                for (int i = 0; i < n; i++) {
                                    String s[] = br.readLine().split("\t");
                                    mp.put(s[0], s[1]);
                                }
                                t.setRoute(mp);
                                t.setAvailable(gen);
                                t.setTatkal(tat);
                                admin.trains.add(t);
                                break;
                            case 2:
                                System.out.print("Enter Train number: ");
//                                int tid = Integer.parseInt(br.readLine());
//                                Train tx = new Train(tid);
//                                System.out.println(admin.users.get(admin.users.indexOf(tx)).toString());
                                for(Train tx: admin.trains)
                                    System.out.println(tx.toString());
                                break;
                            case 3:
                                System.out.println("Total number of Trains: " + admin.trains.size());
                            }
                        } while (ch2 < 4);
                    }
                } while (ch1 < 3);
            } else if(id>0) {
                User u = admin.users.get(id-1);
                System.out.println("Welcome back " + u.name);
                int ch1;
                do {
                    System.out.println("1.Book Train\n2.View Booked PNR\n3.Cancel Tickets\n4.View My Details\n5. Exit");
                    ch1 = Integer.parseInt(br.readLine());
                    switch (ch1) {
                    case 1:
                        Pnr p = bookTicket(admin, id-1);
                        viewPNR(p);
                        break;
                    case 2:
                        viewPNR(u);
                        break;
                    case 3:
                        viewPNR(u);
                        System.out.print("Select Ticket to cancel: ");
                        int n = Integer.parseInt(br.readLine());
                        Pnr pnr = u.bookedPnr.get(n-1);
                        pnr.cancel();
                        break;
                        
                    case 4:
                        System.out.println(u.toString());
                        break;
                    }
                } while (ch1 < 5);
            }
        admin.writeFile();
        } while (id >= 0);

    }

    private static Pnr bookTicket(Admin admin, int uid) throws IOException {
        User u = admin.users.get(uid);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Tatkal? [Y/N]");
        boolean tat = br.readLine().equalsIgnoreCase("Y");
        String from, to;
        List<Train> ls = new ArrayList<>();
        dest: {
            System.out.println("Enter the from place");
            from = br.readLine();
            System.out.println("Enter to place");
            to = br.readLine();
            if (from.equalsIgnoreCase(to)) {
                System.out.println("Error! From cannot be same as To\nTry again...");
                break dest;
            }
            for (Train t : admin.trains) {
                if (t.route.containsKey(from)) {
                    if (t.route.containsKey(to))
                        ls.add(t);
                }
            }
        }
        if(ls.isEmpty())
        {
            System.out.println("No Trains Available");
            return null;
        }
        System.out.println("Available Trains:");
        for (Train t : ls) {
            System.out.println(t.trainNo);
        }
        System.out.println("Choose train number");
        int tno = Integer.parseInt(br.readLine());
        Train t = new Train(tno);
        t = admin.trains.get(admin.trains.indexOf(t));
        System.out.println("Available tickets:");
        int i = 1;
        int tid;
        if (tat) {
            for (Ticket x : t.tatkal) {
                System.out.print(i++ + ". ");
                System.out.println(x.type + " - " + x.count);
            }
            System.out.println("Enter your choice...");
            i = Integer.parseInt(br.readLine());
            tid = t.tatkal.get(i - 1).tID;
        }

        else {
            for (Ticket x : t.available) {
                System.out.print(i++ + ". ");
                System.out.println(x.type + " - " + x.count);
            }
            System.out.println("Enter your choice...");
            i = Integer.parseInt(br.readLine());
            tid = t.available.get(i - 1).tID;
        }
        System.out.println("Enter number of Tickets");
        int count = Integer.parseInt(br.readLine());
        Pnr p = u.bookTicket(t, count, tid, tat, from, to);
        return p;
    }

    private static void viewPNR(Pnr p) {
        System.out.println("PNR Status of " + p.pnrNo);
        System.out.println("Ticket Type: " + p.t.type);
        System.out.println("From: " + p.from + " To: " + p.to);
        System.out.println("Bill: " + p.bill);
        System.out.println("Number of tickets: " + p.count);
        System.out.println("Booking Status: " + p.status);
    }

    private static void viewPNR(User u) {
        System.out.println("Tickets booked by " + u.name);
        int i = 1;
        for (Pnr p : u.bookedPnr) {
            System.out.print(i+++". ");
            viewPNR(p);
        }
    }
}
