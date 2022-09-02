package teleDemo.entities;

import java.io.Serializable;

public class tbareaVo implements Serializable {

    private int status;
    private double lat;
    private double lon;

    public tbareaVo() {
    }

    public tbareaVo(int status, double lat, double lon) {
        this.status = status;
        this.lat = lat;
        this.lon = lon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
