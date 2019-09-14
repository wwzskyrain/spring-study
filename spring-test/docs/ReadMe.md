# 1.配置文件注入
1.  使用<context:property-placeholder>指定的配置文件，既可以在spring.xml中读取，也可以在自动注入的Bean中使用@{"#{key}"}来读取。详细代码见 inject包。
2.  此次整理了一下spring-test，标准化了log配置、Spring Unit的写法。