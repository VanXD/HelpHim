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
3.  checkbox和radio统一使用iCheck：
    1.  common.js中的bindIChecks(selector)，之后优化放到其他文件
    2.  iCheck获得事件源event.target
    3.  $.ajaxSubmit提交含有ICheck的表单时，$.ajaxSubmit是直接提交form表单，所以用的都是value值，而ICheck是不会改变checkbox的value的，所以需要定义事件我们自己去改变(@see common.js 的 bindNormalICheckEvents()方法)
    
4.  权限管理：菜单是由模块->菜单->功能 3级组成
    1.  权限增加时如果是菜单级需要填写相应的URI
    
## 框架尝试
### RequireJS
尝试重构了一下common.js，就导致我每个html文件自己的JS会变成这样：

    require(["/js/common.js","/js/jqGridFactory.js", "/js/ajaxUtils.js", "/js/plugins/artTemplate.js", "/js/iCheckFactory.js"] , function (common, jqGridFactory, ajaxUtils, template) {
实在恶心，还没有我直接使用Themeleaf的引入方便，虽然HTML上会有很多\<script\>。所以，放弃使用RequireJS进行JS模块化编程。


### artTemplate.js
我们使用该渲染框架来进行页面模板的渲染，比如：用户关联角色 的列表。但是因为Thymleaf的原因，会报各种各样的html语法错误。
所以最后是将模板写在js文件中的，类似这样：

    var roleTmpl = `
        <% for(var i = 0, j = result.length; i < j ; i++) { %>
        <tr>
            <td><%=i+1%></td>
            <td><%=result[i].name%></td>
            <td><%=result[i].description%></td>
            <td>
                <label class="i-checks" id="is-show-checks">
                    <div class="icheckbox_square-green <%= result[i].checked ? "checked" : ''%>" style="position: relative;">
                        <input <%= result[i].checked ? 'checked=true' : ''%>" onchange="relation(this, '<%=result[i].id%>', '<%=userId%>')" class="role-icheck" type="checkbox" style="position: absolute; opacity: 0;">
                        <ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins>
                    </div>
                </label>
            </td>
        </tr>
        <% } %>
    `;
    $("#roles").html(template.compile(roleTmpl)(result));
    
看着就打恶心，所以尝试了Vue.js

### Vue.js
为了解决上面那个框架出现的问题，引入了Vue.js，官方文档非常健全，很快上手。

上面的代码变成这样：


HTML部分:v-on:change可以缩写成@change，但是thymeleaf的语法检查让我异常。
    
    <tr v-for="(entity, index) of entities" >
        <td>{{ index+1 }}</td>
        <td>{{ entity.name }}</td>
        <td>{{ entity.description }}</td>
        <td>
            <label class="">
                <div :class="['icheckbox_square-green' , entity.checked ? 'checked' : '' ]" style="position: relative;">
                    <input v-on:change="relation($event, entity.id, userId)" :checked="entity.checked" class="role-icheck" type="checkbox" style="position: absolute; opacity: 0;" />
                    <ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins>
                </div>
            </label>
        </td>
    </tr>
    
JS部分：因为我这里会重复创建Vue对象，所以自己做了判断，之后看有什么好办法没有，然后改掉这里。

    if ( !PAGE.rolesVue ) {
        PAGE.rolesVue = new Vue({
            el: '#roles',
            data: {
                userId : dataId,
                entities: result.result
            },
            methods : {
                relation : (event, roleId, userId) => {
                    relation(event.target, roleId, userId);
                }
            }
        });
    }
    
瞬间比artTemplate.js舒服多了~