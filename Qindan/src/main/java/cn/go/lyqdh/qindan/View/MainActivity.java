package cn.go.lyqdh.qindan.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.go.lyqdh.qindan.Constant;
import cn.go.lyqdh.qindan.R;
import cn.go.lyqdh.qindan.View.collect.CollectActivity;
import cn.go.lyqdh.qindan.View.login.LoginActivity;
import cn.go.lyqdh.qindan.adapter.ArticleAdapter;
import cn.go.lyqdh.qindan.model.Article;
import cn.go.lyqdh.qindan.model.Dao;
import cn.go.lyqdh.qindan.model.Image;
import cn.go.lyqdh.qindan.model.User;
import cn.go.lyqdh.qindan.util.ViewUtils;
import cn.go.lyqdh.qindan.weight.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MUSIC = Constant.QINDAN + Constant.MUSIC; //音乐
    public static final String HOSTNAME = "https://qdan.me";  // Host Name
    public int pageCount = 1;
    public boolean isFirstLoad = true;
    public List<Article> articles1 = new ArrayList<>();
    public Article article;
    public User user;
    public Image image;
    private ArticleAdapter adapter;
    @Bind(R.id.drawerlayout)
    public DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.nav_view)
    public NavigationView navigationView;
    //    @Bind(R.id.list_articl)
//    public ListView listView;
    @Bind(R.id.avatar)
    public CircleImageView avatar;
    @Bind(R.id.nickname)
    public TextView nickName;
    @Bind(R.id.signature)
    public TextView signature;
    //@Bind(R.id.swipe_container)
//    public SwipeRefreshLayout swipeRefresh;
    private ActionBarDrawerToggle drawerToggle;
    private ProgressDialog dialog;

    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    private MainFragmentPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
//        swipeRefresh.setColorSchemeColors(Color.parseColor("#1C86EE"));
//        swipeRefresh.setOnRefreshListener(this);
//        adapter = new ArticleAdapter(this);
//        initData();
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
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Article article = (Article) parent.getItemAtPosition(position);
//                String shareUrl = article.getShareUrl();
//                String title = article.getTitle();
//                Intent intent = new Intent(MainActivity.this, DetialActivity.class);
//                intent.putExtra("SHAREURL", shareUrl);
//                intent.putExtra("TITLE", title);
//                startActivity(intent);
//            }
//        });

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

//    private void initData() {
//        ViewUtils.showDialog(MainActivity.this, "Loading");
//        Dao.getEntity(MUSIC, new Dao.EntityListener() {
//            @Override
//            public void onError() {
//                ViewUtils.showToastShort(MainActivity.this, getString(R.string.error));
//            }
//
//            @Override
//            public void onSuccess(String result) {
////                dialog.dismiss();
//                ViewUtils.hideDialog();
//                Document doc = Jsoup.parse(result);
//                List<Article> list = getBodyInfo(doc);
//                if (list.size() != 0 && list != null) {
//                    articles1.addAll(list);
//                    adapter.setItems(articles1);
//                    adapter.notifyDataSetChanged();
//                    listView.setAdapter(adapter);
//                }
//            }
//        });
//
//    }


    public List getBodyInfo(Document doc) {
        articles1 = new ArrayList<>();
        Element element = doc.body();
        Elements elements = element.getAllElements();
        Elements element2 = element.select("div[class=content]");
        Element firstEle = element2.first();
        Elements element3 = firstEle.getElementsByAttributeValue("class", "section section-lists");
        Element listEle = element3.first();
        Elements element4 = listEle.getElementsByAttributeValue("class", "section-content");
        Element lsEle = element4.first();
        Elements element5 = lsEle.getElementsByAttributeValue("class", "lists");
        Element lEle = element5.first();
        Elements ls = lEle.getElementsByAttributeValue("class", "list");
        for (Element e : ls) {
            article = new Article();
            user = new User();
            image = new Image();
            String cover = e.getElementsByAttributeValue("class", "list-cover").attr("src");
            Element l = e.getElementsByAttributeValue("class", "left").first();
            String s = l.getElementsByTag("a").first().attr("href");
            String text = l.getElementsByTag("a").first().text();
            Element eleDesc = l.getElementsByAttributeValue("class", "list-desc").first();
            Element eleAuthor = l.getElementsByAttributeValue("class", "list-author").first();
            Element eleAvator = eleAuthor.getElementsByTag("a").first().getElementsByTag("img").first();
            String avatar = eleAvator.attr("src");
            int width = Integer.parseInt(eleAvator.attr("width"));
            int height = Integer.parseInt(eleAvator.attr("height"));

            String nickName = eleAuthor.getElementsByAttributeValue("class", "list-author-link-container").first().getElementsByTag("a").text();
            String data = eleAuthor.getElementsByAttributeValue("class", "list-meta").first().getElementsByAttributeValue("class", "list-date").text();
            String count = eleAuthor.getElementsByAttributeValue("class", "list-meta").first().getElementsByAttributeValue("class", "list-count").text();
            String desc = eleDesc.text();

            article.setShareUrl(HOSTNAME + s); // 分享链接
            article.setTitle(text); // 标题
            article.setSummary(desc); // 描述
            user.setAvatar(avatar); //用户头像
            user.setNickname(nickName); //用户名
            user.setCount(count);
            article.setPublisher(user);
            image.setUrl(cover);
            image.setHeight(height);
            image.setWidth(width);
            article.setCover(image);
            article.setPublishTime(data);
            articles1.add(article);
            Log.i(TAG, "Link链接: " + HOSTNAME + s
                    + " title: " + text
                    + " desc: " + desc
                    + " cover: " + cover
                    + " avatar: " + avatar
                    + " width: " + width
                    + " height: " + height
                    + " nickName: " + nickName
                    + " data: " + data
                    + " count: " + count);
        }
        return articles1;
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
