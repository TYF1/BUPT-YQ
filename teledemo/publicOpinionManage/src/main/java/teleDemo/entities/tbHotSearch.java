package teleDemo.entities;

import java.io.Serializable;

public class tbHotSearch implements Serializable {
    private int hsId;
    private String hsContent;
    private int hsHotness;
    private String hsTime;

    public tbHotSearch(int hsId, String hsContent, int hsHotness, String hsTime) {
        this.hsId = hsId;
        this.hsContent = hsContent;
        this.hsHotness = hsHotness;
        this.hsTime = hsTime;
    }

    public tbHotSearch(){}

    public int gethsId() {
        return hsId;
    }

    public void setHsId(int hsId) {
        this.hsId = hsId;
    }

    public String getHsContent() {
        return hsContent;
    }

    public void setHsContent(String hsContent) {
        this.hsContent = hsContent;
    }

    public int getHotness() {
        return hsHotness;
    }

    public void setHotness(int hsHotness) {
        this.hsHotness = hsHotness;
    }

    public String gethsTime() {
        return hsTime;
    }

    public void sethsTime(String hsTime) {
        this.hsTime = hsTime;
    }
}
