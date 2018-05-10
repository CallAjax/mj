

# 前后分离权限实现

### 使用技术栈介绍

###### 前端

react：负责视觉呈现。<br>
redux：状态以及数据管理。<br>
router：页面路由。<br>
saga：抽离业务逻辑，异步处理任务。<br>
axios：ajax通信。<br>
immutable：数据不可变特点，适合store中的复杂对象更新。<br>
localforage：本地存储的封装，特点是异步处理以及支持各种数据格式。<br>


###### 后端

springboot、security、redis、mybatis、jjwt。。。<br>


### 主要实现效果

##### 后端
1、验证码转base64提供给前端，验证码入redis同步，给前端的数据包括验证码加上redis的key，前端提交验证码需要附带上key，后端去redis中根据key取值。<br>
2、后端为登陆的用户提供token，写入报文头，报文体返回前端展示用的路由信息和菜单信息。<br>
3、后端对每次请求先进行token验证拦截，通过后再是security权限拦截，具体权限保存在token中，后端解析后校验。<br>
4、后端开辟一条通道，为第三方提供接口无需token验证。<br>
5、根据token过期时间，设置一个提前更新契机，本demo为1小时，假如token过期前1小时内有请求，
后端在返回业务报文同时由aop负责在返回对象中加入新的token以及权限菜单，在更新token同时会先校验客户状态，
校验失败则返回状态字段，通知前端强制用户下线，基本整体效果属于模拟session延期以及状态更新<br>

整体效果：<br>
通过jwt拦截和security权限判断来保护应用资源，所谓开辟一条通道给第三方也只是在token验证那加了一个判断直接放行，
同时url不需要security保护，security对于不在自己需保护资源中的url会直接放行。<br>
后端专门提供一个接口给前端验证token以及更新，该接口url同样不在security资源保护列表中，但需要token认证，
该接口作为所有登陆用户公共接口开放。<br>
所有保护资源全部入库，用户、角色、权限、资源、以及关联表，总计7张表。<br>
所有异常入库，第一次读取后入redis，支持错误信息中使用占位符动态返回错误信息，所有错误直接抛出无需捕获。<br>
登陆做了特色业务处理，一个用户不管是否存在，登陆失败3次，当天不允许登陆。登陆判断当天上一次登陆是否失败，
如果失败就需要验证码，如果没失败记录，有密码就ok。每天晚上凌晨自动清理一天的登录记录。<br>

需要关注的实现：<br>
CaptchaController:验证码实现。<br>
AuthenticationProviderImpl：登陆信息验证。<br>
JwtAuthenticationFilter：token验证拦截器，在security判断之前拦截。<br>
AjaxAuthenticationSuccessHandler：登陆成功的在此生成token以及权限信息。<br>
FilterInvocationSecurityMetadataSourceImpl<br>
UrlFilterSecurityInterceptor<br>
AccessDecisionManagerImpl: 自定义的保护资源实现，基于数据库表结构，所有资源入redis。<br>
TokenAop:切点设置为指定一个包下的所有controller，所有服务入口都在此包内实现，拦截所有正常返回的对象，
根据是否更新token在返回对象中加入新token。<br>
AllException:统一的异常处理，同时在返回信息做更新tokne判断，加上aop一起实现所有请求可以在返回时判断是否更新token。<br>
ResInfo：所有服务统一返回该对象，spring自动转json格式。
SysTask:一个执行任务的契机，如果有需要随系统启动执行的任务，也可以放在这里，比如启动netty服务之类的。目前设置了一个批次，每天清理数据。<br>
generatorConfig.xml:根据表结构自动生成对应dao实现，具体使用自行百度。(打jar包前需要注释pom.xml中配置信息，否则有问题)<br>


关于redis配置：<br>
springboot2.0.1版本直接默认spring缓存注解使用redis作为缓存，无需实现cacheManager，如果要实现注解控制过期时间，
请自行实现cacheManager。(不建议，不如自己实现一套工具方法)<br>
application-redis.yml，单机配置，本人开发环境使用。<br>
application-redis-ms.yml，主从模式，1主1从，3哨兵，具体怎么玩自己百度。<br>
application-redis-cluster.yml，集群模式，3主3从，自行百度，以上都是自己没事搭着玩的。<br>

表结构以及数据，都在data.zip中，用户密码存的密文，密码是1，建表sql感觉导出有点问题，用户名要自己设置区分大小写,自己加个binary吧，
太懒，懒得自己加了,或者删掉大写用户名那条记录，只留一条测试也能凑合。<br>


本来想写前端的，代码就那点，哎，自己看吧。。。


最后补充下，前端是8083端口，后端8084，我自己开了个nginx做代理，后端所有服务基于/service这个url开头，nginx怎么配代理，自己看着办了。










