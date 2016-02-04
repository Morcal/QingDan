package cn.go.lyqdh.qindan.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.go.lyqdh.qindan.Constant;
import cn.go.lyqdh.qindan.R;
import cn.go.lyqdh.qindan.View.login.LoginActivity;
import cn.go.lyqdh.qindan.model.Article;
import cn.go.lyqdh.qindan.model.User;
import cn.go.lyqdh.qindan.util.ViewUtils;


public class DetialActivity extends AppCompatActivity {
    private static final String TAG = DetialActivity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.wv_detial)
    WebView webView;
    @Bind(R.id.index_progressBar)
    ProgressBar progressBar;
    @Bind(R.id.iv_collect)
    ImageView collect;
    private String url;
    private String title;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial);
        ButterKnife.bind(this);
        initSet();
        Intent intent = getIntent();
        url = intent.getStringExtra("SHAREURL");
        title = intent.getStringExtra("TITLE");
        webView.loadUrl(url);
    }

    private void initSet() {
//        setSupportActionBar(toolbar);
        webView.getSettings().setJavaScriptEnabled(true);
        toolbar.setNavigationIcon(R.drawable.btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //如果页面中链接，如果希望点击链接继续在当前browser中响应，而不是新开Android的系统browser中响应该链接，必须覆盖 webview的WebViewClient对象。
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressBar.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

        });
        //显示进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "收藏本页" + url);
                // 时间戳
                SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                String id = sDateFormat.format(new java.util.Date());
                String date = DateFormat.format(new java.util.Date());
                if (Constant.isLogin) {
                    user = User.getCurrentUser(DetialActivity.this, User.class);
                } else {
                    ViewUtils.showToastShort(DetialActivity.this, "请登录");
                    Intent intent = new Intent(DetialActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                Article collect = new Article();
                collect.setId(id);
                collect.setShareUrl(url);
                collect.setTitle(title);
                collect.setPublishTime(date);
                user.setCollect(collect);
                List<Article> collects = user.getClollects();
                collects.add(user.getCollect());
                user.setClollects(collects);
                user.update(DetialActivity.this, user.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        ViewUtils.showToastShort(DetialActivity.this, "收藏成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i(TAG, i + s);
                        ViewUtils.showToastShort(DetialActivity.this, "收藏失败");
                    }
                });

            }
        });

    }
}
