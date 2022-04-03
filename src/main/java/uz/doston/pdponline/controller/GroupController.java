package uz.doston.pdponline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.doston.pdponline.entity.Faculty;
import uz.doston.pdponline.entity.Group;
import uz.doston.pdponline.payload.GroupDto;
import uz.doston.pdponline.repository.FacultyRepository;
import uz.doston.pdponline.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    GroupRepository groupRepository;
    FacultyRepository facultyRepository;

    @GetMapping
    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    @GetMapping("/byUniversityId/{universityId}")
    public List<Group> getGroupsByUniversityId(@PathVariable Integer universityId) {
        List<Group> allByFaculty_universityId = groupRepository.findAllByFaculty_UniversityId(universityId);
        List<Group> groupsByUniversityId = groupRepository.getGroupsByUniversityId(universityId);
        List<Group> groupsByUniversityIdNative = groupRepository.getGroupsByUniversityIdNative(universityId);
        return allByFaculty_universityId;
    }

    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());
        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if (optionalFaculty.isEmpty()) {
            return "Such faculty not found";
        }
        group.setFaculty(optionalFaculty.get());
        groupRepository.save(group);
        return "Group added";
    }

}
