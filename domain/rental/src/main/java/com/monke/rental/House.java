package com.monke.rental;

import java.util.Objects;

public class House extends Realty{

    private float plotArea;

    public House(String id) {
        super(id);
    }

    public House(String id, String address, float area, int sleeps, int roomsCount, int floorsCount,
                 float plotArea) {
        super(id, address, area, sleeps, roomsCount, floorsCount);
        this.plotArea = plotArea;
    }

    public float getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(float plotArea) {
        this.plotArea = plotArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        House house = (House) o;
        return plotArea == house.plotArea;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), plotArea);
    }

    @Override
    public String toString() {
        return super.toString() + "House{" +
                "plotArea=" + plotArea +
                '}';
    }

    public House clone() {
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
}
