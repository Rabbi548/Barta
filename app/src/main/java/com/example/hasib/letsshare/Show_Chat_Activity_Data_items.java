package com.example.hasib.letsshare;

public class Show_Chat_Activity_Data_items {
    private String Email;
    private String Name;

    public Show_Chat_Activity_Data_items(){


    }

    public Show_Chat_Activity_Data_items(String email, String name) {
        Email = email;
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
