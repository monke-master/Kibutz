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

    public static Identity SMOKING = new Identity(
            Constants.SMOKING_ID,
            "Курю",
            "",
            Constants.SMOKING_ID
    );

    public static Identity NO_SMOKING = new Identity(
            Constants.NO_SMOKING_ID,
            "Не курю",
            "",
            Constants.NO_SMOKING_ID
    );


}
