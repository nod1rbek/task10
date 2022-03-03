package uz.pdp.task10.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task10.entity.Room;



public interface RoomRepository extends JpaRepository<Room, Integer> {

    boolean existsByNumberAndFloor(String number, Integer floor);

    Page<Room> findAllByHotelId(Integer hotel_id, Pageable pageable);
}
