package com.example.pallavi.navigationdrawerapp;

public class DoctorInfo {

    public String doc_name;
    public String doc_category;
    public String doc_email;
    public String doc_contact;
    public String doc_address;

    public DoctorInfo()
    {

    }

    public DoctorInfo(String doc_name, String doc_email) {
        this.doc_name = doc_name;
        this.doc_email = doc_email;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public String getDoc_email() {
        return doc_email;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public void setDoc_email(String doc_email) {
        this.doc_email = doc_email;
    }

    public String getDoc_category() {
        return doc_category;
    }

    public String getDoc_contact() {
        return doc_contact;
    }

    public String getDoc_address() {
        return doc_address;
    }

    public DoctorInfo(String doc_name, String doc_category, String doc_email, String doc_contact, String doc_address) {
        this.doc_name = doc_name;
        this.doc_category = doc_category;
        this.doc_email = doc_email;
        this.doc_contact = doc_contact;
        this.doc_address = doc_address;
    }

    public void setDoc_category(String doc_category) {
        this.doc_category = doc_category;
    }

    public void setDoc_contact(String doc_contact) {
        this.doc_contact = doc_contact;
    }

    public void setDoc_address(String doc_address) {
        this.doc_address = doc_address;
    }
}
