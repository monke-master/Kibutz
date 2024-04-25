package com.monke.rental;

import java.util.Objects;

public class Realty {

    private String id;
    private String address;
    private long area;
    private int sleeps;


    public Realty(String id, String address, long area, int sleeps) {
        this.id = id;
        this.address = address;
        this.area = area;
        this.sleeps = sleeps;
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
