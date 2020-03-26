package erik.spring.transaction.service.impl;

import erik.spring.transaction.dao.AccountDao;
import erik.spring.transaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author 用注解@Transactional添加事务特性的'转账服务实现'
 */
public class AccountServiceImplWithTransactionalAnnotation implements AccountService {

    private static final String MAX_LIMIT_TO_TRANSFER = "10";

    @Autowired
    private AccountDao accountDao;

    @Override
    public void transfer(String outer, String inner, BigDecimal money) {
        accountDao.out(outer, money);

        if (money.compareTo(new BigDecimal(MAX_LIMIT_TO_TRANSFER)) > 0) {
            throw new RuntimeException("money is than 10.");
        }
        accountDao.in(inner, money);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void transferFacade(String outer, String inner, BigDecimal money) {
        transfer(outer, inner, money);
    }
}
