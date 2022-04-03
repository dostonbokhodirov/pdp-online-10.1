package uz.doston.pdponline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.doston.pdponline.entity.Address;
import uz.doston.pdponline.entity.University;
import uz.doston.pdponline.payload.UniversityDto;
import uz.doston.pdponline.repository.AddressRepository;
import uz.doston.pdponline.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UniversityController {

    UniversityRepository universityRepository;
    AddressRepository addressRepository;

    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities() {
        return universityRepository.findAll();
    }

    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto) {
        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        Address savedAddress = addressRepository.save(address);
        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);
        universityRepository.save(university);
        return "University added";
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isEmpty()) return "University not found";
        University university = optionalUniversity.get();
        university.setName(universityDto.getName());
        Address address = university.getAddress();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        addressRepository.save(address);
        universityRepository.save(university);
        return "University edited";
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id) {
        universityRepository.deleteById(id);
        return "University deleted";
    }
}
