package cn.go.lyqdh.qindan.ui.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.go.lyqdh.qindan.R;
import cn.go.lyqdh.qindan.app.Constant;
import cn.go.lyqdh.qindan.ui.adapter.MainFragmentPagerAdapter;
import cn.go.lyqdh.qindan.ui.module.collect.CollectActivity;
import cn.go.lyqdh.qindan.ui.module.home.HomeActivity;
import cn.go.lyqdh.qindan.ui.module.login.LoginActivity;
import cn.go.lyqdh.qindan.weight.CircleImageView;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.drawerlayout)
     DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
     Toolbar toolbar;
    @Bind(R.id.nav_view)
     NavigationView navigationView;
    @Bind(R.id.avatar)
     CircleImageView avatar;
    @Bind(R.id.nickname)
     TextView nickName;
    @Bind(R.id.signature)
     TextView signature;
    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    private MainFragmentPagerAdapter pagerAdapter;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initView() {
        pagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initEvent() {
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String item = menuItem.getTitle().toString();
                int itemId = menuItem.getItemId();
                Log.i("Item", item);
                Log.i("ItemId", itemId + "");
                selectMenuItem(itemId);
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    private void selectMenuItem(int itemId) {
        drawerLayout.closeDrawers();
        switch (itemId) {
            case R.id.nav_action_home: // 首页
                break;
            case R.id.nav_action_jinxuan: // 精选
                break;
            case R.id.nav_action_discovery: // 发现
                Intent intent2 = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_action_collect: // 收藏
                if (Constant.isLogin) {
                    Intent intent3 = new Intent(MainActivity.this, CollectActivity.class);
                    startActivity(intent3);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.nav_action_owen: // 我的
                break;
            case R.id.nav_action_create: // 发布
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                String userName = data.getStringExtra("USERNAME");
                nickName.setText(userName);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
