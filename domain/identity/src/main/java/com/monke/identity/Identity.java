package com.monke.identity;

import java.util.Objects;

public class Identity {

    private String name;
    private String iconUrl;
    private String oppositeId;


    public Identity(String id, String name, String iconUrl, String opposite) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.oppositeId = opposite;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getOppositeId() {
        return oppositeId;
    }

    public void setOppositeId(String oppositeId) {
        this.oppositeId = oppositeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identity identity = (Identity) o;
        return Objects.equals(name, identity.name) &&
               Objects.equals(iconUrl, identity.iconUrl) &&
               Objects.equals(oppositeId, identity.oppositeId) &&
               Objects.equals(id, identity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, iconUrl, oppositeId, id);
    }
}
