package edu.yuriikoval1997.springjdbcexp.exceptions;

public class NoUniqueResult extends RuntimeException {

    public NoUniqueResult(String message) {
        super(message);
    }
}
