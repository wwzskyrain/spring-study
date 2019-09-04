






# 遇到的问题
1.  jsp页面中的表达式没有填充，而原样输出，比如`${product.name}`
2.  答：是因为在web.xml中没有引用servlet3.1规范，web-app标签应该是。参考[SpringMVC中JSP页面不显示EL表达式的原因](https://blog.csdn.net/renfufei/article/details/54599835)
    ```xml
    <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    //...
    </web-app>
    ```