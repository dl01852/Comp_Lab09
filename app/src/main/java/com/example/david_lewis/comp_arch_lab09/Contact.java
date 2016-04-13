package com.example.david_lewis.comp_arch_lab09;

/**
 * Created by David on 4/12/2016.
 *
 * This class will hold get and set data about a particular contact.
 * This will make it easier to get the data to display
 */



public class Contact {

    private int id;
    private String name;
    private String phone;
    private String street;
    private String email;
    private String city;


    public Contact(int id, String name, String phone, String street, String email, String city)
    {
        // Fully Loaded constructor
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.email = email;
        this.city = city;
    }

    public Contact(String name, String phone, String street, String email, String city)
    {
        //fully loaded constructor minus the id... ID will get created when contact is inserted into DB.
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.email = email;
        this.city = city;
    }

    public Contact()
    {
        // just a blank constructor. Not sure if i need this or not but it's here.
    }

    // Getters
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public String getPhone(){return this.phone;}
    public String getStreet(){return this.street;}
    public String getEmail(){return this.email;}
    public String getCity(){return this.city;}

    // Setters
    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setPhone(String phone){this.phone = phone;}
    public void setStreet(String street){this.street = street;}
    public void setEmail(String email){this.email = email;}
    public void setCity(String city){this.city = city;}
}
