package com.monke.rental;

import com.monke.identity.Identity;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class CreateRentalUseCase {

    private final RentalRepository rentalRepository;

    @Inject
    public CreateRentalUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public CreateRentalUseCase create(boolean isFlat) {
        Realty realty;
        if (isFlat) {
            realty = new Flat(UUID.randomUUID().toString());
        } else {
            realty = new House(UUID.randomUUID().toString());
        }
        Rental rental = new Rental(UUID.randomUUID().toString(), realty);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }

    public CreateRentalUseCase saveRoomsCount(int roomsCount) {
        Rental rental = rentalRepository.getCreatingRental();
        Realty realty = rental.getRealty();
        realty.setRoomsCount(roomsCount);
        rental.setRealty(realty);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }

    public CreateRentalUseCase saveFlatArea(float area, float livingArea, float kitchenArea) {
        Rental rental = rentalRepository.getCreatingRental();

        Flat realty = (Flat)rental.getRealty();
        realty.setArea(area);
        realty.setLivingArea(livingArea);
        realty.setKitchenArea(kitchenArea);

        rental.setRealty(realty);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }

    public CreateRentalUseCase saveFloorCount(int floorsCount) {
        Rental rental = rentalRepository.getCreatingRental();
        Realty realty = rental.getRealty();
        realty.setFloorsCount(floorsCount);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }

    public CreateRentalUseCase saveFlatFloor(int floor) {
        Rental rental = rentalRepository.getCreatingRental();
        Flat realty = (Flat) rental.getRealty();
        realty.setFloor(floor);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }

    public CreateRentalUseCase saveFlatmatesCount(int flatmatesCount) {
        Rental rental = rentalRepository.getCreatingRental();
        rental.setMaxFlatmatesCount(flatmatesCount);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }

    public CreateRentalUseCase saveIdentitiesFilters(List<Identity> filters) {
        Rental rental = rentalRepository.getCreatingRental();
        rental.setIdentityFilters(filters);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }

    public CreateRentalUseCase savePhotos(List<String> photosUri) {
        Rental rental = rentalRepository.getCreatingRental();
        rental.setPhotos(photosUri);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }

    public CreateRentalUseCase savePrice(Long price) {
        Rental rental = rentalRepository.getCreatingRental();
        rental.setPrice(price);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }

    public CreateRentalUseCase saveDescription(String description) {
        Rental rental = rentalRepository.getCreatingRental();
        rental.setDescription(description);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }

    public CreateRentalUseCase saveContacts(String email, String phone, String telegram) {
        Rental rental = rentalRepository.getCreatingRental();
        Contacts contacts = new Contacts(phone, telegram, email);
        rental.setContacts(contacts);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }
}
