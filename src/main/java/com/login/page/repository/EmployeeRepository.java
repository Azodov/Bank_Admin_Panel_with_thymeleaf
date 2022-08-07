package com.login.page.repository;

import com.login.page.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "select * from clients where card_number = :cardNumber", nativeQuery = true)
    Employee findByCardNumber(@Param("cardNumber") String cardNumber);

}
