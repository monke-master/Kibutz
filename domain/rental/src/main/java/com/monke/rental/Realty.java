package com.monke.rental;

import java.util.Objects;

public class Realty {

    private String id;
    private String address;
    private long area;
    private int sleeps;
    private int roomsCount;

    public Realty(String id) {
        this.id = id;
    }

    public Realty(String id, String address, long area, int sleeps, int roomsCount) {
        this.id = id;
        this.address = address;
        this.area = area;
        this.sleeps = sleeps;
        this.roomsCount = roomsCount;
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

    public long getArea() {
        return area;
    }

    public void setArea(long area) {
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
}
