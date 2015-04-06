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
        return TitleAlert;
    }

    public void setTitleAlert(String titleAlert) {
        TitleAlert = titleAlert;
    }

    private String TitleAlert;
    private String mail;
}
