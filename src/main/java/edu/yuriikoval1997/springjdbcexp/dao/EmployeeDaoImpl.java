package edu.yuriikoval1997.springjdbcexp.dao;

import edu.yuriikoval1997.springjdbcexp.entities.Employee;
import edu.yuriikoval1997.springjdbcexp.exceptions.GeneratedKeyHasNotBeenRetrieved;
import edu.yuriikoval1997.springjdbcexp.exceptions.NoUniqueResult;
import edu.yuriikoval1997.springjdbcexp.row_mappers.EmployeeMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository("employeeDaoWithJdbcTemplate")
public class EmployeeDaoImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeDaoImpl(JdbcTemplate jdbcTemplate, EmployeeMapper employeeMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM employee_table",
            employeeMapper
        );
    }

    /**
     * WARNING, this method mutates the given object by setting generated id.
     * Getting generated id is tricky with {@link JdbcTemplate}. You have to create {@link PreparedStatement} and
     * specify which field will be generated. Otherwise, you may get unexpected result. Postgres, for instance,
     * returns all of the fields.
     *
     * @param employee {@link Employee}
     */
    @Override
    public void create(Employee employee) {
        var keyHolder = new GeneratedKeyHolder();
        // If you do not specify the name of columns you want to retrieve after the insertion,
        // you will get all of the field (at least with Postgresql)
        jdbcTemplate.update(
            (Connection conn) -> {
                PreparedStatement prep = conn.prepareStatement(
                    "INSERT INTO employee_table (employee_name, salary, email, gender) VALUES (?, ?, ?, ?)",
                    new String[]{"employee_id"}
                );
                prep.setString(1, employee.getEmployeeName());
                prep.setBigDecimal(2, employee.getSalary());
                prep.setString(3, employee.getEmail());
                prep.setString(4, employee.getGender());
                return prep;
            },
            keyHolder
        );
        if (keyHolder.getKey() == null) {
            throw new GeneratedKeyHasNotBeenRetrieved("Primary key has no been retrieved!");
        }
        employee.setEmployeeId(keyHolder.getKey().longValue());
    }

    @Override
    public Optional<Employee> findById(Long employeeId) {
        List<Employee> res = jdbcTemplate.query(
            "SELECT * FROM employee_table WHERE employee_id = ?",
            employeeMapper,
            employeeId
        );
        if (res.isEmpty()) {
            return Optional.empty();
        }
        if (res.size() > 1) {
            throw new NoUniqueResult("There are more than one employee with given id!");
        }
        return Optional.of(res.get(0));
    }

    @Override
    public int deleteById(Long employeeId) {
        return jdbcTemplate.update(
            "DELETE FROM employee_table WHERE employee_id = ?",
            employeeId
        );
    }

    @Override
    public int updateEmployeeNameById(String newEmployeeName, Long employeeId) {
        return jdbcTemplate.update(
            "UPDATE employee_table SET employee_name = ? WHERE employee_id = ?",
            newEmployeeName,
            employeeId
        );
    }
}
