package erik.spring.transaction.service.impl;

import erik.spring.transaction.dao.AccountDao;
import erik.spring.transaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

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
}
