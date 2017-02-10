# SpringBoot项目脚手架
## 集成框架
0.  Java 8， ES6
1.  SpringBoot1.3.5.RELEASE
2.  spring-boot-starter-thymeleaf1.4.0.RELEASE
3.  MyBatis3.4.1
4.  MySQL
5.  Shiro1.2.5
6.  前端基于Bootstrap3的框架：Inspinia2.5， 这个出2.6了，修复了2.5在移动端的BUG等等，之后有时间再升级。
7.  Vue-2.1.8.js

## 目的
1.  抽象Controller、Service、Mapper中的公共方法，
2.  包括JS中也可以使用模板模式节约大量工作量(static/js/common.js，权限做完后这个文件要优化)
3.  实现自己的自定义标签库，之后重新开个github项目

## 使用
0.  约定
    1.  数据库表别名，第一个单词的首字母和下划线后首字母的拼接，例如：sys_role_permission 别名为 srp
    2.  如果该表需要使用JqGrid条件查询，请在POJO中为表和VO字段加上@TableAlias注解 @see com.vanxd.data.entity.user.SysPermission 
    3.  Mapper.xml中在需要用到JqGrid条件查询的地方，一定要写表别名。
1.  分页(page)接口数据筛选条件：    
    1.  可传实体属性作为参数查询。
    2.  可传filters参数，参数值符合jqGrid多条件查询的json字符串格式，具体可看Filter类
    3.  以上两个参数都存在时，只会使用filters参数。当然，这个取决于mapper.xml中的判断
2.  列表页(例，接口：/system/permission/page)
    1.  将会返回/views/system/permission/pageSysPermission.html该页面，可在子控制器重写pageView()来指定页面。
    2.  列表页中使用jqGrid以ajax请求list.json接口获得列表数据。
3.  checkbox和radio统一使用iCheck：
    1.  common.js中的bindIChecks(selector)，之后优化放到其他文件
    2.  iCheck获得事件源event.target
    3.  $.ajaxSubmit提交含有ICheck的表单时，$.ajaxSubmit是直接提交form表单，所以用的都是value值，而ICheck是不会改变checkbox的value的，所以需要定义事件我们自己去改变(@see common.js 的 bindNormalICheckEvents()方法)
    
4.  权限管理：菜单是由模块->菜单->功能 3级组成
    1.  权限增加时如果是菜单级需要填写相应的URI