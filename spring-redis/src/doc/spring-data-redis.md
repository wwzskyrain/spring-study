# 1.spring-data-redis入门教程
## 1.  从错误中学经验
1.  `Cannot get Jedis connection; nested exception is redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool`
    *   根据错误提示信息一看便知，连接参数配置错误；修改下port、password，就好了。