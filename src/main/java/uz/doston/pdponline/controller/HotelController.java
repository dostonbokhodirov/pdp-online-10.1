package uz.doston.pdponline.controller;

import uz.doston.pdponline.dto.HotelDto;
import uz.doston.pdponline.entity.Hotel;
import uz.doston.pdponline.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotel")
public class HotelController {

    private final HotelRepository hotelRepository;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody HotelDto hotelDto) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelDto.getName());
        hotelRepository.save(hotel);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(hotelRepository.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Hotel> allById = hotelRepository.findAllById(id, pageable);
        return ResponseEntity.ok().body(allById);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        if (hotelRepository.existsById(id)){
            hotelRepository.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        }
        return ResponseEntity.badRequest().body("Not found");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody HotelDto hotelDto){
        Optional<Hotel> optional = hotelRepository.findById(id);
        if (optional.isPresent()){
            Hotel hotel = new Hotel();
            hotel.setId(optional.get().getId());
            hotel.setName(hotelDto.getName());
            hotelRepository.save(hotel);
            return ResponseEntity.ok("Successfully edited");
        }
        return ResponseEntity.badRequest().body("Not found");
    }
}
