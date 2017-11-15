扫一扫模块：
该模块主要功能是基于zxing实现扫描二维码和生成二维码的功能
zxing以jar包的形式依赖于scan,如需升级zxing,请更新scan下libs中的core-3.3.0.jar


为能够高度解耦各个模块，以后新增模块除必要条件，否则将不再依赖
base模块。方便以后将单个模块移植。scan不必依赖base模块

识别二维码使用方法：
1、将scan做为module导入到项目中，app依赖scan
2、启动ScanActivity,在onActivityResult中通过"codedContent"为key
可以获取到相机扫一扫获取到的结果值。