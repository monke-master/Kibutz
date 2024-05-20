package com.monke.rental;

public class FlatRemote extends RealtyRemote {

    public int floor;
    public float livingArea;
    public float kitchenArea;



    public FlatRemote() {

    }

    public FlatRemote(Flat flat) {
        super(flat);
        floor = flat.getFloor();
        livingArea = flat.getLivingArea();
        kitchenArea = flat.getKitchenArea();
    }

    @Override
    public Flat toDomain() {
        return new Flat(
                id,
                address,
                area,
                sleeps,
                roomsCount,
                floorsCount,
                floor,
                livingArea,
                kitchenArea
        );
    }


}
