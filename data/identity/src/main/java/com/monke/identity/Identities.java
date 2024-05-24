package com.monke.identity;

import com.monke.utils.StringProvider;

import java.util.UUID;

public class Identities {

    public static Identity MALE = new Identity(
            Constants.MALE_ID,
            StringProvider.getString(com.monke.ui.R.string.male),
            "",
            Constants.FEMALE_ID,
            Identity.Type.GENDER
    );

    public static Identity FEMALE = new Identity(
            Constants.FEMALE_ID,
            StringProvider.getString(com.monke.ui.R.string.female),
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

    public static Identity MUSICIAN = new Identity(
            Constants.MUSICIAN,
            "Музицирую",
            "",
            Constants.NO_MUSICIAN,
            Identity.Type.POSITIVE
    );

    public static Identity NO_MUSIC = new Identity(
            Constants.NO_MUSICIAN,
            "Без живой музыки",
            "",
            Constants.MUSICIAN,
            Identity.Type.NEGATIVE
    );

    public static Identity LOVE_GUESTS = new Identity(
            Constants.LOVE_GUESTS,
            "Люблю гостей",
            "",
            Constants.NO_GUESTS,
            Identity.Type.POSITIVE
    );

    public static Identity NO_GUESTS = new Identity(
            Constants.NO_GUESTS,
            "Без гостей",
            "",
            Constants.LOVE_GUESTS,
            Identity.Type.NEGATIVE
    );

    public static Identity HAS_CHILD = new Identity(
            Constants.HAS_CHILD,
            "Есть ребенок",
            "",
            Constants.NO_CHILDREN,
            Identity.Type.POSITIVE
    );

    public static Identity NO_CHILDREN = new Identity(
            Constants.NO_CHILDREN,
            "Никаких детей",
            "",
            Constants.HAS_CHILD,
            Identity.Type.NEGATIVE
    );

}
