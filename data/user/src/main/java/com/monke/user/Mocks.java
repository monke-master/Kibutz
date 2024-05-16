package com.monke.user;

import com.monke.rental.Contacts;
import com.monke.rental.Flat;
import com.monke.rental.Rental;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Mocks {

    private static String userId = UUID.randomUUID().toString();

    static Flat mockFlat = new Flat(
            UUID.randomUUID().toString(),
            "Berkof Avenu, 26",
            229,
            12,
            10,
            1,
            134,
            10,
            10
    );

    static Contacts mockContacts = new Contacts("588585",
            "momkeMaster", "kibutz@yandex.ru");

    static Rental mockRental = new Rental(
            UUID.randomUUID().toString(),
            userId,
            List.of("https://avatars.dzeninfra.ru/get-zen_doc/1907561/pub_5ee2a8cdb0200314ab97f3bc_5ffb0e76d1a90641ca0bf85b/scale_1200"),
            100500,
            mockFlat,
            "Das ist KIbutz!",
            Collections.emptyList(),
            5,
            Collections.emptyList(),
            100505050,
            mockContacts
    );

    static Profile mockProfile = new Profile(
            UUID.randomUUID().toString(),
            Collections.emptyList(),
            "I am Berkoff Man",
            Collections.emptyList()
            );

    static User mockUser = new User(
            userId,
            "Nikolay Berkovv",
            "berjoff@gmail.com",
            1020230020,
            Collections.emptyList(),
            mockProfile,
            List.of(mockRental)
    );


}
