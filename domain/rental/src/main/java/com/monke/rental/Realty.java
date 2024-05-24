package com.monke.rental;

import java.util.Objects;

public class Realty {

    protected String id;
    protected String address;
    protected float area;
    protected int sleeps;
    protected int roomsCount;
    protected int floorsCount;

    public Realty(String id) {
        this.id = id;
    }

    public Realty(String id, String address, float area, int sleeps, int roomsCount, int floorsCount) {
        this.id = id;
        this.address = address;
        this.area = area;
        this.sleeps = sleeps;
        this.roomsCount = roomsCount;
        this.floorsCount = floorsCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getSleeps() {
        return sleeps;
    }

    public void setSleeps(int sleeps) {
        this.sleeps = sleeps;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }

    public int getFloorsCount() {
        return floorsCount;
    }

    public void setFloorsCount(int floorsCount) {
        this.floorsCount = floorsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Realty realty = (Realty) o;
        return area == realty.area &&
               sleeps == realty.sleeps &&
               Objects.equals(id, realty.id) &&
               Objects.equals(address, realty.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, area, sleeps);
    }

    @Override
    public String toString() {
        return "Realty{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", floorsCount='" + floorsCount + '\'' +
                ", area=" + area +
                ", sleeps=" + sleeps +
                ", roomsCount=" + roomsCount +
                '}';
    }

    public Realty clone() {
        return new Realty(
                id,
                address,
                area,
                sleeps,
                roomsCount,
                floorsCount
        );
    }

    public boolean isFlat() {
        return this instanceof Flat;
    }

    public boolean isHouse() {
        return this instanceof House;
    }
}
