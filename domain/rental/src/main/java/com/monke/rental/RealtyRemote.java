package com.monke.rental;

public class RealtyRemote {

    public String id;
    public String address;
    public float area;
    public int sleeps;
    public int roomsCount;
    public int floorsCount;
    public Type type;
    public int floor;
    public float livingArea;
    public float kitchenArea;

    public float plotArea;

    public RealtyRemote() {

    }

    public RealtyRemote(Realty realty) {
        this.id = realty.getId();
        this.address = realty.getAddress();
        this.area = realty.getArea();
        this.sleeps = realty.getSleeps();
        this.roomsCount = realty.getRoomsCount();
        this.floorsCount = realty.getFloorsCount();
        if (realty instanceof Flat) {
            type = Type.FLAT;
        } else {
            type = Type.HOUSE;
        }
    }

    public Realty toDomain() {
        if (type == Type.FLAT) {
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
        return new House(
                id,
                address,
                area,
                sleeps,
                roomsCount,
                floorsCount,
                plotArea
        );
    }

    enum Type {
        FLAT, HOUSE
    }
}
