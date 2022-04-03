package uz.doston.pdponline.controller;

import uz.doston.pdponline.dto.RoomDto;
import uz.doston.pdponline.entity.Hotel;
import uz.doston.pdponline.entity.Room;
import uz.doston.pdponline.repository.HotelRepository;
import uz.doston.pdponline.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody RoomDto roomDto) {
        Room room = new Room();
        room.setFloor(roomDto.getFloor());
        room.setNumber(roomDto.getNumber());
        room.setSize(roomDto.getSize());
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        optionalHotel.ifPresent(room::setHotel);
        roomRepository.save(room);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(roomRepository.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Slice<Room> allById = roomRepository.findAllByHotel_Id(id, pageable);
        return ResponseEntity.ok().body(allById);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        }
        return ResponseEntity.badRequest().body("Not found");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        Optional<Room> optional = roomRepository.findById(id);
        if (optional.isPresent()) {
            Room room = new Room();
            room.setSize(roomDto.getSize());
            room.setFloor(roomDto.getFloor());
            room.setNumber(roomDto.getNumber());
            roomRepository.save(room);
            return ResponseEntity.ok("Successfully edited");
        }
        return ResponseEntity.badRequest().body("Not found");
    }
}
