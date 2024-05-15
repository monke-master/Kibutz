package com.monke.rental;

import javax.inject.Inject;

public class SaveRentalUseCase {

    private final RentalRepository rentalRepository;

    @Inject
    public SaveRentalUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public SaveRentalUseCase saveRoomsCount(int roomsCount) {
        Rental rental = rentalRepository.getCreatingRental();
        Realty realty = rental.getRealty();
        realty.setRoomsCount(roomsCount);
        rental.setRealty(realty);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }

    public SaveRentalUseCase saveFlatArea(float area, float livingArea, float kitchenArea) {
        Rental rental = rentalRepository.getCreatingRental();

        Flat realty = (Flat)rental.getRealty();
        realty.setArea(area);
        realty.setLivingArea(livingArea);
        realty.setKitchenArea(kitchenArea);

        rental.setRealty(realty);
        rentalRepository.saveCreatingRental(rental);
        return this;
    }
}
