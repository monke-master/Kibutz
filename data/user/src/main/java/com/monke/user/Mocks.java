package com.monke.user;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Mocks {

    static Profile mockProfile = new Profile(
            UUID.randomUUID().toString(),
            Collections.emptyList(),
            "I am Berkoff Man",
            Collections.emptyList()
            );

    static User mockUser = new User(
            UUID.randomUUID().toString(),
            "Nikolay Berkovv",
            "berjoff@gmail.com",
            1020230020,
            Collections.emptyList(),
            mockProfile,
            Collections.emptyList()
    );


}
