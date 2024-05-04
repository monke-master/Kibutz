package com.monke.identity;

import java.util.Objects;

public class IdentityUtils {

    public static boolean isGender(Identity identity) {
        return Objects.equals(identity.getId(), Constants.FEMALE_ID) ||
                Objects.equals(identity.getId(), Constants.MALE_ID);
    }
}
