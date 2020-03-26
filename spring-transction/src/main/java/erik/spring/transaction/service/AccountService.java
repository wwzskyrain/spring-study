package erik.spring.transaction.service;

import java.math.BigDecimal;

public interface AccountService {

    /**
     *
     * @param outer
     * @param inner
     * @param money
     */
    void transfer(String outer, String inner, BigDecimal money);

    /**
     *
     * @param outer
     * @param inner
     * @param money
     */
    void transferFacade(String outer, String inner, BigDecimal money);

}
