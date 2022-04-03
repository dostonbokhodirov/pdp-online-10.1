package uz.doston.pdponline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.doston.pdponline.entity.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    boolean existsByNameAndUniversityId(String name, Integer university_id);

    List<Faculty> findAllByUniversityId(Integer university_id);


}
