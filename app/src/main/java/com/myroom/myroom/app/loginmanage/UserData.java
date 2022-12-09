package com.myroom.myroom.app.loginmanage;

public class UserData {
    String  Fname, Lname ,phone , email ="", currentadd ,permanetadd;



    public UserData(String fname, String lname, String phone, String email, String currentadd, String permanetadd) {

        Fname = fname;
        Lname = lname;
        this.phone = phone;
        this.email = email;
        this.currentadd = currentadd;
        this.permanetadd = permanetadd;

    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentadd() {
        return currentadd;
    }

    public void setCurrentadd(String currentadd) {
        this.currentadd = currentadd;
    }

    public String getPermanetadd() {
        return permanetadd;
    }

    public void setPermanetadd(String permanetadd) {
        this.permanetadd = permanetadd;
    }


}
