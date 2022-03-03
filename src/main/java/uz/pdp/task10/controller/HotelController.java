package uz.pdp.task10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task10.entity.Hotel;
import uz.pdp.task10.entity.Room;
import uz.pdp.task10.repository.HotelRepository;
import uz.pdp.task10.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;

    // CREATE
    @PostMapping
    private String addHotel(@RequestBody Hotel hotel) {
        boolean exists = hotelRepository.existsByName(hotel.getName());
        if (exists) {
            return "This hotel already added";
        }
        hotelRepository.save(hotel);
        return "Hotel added";
    }

    // READ
    @GetMapping
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    // ReadById
    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        return optionalHotel.orElseGet(Hotel::new);
    }

    // UPDATE
    @PutMapping("/{id}")
    public String editedHotel(@PathVariable Integer id, @RequestBody Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel editedHotel = optionalHotel.get();
            editedHotel.setName(hotel.getName());
            hotelRepository.save(editedHotel);
            return "Hotel edited";
        }
        return "Hotel not found";
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id) {
        try {
            hotelRepository.deleteById(id);
            return "Hotel deleted";
        } catch (Exception e) {
            return "Hotel not found";
        }
    }
}
