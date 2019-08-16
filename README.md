# PrimFast
 PrimFast 是全栈技术学习的开源项目,也是我自己的未来发展方向
 项目内设计的技术面很广,有Android进阶技术和大前端的技术、后台技术、还有当前火热的python技术学习库,以及第三方框架原理和设计思想.

1. 包括Android进阶知识:APP快速开发框架搭建、组件化技术实现、插件化技术实现、Okhttp原理、Glide原理、RxJava原理、EventBus原理、音视频、NDK开发、进程通信、IOC、移动架构等.

2. Flutter跨平台技术与实战.

3. RN跨平台技术以及移动Web应用和小程序的实战.

4. Kotlin 开发技术学习与使用kotlin开发Android实战.

5. JavaScript/TypeScript 技术详解, 大前端时代必备的知识

6. 数据结构与算法

7. 后台开发:Node Java

8. 数据分析与人工智能:python

   专注于技术的最前线,欢迎关注我的个人网站:https://jakeprim.cn/

[TOC]

## PrimFast 介绍

> PrimFastCommon 是一套通用的快速开发app的框架,采用 Jetpack + RxJava + Retrofit + OkHttp + kotlin/Java(混合) + 组件化/插件化 搭建的Android项目开发框架.

> PrimFastFlutter 是学习Flutter的一个完整的项目,包括项目完整实现的讲解

> PrimFastKotlin 是学习kotlin的一个完整的项目,包括项目完整实现的讲解

> PrimFastWeChatApplets 是学习小程序开发的一个完整项目

> PrimFastRn 是学习RN开发的一个完整项目

> PrimJs  是专门给Android开发者,Js入门到进阶的知识

> PrimFastWeb 是学习移动Web开发的一个完整项目(项目准备采用Vue/React来实现)

> [PrimAlgorithm](https://github.com/JakePrim/PrimFastApp/tree/master/PrimAlgorithm) 是数据结构和算法,Java的实现

> AdvancedDemo 是Android进阶的知识点的Demo练习

> PrimFastJava Java后台开发学习项目

> PrimFastNode Node开发学习项目

> PrimFastPython Python 开发学习项目

## 进度

### PrimFast 进度

PrimFastCommon [项目地址](https://github.com/JakePrim/PrimFastApp/tree/master/PrimFastCommon)

PrimFastFlutter [项目地址](https://github.com/JakePrim/PrimFastApp/tree/master/PrimFastFlutter)

PrimFastKotlin [项目地址](https://github.com/JakePrim/PrimFastApp/tree/master/PrimFastKotlin)

PrimFastApplets [项目地址](https://github.com/JakePrim/PrimFastApp/tree/master/PrimFastWeChatApplets)

PrimFastRn [项目地址](https://github.com/JakePrim/PrimFastApp/tree/master/PrimFastRn)

PrimJs [项目地址](https://github.com/JakePrim/PrimFastApp/tree/master/PrimJs)

AdvancedDemo [项目地址](https://github.com/JakePrim/PrimFastApp/tree/master/AdvancedDemo)

PrimAlgorithm [项目地址](https://github.com/JakePrim/PrimFastApp/tree/master/PrimAlgorithm)

---
技术详解部分

### Android 高级进阶知识部分

#### Android 组件化原理及路由的实现
- 实现demo地址:https://github.com/JakePrim/PrimRoute
- [x] [Android组件化专题 - 组件化配置](https://www.jianshu.com/p/dd323c987c94)
- [x] [Android APT(Annotation Processing Tool) 实践](https://www.jianshu.com/p/160a832ce135)
- [x] [Android组件化专题 - 路由框架原理
](https://www.jianshu.com/p/e2d93259dc34)
- [x] [Android组件化专题 - 路由框架进阶模块间的业务通信](https://www.jianshu.com/p/f215eaf2f687)
- [x] [Android组件化专题-路由动态注入跳转参数以及获取其他模块的fragment](https://www.jianshu.com/p/3dcbde5acc3b)
- [ ] 组件化总结与用组件化如何开发一个完整的项目

#### Android 插件化实现方案
- 实现demo地址:https://github.com/JakePrim/PrimPlug
- [x] [Android插件化技术之旅 1 开篇 - 实现启动插件与调用插件中的Activity和Service](https://www.jianshu.com/p/ef3f8c5c3810)
- [x] [Android插件化技术之旅 2-广播插件的实现与安装apk原理](https://www.jianshu.com/p/64bbbb3bc38a)
- [ ] hook技术详解
- [ ] Binder核心机制分析与跨进程原理
- [ ] hook实现插件化
- [ ] 插件化总结与展望

#### 数据库框架设计实现自己的数据库框架与greenDao源码分析
- 实现demo地址:https://github.com/JakePrim/PrimDao
- [x] [数据库框架设计 自动建表](https://www.jianshu.com/p/4adc3b6213b0)
- [x] [数据库框架设计 增删改查](https://note.youdao.com/)
- [x] [数据库框架设计 多用户分库实现](https://www.jianshu.com/p/ced35d1fba5e)
- [ ] 数据库框架设计 升级数据库
- [ ] greenDao源码分析

#### OkHttp 实现原理及详细分析 [代码](https://github.com/JakePrim/PrimFast/tree/master/AdvancedDemo/PrimOkHttpCore)
- [x] [OkHttp 源码解析及OkHttp的设计思想](https://www.jianshu.com/p/cb444f49a777)
- [ ] [手写OkHttp核心代码]()
- [ ] [OkHttp 各个责任链详细分析]()
- [ ] [理解Http与Tcp的相关知识]()

#### Retrofit 实现原理
- [x] [架构设计之美-揭秘Retrofit设计原理](https://jakeprim.cn/2019/05/21/retrofit-1/)

#### Glide 实现原理及详细分析

- [ ] Glide 的图片缓存实现原理；
- [ ] Glide 图片加载器 - ModeLoader 模型加载器与其注册机；
- [ ] Glide 加载图片的流程；
- [ ] Glide 的生命周期管理；
- [ ] Glide 框架整体结构；
- [ ] Glide 的核心 API 的设计思路；
- [ ] 总结 Glide 的架构。

#### 序列化与反序列化Json框架原理解析

#### EventBus原理分析与如何实现可跨进程的通信框架

#### RxJava基础和RxJava实现原理

- [x] [RxJava2.x 庖丁解牛 - 操作符使用详解与场景分析](https://jakeprim.cn/2019/05/09/rxjava-1/)
- [ ] RxJava2.x 源码分析

####  IOC 注入式框架分析

### Android View 部分

#### View绘制的原理

#### ListView和RecyclerView实现原理解析

### Android性能优化部分

### NDK开发部分

#### NDK开发基础知识掌握
- C基础学习
- C++基础学习
- [x] [NDK 开发必知必会1⃣️CC++编译器配置](https://www.jianshu.com/p/00e999e72f66)
- [x] [NDK 开发必知必会2⃣️NDK开发环境配置与MakeFile](https://www.jianshu.com/p/d4f02c41859e)
- [x] [NDK开发必知必会3⃣️MakeFile详解](https://www.jianshu.com/p/b77367d0654e)
- [x] [JNI 编程详解-基本数据类型](https://www.jianshu.com/p/d8061f0f2eac)
- [x] [JNI编程-动态注册及native线程调用Java](https://www.jianshu.com/p/290fbbfac841)

### Kotlin 开发
- [ ] Kotlin 基础语法与核心特性
- [ ] Kotlin 开发github客户端实战

### 编程内功修炼部分

#### 数据结构
- [x] [数据结构之表的总结](https://www.jianshu.com/p/88a0e9c77fb1)
- [x] [链表问题补充](https://www.jianshu.com/p/87d8e0320bb5)
- [x] [数据结构之Java中哈希表的经典实现HashMap分析](https://www.jianshu.com/p/e15277533dcf)
- [x] [队列：彻底理解队列](https://www.jianshu.com/p/1b8270f3c881)
- [ ] 二叉树
- [ ] 红黑树
- [ ] 图论

#### 算法
...
#### 浏览器的工作原理
...
#### 网络协议
...
#### 操作系统
...
#### 数据库
...

### 大前端技术

#### Deep JavaScript

 [项目地址](https://github.com/JakePrim/PrimFastApp/tree/master/PrimJs)

- [x] [初遇JavaScript](https://jakeprim.cn/2019/07/07/javascript-1/)
- [x] [JavaScript 变量和作用域](https://jakeprim.cn/2019/08/01/javascript-2/)
- [x] [JavaScript 函数](https://jakeprim.cn/2019/08/02/javascript-%E5%87%BD%E6%95%B0/)

#### ES6
...

#### TypeScript
...
#### Vue.js
...
#### React.js
...
#### Node.js
...
#### Flutter 开发 [项目地址](https://github.com/JakePrim/PrimFastApp/tree/master/PrimFastFlutter)
- [x] [Flutter 常用的布局与事件](https://jakeprim.cn/2019/03/26/flutter-1-1/)
- [x] [Flutter 请求接口以及DAO层的实现](https://jakeprim.cn/2019/03/26/flutter-1-2/)
- [x] [Flutter WebView使用详解](https://jakeprim.cn/2019/04/07/flutter-1-3/)
- [x] [Flutter 中的组件通信与状态改变](https://jakeprim.cn/2019/03/15/flutter-event-bus/)
- [x] [Dart 语言基础与核心特性](https://jakeprim.cn/2019/07/23/dart-2/)
- [x] [Flutter与Native的生死相依](https://jakeprim.cn/2019/07/04/flutter-native-1/)

#### ReactNative 开发
...

### 后端技术

#### Java后台
...

#### Python
...
