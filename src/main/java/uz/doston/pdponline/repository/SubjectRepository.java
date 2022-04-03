package uz.doston.pdponline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.doston.pdponline.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByName(String name);

}
