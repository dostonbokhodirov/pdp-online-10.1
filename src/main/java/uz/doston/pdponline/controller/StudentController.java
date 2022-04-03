package uz.doston.pdponline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.doston.pdponline.entity.Address;
import uz.doston.pdponline.entity.Group;
import uz.doston.pdponline.entity.Student;
import uz.doston.pdponline.entity.Subject;
import uz.doston.pdponline.payload.StudentDto;
import uz.doston.pdponline.repository.AddressRepository;
import uz.doston.pdponline.repository.GroupRepository;
import uz.doston.pdponline.repository.StudentRepository;
import uz.doston.pdponline.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository studentRepository;
    private final AddressRepository addressRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;

    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAll(pageable);
    }

    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentListForUniversity(
            @PathVariable Integer universityId,
            @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
    }

    @GetMapping("/forFaculty/{facultyId}")
    public Page<Student> getStudentListForFaculty(@PathVariable Integer facultyId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        return studentRepository.findAllByGroup_Faculty_Id(facultyId, pageable);
    }

    @GetMapping("/forGroupOwner/{groupId}")
    public Page<Student> getStudentListForGroupOwner(@PathVariable Integer groupId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        return studentRepository.findAllByGroup_Id(groupId, pageable);
    }

    @PostMapping("/add")
    public String add(@RequestBody StudentDto studentDto) {
        Address address = new Address();
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        Address savedAddress = addressRepository.save(address);
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (optionalGroup.isEmpty()) return "Group not found";
        Student student = new Student();
        student.setAddress(savedAddress);
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        save(studentDto, optionalGroup, student);
        return "Student successfully added";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            Integer addressId = student.getAddress().getId();
            studentRepository.deleteById(id);
            addressRepository.deleteById(addressId);
        }
        return "Successfully deleted";
    }

    @PutMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, @RequestBody StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) return "Student not found";
        Address address = new Address();
        address.setId(optionalStudent.get().getAddress().getId());
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        Address savedAddress = addressRepository.save(address);
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (optionalGroup.isEmpty()) return "Group not found";
        Student student = new Student();
        student.setId(optionalStudent.get().getId());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setAddress(savedAddress);
        save(studentDto, optionalGroup, student);
        return "Successfully edited";
    }

    private void save(@RequestBody StudentDto studentDto, Optional<Group> optionalGroup, Student student) {
        if (optionalGroup.isPresent()) {
            student.setGroup(optionalGroup.get());
            List<Subject> subjects = new ArrayList<>();
            for (Integer id : studentDto.getSubjectsId()) {
                Optional<Subject> optionalSubject = subjectRepository.findById(id);
                optionalSubject.ifPresent(subjects::add);
            }
            student.setSubjects(subjects);
            studentRepository.save(student);
        }
    }

}
