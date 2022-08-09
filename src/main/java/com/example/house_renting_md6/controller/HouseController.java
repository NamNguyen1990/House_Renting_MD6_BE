package com.example.house_renting_md6.controller;


import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.service.impl.HouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/houses")
public class HouseController {
    @Autowired
    HouseServiceImpl houseService;

    @GetMapping
    public ResponseEntity<Page<House>> findAllHouse(@PageableDefault(value = 9) Pageable pageable) {
        Page<House> houses = houseService.findAll(pageable);
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<House> findById(@PathVariable Long id) {
        Optional<House> houseOptional = houseService.findById(id);
        if (!houseOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(houseOptional.get(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<House> saveHouse(@Valid @RequestBody House house) {
        houseService.save(house);
        return new ResponseEntity<>(houseService.findLastHouse(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<House> updateHouse(@PathVariable Long id, @RequestBody House house) {
        Optional<House> houseOptional = houseService.findById(id);
        if (!houseOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        house.setId(houseOptional.get().getId());
        houseService.save(house);
        return new ResponseEntity<>(house, HttpStatus.OK);
    }

//trong backlog không có xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<House> deleteHouse(@PathVariable Long id) {
        Optional<House> houseOptional = houseService.findById(id);
        if (!houseOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        houseOptional.get().setStatus(0);
        houseService.save(houseOptional.get());
        return new ResponseEntity<>(houseOptional.get(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/find-by-ownerId")  // Tìm theo id User đăng nhập để ra số house đã đăng của id đó!
    public ResponseEntity<Iterable<House>> findHouseByOwnerId(@RequestParam(value = "owner_id") Long owner_id) {
        List<House> houses = (List<House>) houseService.findByOwnerId(owner_id);
        if (houses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @GetMapping("/findTop5")
    public ResponseEntity<Iterable<House>>findHouseTop5(){
        List<House> houseList=(List<House>)houseService.findTop5();
        if(houseList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(houseList,HttpStatus.OK);
    }


    @GetMapping("/searchByAll")
    public ResponseEntity<Iterable<House>> findByAll(@RequestParam(value = "address",defaultValue = "%",required = false) String address, @RequestParam(value = "startPrice",defaultValue = "0",required = false) int start, @RequestParam(value = "endPrice",defaultValue = "999999",required = false) int end,
                                                     @RequestParam(value = "bath",defaultValue = "0",required = false) int bathroom, @RequestParam(value = "bed",defaultValue = "0",required = false) int bedroom,
                                                     @RequestParam(value = "dateBegin") String dateBegin, @RequestParam(value = "dateEnd") String dateEnd) {
        if ((dateBegin.equals("") && dateEnd.equals(""))) {
            dateBegin = "1900-01-01";
            dateEnd = String.valueOf(LocalDate.now());
        }
        Iterable<House> houses = houseService.findByManyThing(address , start, end, bathroom, bedroom, LocalDate.parse(dateBegin), LocalDate.parse(dateEnd));
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

}
