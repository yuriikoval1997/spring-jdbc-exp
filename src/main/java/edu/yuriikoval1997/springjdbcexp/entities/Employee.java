package edu.yuriikoval1997.springjdbcexp.entities;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = {"email", "gender"})
public class Employee {

    private Long employeeId;
    private String employeeName;
    private BigDecimal salary;
    private String email;
    private String gender;
}
