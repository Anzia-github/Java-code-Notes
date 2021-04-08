-- 查询全部的学生  SELECT 字段 FROM 表
SELECT * FROM student

-- 查询指定字段
SELECT `StudentNo`,`StudentName` FROM student

-- 别名，给结果起一个名字 AS  可以给字段起别名，也可以给表起别名
SELECT `StudentNo` AS 学号, `StudentName` AS 学生姓名 FROM student AS s

-- 函数 Concat(a,b)
SELECT CONCAT('姓名：',StudentName) AS 新名字 FROM student


-- 查询一下有那些同学参加了考试，成绩
SELECT * FROM result -- 查询全部的考试成绩
-- 查询有那些同学参加了考试
SELECT `StudentNo` FROM result
-- 发现了重复数据，去重
SELECT DISTINCT `StudentNo` FROM result


SELECT VERSION() -- 查询系统版本（函数）
SELECT 100*3-1 AS 计算结果 -- 用来计算（表达式）
SELECT @@auto_increment_increment -- 查询自增的步长（变量）

-- 学员考试成绩+1分 查看
SELECT `StudentNo`,`StudentResult`+1 AS 提分后 FROM result



-- ================where==================
SELECT `studentno`,`studentresult` FROM result
WHERE studentresult >= 90 AND studentresult < 100

-- 模糊查询(区间)
SELECT studentno, studentresult FROM result
WHERE studentresult BETWEEN 80 AND 100

-- not !
SELECT studentno, studentresult FROM result
WHERE NOT studentno = 1000

-- ================模糊查询==============
-- =====like======
-- 查询姓张的同学
-- like结合 %(代表0到任意个字符) _(一个字符)
SELECT studentno,studentname FROM student
WHERE studentname LIKE '张%'
-- 查询名字后面只有一个字的
SELECT studentno,studentname FROM student
WHERE studentname LIKE '张__'
-- 查询名字中间有伟字的同学
SELECT studentno,studentname FROM student
WHERE studentname LIKE '%伟%'

-- ======in（具体的一个或多个值）=======
-- 查询1001，1002，1003号学员
SELECT studentno,studentname FROM student
WHERE studentno IN (1001,1002,1003)

-- 查询在北京的学生
SELECT studentno,studentname FROM student
WHERE address IN ('北京')


-- === null  not null ''=====
-- 查询地址为空的学生 null
SELECT studentno,studentname FROM student
WHERE address='' OR address IS NULL

-- 查询有出生日期的同学 不为空
SELECT studentno,studentname FROM student
WHERE borndate IS NOT NULL






-- =========联表查询 join ==========
-- 查询参加了考试的同学（学号，姓名，科目编号，分数）
SELECT * FROM student
SELECT * FROM result

/*
1、分析需求，分析查询的字段来自哪些表（连接查询）
2、确定使用哪种连接查询（7种）
确定交叉点（这两个表中哪些数据是相同的）
判断的条件：学生表中的studentNO = 成绩表的studentNO
*/

-- join (连接的表) on (判断的的条件) 连接查询
-- where 等值查询

SELECT s.studentNO,studentName,SubjectNo,studentresult
FROM student AS s
INNER JOIN result AS r
WHERE s.studentno=r.studentno

-- right join
SELECT s.studentNO,studentName,SubjectNo,studentresult
FROM student s
RIGHT JOIN result r
ON s.studentno=r.studentno

-- left join
SELECT s.studentNO,studentName,SubjectNo,studentresult
FROM student s
LEFT JOIN result r
ON s.studentno=r.studentno


-- 查询缺考的同学
SELECT s.studentNO,studentName,SubjectNo,studentresult
FROM student s
LEFT JOIN result r
ON s.studentno=r.studentno
WHERE studentresult IS NULL

-- 思考题（查询了参加考试的同学信息：学号，学生姓名，科目名，分数）
/*
1、分析需求，分析查询的字段来自哪些表，student、result、subject（连接查询）
2、确定使用哪种连接查询（7种）
确定交叉点（这两个表中哪些数据是相同的）
判断的条件：学生表中的studentNO = 成绩表的studentNO  成绩表的subjectNo = 科目表的subjectNo
*/
SELECT s.studentno,studentname,subjectname,studentresult
FROM student s
RIGHT JOIN result r
ON s.studentno = r.studentno
INNER JOIN `subject` sub
ON r.subjectno = sub.subjectno









-- =========自连接=========
CREATE TABLE category (
	categoryid INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主题ID',
	pid INT(10) NOT NULL COMMENT '父ID',
	categoryName VARCHAR(50) NOT NULL COMMENT '主题名字',
	PRIMARY KEY(categoryid)
) ENGINE=INNODB AUTO_INCREMENT=9 DEFAULT CHARSET = utf8;

INSERT INTO category(categoryid,pid,categoryName)
VALUES('2','1','信息技术'),('3','1','软件开发'),('4','3','数据库'),
('5','1','美术设计'),('6','3','web开发'),('7','5','PS技术'),('8','2','办公信息');


-- 查询父子信息：把一张表看成两个一模一样的表
SELECT a.categoryname AS '父栏目',b.categoryname AS '子栏目'
FROM category AS a,category AS b
WHERE a.categoryid=b.pid


-- 查询科目所属的年级（科目名称，年级名称）
SELECT subjectname,gradename
FROM `subject` s
INNER JOIN grade g
ON s.gradeid=g.gradeid


-- 思考题（查询了参加数据库结构考试-1考试的同学信息：学号，学生姓名，科目名，分数）
SELECT s.studentno,studentname,subjectname,studentresult
FROM student s 
RIGHT JOIN result r
ON s.studentno = r.studentno
INNER JOIN `subject` sub
ON r.subjectno = sub.subjectno
WHERE subjectname='数据库结构考试-1'




SELECT [ALL | DISTINCT]
{* | table.* | [table.fileld[AS alias1][,table.field2[AS alias2]][,...]}
FROM table_name [AS table_alias]
    [LEFT | RIGHT | INNER | JOIN table_name2] -- 联合查询
    [WHERE ...] -- 指定结果需满足的条件
    [GROUP BY ...] -- 指定结果按照哪几个字段来分组
    [HAVING] -- 过滤分组的记录必须满足的次要条件
    [ORDER BY ...] -- 指定查询记录按一个或多个条件排序
    [LIMIT {[OFFSET,]ROW_COUNT | row_countOFFSET OFFSET}];
    -- 指定查询的记录从哪条至哪条

-- =============分页limit和排序order by===============
-- 排序：升序ASC，降序DECS
-- 语法：ORDER BY 字段名 ASC/DECS
SELECT s.studentno,studentname,subjectname,studentresult
FROM student s 
RIGHT JOIN result r
ON s.studentno = r.studentno
INNER JOIN `subject` sub
ON r.subjectno = sub.subjectno
WHERE subjectname != '数据库结构考试-1'
ORDER BY studentresult ASC

-- 为什么要分页
-- 缓解数据库压力，给人体验更好
-- 分页，每页只显示2条数据、
-- 语法：LIMIT 起始值,页面大小
-- LIMIT 0,3
SELECT s.studentno,studentname,subjectname,studentresult
FROM student s 
RIGHT JOIN result r
ON s.studentno = r.studentno
INNER JOIN `subject` sub
ON r.subjectno = sub.subjectno
WHERE subjectname != '数据库结构考试-1'
ORDER BY studentresult ASC
LIMIT 0,3

-- 第一页 limit 0,3
-- 第二页 limit 3,3
-- 第三页 limit 6,3
-- 第n页 limit 3*(n-1),3

-- 查询Java第一学年 课程成绩排名前10的学生，并且分数要大于80的学生信息（学号，姓名，课程，成绩）
SELECT s.studentno,studentname,subjectname,studentresult
FROM student s
INNER JOIN result r
ON s.studentno = r.studentno
INNER JOIN `subject` sub
ON r.subjectno = sub.subjectno
WHERE subjectname = "Java程序设计-1" AND r.studentresult > 80
ORDER BY r.studentresult DESC
LIMIT 0,10


-- =========where=========
-- 1、查询 C语言-1 的所有考试结果（学号，科目编号，成绩），降序排列
-- 方式一：使用连接查询
SELECT studentno,r.subjectno,studentresult
FROM result r
INNER JOIN `subject` sub
ON r.subjectno = sub.subjectno
WHERE `subjectname` = 'C语言-1'
ORDER BY studentresult DESC

-- 方式二：使用子查询(由里及外)
SELECT studentno,subjectno,studentresult
FROM result
WHERE subjectno=(
    SELECT subjectno
    FROM `subject`
    WHERE subjectname='C语言-1'
)
ORDER BY studentresult DESC

-- 分数不小于80分的学生的学号和姓名
-- 连接查询
SELECT DISTINCT s.studentno,studentname
FROM student s
INNER JOIN result r
ON s.studentno = r.studentno
WHERE studentresult >= 80

-- 用子查询的方式在此基础上增加一个科目，高等数学-2
SELECT DISTINCT s.studentno,studentname
FROM student s
INNER JOIN result r
ON s.studentno = r.studentno
WHERE studentresult >= 80 AND subjectno = (
    SELECT subjectno
    FROM `subject`
    WHERE subjectname = '高等数学-2'
)

-- 子查询
-- 这里子查询结果中有两个重复的结果会影响父查询，所以必须去重distinct
SELECT studentno,studentname
FROM student 
WHERE studentno=(
    SELECT DISTINCT studentno
    FROM result 
    WHERE studentresult >= 80
)

-- 练习：查询 C语言-1 前5名同学的成绩的信息（学号，姓名，分数）
-- 子表查询写法
SELECT s.studentno,studentname,studentresult
FROM student s
INNER JOIN result r
ON s.studentno = r.studentno
WHERE subjectno = (
    SELECT DISTINCT subjectno
    FROM `subject`
    WHERE subjectname = 'C语言-1'
)
ORDER BY studentresult DESC
LIMIT 0,5

-- 连接查询写法
SELECT s.studentno,studentname,studentresult
FROM student s
INNER JOIN result r
ON s.studentno = r.studentno
INNER JOIN `subject` sub
ON r.subjectno = sub.subjectno
WHERE subjectname = 'C语言-1'
ORDER BY studentresult DESC
LIMIT 0,5


-- =========常用函数==========
-- 数学运算
SELECT ABS(-8) -- 绝对值
SELECT CEILING(9.4) -- 向上取整
SELECT FLOOR(9.4) -- 向下取整
SELECT RAND() -- 返回一个0~1之间的随机数
SELECT SIGN(0) -- 判断一个数的符号 0-0 负数返回-1，整数返回1

-- 字符串函数
SELECT CHAR_LENGTH('即使再小的帆也能远航') -- 字符串长度
SELECT CONCAT('我','爱','Java') -- 拼接字符串
SELECT INSERT('我爱编程helloworld',1,2,'超级热爱') -- 插入，从某个位置开始替换某个长度
SELECT LOWER('KUANGSHEN') -- 转小写
SELECT UPPER('kuangshen') -- 转大写
SELECT INSTR('kuangshen','h') -- 返回第一次出现的子串的索引
SELECT REPLACE('狂神说坚持就能成功','坚持','努力') -- 替换出现的指定字符串
SELECT SUBSTR('狂神说坚持就能成功',4,6) -- 返回指定的子字符串（源字符串，截取的位置，截取的长度）
SELECT REVERSE('狂神说坚持就能成功') -- 反转

-- 查询姓 张 的同学，名字 章 
SELECT REPLACE(studentname,'张','章')
FROM student
WHERE studentname LIKE '张%'

-- 时间和日期函数（记住）
SELECT CURRENT_DATE() -- 获取当前日期
SELECT CURDATE() -- 获取当前日期
SELECT NOW() -- 获取当前时间
SELECT LOCALTIME() -- 本地时间
SELECT SYSDATE() -- 系统时间

SELECT YEAR(NOW())
SELECT MONTH(NOW())
SELECT DAY(NOW())
SELECT HOUR(NOW())
SELECT MINUTE(NOW())
SELECT SECOND(NOW())

-- 系统
SELECT SYSTEM_USER()
SELECT USER()
SELECT VERSION()



-- ========聚合函数==============
SELECT COUNT(borndate) FROM student -- count(字段)，会忽略所有的null值
SELECT COUNT(*) FROM student -- 不会忽略null值
SELECT COUNT(1) FROM student -- 忽略所有的null值

SELECT SUM(studentresult) AS 总和 FROM result
SELECT AVG(studentresult) AS 平均分 FROM result
SELECT MAX(studentresult) AS 最高分 FROM result
SELECT MIN(studentresult) AS 最低分 FROM result
 
-- 查询不同课程的平均分，最高分，最低分
SELECT subjectname AS 课程名,AVG(studentresult) AS 平均分,MAX(studentresult) AS 最高分,MIN(studentresult) AS 最低分
FROM result r
INNER JOIN `subject` sub
ON r.subjectno = sub.subjectno 
GROUP BY r.subjectno -- 通过声明字段来分组
HAVING 平均分 >= 80


-- ======测试MD5 加密======
CREATE TABLE `testmd5` (
    `id` INT(4) NOT NULL,
    `name` VARCHAR(20) NOT NULL,
    `pwd` VARCHAR(50) NOT NULL,
    PRIMARY KEY(`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8

-- 铭文密码
INSERT INTO testmd5 VALUES(1,'张三','123456'),(2,'李四','123456'),(3,'王五','123456')

-- 加密
UPDATE testmd5 SET pwd=MD5(pwd)

-- 插入的时候加密
INSERT INTO testmd5 VALUES(4,'小明',MD5('123456'))


-- ===========事务===========
-- mysql 是默认开启事务自动提交的
SET autocommit = 0 -- 关闭
SET autocommit = 1 -- 开启

-- ===============================

-- 手动处理事务
SET autocommit = 0 -- 关闭自动提交

-- 事务开启
START TRANSACTION -- 标记一个事务的开始，从这个之后的sql都在同一个事务内

INSERT xx
INSERT xx

-- 提交：持久化（成功！）
COMMIT
-- 回滚：回到的原来的样子（失败！）
ROLLBACK

-- 事务结束
SET COMMIT = 1 -- 开启自动提交
-- ===============================



-- 了解
SAVEPOINT 保存点名 -- 设置一个事务的保存点
ROLLBACK TO SAVEPOINT 保存点名 -- 回到保存点
RELEASE SAVEPOINT 保存点名 -- 撤销保存点名


-- 转帐
CREATE DATABASE shop CHARACTER SET utf8 COLLATE utf8_general_ci
USE shop

CREATE TABLE `account` (
    `id` INT(3) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    `monye` DECIMAL(9,2) NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO ACCOUNT(`name`,`monye`)
VALUES ('A',2000.00),('B',10000.00)

-- 模拟转账
SET autocommit = 0 -- 关闭自动提交
START TRANSACTION -- 开启一个事务 (一组事务)

UPDATE ACCOUNT SET money=money-500 WHERE `name`='A' -- A减500
UPDATE ACCOUNT SET money=money+500 WHERE `name`='B' -- B加500

COMMIT -- 提交事务
ROLLBACK -- 回归

SET autocommit=1 -- 恢复默认值

-- alter table `account` change monye money decimal(9,2)




-- ==============索引的使用============
-- 1、在创建表的时候给字段增加索引
-- 2、创建完毕后，增加索引

-- 显示所有的索引信息
USE school
SHOW INDEX FROM student

-- 增加一个全文索引 注意：school.student
ALTER TABLE school.student ADD FULLTEXT INDEX `studentname`(`studentname`)

-- EXPLAIN 分析sql执行的状况
EXPLAIN SELECT * FROM student -- 非全文索引

SELECT * FROM student WHERE MATCH(studentname) AGAINST('张')


CREATE TABLE `app_user` (
`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
`name` VARCHAR(50) DEFAULT'' COMMENT'用户昵称',
`email` VARCHAR(50) NOT NULL COMMENT'用户邮箱',
`phone` VARCHAR(20) DEFAULT'' COMMENT'手机号',
`gender` TINYINT(4) UNSIGNED DEFAULT '0'COMMENT '性别（0：男;1:女）',
`password` VARCHAR(100) NOT NULL COMMENT '密码',
`age` TINYINT(4) DEFAULT'0'  COMMENT '年龄',
`create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT = 'app用户表'

-- 插入100万数据
DELIMITER $$ -- 写函数之前必须要写，标志
CREATE FUNCTION mock_data()
RETURNS INT
BEGIN
    DECLARE num INT DEFAULT 1000000;
    DECLARE i INT DEFAULT 0;    
    
    WHILE i < num DO
        -- 插入语句
        INSERT INTO app_user(`name`,`email`,`phone`,`gender`,`password`,`age`) VALUES(CONCAT('用户',i),'123456@qq.com',CONCAT('18',FLOOR(RAND()*((999999999-100000000)+100000000))),FLOOR(RAND()*2),UUID(),FLOOR(RAND()*100));
        SET i = i + 1;
    END WHILE;
    RETURN i;
END;

SELECT mock_data();

SELECT * FROM app_user WHERE `name` = '用户9999'

-- 建立索引之后唯一定位了，不需要去遍历了
EXPLAIN SELECT * FROM app_user WHERE `name` = '用户9999'

-- id_表名_字段名
-- CREATE INDEX 索引名 ON 表(字段)
CREATE INDEX id_app_user_name ON app_user(`name`)


-- truncate table app_user







-- 创建用户 CREATE USER 用户 IDENTIFIED BY '密码'
CREATE USER kuangshen IDENTIFIED BY '123456'



































