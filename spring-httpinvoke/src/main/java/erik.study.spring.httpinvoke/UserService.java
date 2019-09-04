package erik.study.spring.httpinvoke;

public interface UserService {
  
    /** 
     * 获得用户 
     *  
     * @param username 
     *            用户名 
     * @return 
     */  
    User getUser(String username);  
}  