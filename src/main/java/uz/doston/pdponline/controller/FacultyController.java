package uz.doston.pdponline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.doston.pdponline.entity.Faculty;
import uz.doston.pdponline.entity.University;
import uz.doston.pdponline.payload.FacultyDto;
import uz.doston.pdponline.repository.FacultyRepository;
import uz.doston.pdponline.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/faculty")
public class FacultyController {

    FacultyRepository facultyRepository;
    UniversityRepository universityRepository;

    @GetMapping
    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto) {
        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exists)
            return "This university such faculty exist";
        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());
        Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
        if (optionalUniversity.isEmpty())
            return "University not found";
        faculty.setUniversity(optionalUniversity.get());
        facultyRepository.save(faculty);
        return "Faculty saved";
    }

    @GetMapping("/byUniversityId/{universityId}")
    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId) {
        return facultyRepository.findAllByUniversityId(universityId);
    }


    @DeleteMapping("/{id}")
    public String deleteFaculty(@PathVariable Integer id) {
        try {
            facultyRepository.deleteById(id);
            return "Faculty deleted";
        } catch (Exception e) {
            return "Error in deleting";
        }
    }

    @PutMapping("/{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) {
            return "Faculty not found";
        }
        Faculty faculty = optionalFaculty.get();
        faculty.setName(facultyDto.getName());
        Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
        if (optionalUniversity.isEmpty()) {
            return "University not found";
        }
        faculty.setUniversity(optionalUniversity.get());
        facultyRepository.save(faculty);
        return "Faculty edited";
    }


}
