package uz.doston.pdponline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.doston.pdponline.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    Page<Hotel> findAllById(Integer id, Pageable pageable);
}
