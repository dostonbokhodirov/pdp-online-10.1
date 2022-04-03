package uz.doston.pdponline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.doston.pdponline.entity.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {
}
