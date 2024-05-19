package com.monke.user;

import com.monke.rental.Contacts;
import com.monke.rental.Flat;
import com.monke.rental.Rental;
import com.monke.rental.Response;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Mocks {

    private static String userId = UUID.randomUUID().toString();
    private static String userId2 = UUID.randomUUID().toString();
    private static String rentalId = UUID.randomUUID().toString();

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
            "Monke_master", "igor.streshnev2019@gmail.com");


    public static Response mockResponse = new Response(
            UUID.randomUUID().toString(),
            userId,
            rentalId,
            Calendar.getInstance().getTimeInMillis(),
            Response.Status.LIKED
    );

    public  static Rental mockRental = new Rental(
            rentalId,
            userId,
            List.of("https://avatars.dzeninfra.ru/get-zen_doc/1907561/pub_5ee2a8cdb0200314ab97f3bc_5ffb0e76d1a90641ca0bf85b/scale_1200"),
            100500,
            mockFlat,
            "Das ist KIbutz!",
            Collections.emptyList(),
            5,
            List.of(userId),
            100505050,
            mockContacts,
            List.of(mockResponse)
    );

    public static Profile mockProfile = new Profile(
            UUID.randomUUID().toString(),
            List.of("https://sun1-91.userapi.com/s/v1/if1/nlwrIHMavQQQCrtrznR0aqYprdCm6W09-YP8GPiio9QVE1-iziHN6XtYGGeMrHNVHbtR-Bn7.jpg?size=862x862&quality=96&crop=166,0,862,862&ava=1"),
            "I am Berkoff Man",
            List.of()
    );

    public static Profile mockProfile2 = new Profile(
            UUID.randomUUID().toString(),
            List.of("https://content.foto.my.mail.ru/mail/berkow/_myphoto/h-6.jpg"),
            "I am Berkoff Man",
            List.of()
    );

    public static User mockUser = new User(
            userId,
            "Nikolay Berkovv",
            "berjoff@gmail.com",
            1020230020,
            Collections.emptyList(),
            mockProfile,
            List.of(mockRental)
    );

    public static User cockUser = new User(
            userId2,
            "Михаил Елизаров",
            "berjoff@gmail.com",
            1020230020,
            List.of(mockResponse),
            mockProfile2,
            Collections.emptyList()
    );


}
