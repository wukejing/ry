
1、man的IndexController的login写登录cookie时，
多个子系统如果采用字母的二级域名方式，则此处cookie的path为/，domain默认，
如果采用首域名不同的二级域名方式，则此处path默认,domain用根域，如(.dmall.com)，需要放到配置文件中

2、临时采用调用erpPrivilegeService的方法验证权限，多系统整合时应改为调用erp系统的dubbo接口校验权限

3、多个系统在同一个数据库时，需要注意operate_log_yyyyMM日志表需要区分，否则日志会混到一张表里

4、复制工程需修改的地方:1、dev.properties的本地域名和erp域名，2、数据库中的system域名
