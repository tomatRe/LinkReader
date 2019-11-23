package model;

import java.util.ArrayList;
import java.util.List;

public class WebPage {
    String name;
    String url;
    List<String> lniksInside;

    public WebPage(String name, String url) {
        this.name = name;
        this.url = url;
        lniksInside = new ArrayList<>();
    }

    public WebPage(String name, String url, List<String> lniksInside) {
        this.name = name;
        this.url = url;
        this.lniksInside = lniksInside;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addLink(String link) {
        lniksInside.add(link);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getLniksInside() {
        return lniksInside;
    }

    public void setLniksInside(List<String> lniksInside) {
        this.lniksInside = lniksInside;
    }
}
