package com.monke.rental;

import java.util.Objects;

public class House extends Realty{

    private int plotArea;

    public House(String id) {
        super(id);
    }

    public House(String id, String address, long area, int sleeps, int roomsCount, int plotArea) {
        super(id, address, area, sleeps, roomsCount);
        this.plotArea = plotArea;
    }

    public int getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(int plotArea) {
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
}
