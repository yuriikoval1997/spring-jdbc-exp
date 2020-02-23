package edu.yuriikoval1997.springjdbcexp.entities;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "uuid")
@Data
public class Order {

    private Long id;
    private String uuid = UUID.randomUUID().toString();
    private Long customerId;
}
