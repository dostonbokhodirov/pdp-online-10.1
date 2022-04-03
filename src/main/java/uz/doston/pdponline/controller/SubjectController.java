package uz.doston.pdponline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.doston.pdponline.entity.Subject;
import uz.doston.pdponline.repository.SubjectRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/subject")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    @RequestMapping(method = RequestMethod.POST)
    public String addSubject(@RequestBody Subject subject) {
        if (subjectRepository.existsByName(subject.getName())) return "This subject already exist";
        subjectRepository.save(subject);
        return "Subject added";
    }

    @GetMapping
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }


}
