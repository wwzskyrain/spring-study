# 1.目标：
1.  总概括：理解定时调度任务功能抽象，并熟悉实践Spring的schedule框架
2.  具体目标
    1.理解定时调度抽象
    2.spring实现定时调度、cron调度


# 2.实践
## 2.1 定时调度抽象
任务的执行即任务的调度；执行一次和执行多次是没有本质难度区别的；有区别的是对执行时间点的表达。
根据对时间点的表达的难易程度分成以下四级

执行逻辑描述 | 相关类 | 相关方法 | 备注
--- | --- | --- | ---
立刻执行一次|Executor|execute(Runnable command) |
重复执行 | ScheduledExecutorService  |  schedule(Runnable command,long delay, TimeUnit unit) scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit).... | jdk提供的接口
cron表达式执行| TaskScheduler | schedule(Runnable task, Trigger trigger) scheduleAtFixedRate(Runnable task, Date startTime, long period) | spring提供的接口
自定义时间点| 自定义 | 自定义 | 可以参考Spring的TaskScheduler

如果想用cron的方式调度任务，那就最好使用Spring提供的功能；
然而，TaskScheduler的推荐实现是ThreadPoolTaskScheduler，然而该scheduler中需要一个执行器ScheduledExecutorService，
当然这个执行器是jdk的接口，jdk自然有实现；可是Spring没有给出这个属性的set方法，也即是我们不能使用new的方式
来创建`ThreadPoolTaskScheduler` (然后在设置它的属性)，而只能依赖spring的形式，即配置bean，而且只能用Spring容器中拿到它；
追加：这里就有一个问题，new ThreadPoolTaskScheduler和在spring-配置文件中配置一个它的bean有什么不同呢？

## 2.2 实践
1.  立刻调用
    `TaskExecutor`接口，参见`TaskExecutorExample`
2.  jdk提供的`ScheduledExecutorService`接口，参见`BeeperControl`
3.  spring提供的`TaskScheduler`接口，参见`TaskSchedulerExample`
4.


## 2.3 实践小节
1.  对于基础的任务调度，jdk已经给出了解决方案，那就是接口`ScheduledExecutorService`，
提供了基础的`scheduleAtFixedRate`，`scheduleWithFixedDelay`等功能
2.  对于cron表达式定义的时间点序列的任务调度，spring给出了解决方案，那就是接口`TaskScheduler`
除了提供基础调用逻辑外，还提供了按照cron表达式进行的调度功能`schedule(Runnable task, Trigger trigger)`
3.  自定义。。
4.  quartz调用






# 附录
## A.spring的beans模板
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="..." class="...">
        <!-- collaborators and configuration for this bean go here -->
    </bean>

    <bean id="..." class="...">
        <!-- collaborators and configuration for this bean go here -->
    </bean>

    <!-- more bean definitions go here -->

</beans>
```