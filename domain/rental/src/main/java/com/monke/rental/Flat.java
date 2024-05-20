package com.monke.rental;

import java.util.Objects;

public class Flat extends Realty{

    private int floor;
    private float livingArea;
    private float kitchenArea;

    public Flat(String id, String address, float area, int sleeps, int roomsCount,
                int floorCount, int floor, float livingArea, float kitchenArea) {
        super(id, address, area, sleeps, roomsCount, floorCount);
        this.floor = floor;
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

    public float getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(float livingArea) {
        this.livingArea = livingArea;
    }

    public float getKitchenArea() {
        return kitchenArea;
    }

    public void setKitchenArea(float kitchenArea) {
        this.kitchenArea = kitchenArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Flat flat = (Flat) o;
        return floor == flat.floor &&
               livingArea == flat.livingArea &&
               kitchenArea == flat.kitchenArea;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), floor, livingArea, kitchenArea);
    }

    @Override
    public String toString() {
        return super.toString() + "Flat{" +
                "floor=" + floor +
                ", livingArea=" + livingArea +
                ", kitchenArea=" + kitchenArea +
                '}';
    }

    public Flat clone() {
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
