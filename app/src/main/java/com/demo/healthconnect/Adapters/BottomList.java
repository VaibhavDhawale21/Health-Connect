package com.demo.healthconnect.Adapters;

public class BottomList {
    String driver_name, vehicle_number, km;

    public BottomList(String driver_name, String vehicle_number, String km) {
        this.driver_name = driver_name;
        this.vehicle_number = vehicle_number;
        this.km = km;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }
}
