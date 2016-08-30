![](resource/logo.png) 1.0

----------

> **目录**

- [简介](#user-content-intro)
- [如何使用](#user-content-how)
- [优化设置](#user-content-conf)
- [测试项目](#user-content-project)
- [测试流程](#user-content-flow)

----------

# 用户手册 - ORM框架性能测试程序 #

## <div id="intro">简介</div> ##

本程序用于测试各ORM框架的运行性能，也可以用于开发/生产环境数据库性能评估。参与测试的框架包括：

- Rexdb
- Hibernate
- Mybatis
- Spring JDBC
- JDBC接口直接调用

测试项目有：

- 写入性能
- 批处理性能
- 查询性能

程序已经集成了各主流数据库的驱动，在修改数据库连接配置后，可以直接使用。已经集成的驱动有：

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

各框架均使用了本程序发布时的最新正式版本，均使用了Apache dbcp连接池。程序运行结束后，将以文本形式在控制台展现各测试项目的“每秒操作次数”。在作者的台式机系统中的某次测试结果请参考：[http://db.rex-soft.org/performance.php](http://db.rex-soft.org/performance.php)。

## <div id="how">如何使用</div> ##

本程序已经集成了大部分数据库驱动和运行所需的Jar包，修改配置后，直接运行启动脚本文件即可。使用步骤如下：

1. 确保系统已经安装了JDK 6.0及以上版本
2. 修改conf/conn.properties，启用相应的数据库连接
3. 运行bin/run.bat（Windows系统），或者bin/run.sh（Linux系统），耐心等待执行完成

```bash
run.bat #Windows
./run.sh #Linux
```

## <div id="conf">优化设置</div> ##

在执行测试时，程序会反复执行重复同一操作，直到运行完毕后计算平均耗时。理论上，重复执行的次数越多，结果越准确，但耗时也越长。在run.bat/run.sh脚本中，实际执行了如下命令：

```
java -classpath ../conf -Djava.ext.dirs=../lib org.rex.db.test.RunAllTests speed=10 loop=30
```

您可以自行编辑该文件，修改以下的两项参数，以获取速度和误差之间的平衡：

- speed：执行速度，默认是10。值越大，每次测试中使用的数据量越小，误差越大
- loop：循环次数，默认是30。值越大，每个测试项目的循环次数越多，误差越小


## <div id="project">测试项目</div> ##

本程序分别测试了各框架如下操作的性能：

- 写入性能
- 批处理性能
- 查询性能

在每项测试执行前，为防止各框架首次运行时的初始化操作影响测试结果，因此程序会首先运行若干次测试项目，不计入测试耗时。

在执行测试时，同一测试项目可能会应用不同的设置，并多次运行。例如，在测试写入性能时，程序分别测试了Java对象和org.rex.db.Ps对象做为参数时的性能；测试查询性能时，又分别测试了查询Java对象和查询Map的性能，查询Java对象时还分别测试了启用和禁用动态字节码时的性能。

## <div id="flow">测试流程</div> ##

本程序的测试流程如下：

1. 连接数据库，创建测试需要的表（部分数据库还会创建存储过程）
2. 测试Rexdb的各接口是否可用（部分数据库会测试调用接口）
3. 测试各框架是否可用
4. 执行性能测试
5. 输出结果
