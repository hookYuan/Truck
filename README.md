# Truck
模块化开发，代码采用MVP设计模式

#### 创建项目目的：
1、收集整理我在Android开发路上的遇到过的常用工具类 <br>
    2、提高代码的复用，方便下次快速开发<br>
    3、最近太闲、找点事情做<br>
    4、适用于中小型项目开发，易于后期维护和扩展<br>
    5、因不同的项目开发不同module,提高代码复用率<br>
    6、适合多人同时开发，提高开发效率，缩短开发周期<br>
    
#### 项目说明：
本项目总体采用组件化的思想搭建，项目整体结构分为三个层级：<br>
* base层：负责整个项目的常用的模块<br>
* module层： 业务组件，这里的module可以自由扩展，数量不受显示，但是所有的module层的组件都应该依赖于
base层组件开发.module层之间的通讯采用路由的方式实现。所有module层代码君可以在依赖base层后独立
开发调试、运行。项目中所有的module模块都可以不使用，也可以根据具体需求自行选择添加。<br>
* app层：app层是具体项目层，他应该依赖所有的module层。通过不同module组合实现不同app功能，以达到快速
            开发的目的。<br>
        项目的渠道包、签名等均在app下的build.gradle中配置，具体参数位于gradle.properties
        
#### Truck框架简介：

###### base模块
base为模块化开发中的一个基本模块。<br>
base模块主要分为五大部分：
模块一：commUtil
       模块说明：改模块包含常用工具类，以java代码为主，主要参考XDroid中的kit
模块二：http
模块说明：该模块主要包含RxJava,OkHttp,Retrofit。并对他们进行二次封装，方便修改。
支持http本地缓存，默认wifi环境不缓存，断网情况下读取缓存。
模块三：baseView
模块说明：该模块以XMl文件为主，包括常用dimen,layout,style,drawable，其中最重要的是
FlycoRoundView_Lib库，它可以减少项目中的shape、select文件的使用。
模块四：ui
ui模块中包含了Activity和fragment,其中Activity可以通过实现不同的接口实现不同的功能。
现在默认集成了:滑动返回Activity、状态控制、XToolbar、自定义Dialog、OkHttpUtil、RoutrofitUtil、GlideHelper、下拉刷新等
Fragment还包含LazyFragment,可配合FragmentActivity、viewpager使用
模块五：Router
路由模块采用ARouter作为基础，在Router中实现了基本的跨module通讯，可以通过ARouter在
module中启动其他module的Activity,而且activity也无需在app的Manifest注册。高度解耦
各大组件。
                
    album模块：
        album组件为图库选择器，依赖于base模块，其他module可以通过RouterHelper调用album。
        album现阶段主要包括手机图片预览、图片选择、拍照、分相册展示等基础图片选择功能。
        以后待加入：
            1.图片压缩（类似微信压缩上传，减小流量）
            
     登录模块：
        1、登录、注册
        2、键盘弹出控制、界面动画、输入校验
        3、验证码（待完成）、三方登录（待完成）
        
     map模块：
        城市选择列表
        待完成：
        map模块为地图模块（采用高德SDK），主要包含以下功能：
        map依赖于base模块，其他module可以通过RouterHelper调用map。
        1、定位，支持单次定位、连续定位。
        2、地图描点,设置标记。
        3、物体平滑移动，类似滴滴小车移动。
        4、路线规划。
        5、路线导航。
        6、位置选择列表。
# 使用方法：
      把base模块作为moudle导入。


