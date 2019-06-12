1. @Database代表一个数据库，包含了所需的数据表，包含四个配置项
    - Class[] entities();
    - Class[] views() default {};
    - int version();
    - boolean exportSchema() default true;
2. @Entity代表一张table
    - String tableName() default "";表名
    - Index[] indices() default {};索引列表
    - boolean inheritSuperIndices() default false;
    - String[] primaryKeys() default {};主键
    - ForeignKey[] foreignKeys() default {};外键
    - String[] ignoredColumns() default {};需要忽略的字段
3. @Dao代表访问table的一系列方法













## Kotlin知识
1. data class 只包含数据字段的类，等同于bean,entity,POJO等Java类，编译器会自动生成get/set\hashCode()\toString()等方法
2. 
