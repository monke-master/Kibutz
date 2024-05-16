package com.monke.user;

import com.monke.identity.Identities;
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
            List.of("https://sun1-91.userapi.com/s/v1/if1/nlwrIHMavQQQCrtrznR0aqYprdCm6W09-YP8GPiio9QVE1-iziHN6XtYGGeMrHNVHbtR-Bn7.jpg?size=862x862&quality=96&crop=166,0,862,862&ava=1"),
            "I am Berkoff Man",
            List.of(Identities.HAS_ANIMALS)
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
