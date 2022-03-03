package uz.pdp.task10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task10.entity.Hotel;
import uz.pdp.task10.entity.Room;
import uz.pdp.task10.payload.RoomDto;
import uz.pdp.task10.repository.HotelRepository;
import uz.pdp.task10.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    // CREATE
    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto) {
        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent()) {
            room.setHotel(optionalHotel.get());
            roomRepository.save(room);
            return "Room added";
        }
        return "ERROR! Hotel not found";
    }

    // READ
    @GetMapping
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    // Get all rooms in hotel by id
    @GetMapping("/{hotelId}")
    public Page<Room> getRoomsByHotelId(@PathVariable Integer hotelId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return roomRepository.findAllByHotelId(hotelId, pageable);
    }

    // UPDATE
    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        boolean exists = roomRepository.existsByNumberAndFloor(roomDto.getNumber(), roomDto.getFloor());
        if (exists) {
            return "This room already added";
        }
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.setNumber(roomDto.getNumber());
            room.setFloor(roomDto.getFloor());
            room.setSize(roomDto.getSize());
            Optional<Hotel> optionalHotel = hotelRepository.findById(id);
            optionalHotel.ifPresent(room::setHotel);
            return "Room edited";
        }
        return "Room not found";
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        roomRepository.deleteById(id);
        return "Room deleted";
    }
}
