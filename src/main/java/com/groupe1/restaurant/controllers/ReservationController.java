package com.groupe1.restaurant.controllers;

import com.groupe1.restaurant.dto.BaseResponse;
import com.groupe1.restaurant.dto.ReservationDto;
import com.groupe1.restaurant.dto.ReservationRequest;
import com.groupe1.restaurant.services.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {

    public static final String RESERVATION_TROUVE = "Reservation trouvé";
    private final IReservationService reservationService;

    @Autowired
    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/")
    public ResponseEntity<BaseResponse<ReservationDto>> create(@RequestBody ReservationRequest request){
        return ResponseEntity.ok(
                new BaseResponse<>(HttpStatus.CREATED.value(), "Reservation créé", reservationService.create(request))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ReservationDto>> get (@PathVariable Integer id){
        return ResponseEntity.ok(
                new BaseResponse<>(HttpStatus.OK.value(), RESERVATION_TROUVE, reservationService.findById(id))
        );
    }

    @GetMapping("/client/{name}")
    public ResponseEntity<BaseResponse<List<ReservationDto>>> findByUser (@PathVariable String name){
        return ResponseEntity.ok(
                new BaseResponse<>(HttpStatus.OK.value(), RESERVATION_TROUVE, reservationService.findByClientName(name))
        );
    }

    @GetMapping("/number/{reservationNumber}")
    public ResponseEntity<BaseResponse<ReservationDto>> findByReservationNumber (@PathVariable String reservationNumber){
        return ResponseEntity.ok(
                new BaseResponse<>(HttpStatus.OK.value(), RESERVATION_TROUVE, reservationService.findByReservationNumber(reservationNumber))
        );
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<BaseResponse<List<ReservationDto>>> findByRestaurant (@PathVariable Integer id){
        return ResponseEntity.ok(
                new BaseResponse<>(HttpStatus.OK.value(), RESERVATION_TROUVE, reservationService.findByRestaurant(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ReservationDto>> update (@PathVariable Integer id, @RequestBody ReservationRequest request){
        return ResponseEntity.ok(
                new BaseResponse<>(HttpStatus.OK.value(), "Reservation modifié", reservationService.update(id, request))
        );
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> delete(@PathVariable("number") String number){
        reservationService.delete(number);

        return ResponseEntity.ok(null);
    }




}
