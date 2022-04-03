package uz.doston.pdponline.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.doston.pdponline.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Slice<Room> findAllByHotel_Id(Integer hotel_id, Pageable pageable);
}
