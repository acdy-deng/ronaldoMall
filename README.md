# AWMall
<a href='https://github.com/crbug/awmall/wiki'>Wiki文档地址</a>

### 一睹为快
![05DCF68F-0DB7-430F-931E-AEE5DFBA76A1.png](https://i.loli.net/2019/03/24/5c97537ddf4a8.png)
![FAEA2A9D-4C39-46D1-B56C-53EC6C5B80BE.png](https://i.loli.net/2019/03/24/5c97539dc1608.png)
![B9CBD235-9599-49B2-B58E-BB3D604BC59D.png](https://i.loli.net/2019/03/24/5c9752dae0c4f.png)
![C3799A58-F06B-4827-B906-BE0E2ACBB9B2.png](https://i.loli.net/2019/03/24/5c97530d93c04.png)
![9054CD7D-DE77-4B1F-8F99-40A43A9769A6.png](https://i.loli.net/2019/03/24/5c975330d2e04.png)
![9D5E4C3E-672A-45A1-B88C-B37BF371D80D.png](https://i.loli.net/2019/03/24/5c975350e82d7.png)

### 技术栈
+ 后端
  + Maven
  + Spring
  + SpringBoot
  + SpringDataJPA
  + MySQL
  + Druid：阿里高性能数据库连接池
  + Fastjson：阿里JSON格式化
  + Swagger2：Api文档生成
  + Guava：Google Java集合优化框架
  + Zxing：Google 二维码生成
  + Alipay：阿里支付宝
  + Mail / Thymeleaf：邮件推送服务
  + Pagehelper：分页
  + JodaTime：时间格式化
+ 前端
  + Html / css 
  + JavaScript / Jqeury
  + Ajax / Pjax
  + Bootstrap
  + Handlebars：渲染模板
  + Layer：Layui弹层组件
  
### 项目亮点
+ 前后端彻底分离
+ 秒杀抢购
+ 支付宝当面付
+ 邮件定时推送服务 / 邮件辅助验证服务
+ 解决横向越权 纵向越权
+ POJO / BO / VO抽象模型
+ 高复用的JSON数据响应封装
  
### 系统功能概述
+ ( 前台系统 )
  + 首页：秒杀轮播图、风格精选、新品首发、人气推荐、热门评论
  + 商品模块：推荐商品展示、分类商品展示、商品详情展示、商品搜索
  + 购物车模块：添加商品、删除商品、修改商品数量、生成订单
  + 订单模块：订单查询、订单支付、订单取消、订单删除
  + 用户模块：用户登录 注册、用户信息修改、用户头像上传、收货地址、用户订单列表、用户评论列表
+ ( 后台系统 ) 
  + 项目二期考虑后台系统的开发、并接入微信支付、Redis、MyCat、Shiro、ActiveMQ、Vue...
