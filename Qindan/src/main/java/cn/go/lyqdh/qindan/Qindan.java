package cn.go.lyqdh.qindan;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.Bmob;
import okhttp3.OkHttpClient;


/**
 * Created by lyqdhgo on 2016/1/24.
 */
public class Qindan extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 配置OkHttpUtills
        OkHttpClient client =
                OkHttpUtils.getInstance().getOkHttpClient();

        // 初始化Bmob
        Bmob.initialize(getApplicationContext(), "da560ed7f217bc2ef3922ddda5528e7d");
    }
}
