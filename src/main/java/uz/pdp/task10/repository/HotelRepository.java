package uz.pdp.task10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task10.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    boolean existsByName(String name);


}
