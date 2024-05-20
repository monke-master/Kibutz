package com.monke.user;

import com.monke.identity.Constants;
import com.monke.identity.Identity;
import com.monke.rental.Contacts;
import com.monke.rental.Flat;
import com.monke.rental.Rental;
import com.monke.rental.Response;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Mocks {

    public static Identity MALE = new Identity(
            Constants.MALE_ID,
            "Мужчина",
            "",
            Constants.FEMALE_ID,
            Identity.Type.GENDER
    );

    public static Identity FEMALE = new Identity(
            Constants.FEMALE_ID,
            "Женшина",
            "",
            Constants.MALE_ID,
            Identity.Type.GENDER
    );

    public static Identity SMOKING = new Identity(
            Constants.SMOKING_ID,
            "Курю",
            "",
            Constants.SMOKING_ID,
            Identity.Type.POSITIVE
    );

    public static Identity NO_SMOKING = new Identity(
            Constants.NO_SMOKING_ID,
            "Не курю",
            "",
            Constants.NO_SMOKING_ID,
            Identity.Type.NEGATIVE
    );

    public static Identity HAS_ANIMALS = new Identity(
            Constants.HAS_ANIMALS_ID,
            "Есть животное",
            "",
            Constants.NO_ANIMALS_ID,
            Identity.Type.POSITIVE
    );

    public static Identity NO_ANIMALS = new Identity(
            Constants.NO_ANIMALS_ID,
            "Без животных",
            "",
            Constants.HAS_ANIMALS_ID,
            Identity.Type.NEGATIVE
    );

    private static String userId = UUID.randomUUID().toString();
    private static String userId2 = UUID.randomUUID().toString();
    private static String rentalId = UUID.randomUUID().toString();
    private static String rentalId2 = UUID.randomUUID().toString();

    static Flat mockFlat = new Flat(
            UUID.randomUUID().toString(),
            "Berkof Avenu, 26",
            229,
            12,
            10,
            134,
            1,
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

    public static Rental mockRental = new Rental(
            rentalId,
            userId,
            List.of("https://avatars.dzeninfra.ru/get-zen_doc/1907561/pub_5ee2a8cdb0200314ab97f3bc_5ffb0e76d1a90641ca0bf85b/scale_1200",
                    "https://avatars.mds.yandex.net/get-altay/2425845/2a00000173ff1e175ff73936f1d545d549c0/XXL",
                    "https://avatars.mds.yandex.net/get-altay/2094876/2a0000016c004f3b140cc297cb6c59645f42/XXL",
                    "https://avatars.dzeninfra.ru/get-zen_doc/1907561/pub_5ee2a8cdb0200314ab97f3bc_5ffb0e76d1a90641ca0bf85b/scale_1200",
                    "https://avatars.mds.yandex.net/get-altay/2425845/2a00000173ff1e175ff73936f1d545d549c0/XXL",
                    "https://avatars.mds.yandex.net/get-altay/2094876/2a0000016c004f3b140cc297cb6c59645f42/XXL"),
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

    public static Rental mockRental2 = new Rental(
            rentalId2,
            userId,
            List.of("https://avatars.dzeninfra.ru/get-zen_doc/1907561/pub_5ee2a8cdb0200314ab97f3bc_5ffb0e76d1a90641ca0bf85b/scale_1200"),
            100500,
            mockFlat,
            "Das ist KIbutz!",
            List.of(NO_ANIMALS),
            5,
            List.of(userId),
            100505050,
            mockContacts,
            List.of(mockResponse)
    );

    public static Profile mockProfile = new Profile(
            UUID.randomUUID().toString(),
            List.of("https://sun1-91.userapi.com/s/v1/if1/nlwrIHMavQQQCrtrznR0aqYprdCm6W09-YP8GPiio9QVE1-iziHN6XtYGGeMrHNVHbtR-Bn7.jpg?size=862x862&quality=96&crop=166,0,862,862&ava=1",
                    "https://sun9-36.userapi.com/impf/c857536/v857536860/25a03/n3vTEiG_bwQ.jpg?size=385x561&quality=96&sign=870af1ba41b41d2010522c1ba13cff54&c_uniq_tag=Z6iPqdSTqRmY4AdTBzwAy1jD47Fz6I9e-6XfVp2pQmE&type=album"),
            "I am Berkoff Man",
            List.of(HAS_ANIMALS)
    );

    public static Profile mockProfile2 = new Profile(
            UUID.randomUUID().toString(),
            List.of("https://content.foto.my.mail.ru/mail/berkow/_myphoto/h-6.jpg",
                    "https://sun9-36.userapi.com/impf/c857536/v857536860/25a03/n3vTEiG_bwQ.jpg?size=385x561&quality=96&sign=870af1ba41b41d2010522c1ba13cff54&c_uniq_tag=Z6iPqdSTqRmY4AdTBzwAy1jD47Fz6I9e-6XfVp2pQmE&type=album"),
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
