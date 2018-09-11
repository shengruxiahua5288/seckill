cache--存放缓存，比如数据库在内存中的缓存
dao---数据接口层，可以是访问mysql的数据层，也可以是访问redis的数据层
dto---数据暴露层
entity--对应数据库的bean层
service--组合dao层目录中的方法逻辑执行顺序
    impl----这个是Service接口中类的实现
exception---实现接口抛异常层
enums---是枚举常量包，主要是枚举一些接口返回的数据字典，统一封装返回的状态，返回的状态信息message
resources--主要是存放数据库连接的配置文件，连接数据库的查询语句，spring 和mapper的配合一起连接数据库
sql--存放数据库的配置信息


