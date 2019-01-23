package com.example.katerynastorozh.newsapi.login.login.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel implements IUser {
    public String email;
    public String paasw;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String phone;
    private String dob;


    public UserModel(String name, String paasw) {
        this.email = name;
        this.paasw = paasw;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPasswd() {
        return null;
    }

    @Override
    public int checkUserValidity(String inputName, String inputPass) {

    //TODO create class for work woth database
        System.out.println("Here");

       /* if (inputName==null||inputPass==null||!inputName.equals(name)||!inputPass.equals(paasw))
      {
          return -1;
      }*/
        return 0;

    }


}
