package com.groupe1.restaurant.services.impl;

import com.groupe1.restaurant.dto.ReservationDto;
import com.groupe1.restaurant.dto.ReservationRequest;
import com.groupe1.restaurant.entities.Reservation;
import com.groupe1.restaurant.entities.Restaurant;
import com.groupe1.restaurant.exception.OperationNonPermittedException;
import com.groupe1.restaurant.repository.ReservationRepository;
import com.groupe1.restaurant.repository.RestaurantRepository;
import com.groupe1.restaurant.services.IReservationService;
import com.groupe1.restaurant.validators.ObjectValidators;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationServiceImpl implements IReservationService {

    public static final String RESTAURANT_NON_TROUVE = "Restaurant non trouvé";
    private final ReservationRepository reservationRepository;

    private final RestaurantRepository restaurantRepository;

    private final ObjectValidators<ReservationRequest> validators;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, ObjectValidators<ReservationRequest> validators) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.validators = validators;
    }

    @Override
    public ReservationDto create(ReservationRequest request) {
        validators.validate(request);

        if(!request.getEndTime().isAfter(request.getStartTime()))
            throw new OperationNonPermittedException("L'heure de fin doit être après l'heure de début");

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(()-> new EntityNotFoundException(RESTAURANT_NON_TROUVE));

        if(request.getStartTime().isBefore(restaurant.getOpeningHours()))
            throw new OperationNonPermittedException("Le restaurant n'est pas encore ouvert à l'heure mentionné !");
        if(request.getStartTime().isAfter(restaurant.getClosingHours()))
            throw new OperationNonPermittedException("Le restaurant est déjà fermé à l'heure de début mentionné");
        if(request.getEndTime().isAfter(restaurant.getClosingHours()))
            throw new OperationNonPermittedException("Vous ne pourrez pas finir à cette heure, le restaurant ferme à: "+restaurant.getClosingHours().format(DateTimeFormatter.ofPattern("HH:mm")));
        if(request.getNbOfPeople() > (restaurant.getCapacity()-5) )
            throw new OperationNonPermittedException("Le nombre maximal de place à reserver est de "+ (restaurant.getCapacity()-5));

        Reservation reservation = ReservationRequest.toEntity(request);
        reservation.setRestaurant(restaurant);
        String num  = UUID.randomUUID().toString();
        reservation.setReservationNumber(request.getClientName().substring(0,3)+"-"+num);
        return ReservationDto.fromEntity(reservationRepository.save(reservation));
    }

    @Override
    public ReservationDto update(Integer id, ReservationRequest request) {
        validators.validate(request);

        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Reservation non trouvé"));
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(()-> new EntityNotFoundException(RESTAURANT_NON_TROUVE));
        reservation.setRestaurant(restaurant);
        reservation.setClientName(request.getClientName());
        reservation.setReservationDate(request.getReservationDate());
        reservation.setNbSeats(request.getNbOfPeople());

        String num  = UUID.randomUUID().toString();
        reservation.setReservationNumber(request.getClientName().substring(0,3)+"-"+num);

        return ReservationDto.fromEntity(reservationRepository.save(reservation));
    }

    @Override
    public List<ReservationDto> findByClientName(String name) {
        return reservationRepository.findByClientName(name).stream()
                .map(ReservationDto::fromEntity)
                .toList();
    }

    @Override
    public List<ReservationDto> findByRestaurant(Integer idRestaurant) {
        Restaurant restaurant = restaurantRepository.findById(idRestaurant).orElseThrow(()-> new EntityNotFoundException(RESTAURANT_NON_TROUVE));
        return reservationRepository.findByRestaurant(restaurant).stream()
                .map(ReservationDto::fromEntity)
                .toList();
    }

    @Override
    public ReservationDto findByReservationNumber(String reservationNumber) {
        return ReservationDto.fromEntity(reservationRepository.findByReservationNumber(reservationNumber).orElseThrow(()->new EntityNotFoundException(RESTAURANT_NON_TROUVE)));
    }

    @Override
    public ReservationDto findById(Integer id) {
        return ReservationDto.fromEntity(reservationRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(RESTAURANT_NON_TROUVE)));
    }

    @Override
    public void delete(String reservationNumber){
        reservationRepository.delete(reservationRepository.findByReservationNumber(reservationNumber).orElseThrow(()->new EntityNotFoundException(RESTAURANT_NON_TROUVE)));
    }
}
