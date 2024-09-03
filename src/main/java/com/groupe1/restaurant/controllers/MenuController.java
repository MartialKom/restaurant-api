package com.groupe1.restaurant.controllers;

import com.groupe1.restaurant.dto.BaseResponse;
import com.groupe1.restaurant.dto.MenuDto;
import com.groupe1.restaurant.dto.MenuRequest;
import com.groupe1.restaurant.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    public static final String MENU_TROUVE = "Menu trouvé";
    private final IMenuService menuService;

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/")
    public ResponseEntity<BaseResponse<MenuDto>> create (@RequestBody MenuRequest request){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(
                new BaseResponse<>(HttpStatus.CREATED.value(), "Menu créé",menuService.create(request))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MenuDto>> get (@PathVariable Integer id){
        return ResponseEntity.ok(
                new BaseResponse<>(HttpStatus.OK.value(), MENU_TROUVE, menuService.findById(id))
        );
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<BaseResponse<List<MenuDto>>> findByRestaurant (@PathVariable Integer id){
        return ResponseEntity.ok(
                new BaseResponse<>(HttpStatus.OK.value(), MENU_TROUVE, menuService.findAllByRestaurant(id))
        );
    }

    @GetMapping("/days/{days}/restaurant/{id}")
    public ResponseEntity<BaseResponse<List<MenuDto>>> findByDaysAndRestaurant (@PathVariable String days, @PathVariable Integer id){
        return ResponseEntity.ok(
                new BaseResponse<>(HttpStatus.OK.value(), MENU_TROUVE, menuService.findByDaysAndRestaurant(days, id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MenuDto>> update (@PathVariable Integer id, @RequestBody MenuRequest request){
        return ResponseEntity.ok(
                new BaseResponse<>(HttpStatus.OK.value(), "Menu modifié", menuService.update(request, id))
        );
    }
}
