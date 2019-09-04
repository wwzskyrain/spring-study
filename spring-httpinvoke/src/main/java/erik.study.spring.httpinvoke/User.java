package erik.study.spring.httpinvoke;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
  
    private static final long serialVersionUID = 5590768569302443813L;  
    private String username;  
    private Date birthday;
  
    /** 
     * @param username 
     * @param birthday 
     */  
    public User(String username, Date birthday) {  
        this.username = username;  
        this.birthday = birthday;  
    }  
       // 省略  
    /* 
     * (non-Javadoc) 
     *  
     * @see java.lang.Object#toString() 
     */  
    @Override  
    public String toString() {  
        return String.format("%s\t%s\t", username, birthday);  
    }  
}  
