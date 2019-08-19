package erik.spring.transaction.service.impl;

import erik.spring.transaction.dao.AccountDao;
import erik.spring.transaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


import java.math.BigDecimal;

@Service
public class AccountServiceImplWithTransactionTemplate implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public void transfer(final String outer,final String inner,final BigDecimal money) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                accountDao.out(outer, money);
                System.out.println("out over");

//                int i = 1/0;

                accountDao.in(inner, money);
                System.out.println("in over");

            }
        });


    }
}
