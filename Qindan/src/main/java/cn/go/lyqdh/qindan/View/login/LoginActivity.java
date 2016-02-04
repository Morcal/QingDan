package cn.go.lyqdh.qindan.View.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.SaveListener;
import cn.go.lyqdh.qindan.ArticleManager;
import cn.go.lyqdh.qindan.Constant;
import cn.go.lyqdh.qindan.R;
import cn.go.lyqdh.qindan.View.MainActivity;
import cn.go.lyqdh.qindan.adapter.ArticleAdapter;
import cn.go.lyqdh.qindan.model.Article;
import cn.go.lyqdh.qindan.model.User;
import cn.go.lyqdh.qindan.util.ViewUtils;

/**
 * Created by lyqdh on 2016/1/4.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    @Bind(R.id.login_toolbar)
    public Toolbar toolbar;
    @Bind(R.id.et_name)
    public EditText name;
    @Bind(R.id.et_password)
    public EditText pwd;
    @Bind(R.id.iv_clearname)
    public ImageView clearName;
    @Bind(R.id.iv_clearpwd)
    public ImageView clearPwd;
    @Bind(R.id.btn_login)
    public TextView login;
    @Bind(R.id.btn_register)
    public TextView register;

    private String userName;
    private String password;

    private User user = new User();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        initData();
        initEvent();
    }

    private void initData() {
        userName = name.getText().toString().trim();
        password = pwd.getText().toString().trim();
    }

    private void initEvent() {
        clearName.setOnClickListener(this);
        clearPwd.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        toolbar.setNavigationIcon(R.drawable.btn_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (name.getText().toString().isEmpty()) {
                    clearName.setVisibility(View.GONE);
                } else {
                    clearName.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pwd.getText().toString().isEmpty()) {
                    clearPwd.setVisibility(View.GONE);
                } else {
                    clearPwd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clearname:
                name.setText("");
                break;
            case R.id.iv_clearpwd:
                pwd.setText("");
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                register();
                break;
            default:
                break;
        }
    }

    private void register() {
        userName = name.getText().toString().trim();
        password = pwd.getText().toString().trim();
        user.setUsername(userName);
        user.setPassword(password);
        user.signUp(LoginActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                ViewUtils.showToastShort(LoginActivity.this, "注册成功");
                name.setText("");
                pwd.setText("");
            }

            @Override
            public void onFailure(int i, String s) {
                ViewUtils.showToastShort(LoginActivity.this, "注册失败,请重试");
                name.setText("");
                pwd.setText("");
            }
        });
    }

    private void login() {
        user.setUsername(name.getText().toString().trim());
        user.setPassword(pwd.getText().toString().trim());
        user.login(LoginActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                ViewUtils.showToastShort(LoginActivity.this, "登录成功");
                Constant.isLogin = true;
                Intent intent = new Intent();
                intent.putExtra("USERNAME", name.getText().toString().trim());
                setResult(1, intent);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ViewUtils.showToastShort(LoginActivity.this, "登录失败,请重试");
            }
        });
    }
}
