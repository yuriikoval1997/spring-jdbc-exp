package edu.yuriikoval1997.springjdbcexp;

import edu.yuriikoval1997.springjdbcexp.dao.CustomerDao;
import edu.yuriikoval1997.springjdbcexp.dao.EmployeeDao;
import edu.yuriikoval1997.springjdbcexp.dao.OrderDao;
import edu.yuriikoval1997.springjdbcexp.entities.Order;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJdbcExpApplication implements CommandLineRunner {

    private final EmployeeDao employeeDao;
    private final CustomerDao customerDao;
    private final OrderDao orderDao;

    public SpringJdbcExpApplication(
        @Qualifier("employeeDaoWithJdbcTemplate") EmployeeDao employeeDao,
        CustomerDao customerDao, OrderDao orderDao) {
        this.employeeDao = employeeDao;
        this.customerDao = customerDao;
        this.orderDao = orderDao;
    }

    public static void main(String[] args) {
		SpringApplication.run(SpringJdbcExpApplication.class, args);
	}

    @Override
    public void run(String... args) {
        System.out.println("--------------- Employees ---------------");
        employeeDao
            .findAll()
            .forEach(System.out::println);

        System.out.println("--------------- Customers ---------------");
        customerDao
            .findAllCustomersEager()
            .forEach(System.out::println);

        System.out.println("--------------- Orders ---------------");
        orderDao
            .findAll()
            .forEach(System.out::println);

        System.out.println("--------------- Saved orders ---------------");
        var customer = customerDao.findById(4L).orElseThrow();
        var orders = Stream.generate(Order::new)
            .limit(3)
            .peek(order -> order.setCustomerId(customer.getId()))
            .collect(toSet());
        orderDao.saveAll(orders);
        orders.forEach(System.out::println);
    }
}
