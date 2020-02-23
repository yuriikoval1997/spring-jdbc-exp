package edu.yuriikoval1997.springjdbcexp;

import edu.yuriikoval1997.springjdbcexp.dao.CustomerDao;
import edu.yuriikoval1997.springjdbcexp.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringJdbcExpApplication implements CommandLineRunner {

    private final EmployeeDao employeeDao;
    private final CustomerDao customerDao;

    public SpringJdbcExpApplication(
        @Qualifier("employeeDaoWithJdbcTemplate") EmployeeDao employeeDao,
        CustomerDao customerDao) {
        this.employeeDao = employeeDao;
        this.customerDao = customerDao;
    }

    public static void main(String[] args) {
		SpringApplication.run(SpringJdbcExpApplication.class, args);
	}

    @Override
    public void run(String... args) {
        customerDao
            .findAllCustomersEager()
            .forEach(System.out::println);
        employeeDao
            .findAll()
            .forEach(System.out::println);
    }
}
