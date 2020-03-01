package edu.yuriikoval1997.springjdbcexp.dao;

import edu.yuriikoval1997.springjdbcexp.entities.Customer;
import edu.yuriikoval1997.springjdbcexp.entities.Employee;
import edu.yuriikoval1997.springjdbcexp.exceptions.NoUniqueResult;
import edu.yuriikoval1997.springjdbcexp.resultset_extractors.CustomerOrderExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    private final JdbcTemplate jdbc;
    private final CustomerOrderExtractor customerOrderExtractor;

    @Autowired
    public CustomerDaoImpl(JdbcTemplate jdbc, CustomerOrderExtractor customerOrderExtractor) {
        this.jdbc = jdbc;
        this.customerOrderExtractor = customerOrderExtractor;
    }

    @Override
    public List<Customer> findAllCustomersEager() {
        return jdbc.query(
            "SELECT c.id AS cid, c.email, o.id AS oid, o.uuid, o.customer_id " +
                "FROM customers c " +
                "LEFT JOIN orders o " +
                "ON c.id = o.customer_id",
            customerOrderExtractor
        );
    }

    @Override
    public Optional<Customer> findById(Long id) {
        List<Customer> res = jdbc.query(
            "SELECT * FROM customers WHERE id = ?",
            (rs, rowNum) -> new Customer(
                rs.getLong("id"),
                rs.getString("email"),
                null
            ),
            id
        );
        if (res.isEmpty()) {
            return Optional.empty();
        }
        if (res.size() > 1) {
            throw new NoUniqueResult("There is more than one employee with given id!");
        }
        return Optional.of(res.get(0));
    }
}
