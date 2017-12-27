OKHttpUtil使用说明

1、OkHttpUtil有两种使用方式：
一、全局设置，使用OKHttpUtil类实现，使用前需要在Application中做全局初始化的操作
二、分别控制每次请求，使用OKHttp类实现

2、OKHttpUtil支持Json、post、get、file、byte数据格式上传，如果需要添加其他格式、请
    参考Params包的初始化设置。

3、OkHttpUtil支持文件下载和json解析。
一、文件下载，只需要实现FileBack即可获取到下载后保存的文件路径和下载进度显示。支持
    自定义下载文件保存目录。
二、json解析，采用Gson解析json,如果需要更换解析框架，可重写GsonBack中的parseJson方法。
    GsonBack支持自动解析：1.统一处理异常信息；2.自动判断json返回Data中的是对象、String
    还是List;3.支持id过滤、Class过滤解析字段;4.支持全局异常捕获。
