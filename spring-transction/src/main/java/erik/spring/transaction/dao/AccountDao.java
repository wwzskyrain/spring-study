package erik.spring.transaction.dao;

import java.math.BigDecimal;

public interface AccountDao {

    void out(String outer, BigDecimal money);
    void in(String inner, BigDecimal money);

}
