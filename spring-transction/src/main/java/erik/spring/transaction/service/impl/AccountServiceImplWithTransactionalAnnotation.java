package erik.spring.transaction.service.impl;

import erik.spring.transaction.dao.AccountDao;
import erik.spring.transaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class AccountServiceImplWithTransactionalAnnotation implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional
    public void transfer(String outer, String inner, BigDecimal money) {

        accountDao.out(outer, money);

        int i = 1 / 0;

        accountDao.in(inner, money);

    }
}
