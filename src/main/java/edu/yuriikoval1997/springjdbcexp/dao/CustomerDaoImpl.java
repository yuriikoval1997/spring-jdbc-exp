package edu.yuriikoval1997.springjdbcexp.dao;

import edu.yuriikoval1997.springjdbcexp.entities.Customer;
import edu.yuriikoval1997.springjdbcexp.resultset_extractors.CustomerOrderExtractor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerOrderExtractor customerOrderExtractor;

    @Autowired
    public CustomerDaoImpl(JdbcTemplate jdbcTemplate, CustomerOrderExtractor customerOrderExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerOrderExtractor = customerOrderExtractor;
    }

    @Override
    public List<Customer> findAllCustomersEager() {
        return jdbcTemplate.query(
            "SELECT c.id AS cid, c.email, o.id AS oid, o.uuid, o.customer_id " +
                "FROM customers c " +
                "LEFT JOIN orders o " +
                "ON c.id = o.customer_id",
            customerOrderExtractor
        );
    }
}
