package cn.go.lyqdh.qindan.ui.module.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.go.lyqdh.qindan.ui.module.detail.DetialActivity;
import cn.go.lyqdh.qindan.app.Constant;
import cn.go.lyqdh.qindan.R;
import cn.go.lyqdh.qindan.ui.adapter.ArticleAdapter;
import cn.go.lyqdh.qindan.bean.Article;
import cn.go.lyqdh.qindan.bean.Dao;
import cn.go.lyqdh.qindan.bean.Image;
import cn.go.lyqdh.qindan.bean.User;
import cn.go.lyqdh.qindan.util.ViewUtils;

/**
 * Created by lyqdhgo on 2016/2/13.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MainFragment.class.getSimpleName();
    private static final String MUSIC = Constant.QINDAN + Constant.MUSIC; //音乐
    private static final String HOSTNAME = "https://qdan.me";  // Host Name

    public int pageCount = 1;
    public boolean isFirstLoad = true;
    public List<Article> articles1 = new ArrayList<>();
    public Article article;
    public User user;
    public Image image;
    private ArticleAdapter adapter;
    private String type;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.list_articl)
    ListView listView;

    public MainFragment(){}
    public MainFragment(String type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(type == null && getArguments() != null) {
            String type = getArguments().getString("type");
            this.type = type;
        }
        View view = inflater.inflate(R.layout.content_main, container, false);
        ButterKnife.bind(this, view);
        swipeRefresh.setColorSchemeColors(Color.parseColor("#1C86EE"));
        swipeRefresh.setOnRefreshListener(this);
        adapter = new ArticleAdapter(getActivity());
        initData(type);
        initEvent();
        return view;
    }

    private void initData(String type) {
        ViewUtils.showDialog(getActivity(), "Loading");
        Dao.getEntity(type, new Dao.EntityListener() {
            @Override
            public void onError() {
                ViewUtils.showToastShort(getActivity(), getString(R.string.error));
                ViewUtils.hideDialog();
            }

            @Override
            public void onSuccess(String result) {
                ViewUtils.hideDialog();
                Document doc = Jsoup.parse(result);
                List<Article> list = getBodyInfo(doc);
                if (list.size() != 0 && list != null) {
                    articles1.addAll(list);
                    adapter.setItems(articles1);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        pageCount++;
        Dao.getEntity(type + "?page=" + pageCount, new Dao.EntityListener() {
            @Override
            public void onError() {
                ViewUtils.showToastShort(getActivity(), getString(R.string.error));
            }

            @Override
            public void onSuccess(String result) {
                Document doc = Jsoup.parse(result);
                List<Article> list = getBodyInfo(doc);
                if (list.size() != 0 && list != null) {
                    articles1.addAll(list);
                    adapter.setItems(articles1);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    swipeRefresh.setRefreshing(false);
                }
            }
        });
    }

    private void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = (Article) parent.getItemAtPosition(position);
                String shareUrl = article.getShareUrl();
                String title = article.getTitle();
                Intent intent = new Intent(getActivity(), DetialActivity.class);
                intent.putExtra("SHAREURL", shareUrl);
                intent.putExtra("TITLE", title);
                startActivity(intent);
            }
        });
    }

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


}
