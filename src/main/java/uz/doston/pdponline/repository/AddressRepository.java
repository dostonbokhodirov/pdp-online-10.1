package uz.doston.pdponline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.doston.pdponline.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
