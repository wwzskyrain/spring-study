package inject.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author erik.wang
 * @date 2019/05/19
 **/

@Component
public class InjectTest {

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.username}")
    private String dbUserName;


    @Value("${db.password}")
    private String dbPassword;

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
}
