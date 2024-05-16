package com.monke.identity;

import java.util.Objects;

public class Identity {

    private String id;
    private String name;
    private String iconUrl;
    private String oppositeId;
    private Type type;

    public Identity(String id, String name, String iconUrl, String opposite, Type type) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.oppositeId = opposite;
        this.type = type;
    }

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
               Objects.equals(id, identity.id) &&
               Objects.equals(type, identity.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, iconUrl, oppositeId, id, type);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        GENDER, POSITIVE, NEGATIVE
    }

    @Override
    public String toString() {
        return "Identity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", oppositeId='" + oppositeId + '\'' +
                ", type=" + type +
                '}';
    }
}
