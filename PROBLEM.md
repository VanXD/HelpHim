# 各式各样的坑

##Spring
###@Configuration类中不能获得Properties中的值
增加了3个Profile(dev, test, pro)，目的是为了根据不同的环境使用不同的配置文件。所以我们对数据源(MyBatisConfig.getDataSource())的配置
需要从Profile中读取变量。此时出现了问题：
1.  无论是用@Value 或是 @EnableConfigurationProperties+@ConfigurationProperties我都不能在@Configuration类下获得yml中的值。
2.  但是在@Controller类下是可以的，所以profile配置没问题，猜测是生命周期问题。
在Github找到别人代码，在@Configuration类中实现了该接口。
        
>        implements EnvironmentAware 
之后就可以获得Environment对象，该对象可以获得所有的Properties文件，也就能获得相应的值。
到此问题解决。       

## thymeleaf
### 在页面中使用\<script type="text/html">\</script>
thymeleaf会检查html语法，对于<%%>这种符号不允许出现！！看之后能找到地方修改thymelefa语法检查么

##  shiro
### subject.isPermitted(String permission) 权限验证的逻辑
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
>所以我们在使用的时候，全选就把父菜单的权限分配进数据库，否则只能分配子菜单的权限进数据库。



## RequireJS
尝试重构了一下common.js，就导致我每个html文件自己的JS会变成这样：

    require(["/js/common.js","/js/jqGridFactory.js", "/js/ajaxUtils.js", "/js/plugins/artTemplate.js", "/js/iCheckFactory.js"] , function (common, jqGridFactory, ajaxUtils, template) {
实在恶心，还没有我直接使用Themeleaf的引入方便，虽然HTML上会有很多\<script\>。所以，放弃使用RequireJS进行JS模块化编程。


## artTemplate.js
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

## Vue.js
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