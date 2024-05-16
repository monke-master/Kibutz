package com.monke.ui.rental;

import android.content.Context;

import com.monke.rental.Flat;
import com.monke.ui.R;

import java.util.List;

public class RealtyUiHelpers {

    public static List<RealtyDetail> getFlatRealtyDetails(Context context, Flat flat) {
        return List.of(
                new RealtyDetail(context.getString(R.string.area_flat), flat.getArea()),
                new RealtyDetail(context.getString(R.string.living_area), flat.getLivingArea()),
                new RealtyDetail(context.getString(R.string.kitchen), flat.getKitchenArea())
        );
    }
}
