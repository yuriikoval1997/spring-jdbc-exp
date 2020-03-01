package edu.yuriikoval1997.springjdbcexp.dao;

import edu.yuriikoval1997.springjdbcexp.entities.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> findAllCustomersEager();

    Optional<Customer> findById(Long id);
}
