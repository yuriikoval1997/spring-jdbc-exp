package edu.yuriikoval1997.springjdbcexp.dao;

import edu.yuriikoval1997.springjdbcexp.entities.Employee;
import edu.yuriikoval1997.springjdbcexp.row_mappers.EmployeeMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository("employeeDaoWithNamedJdbcTemplate")
public class EmployeeDaoNamed implements EmployeeDao {

    private final NamedParameterJdbcTemplate namedJdbc;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeDaoNamed(NamedParameterJdbcTemplate namedJdbc, EmployeeMapper employeeMapper) {
        this.namedJdbc = namedJdbc;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<Employee> findAll() {
        return namedJdbc.query(
            "SELECT * FROM employee_table",
            employeeMapper
        );
    }

    /**
     * WARNING, this method mutates the given object by setting generated id.
     * Do not forget to specify which field is generated!
     * In this example, <code>new String[]{"employee_id"}</code>;
     *
     * @param employee {@link Employee}
     */
    @Override
    public void save(Employee employee) {
        var keyHolder = new GeneratedKeyHolder();
        var params = new MapSqlParameterSource(Map.of(
            "employeeName", employee.getEmployeeName(),
            "salary", employee.getSalary(),
            "email", employee.getSalary(),
            "gender", employee.getGender()
        ));
        // If you do not specify the name of columns you want to retrieve after the insertion,
        // you will get all the field (at least with Postgresql)
        namedJdbc.update(
            "INSERT INTO employee_table (employee_name, salary, email, gender) " +
                "VALUES (:employeeName, :salary, :email, :gender)",
            params,
            keyHolder,
            new String[]{"employee_id"}
        );
        if (keyHolder.getKey() == null) {
            throw new RuntimeException("Primary key has no been retrieved!");
        }
        employee.setEmployeeId(keyHolder.getKey().longValue());
    }

    @Override
    public Optional<Employee> findById(Long id) {
        List<Employee> res = namedJdbc.query(
            "SELECT * FROM employee_table WHERE employee_id = :id",
            Map.of("id", id),
            employeeMapper
        );
        if (res.isEmpty()) {
            return Optional.empty();
        }
        if (res.size() > 1) {
            throw new RuntimeException("There are more than one employee with given id!");
        }
        return Optional.of(res.get(0));
    }

    @Override
    public int deleteById(Long employeeId) {
        return namedJdbc.update(
            "DELETE FROM employee_table WHERE employee_id = :employeeId",
            Map.of("employeeId", employeeId)
        );
    }

    @Override
    public int updateEmployeeNameById(String newEmployeeName, Long employeeId) {
        return namedJdbc.update(
            "UPDATE employee_table SET employee_name = :newEmployeeName WHERE employee_id = :employeeId",
            Map.of(
                "newEmployeeName", newEmployeeName,
                "employeeId", employeeId
            )
        );
    }
}
