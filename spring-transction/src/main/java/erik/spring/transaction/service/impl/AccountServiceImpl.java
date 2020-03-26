package erik.spring.transaction.service.impl;

import erik.spring.transaction.dao.AccountDao;
import erik.spring.transaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author
 * 没有事务控制的转账服务实现
 */
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public void transfer(String outer, String inner, BigDecimal money) {
        accountDao.out(outer, money);
        System.out.println("out over");

        accountDao.in(inner, money);
        System.out.println("in over");
    }


    @Override
    public void transferFacade(String outer, String inner, BigDecimal money) {

    }
}
