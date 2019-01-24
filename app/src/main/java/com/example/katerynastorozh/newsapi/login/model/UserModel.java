package com.example.katerynastorozh.newsapi.login.model;

public class UserModel implements IUser {
    public String email;
    public String paasw;
    public String firstName;
    public String lastName;
    public String country;
    public String city;
    public String phone;
    public String dob;


    public UserModel() {
    }


    private UserModel(UserBuilder builder)
    {
        email = builder.email;
        paasw = builder.paasw;
        firstName = builder.firstName;
        lastName = builder.lastName;
        country = builder.country;
        city = builder.city;
        phone = builder.phone;
        dob = builder.dob;
    }

  /*  public UserModel(String name, String paasw) {
        this.email = name;
        this.paasw = paasw;
    }*/


    public String getFirstName() {
        return firstName;
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


    public static class UserBuilder {
        public String email;
        public String paasw;
        public String firstName;
        public String lastName;
        public String country;
        public String city;
        public String phone;
        public String dob;


        public UserBuilder(String email, String paasw) {
            this.email = email;
            this.paasw = paasw;
        }

        public UserBuilder(UserModel user)
        {
            email = user.email;
            paasw = user.paasw;
            firstName = user.firstName;
            lastName = user.lastName;
            country = user.country;
            city = user.city;
            phone = user.phone;
            dob = user.dob;
        }


        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public UserBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public UserBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder setDob(String dob) {
            this.dob = dob;
            return this;
        }

        public UserModel build()
        {
            return new UserModel(this);
        }


    }


}
