package com.launchcode.cheese.models.data;

import com.launchcode.cheese.CheeseApplication;
import com.launchcode.cheese.models.Cheese;
import com.launchcode.cheese.models.CheeseRating;
import com.launchcode.cheese.models.CheeseType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class CheeseManager {
    //a collection of cheese objects
    private static HashMap<Integer, Cheese> cheeseTable = new HashMap<>();

    //CRUD methods
    //R --> all, individual
    //C --> create a cheese
    //U --> update an individual
    //D --> remove all, individual

    public static Collection<Cheese> getAll(){
        return cheeseTable.values();
    }

    public static Cheese getCheeseByID(int cheeseID){
        return cheeseTable.get(cheeseID);
    }

    public static <rating> void create(String name, String description, CheeseType type, CheeseRating rating){
        Cheese cheese = new Cheese(name,description,type, rating);
        add(cheese);
    }
    public static void add(Cheese cheese){
        cheeseTable.put(cheese.getCheeseID(), cheese);
    }

    public static void remove(int cheeseID){
        cheeseTable.remove(cheeseID);
    }
    public static void removeMany(int[] cheeseIDs){
        for(int cheeseID: cheeseIDs){
            cheeseTable.remove(cheeseID);
        }
    }
}
