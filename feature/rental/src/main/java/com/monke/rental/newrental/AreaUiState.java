package com.monke.rental.newrental;

import com.monke.rental.Flat;

public class AreaUiState {
    private Float area = null;
    private Float livingArea = null;
    private Float kitchenArea = null;
    private Float plotArea = null;

    public Float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public Float getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(float livingArea) {
        this.livingArea = livingArea;
    }

    public Float getKitchenArea() {
        return kitchenArea;
    }

    public void setKitchenArea(float kitchenArea) {
        this.kitchenArea = kitchenArea;
    }

    public Float getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(float plotArea) {
        this.plotArea = plotArea;
    }
}
