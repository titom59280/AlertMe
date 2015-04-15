package com.alertme.projet.alertme;

import android.app.Application;

/**
 * Created by Thomas on 05/04/2015.
 */
public class VariableGlobalClass extends Application{
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    private String template;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    private String phone;
    private String email;

    public String getTitleAlert() {
        return titleAlert;
    }

    public void setTitleAlert(String titleAlert) {
        this.titleAlert = titleAlert;
    }

    private String titleAlert;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    private String pass;
    private String mail;
}
