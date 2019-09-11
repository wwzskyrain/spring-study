# spring base

## 1.java-based erik.study.spring.base.annotation.config
### 1.1 Bean dependency to bean method parameter
1.  Bean方法参数的注入，即Bean依赖，有四种情况
    ```$xslt
    1.  Injecting by type
        如果容器中只有参数类型的实例，则通过参数类型注入 
    2.  Injecting by name:
        如果容器中有多个参数类型的实例，则这时就不能用'by tpye'的形式来注入，而是用'by name'；
        Bean的名字可以有两种方式定义，一是被@Bean所注解的方法名，二是@Bean(name = beanName);
    3.  Injecting by bean's name with matching @Qualifier
        这也是一种byName，name通过@Bean(name = beanName) 来完成；
        注入点通过@Qualifier(value = beanName)注解到参数上来完成；
    4.  Injecting by matching @Qualifiers
        与3相似，不过注入点是通过@Qualifier(value = qualifier)来完成；
        相应的，bean的qualifer也是需要定义的，定义用@Qualifier来做；
    ```

### 1.2 Bean的id，name，qualifier 都是什么意思？
```$xslt
    1.  id = name ，两者只设置一个；
    2.  在基于java的配置中，比如@Bean注解，根本就没有id属性；
    3.  @Bean注解name属性的值可以是一个字符串数组，表示可以用多个名称来指代Bean实例
        ——在ApplicationContext.get(beanName)时，返回相同的Bean实例
    
    4.  qualifer也是用来标识bean实例的，定义和使用都是由这@Qualifier注解完成；
        如果bean定义时没有指定qualifier，那么qualifier为bean的id-为什么不是name？哪一个name？第一个？
```

### 1.3 @Autowire+@Qualifier  VS @Resource
  
```markdown
    1.  作用域不同而已，@Qualifier可以作用到多参数函数的参数上，
        @Resource只能作用的设置属性的单参数setter函数上；
  
    [详见spring文档只 tip](https://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/beans.html#beans-autowired-annotation-qualifiers)
```

    