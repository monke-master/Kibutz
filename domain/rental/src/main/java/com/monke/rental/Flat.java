package com.monke.rental;

import java.util.Objects;

public class Flat extends Realty{

    private int floor;
    private int floorCount;
    private int livingArea;
    private int kitchenArea;

    public Flat(String id, String address, long area, int sleeps, int roomsCount,
                int floor, int floorCount, int livingArea, int kitchenArea) {
        super(id, address, area, sleeps, roomsCount);
        this.floor = floor;
        this.floorCount = floorCount;
        this.livingArea = livingArea;
        this.kitchenArea = kitchenArea;
    }

    public Flat(String id) {
        super(id);
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(int floorCount) {
        this.floorCount = floorCount;
    }

    public int getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(int livingArea) {
        this.livingArea = livingArea;
    }

    public int getKitchenArea() {
        return kitchenArea;
    }

    public void setKitchenArea(int kitchenArea) {
        this.kitchenArea = kitchenArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Flat flat = (Flat) o;
        return floor == flat.floor &&
               floorCount == flat.floorCount &&
               livingArea == flat.livingArea &&
               kitchenArea == flat.kitchenArea;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), floor, floorCount, livingArea, kitchenArea);
    }
}
