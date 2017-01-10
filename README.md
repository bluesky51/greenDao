# greenDao
GreenDap数据库框架的增删改查的基本使用

####常见的数据库框架(注解和反射以及实体类完成)：
#####1.GreenDao;目前使用比较流行；
#####2.ORMLite:在xutils之后出现；跟系统SQLite数据库相似，代码更复杂；
#####3.ActiveAndroid:最早出现；
#####4.xutils(四大模块，viewUtils,BitmapUtils,DbUtils,HttpUtils)-dbUtils:在ActiveAndroid之后出现的

####使用GreenDao的原因:
  * 1.light:占用空间小，配置容易；
  * 2.fast:进行数据库操作速率比较快；
  * 3.map:映射：把实体类对象映射成Sqlite数据库中的内容
  * 4.进行最大的优化：性能高，消耗内存少；
  *
  * 特征：
  * 1.使用范围广，2011之后；
  * 2.超级简单：
  * 3.小：library小于150k,不占用CPU;
  * 4.最快速：通过智能的代码生成去完成数据库操作；
  * 5.安全和较多的查询配置；QueryBuilder
  * 6.强大的表链接:
  * 7.支持多种属性的类型(属性映射到数据库表中的列)
  * 8.支持数据加密技术

####1》GreenDao库的配置或者集成
 打开Project下的build.gradle,做如下操作：  
 
	  buildscript {
	      repositories {
	          mavenCentral()
	      }
	      dependencies {
	          classpath 'org.greenrobot:greendao-gradle-plugin:3.2.0'
	      }
	  }

 打开Module下的build.gradle,做如下操作：  
 
    apply plugin: 'org.greenrobot.greendao'
#####备注：以上的内容添ply plugin: 'com.android.application'之前
  
    compile 'org.greenrobot:greendao:3.2.0'//放在Module下的build.gradle的dependencies里面
#####备注：一步完成进行测试；

####2》实体类：
 给映射要数据库中表的实体类添加注解@Entity  :给class类添加  
 每个实体类必须要作为数据库中表的主键，注解@Id(autoincrement = true), id类型必须为long,autoincrement无效;  

####3》实体类操作；
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;1>情况1操作方式：Build-Make Project(给实体类自动生成有参构造方法以及set和get,给有参构造方法自动添加@Generated)  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;2>情况2操作方式：用户自己生成生成有参构造方法以及set和get，给有参构造方法添加@Keep注解再编译运行
####4》编译运行生成的文件去查找：

    Module模版名称－build-generated-source-greendao(目录下有3个文件)

####备注：Dao:全程Data Acess Object:数据访问对象；

	 DaoMaster:数据访问对象的入口的关键的类,目的是得到DaoSeesion
	 DaoSession: 数据访问对象的会话层；交互：最终目的是得到实体类的Dao  (增删查改)
	 ProductDao(bean类的名字Dao): 生成实体类对应的表



####项目具体示例：
#####数据操作前的准备工作：
 //参数2:数据库的名称  
1》根据DaoMaster得到DaoSession对象，例如： DaoSession daoSession = DaoMaster.newDevSession(ctx, "1019");  
//获取实体类对应的表  
1》根据DaoSession得到实体类的访问对象Dao，例如：  
StudentDao studentDao = daoSession.getStudentDao();  
productDao = daoSession.getProductDao();  
#####1.插入说明如下：  
//可以插入多次，但是相同的数据只是替换新值；没有的数据也就是不同数据插入  
批量插入： TDao.insertOrReplaceInTx(List<T> list);  
单条插入：TDao.insertOrReplace(T t); T代表实体类对象的名称  
2.排序查询数据说明如下：  
先QueryBuilder<Product> builder = productDao.queryBuilder();  
再使用QueryBuilder进行数据排序查询条件关键如：  
builder.orderDesc("列名");递减排序  
builder.orderAsc("列名");递增排序  
3。分页查询数据说明如下：  
先QueryBuilder<Product> builder = productDao.queryBuilder();  
再使用QueryBuilder进行分页数据查询条件关键如右：//offset(m).limit(n)从第m个数开始查询，查询n条数据  
4.模糊查询搜索模块如下说明：  
先QueryBuilder<Product> builder = productDao.queryBuilder();  
再使用QUeryBuilder进行模糊查询条件关键如右：  where(ProductDao.Properties.Name.like("%" + key + "%"))  
5.

6.


备注：SearchView的基本使用：  
  1.xml布局介绍：  
  基本属性介绍：  
      
      <!--app:iconifiedByDefault设置搜索框是否展开，默认true,true :折叠未展开效果(只有一个图标，需要点击图标才可展开搜索框)false:展开效果，图标在搜索框的外面-->  
      <!--app:closeIcon修改删除图标按钮(x)-->
      <!--app:defaultQueryHint设置搜索框需要输入的内容提示信息-->
      <!--app:queryBackground设置搜索框的背景颜色-->
      <!--app:searchIcon设置搜索框的搜索图标(放大镜)-->
  2.java代码监听：setOnQueryTextListener：重点在 该onQueryTextChange(String newText)方法中操作


