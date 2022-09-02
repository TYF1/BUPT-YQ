package teleDemo.entities;

import java.io.Serializable;

public class tbarea implements Serializable {
    private int id;
    private int num;
    private double lat;
    private double lon;

    public tbarea(Integer id, Integer num, Double lat, Double lon) {
        this.id = id;
        this.num = num;
        this.lat = lat;
        this.lon = lon;
    }
    public tbarea(Double lat, Double lon,Integer id, Integer num) {
        this.id = id;
        this.num = num;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
