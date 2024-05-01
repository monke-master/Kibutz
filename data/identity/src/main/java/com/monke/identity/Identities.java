package com.monke.identity;

import com.monke.utils.StringProvider;

import java.util.UUID;

public class Identities {

    public static Identity MALE = new Identity(
            Constants.MALE_ID,
            StringProvider.getString(com.monke.ui.R.string.male),
            "",
            Constants.FEMALE_ID
    );

    public static Identity FEMALE = new Identity(
            Constants.FEMALE_ID,
            StringProvider.getString(com.monke.ui.R.string.female),
            "",
            Constants.MALE_ID
    );


}
