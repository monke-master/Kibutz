package com.monke.rental;

import java.util.Objects;

public class Contacts {
    private String phoneNumber;
    private String telegramLogin;
    private String email;

    public Contacts(String phoneNumber, String telegramLogin, String email) {
        this.phoneNumber = phoneNumber;
        this.telegramLogin = telegramLogin;
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTelegramLogin() {
        return telegramLogin;
    }

    public void setTelegramLogin(String telegramLogin) {
        this.telegramLogin = telegramLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contacts contacts = (Contacts) o;
        return Objects.equals(phoneNumber, contacts.phoneNumber) && Objects.equals(telegramLogin, contacts.telegramLogin) && Objects.equals(email, contacts.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, telegramLogin, email);
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", telegramLogin='" + telegramLogin + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
