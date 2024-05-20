package com.monke.rental;

import com.monke.identity.Identity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RentalRemote {
    public String id;
    public String authorId;
    public List<String> photos;
    public long price;
    public RealtyRemote realty;
    public String description;
    public List<String> identityFilters;
    public int maxFlatmatesCount;
    public List<String> flatmatesIds = new ArrayList<>();
    public long creationDate;
    public ContactsRemote contacts;
    public List<String> responsesIds = new ArrayList<>();

    public RentalRemote() {

    }

    public RentalRemote(Rental rental) {
        this.id = rental.getId();
        this.authorId = rental.getAuthorId();
        this.photos = rental.getPhotos();
        this.price = rental.getPrice();
        this.realty = new RealtyRemote(rental.getRealty());
        this.description = rental.getDescription();
        this.identityFilters = rental.getIdentityFilters().stream().map(Identity::getId).collect(Collectors.toList());
        this.maxFlatmatesCount = rental.getMaxFlatmatesCount();
        this.flatmatesIds = rental.getFlatmatesIds();
        this.creationDate = rental.getCreationDate();
        this.contacts = new ContactsRemote(rental.getContacts());
        this.responsesIds = rental.getResponses().stream().map(Response::getResponseId).collect(Collectors.toList());
    }

    public Rental toDomain(List<Identity> identities, List<Response> responses)  {
        return new Rental(
                id,
                authorId,
                photos,
                price,
                realty.toDomain(),
                description,
                identities,
                maxFlatmatesCount,
                flatmatesIds,
                creationDate,
                contacts.toDomain(),
                responses
        );
    }

}
