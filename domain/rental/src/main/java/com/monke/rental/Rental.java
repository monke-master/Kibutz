package com.monke.rental;

import com.monke.identity.Identity;

import java.util.List;
import java.util.Objects;

public class Rental {

    private String id;
    private String authorId;
    private List<String> photos;
    private long price;
    private Realty realty;
    private String description;
    private List<Identity> identityFilters;
    private int flatmatesCount;
    private List<String> flatmatesIds;
    private long creationDate;

    public Rental(String id, Realty realty) {
        this.id = id;
        this.realty = realty;
    }

    public Rental(String id, String authorId, List<String> photos, long price, Realty realty,
                  String description, List<Identity> identityFilters, int flatmatesCount,
                  List<String> flatmatesIds, long creationDate) {
        this.id = id;
        this.authorId = authorId;
        this.photos = photos;
        this.price = price;
        this.realty = realty;
        this.description = description;
        this.identityFilters = identityFilters;
        this.flatmatesCount = flatmatesCount;
        this.flatmatesIds = flatmatesIds;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Realty getRealty() {
        return realty;
    }

    public void setRealty(Realty realty) {
        this.realty = realty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Identity> getIdentityFilters() {
        return identityFilters;
    }

    public void setIdentityFilters(List<Identity> identityFilters) {
        this.identityFilters = identityFilters;
    }

    public int getFlatmatesCount() {
        return flatmatesCount;
    }

    public void setFlatmatesCount(int flatmatesCount) {
        this.flatmatesCount = flatmatesCount;
    }

    public List<String> getFlatmatesIds() {
        return flatmatesIds;
    }

    public void setFlatmatesIds(List<String> flatmatesIds) {
        this.flatmatesIds = flatmatesIds;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return price == rental.price &&
               flatmatesCount == rental.flatmatesCount &&
               creationDate == rental.creationDate &&
               Objects.equals(id, rental.id) && Objects.equals(authorId, rental.authorId) &&
               Objects.equals(photos, rental.photos) && Objects.equals(realty, rental.realty) &&
               Objects.equals(description, rental.description) &&
               Objects.equals(identityFilters, rental.identityFilters) &&
               Objects.equals(flatmatesIds, rental.flatmatesIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorId, photos, price, realty, description, identityFilters,
                flatmatesCount, flatmatesIds, creationDate);
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id='" + id + '\'' +
                ", authorId='" + authorId + '\'' +
                ", photos=" + photos +
                ", price=" + price +
                ", realty=" + realty +
                ", description='" + description + '\'' +
                ", identityFilters=" + identityFilters +
                ", flatmatesCount=" + flatmatesCount +
                ", flatmatesIds=" + flatmatesIds +
                ", creationDate=" + creationDate +
                '}';
    }
}
