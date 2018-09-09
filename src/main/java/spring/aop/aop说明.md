2.1 @Aspect

作用是把当前类标识为一个切面供容器读取

2.2 @Before
标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有

2.3 @AfterReturning

后置增强，相当于AfterReturningAdvice，方法正常退出时执行

2.4 @AfterThrowing

异常抛出增强，相当于ThrowsAdvice

2.5 @After

final增强，不管是抛出异常或者正常退出都会执行

2.6 @Around

环绕增强，相当于MethodInterceptor

2.7 @DeclareParents

引介增强，相当于IntroductionInterceptor
3 execution切点函数

 

execution函数用于匹配方法执行的连接点，语法为：

execution(方法修饰符(可选)  返回类型  方法名  参数  异常模式(可选)) 

 

参数部分允许使用通配符：

*  匹配任意字符，但只能匹配一个元素

.. 匹配任意字符，可以匹配任意多个元素，表示类时，必须和*联合使用

+  必须跟在类名后面，如Horseman+，表示类本身和继承或扩展指定类的所有类

 

示例中的* chop(..)解读为：

方法修饰符  无

返回类型      *匹配任意数量字符，表示返回类型不限

方法名          chop表示匹配名称为chop的方法

参数               (..)表示匹配任意数量和类型的输入参数

异常模式       不限

 

更多示例：

void chop(String,int)

匹配目标类任意修饰符方法、返回void、方法名chop、带有一个String和一个int型参数的方法

public void chop(*)

匹配目标类public修饰、返回void、方法名chop、带有一个任意类型参数的方法

public String *o*(..)

 匹配目标类public修饰、返回String类型、方法名中带有一个o字符、带有任意数量任意类型参数的方法

public void *o*(String,..)

 匹配目标类public修饰、返回void、方法名中带有一个o字符、带有任意数量任意类型参数，但第一个参数必须有且为String型的方法

也可以指定类：

public void examples.chap03.Horseman.*(..)

匹配Horseman的public修饰、返回void、不限方法名、带有任意数量任意类型参数的方法

public void examples.chap03.*man.*(..)

匹配以man结尾的类中public修饰、返回void、不限方法名、带有任意数量任意类型参数的方法

指定包：

public void examples.chap03.*.chop(..)

匹配examples.chap03包下所有类中public修饰、返回void、方法名chop、带有任意数量任意类型参数的方法

public void examples..*.chop(..)

匹配examples.包下和所有子包中的类中public修饰、返回void、方法名chop、带有任意数量任意类型参数的方法
可以用这些表达式替换StorageAdvisor中的代码并观察效果