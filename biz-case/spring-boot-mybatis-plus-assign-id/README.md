# mybatis plus 使用过程中的问题记录

## 使用ASSIGN_ID出现主键重复
```
com.baomidou.mybatisplus.annotation.IdType#ASSIGN_ID

2023-02-28 17:28:12.271 ERROR [http-nio-8080-exec-1] [47dcc778de33457eb390498d2a
152173] [UIDXXXXXX] [001XXXXXX] [com.xxx.xxx.config.GlobalExceptionHandler] 
### Error updating database.  Cause: org.postgresql.util.PSQLException: ERROR: d
uplicate key value violates unique constraint "sys_audit_pkey"
  Detail: Key (id)=(1630500102261559297) already exists.
### The error may exist in com/xxx/xxx/sys/audit/mapper/AuditMapper.java (best
 guess)
### The error may involve com.xxx.xxx.sys.audit.mapper.AuditMapper.insert-Inli
ne
### The error occurred while setting parameters
### SQL: INSERT INTO sys_audit  ( id, type, name, content,  deleted, create_time
, create_user )  VALUES  ( ?, ?, ?, ?,  ?, ?, ? )
### Cause: org.postgresql.util.PSQLException: ERROR: duplicate key value violate
s unique constraint "sys_audit_pkey"
  Detail: Key (id)=(1630500102261559297) already exists.
; ERROR: duplicate key value violates unique constraint "sys_audit_pkey"
  Detail: Key (id)=(1630500102261559297) already exists.; nested exception is or
g.postgresql.util.PSQLException: ERROR: duplicate key value violates unique cons
traint "sys_audit_pkey"
  Detail: Key (id)=(1630500102261559297) already exists.
org.springframework.dao.DuplicateKeyException: 
### Error updating database.  Cause: org.postgresql.util.PSQLException: ERROR: d
uplicate key value violates unique constraint "sys_audit_pkey"
  Detail: Key (id)=(1630500102261559297) already exists.
### The error may exist in com/xxx/xxx/sys/audit/mapper/AuditMapper.java (best
 guess)
### The error may involve com.xxx.xxx.sys.audit.mapper.AuditMapper.insert-Inli
ne
### The error occurred while setting parameters
### SQL: INSERT INTO sys_audit  ( id, type, name, content,  deleted, create_time
, create_user )  VALUES  ( ?, ?, ?, ?,  ?, ?, ? )
### Cause: org.postgresql.util.PSQLException: ERROR: duplicate key value violate
s unique constraint "sys_audit_pkey"
  Detail: Key (id)=(1630500102261559297) already exists.
; ERROR: duplicate key value violates unique constraint "sys_audit_pkey"
  Detail: Key (id)=(1630500102261559297) already exists.; nested exception is or
g.postgresql.util.PSQLException: ERROR: duplicate key value violates unique cons
traint "sys_audit_pkey"
  Detail: Key (id)=(1630500102261559297) already exists.
        at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.d
oTranslate(SQLErrorCodeSQLExceptionTranslator.java:247)
        at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslat
or.translate(AbstractFallbackSQLExceptionTranslator.java:70)
        at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPos
sible(MyBatisExceptionTranslator.java:91)
        at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(Sq
lSessionTemplate.java:441)
        at com.sun.proxy.$Proxy156.insert(Unknown Source)
        at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:
272)
        at com.baomidou.mybatisplus.core.override.MybatisMapperMethod.execute(My
batisMapperMethod.java:59)
        at com.baomidou.mybatisplus.core.override.MybatisMapperProxy$PlainMethod
Invoker.invoke(MybatisMapperProxy.java:148)
        at com.baomidou.mybatisplus.core.override.MybatisMapperProxy.invoke(Myba
tisMapperProxy.java:89)
        at com.sun.proxy.$Proxy171.insert(Unknown Source)
        at cn.bba.boot.starter.mybatisplus.base.BaseService.saveEntity(BaseServi
ce.java:29)
        at com.xxx.xxx.sys.audit.service.impl.AuditServiceImpl.save(AuditServi
ceImpl.java:42)
        at com.xxx.xxx.sys.audit.service.impl.AuditServiceImpl$$FastClassBySpr
ingCGLIB$$5405f302.invoke(<generated>)
        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:2
18)
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation
.invokeJoinpoint(CglibAopProxy.java:783)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(
ReflectiveMethodInvocation.java:163)
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation
.proceed(CglibAopProxy.java:753)
        at org.springframework.transaction.interceptor.TransactionInterceptor$1.
proceedWithInvocation(TransactionInterceptor.java:123)
        at org.springframework.transaction.interceptor.TransactionAspectSupport.
invokeWithinTransaction(TransactionAspectSupport.java:388)
        at org.springframework.transaction.interceptor.TransactionInterceptor.in
voke(TransactionInterceptor.java:119)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(
ReflectiveMethodInvocation.java:186)
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation
.proceed(CglibAopProxy.java:753)
        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterce
ptor.intercept(CglibAopProxy.java:698)
        at com.xxx.xxx.sys.audit.service.impl.AuditServiceImpl$$EnhancerBySpri
ngCGLIB$$5c5d2067.save(<generated>)
        at com.xxx.xxx.sys.audit.AuditUtils.addAuditLog(AuditUtils.java:11)
        at com.xxx.xxx.sys.audit.AuditUtils.addAuditLog(AuditUtils.java:16)
        at com.xxx.xxx.time.common.config.log.RunTimeLogUtil.audit(RunTimeLogU
til.java:125)
        at com.xxx.xxx.time.common.config.log.RunTimeLogAspect.around(RunTimeL
ogAspect.java:79)
        at sun.reflect.GeneratedMethodAccessor150.invoke(Unknown Source)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAcces
sorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMet
hodWithGivenArgs(AbstractAspectJAdvice.java:634)
        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMet
hod(AbstractAspectJAdvice.java:624)
        at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAro
undAdvice.java:72)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(
ReflectiveMethodInvocation.java:186)
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation
.proceed(CglibAopProxy.java:753)
        at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invok
e(ExposeInvocationInterceptor.java:97)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(
ReflectiveMethodInvocation.java:186)
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation
.proceed(CglibAopProxy.java:753)
        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterce
ptor.intercept(CglibAopProxy.java:698)
        at com.xxx.xxx.time.attendance.controller.AttendanceController$$Enhanc
erBySpringCGLIB$$b3496a26.attendance(<generated>)
        at sun.reflect.GeneratedMethodAccessor304.invoke(Unknown Source)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAcces
sorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.springframework.web.method.support.InvocableHandlerMethod.doInvok
e(InvocableHandlerMethod.java:205)
        at org.springframework.web.method.support.InvocableHandlerMethod.invokeF
orRequest(InvocableHandlerMethod.java:150)
        at org.springframework.web.servlet.mvc.method.annotation.ServletInvocabl
eHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)
        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingH
andlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)
        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingH
andlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)
        at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapt
er.handle(AbstractHandlerMethodAdapter.java:87)
        at org.springframework.web.servlet.DispatcherServlet.doDispatch(Dispatch
erServlet.java:1067)
        at org.springframework.web.servlet.DispatcherServlet.doService(Dispatche
rServlet.java:963)
        at org.springframework.web.servlet.FrameworkServlet.processRequest(Frame
workServlet.java:1006)
        at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServ
let.java:909)
        at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
        at org.springframework.web.servlet.FrameworkServlet.service(FrameworkSer
vlet.java:883)
        at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(Appl
icationFilterChain.java:227)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationF
ilterChain.java:162)
        at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53
)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(Appl
icationFilterChain.java:189)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationF
ilterChain.java:162)
        at com.xxx.xxx.time.grayboxtest.config.GrayBoxTestFilter.doFilterInter
nal(GrayBoxTestFilter.java:71)
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerR
equestFilter.java:117)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(Appl
icationFilterChain.java:189)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationF
ilterChain.java:162)
        at org.springframework.security.web.FilterChainProxy.doFilterInternal(Fi
lterChainProxy.java:204)
        at org.springframework.security.web.FilterChainProxy.doFilter(FilterChai
nProxy.java:183)
        at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(D
elegatingFilterProxy.java:354)
        at org.springframework.web.filter.DelegatingFilterProxy.doFilter(Delegat
ingFilterProxy.java:267)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(Appl
icationFilterChain.java:189)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationF
ilterChain.java:162)
        at cn.bba.boot.starter.sso.web.SsoOAuth2Filter.doFilter(SsoOAuth2Filter.
java:45)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(Appl
icationFilterChain.java:189)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationF
ilterChain.java:162)
        at cn.bba.boot.starter.sso.app.SsoAppFilter.doFilter(SsoAppFilter.java:8
6)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(Appl
icationFilterChain.java:189)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationF
ilterChain.java:162)
        at org.springframework.web.filter.RequestContextFilter.doFilterInternal(
RequestContextFilter.java:100)
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerR
equestFilter.java:117)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(Appl
icationFilterChain.java:189)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationF
ilterChain.java:162)
        at org.springframework.web.filter.FormContentFilter.doFilterInternal(For
mContentFilter.java:93)
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerR
equestFilter.java:117)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(Appl
icationFilterChain.java:189)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationF
ilterChain.java:162)
        at org.springframework.session.web.http.SessionRepositoryFilter.doFilter
Internal(SessionRepositoryFilter.java:142)
        at org.springframework.session.web.http.OncePerRequestFilter.doFilter(On
cePerRequestFilter.java:82)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(Appl
icationFilterChain.java:189)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationF
ilterChain.java:162)
        at org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFil
ter.doFilterInternal(WebMvcMetricsFilter.java:96)
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerR
equestFilter.java:117)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(Appl
icationFilterChain.java:189)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationF
ilterChain.java:162)
        at org.springframework.web.filter.CharacterEncodingFilter.doFilterIntern
al(CharacterEncodingFilter.java:201)
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerR
equestFilter.java:117)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(Appl
icationFilterChain.java:189)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationF
ilterChain.java:162)
        at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperV
alve.java:197)
        at org.apache.catalina.core.StandardContextValve.invoke(StandardContextV
alve.java:97)
        at org.apache.catalina.authenticator.AuthenticatorBase.invoke(Authentica
torBase.java:540)
        at org.apache.catalina.core.StandardHostValve.invoke$original$BaP1f5sG(S
tandardHostValve.java:135)
        at org.apache.catalina.core.StandardHostValve.invoke$original$BaP1f5sG$a
ccessor$inSXAupr(StandardHostValve.java)
        at org.apache.catalina.core.StandardHostValve$auxiliary$d1bv3ZEZ.call(Un
known Source)
        at org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstM
ethodsInter.intercept(InstMethodsInter.java:86)
        at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.j
ava)
        at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.j
ava:92)
        at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineVal
ve.java:78)
        at org.apache.catalina.valves.RemoteIpValve.invoke(RemoteIpValve.java:76
9)
        at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.jav
a:357)
        at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java
:382)
        at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLig
ht.java:65)
        at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(Abstract
Protocol.java:895)
        at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpo
int.java:1732)
        at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBas
e.java:49)
        at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoo
lExecutor.java:1191)
        at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPo
olExecutor.java:659)
        at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskTh
read.java:61)
        at java.lang.Thread.run(Thread.java:748)
Caused by: org.postgresql.util.PSQLException: ERROR: duplicate key value violate
s unique constraint "sys_audit_pkey"
  Detail: Key (id)=(1630500102261559297) already exists.
        at org.postgresql.core.v3.QueryExecutorImpl.receiveErrorResponse(QueryEx
ecutorImpl.java:2674)
        at org.postgresql.core.v3.QueryExecutorImpl.processResults(QueryExecutor
Impl.java:2364)
        at org.postgresql.core.v3.QueryExecutorImpl.execute(QueryExecutorImpl.ja
va:354)
        at org.postgresql.jdbc.PgStatement.executeInternal(PgStatement.java:484)
        at org.postgresql.jdbc.PgStatement.execute(PgStatement.java:404)
        at org.postgresql.jdbc.PgPreparedStatement.executeWithFlags$original$oxN
wW64X(PgPreparedStatement.java:162)
        at org.postgresql.jdbc.PgPreparedStatement.executeWithFlags$original$oxN
wW64X$accessor$fb1zxLjf(PgPreparedStatement.java)
        at org.postgresql.jdbc.PgPreparedStatement$auxiliary$M6sKUyj3.call(Unkno
wn Source)
        at org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstM
ethodsInter.intercept(InstMethodsInter.java:86)
        at org.postgresql.jdbc.PgPreparedStatement.executeWithFlags(PgPreparedSt
atement.java)
        at org.postgresql.jdbc.PgPreparedStatement.execute(PgPreparedStatement.j
ava:151)
        at com.zaxxer.hikari.pool.ProxyPreparedStatement.execute(ProxyPreparedSt
atement.java:44)
        at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.execute(HikariPro
xyPreparedStatement.java)
        at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(
PreparedStatementHandler.java:47)
        at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(R
outingStatementHandler.java:74)
        at sun.reflect.GeneratedMethodAccessor262.invoke(Unknown Source)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAcces
sorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:64)
        at com.sun.proxy.$Proxy252.update(Unknown Source)
        at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.jav
a:50)
        at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:117)
        at sun.reflect.GeneratedMethodAccessor213.invoke(Unknown Source)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAcces
sorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.apache.ibatis.plugin.Invocation.proceed(Invocation.java:49)
        at com.xxx.xxx.sys.audit.SqlTrackInterceptor.intercept(SqlTrackInterce
ptor.java:37)
        at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:62)
        at com.sun.proxy.$Proxy251.update(Unknown Source)
        at sun.reflect.GeneratedMethodAccessor213.invoke(Unknown Source)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAcces
sorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.apache.ibatis.plugin.Invocation.proceed(Invocation.java:49)
        at com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor.int
ercept(MybatisPlusInterceptor.java:106)
        at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:62)
        at com.sun.proxy.$Proxy251.update(Unknown Source)
        at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSq
lSession.java:194)
        at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSq
lSession.java:181)
        at sun.reflect.GeneratedMethodAccessor261.invoke(Unknown Source)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAcces
sorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(Sq
lSessionTemplate.java:427)
        ... 115 common frames omitted
```
复现
需要尝试使用接口压测的方式，高并发请求，看是否会出现id重复现象。必要时可以手动延长接口耗时。


分析-多线程

解决
