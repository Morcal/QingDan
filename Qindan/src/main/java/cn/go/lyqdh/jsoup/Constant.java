package cn.go.lyqdh.jsoup;


/**
 * -----------------------------------------------------------
 * 版 权 ： BigTiger 版权所有 (c) 2015
 * 作 者 : BigTiger
 * 版 本 ： 1.0
 * 创建日期 ：2015/6/18 11:16
 * 描 述 ：常量
 * <p/>
 * -------------------------------------------------------------
 */
public interface Constant {
    //当前是否为调试模式
    boolean DEBUG = false;

    boolean TRACE = false;

    /**
     * 调用函数 Picasso.setDebug(true) 可以在加载的图片左上角显示一个 三角形 ，不同的颜色代表加载的来源
     * 红色：代表从网络下载的图片
     * 蓝色：代表从磁盘缓存加载的图片
     * 绿色：代表从内存中加载的图片
     * 如果项目中使用了OkHttp库的话，默认会使用OkHttp来下载图片。否则使用HttpUrlConnection来下载图片。
     */
    boolean IMAGE_DEBUG = false;

    //是否使用外网地址
    boolean USE_REAL_SEVER = true;

    //屏蔽用户频繁点击的最短时间间隔 ms
    long CLICK_INTERVAL = 600;

    /**
     * 平台类型 3：Android
     */
    String OS_TYPE = "3";

    /**
     * 服务器地址
     */
    String REAL_SERVER = "http://api.app.yiwaiart.com:8080/api.php";

    //    String TEST_SERVER = "http://hongzhibing.yiwaiart/api.php";
    String TEST_SERVER = "http://test.admin.app.yiwaiart.com:8080/api.php";
//    String TEST_SERVER = "http://192.168.45.4:8090/api.php";

    int LOADING_IMAGE_ID = R.drawable.load_img;
    int FAIL_IMAGE_ID = R.drawable.load_img;

    int DEFAULT_AVATAR_SMALL = R.drawable.default_face_70px;
    int DEFAULT_AVATAR = R.drawable.default_face_100px;

    String USER_PROTOCOL_FILE_PATH = "file:///android_asset/yishuart_protocol.html";
    String TEST_WEB_CONTENT = "test_web2.html";

    int LOADING_CIRCLE_VIEW_MAX_SIZE = 35;

    /**
     * 检查更新，本地有安装包下提示是否安装的倒计时
     */
    int CHECK_NEW_VERSION = 8 * 1000;

    String WEB_DEFAULT_BG = "file:///android_asset/load_img.png";


    //弹窗延迟启动时间
    int POPUP_DELAY_TIME = 5000;

    int CHECK_NEW_VERSION_INTERVAL_TIME = 24 * 60 * 60 * 1000;
//    int CHECK_NEW_VERSION_INTERVAL_TIME = 5 * 60 * 1000; //测试检查更新时间为5分钟

}
