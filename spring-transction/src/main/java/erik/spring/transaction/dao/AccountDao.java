package erik.spring.transaction.dao;

import java.math.BigDecimal;

/**
 * @author
 */
public interface AccountDao {

    /**
     * 扣减指定账户金额
     *
     * @param outer
     * @param money
     */
    void out(String outer, BigDecimal money);

    /**
     * 添加指定账户金额
     *
     * @param inner
     * @param money
     */
    void in(String inner, BigDecimal money);

}
