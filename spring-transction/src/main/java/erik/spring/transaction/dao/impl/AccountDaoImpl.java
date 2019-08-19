package erik.spring.transaction.dao.impl;

import erik.spring.transaction.dao.AccountDao;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.math.BigDecimal;

public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {

    @Override
    public void out(String outer, BigDecimal money) {
        this.getJdbcTemplate().update(
                "UPDATE ACCOUNT SET BALANCE = BALANCE - ? WHERE USER_NAME = ?", money, outer);
    }

    @Override
    public void in(String inner, BigDecimal money) {
        this.getJdbcTemplate().update(
                "UPDATE ACCOUNT SET BALANCE = BALANCE + ? WHERE USER_NAME = ?", money, inner);
    }
}
