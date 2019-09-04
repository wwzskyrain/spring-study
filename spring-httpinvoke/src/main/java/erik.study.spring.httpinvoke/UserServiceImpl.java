package erik.study.spring.httpinvoke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see
     * org.zlex.spring.httpinvoke.service.UserService#getUser(java.lang.String)
     */
    @Override
    public User getUser(String username) {
        if (logger.isDebugEnabled()) {
            logger.debug("username:[" + username + "]");
        }
        User user = new User(username, new Date());
        if (logger.isDebugEnabled()) {
            logger.debug("user:[" + user + "]");
        }
        return user;
    }

} 