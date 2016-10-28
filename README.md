# SpringBoot项目脚手架
## 集成框架
1.  SpringBoot1.3.5.RELEASE
2.  spring-boot-starter-thymeleaf1.4.0.RELEASE
3.  MyBatis3.4.1
4.  MySQL
5.  Shiro1.2.5
6.  前端基于Bootstrap3的框架：Inspinia2.5， 这个出2.6了，修复了2.5在移动端的BUG等等，之后有时间再升级。

##  shiro
### 问题 subject.isPermitted(String permission) 权限验证的逻辑
    该方法判断登陆用户是否有某个权限，逻辑位于WildcardPermission.implies
1.  假设用户具有system权限，我们需要验证用户是否具有system:role:page权限
2.  shiro会将权限分开成3部分变成:用户已有权限：[system], 待验证权限：[system]:[role]:[page]
3.  在第一部分[system]shiro会判断权限验证通过，而已有权限部分长度小于待验证的，shiro就认为用户有该部分开头的所有权限,代码如下

        // If this permission has less parts than the other permission, everything after the number of parts contained
        // in this permission is automatically implied, so return true
        if (getParts().size() - 1 < i) {
            return true;
        } else {
            Set<String> part = getParts().get(i);
            if (!part.contains(WILDCARD_TOKEN) && !part.containsAll(otherPart)) {
                return false;
            }
            i++;
        }
### 结论
>所以我们在使用的时候，全选就把父菜单的权限分配进数据库，否则只能分配子菜单的权限进数据库。

## thymeleaf
### 问题 在页面中使用\<script type="text/html">\</script>
1.  thymeleaf会检查html语法，对于<%%>这种符号不允许出现！！看之后能找到地方修改thymelefa语法检查么




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
3.  checkbox和radio统一使用iCheck
    1.common.js中的bindIChecks(selector)，之后优化放到其他文件