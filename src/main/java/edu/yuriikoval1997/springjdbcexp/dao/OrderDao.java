package edu.yuriikoval1997.springjdbcexp.dao;

import edu.yuriikoval1997.springjdbcexp.entities.Order;
import java.util.List;
import java.util.Set;

public interface OrderDao {

    List<Order> findAll();

    List<Order> findByCustomerId(Long customerId);

    void save(Order order);

    void saveAll(Set<Order> order);
}
