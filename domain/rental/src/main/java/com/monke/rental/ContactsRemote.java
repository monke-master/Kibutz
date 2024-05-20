package com.monke.rental;

public class ContactsRemote {
    public String phoneNumber;
    public String telegramLogin;
    public String email;

    public ContactsRemote() {

    }

    public ContactsRemote(Contacts contacts) {
        this.phoneNumber = contacts.getPhoneNumber();
        this.telegramLogin = contacts.getTelegramLogin();
        this.email = contacts.getEmail();
    }

    public Contacts toDomain() {
        return new Contacts(
                phoneNumber,
                telegramLogin,
                email
        );
    }
}
