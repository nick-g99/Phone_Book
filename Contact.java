package com.example.phonebook;
/*
This is a utility class.
This will allow for contact objects.
 */
public class Contact {
    private String firstName,lastName,phoneNumber;

    public Contact() {}

    public Contact(String f, String l, String p) {
        firstName = f;
        lastName = l;
        phoneNumber = p;
    }

    public void setFirstName(String f) {this.firstName = f;}

    public void setLastName(String l) {this.lastName = l;}

    public void setPhoneNumber(String p) {phoneNumber = p;}

    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public String getPhoneNumber() {return phoneNumber;}
}
