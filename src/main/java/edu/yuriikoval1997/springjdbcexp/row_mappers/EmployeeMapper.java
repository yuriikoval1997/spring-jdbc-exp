package edu.yuriikoval1997.springjdbcexp.row_mappers;

import edu.yuriikoval1997.springjdbcexp.entities.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Employee(
            rs.getLong("employee_id"),
            rs.getString("employee_name"),
            rs.getBigDecimal("salary"),
            rs.getString("gender"),
            rs.getString("email")
        );
    }
}
