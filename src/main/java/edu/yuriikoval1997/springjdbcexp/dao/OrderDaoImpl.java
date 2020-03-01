package edu.yuriikoval1997.springjdbcexp.dao;

import edu.yuriikoval1997.springjdbcexp.entities.Order;
import edu.yuriikoval1997.springjdbcexp.exceptions.GeneratedKeyHasNotBeenRetrieved;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Set;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final JdbcTemplate jdbc;

    public OrderDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Order> findAll() {
        return jdbc.query("SELECT * FROM orders", new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        return jdbc.query(
            "SELECT * FROM orders WHERE customer_id = ?",
            new BeanPropertyRowMapper<>(Order.class),
            customerId);
    }

    @Override
    public void save(Order order) {
        var keyHolder = new GeneratedKeyHolder();
        jdbc.update((Connection conn) -> {
            PreparedStatement prep = conn.prepareStatement("INSERT INTO orders (uuid, customer_id) VALUES (?, ?)",
                new String[]{"id"});
            prep.setString(1, order.getUuid());
            prep.setLong(2, order.getCustomerId());
            return prep;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new GeneratedKeyHasNotBeenRetrieved("Primary key has no been retrieved!");
        }
        order.setId(keyHolder.getKey().longValue());
    }

    @Transactional
    @Override
    public void saveAll(Set<Order> orders) {
        jdbc.batchUpdate(
            "INSERT INTO orders (uuid, customer_id) VALUES (?, ?)",
            orders,
            orders.size(),
            (ps, order) -> {
                ps.setString(1, order.getUuid());
                ps.setLong(2, order.getCustomerId());
            });
    }
}
