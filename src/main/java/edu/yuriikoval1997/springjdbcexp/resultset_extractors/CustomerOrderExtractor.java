package edu.yuriikoval1997.springjdbcexp.resultset_extractors;

import edu.yuriikoval1997.springjdbcexp.entities.Customer;
import edu.yuriikoval1997.springjdbcexp.entities.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component("customerOrderExtractor")
public class CustomerOrderExtractor implements ResultSetExtractor<List<Customer>> {

    /**
     * {@link ResultSet} by default is scrollable only forward!
     *
     * @param rs - {@link ResultSet}
     * @return {@link List} of {@link Customer}s along with their {@link Order}s.
     */
    @Override
    public List<Customer> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var resultMap = new HashMap<Long, Customer>();
        Customer currentCustomer = null;
        while (rs.next()) {
            Long customerId = rs.getLong("cid");
            if (currentCustomer == null || ! currentCustomer.getId().equals(customerId)) {
                currentCustomer = new Customer(
                    customerId,
                    rs.getString("email"),
                    new HashSet<>()
                );
            }

            // rs.getLong("<column-name>") returns 0, if the value is NULL
            String ordersCustomerId = rs.getString("customer_id");
            if (ordersCustomerId != null) {
                var order = new Order(
                    rs.getLong("oid"),
                    rs.getString("uuid"),
                    rs.getLong("customer_id")
                );
                currentCustomer.getOrders().add(order);
            }
            resultMap.put(customerId, currentCustomer);
        }
        return List.copyOf(resultMap.values());
    }
}