package edu.yuriikoval1997.springjdbcexp.entities;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "email")
@Data
public class Customer {

    private Long id;
    private String email;
    private Set<Order> orders;
}
