package erik.study.spring.aop.tutorial.exception;

/**
 * @Date 2019-09-27
 * @Created by erik
 */
public class EmployeeEmptyException extends EmployeeException {

    public EmployeeEmptyException(String name) {
        super(name);
    }

}
