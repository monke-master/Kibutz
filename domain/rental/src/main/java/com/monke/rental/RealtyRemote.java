package com.monke.rental;

public class RealtyRemote {
    public String id;
    public String address;
    public float area;
    public int sleeps;
    public int roomsCount;
    public int floorsCount;

    public RealtyRemote() {

    }

    public RealtyRemote(Realty realty) {
        this.id = realty.getId();
        this.address = realty.getAddress();
        this.area = realty.getArea();
        this.sleeps = realty.getSleeps();
        this.roomsCount = realty.getRoomsCount();
        this.floorsCount = realty.getFloorsCount();
    }

    public Realty toDomain() {
        return new Realty(
                id,
                address,
                area,
                sleeps,
                roomsCount,
                floorsCount
        );
    }



}
