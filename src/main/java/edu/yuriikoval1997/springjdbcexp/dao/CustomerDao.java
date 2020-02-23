package edu.yuriikoval1997.springjdbcexp.dao;

import edu.yuriikoval1997.springjdbcexp.entities.Customer;
import java.util.Collection;
import java.util.List;

public interface CustomerDao {

    Collection<Customer> findAllCustomersEager();
}
