package org.alexdev.icarus.web.controllers.home;

public class Page {
    private String name;
    private String description;

    public Page() {
        this.name = "Index";
        this.description = "Desc";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
