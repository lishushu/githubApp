# GitAssistant Gitub简单Android端应用

### 运行使用说明
Github 开发API，登录授权已经摒弃用户名和密码方式，使用token授权，所以需要自行申请对应的clientId和secretId填写对应的Authorization callback URL，
配置到项目的local.properties. 该项目代码内使用的**CallBack url = "lycgithub://oauth"**
``` 
CLIENT_ID = "ccd**********947"
CLIENT_SECRET = "cc***********7"
```
![image](https://user-images.githubusercontent.com/2612850/179501900-dcf1920d-07a0-400a-bc8d-0bda1764701f.png)

申请注册Github APP ： https://github.com/settings/applications/new

API授权官方文档：[作为 GitHub 应用程序 访问 API 端点](https://docs.github.com/cn/developers/apps/building-github-apps/authenticating-with-github-apps#%E4%BD%9C%E4%B8%BA-github-%E5%BA%94%E7%94%A8%E7%A8%8B%E5%BA%8F-%E8%AE%BF%E9%97%AE-api-%E7%AB%AF%E7%82%B9)


### 项目简介
GitAssistant是一款为Github打造的简单Android应用，评估计划为小型App项目，故而不走组件化设计方案。初步也未规划模块划分，初步以包划分不同模块。

大致包结构如下：
![GitAssistant](https://user-images.githubusercontent.com/2612850/179391493-03e3a605-9004-44d9-9e6a-903ea86b5c13.png)

- common提供基础业务无关的功能以及集成和隔离第三方SDK，后续业务发展可单独成为一个独立module


#### 使用的技术栈和架构划分
Kotlin + ViewMoel + LiveData + Navigation + Retrofit + Rxjava构建的MVVM-LiveData架构：
![架构设计图](https://user-images.githubusercontent.com/2612850/179391500-0fe03ebf-4752-47a7-88ec-e74f85df9a89.png)

数据处理部分设计为网络http拉取以及Room本地数据库并存，通过repository内部数据层隔离具体数据来源。

#### 使用到的第三方库
- RxPermission -- 权限请求
- MMKV -- 取代Preference的轻量级存储
- Glide -- 图片加载
- Leakcanary / Bugly 内存和crash监控
- Junit/Mockito 辅助测试相关等库

#### 目前完成情况
- 启动页
- 登录页面&webView授权登录页
- 主页面框架（三个子页面+NavigationBar切换页面）
- 主页-搜索页面

<img width="884" alt="image" src="https://user-images.githubusercontent.com/2612850/179400156-c4c7f57b-942e-4b95-b373-fc8ed68a036a.png">
<img width="220" alt="image" src="https://user-images.githubusercontent.com/2612850/179401199-f4122e38-1f94-4b3e-b64e-655f44c69a5c.png">


https://user-images.githubusercontent.com/2612850/179502602-6fb88bbd-f29d-404c-8c43-4652040c2b86.mp4


