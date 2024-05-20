package com.monke.rental;

public class HouseRemote extends RealtyRemote {

    private float plotArea;

    public HouseRemote() {

    }

    public HouseRemote(House house) {
        super(house);
        this.plotArea = house.getPlotArea();
    }

    @Override
    public Realty toDomain() {
        return super.toDomain();
    }
}
