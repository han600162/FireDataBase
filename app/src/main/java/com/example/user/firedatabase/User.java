package com.example.user.firedatabase;

public class User {
    private String name, address, rating,score;
//    private int age ;

    public User(String name, String address, String rating,String score) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.score = score;
    }

    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }


    public String getScore() {
        return score;
    }

    public String getRating() {
        return rating;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
