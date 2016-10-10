# SpringBoot项目脚手架
## 集成框架
1.  SpringBoot1.3.5.RELEASE
2.  spring-boot-starter-thymeleaf1.4.0.RELEASE
3.  MyBatis3.4.1
4.  MySQL
5.  Shiro1.2.5
6.  前端基于Bootstrap3的框架：Inspinia2.5， 这个出2.6了，修复了2.5在移动端的BUG等等，之后有时间再升级。

## 目的
1.  抽象Controller、Service、Mapper中的公共方法，
2.  包括JS中也可以使用模板模式节约大量工作量(static/js/common.js，权限做完后这个文件要优化)
3.  实现自己的自定义标签库，之后重新开个github项目


## 使用
1.  分页(page)接口数据筛选条件：    
    1.  可传实体属性作为参数查询。
    2.  可传filters参数，参数值符合jqGrid多条件查询的json字符串格式，具体可看Filter类
    3.  以上两个参数都存在时，只会使用filters参数。当然，这个取决于mapper.xml中的判断