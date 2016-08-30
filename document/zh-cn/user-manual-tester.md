![](resource/logo.png) 1.0

----------

> **目录**

- [简介](#user-content-intro)
- [如何使用](#user-content-how)
- [优化设置](#user-content-conf)
- [测试项目](#user-content-project)
- [测试流程](#user-content-flow)

----------

# 用户手册 - Rexdb性能测试程序 #

## <div id="intro">简介</div> ##

本程序用于Rexdb在开发/生产环境中的运行效率，同时也测试了其它主流ORM框架、JDBC接口直接调用的性能，以便为开发者提供一个全方位的参考。参与测试的有：

- Rexdb
- Hibernate
- Mybatis
- Spring JDBC
- JDBC接口直接调用

各框架均使用了较新的正式版本，测试时均使用Apache Dbcp连接池。除此之外，本程序还集成了主流数据库的驱动，包括：

- oracle
- mysql
- SQL Server
- DB2
- postgresql
- derby
- h2
- hsqldb
- 金仓数据库
- 神通数据库
- 达梦数据库

## <div id="how">如何使用</div> ##

本程序已经集成了大部分数据库驱动和运行所需的Jar包，修改配置后直接运行启动文件即可。

1. 将本程序拷贝至待测试系统，并确保系统已经安装了JDK 6.0及以上版本
2. 修改/conf/conn.properties，启用并修改相应数据库的配置
3. 运行/bin/run.bat（Windows系统），或者/bin/run.sh（Linux系统），耐心等待执行完成

```bash
run.bat #Windows
./run.sh #Linux
```

## <div id="conf">优化设置</div> ##

在测试性能时，程序会反复执行重复同一操作，直到最终计算平均耗时。理论上，重复执行次数越多，测试数据越准确，但耗时也越长。在run.bat/run.sh文件中，执行了以下命令：

```
java -classpath ../conf -Djava.ext.dirs=../lib org.rex.db.test.RunAllTests speed=10 loop=30
```

您可以自行编辑该文件，修改以下的两项参数，以获取速度和误差之间的平衡：

- speed：执行速度，默认是10。值越大，每次测试中使用的数据量越小，误差越大
- loop：循环次数，默认是30。值越大，每个测试项目的循环次数越多，误差越小


## <div id="project">测试项目</div> ##

本程序测试了以下操作的性能：

- 更新
- 批处理
- 查询Java对象
- 查询Map对象

在测试时，测试程序可能会分别将不同的设置应用到同一测试项目。例如，测试更新时，程序分别测试了Java对象和org.rex.db.Ps对象做为参数时的性能；测试查询Java对象时，又分别测试了启用和禁用动态字节码参数时的性能。

## <div id="flow">测试流程</div> ##

本程序的测试流程如下：

1. 连接数据库，创建测试需要的表（部分数据库还会创建存储过程）
2. 测试Rexdb的各接口是否可用（部分数据库会测试调用接口）
3. 测试各框架是否可用
4. 执行性能测试
5. 输出结果
