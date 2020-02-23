package edu.yuriikoval1997.springjdbcexp.dao;

import edu.yuriikoval1997.springjdbcexp.entities.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    List<Employee> findAll();

    void create(Employee employee);

    Optional<Employee> findById(Long employeeId);

    int deleteById(Long employeeId);

    int updateEmployeeNameById(String newEmployeeName, Long employeeId);
}
