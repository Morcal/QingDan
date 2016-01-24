package cn.go.lyqdh.jsoup.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.go.lyqdh.jsoup.R;
import cn.go.lyqdh.jsoup.adapter.ArticleAdapter;
import cn.go.lyqdh.jsoup.model.Article;
import cn.go.lyqdh.jsoup.model.Image;
import cn.go.lyqdh.jsoup.model.User;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String TRAVEL = "https://qdan.me/list/Vm4qtjSjy0Zqj6IF";
    public static final String QINDAN = "https://qdan.me/topic/VNcd-A1Rjqr6Lfsl"; // 旅游类
    public static final String HOSTNAME = "https://qdan.me";  // Host Name
    public int pageCount = 1;
    public boolean isFirstLoad = true;
    public List<Article> articles1;
    public Article article;
    public User user;
    public Image image;
    private ArticleAdapter adapter;
    @Bind(R.id.list_articl)
    public ListView listView;
    @Bind(R.id.swipe_container)
    public SwipeRefreshLayout swipeRefresh;
    private ProgressDialog dialog;

    String testUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().getSystemUiVisibility();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        new DrawerBuilder().withActivity(this).build();
        swipeRefresh.setColorSchemeColors(Color.parseColor("#1C86EE"));
        swipeRefresh.setOnRefreshListener(this);
        adapter = new ArticleAdapter(this);
        new JsoupTask().execute(QINDAN);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = (Article) parent.getItemAtPosition(position);
                String shareUrl = article.getShareUrl();
                Log.i(TAG, "shareUrl:" + shareUrl);
                Intent intent = new Intent(MainActivity.this, DetialActivity.class);
                intent.putExtra("SHAREURL", shareUrl);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onRefresh() {
        pageCount++;
        new JsoupTask().execute(QINDAN + "?page=" + pageCount);
    }

    class JsoupTask extends AsyncTask<String, Void, List<Article>> {

        @Override
        protected void onPreExecute() {
            if (isFirstLoad) {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Loading");
                dialog.show();
            }
            isFirstLoad = false;
        }

        @Override
        protected List<Article> doInBackground(String... params) {
            try {
                int count = 0;
                Document doc = Jsoup.connect(params[0]).get();
                String title = doc.title();
                Element head = doc.head();
                Elements HeadElem = head.getAllElements();
                Elements elements = doc.getAllElements();
                Log.i(TAG, "element: " + elements.size());
                for (Element e : HeadElem) {
                    String eleStr = e.toString();
                    Elements el = e.getElementsByAttribute("name");
                    int eNameSize = el.size();
                    Log.i(TAG, "eNameSize: " + eNameSize);
                    String name = e.attr("name");
                    String link = e.attr("href");
                    Log.i(TAG, "Name: " + name);
                    Log.i(TAG, "Href: " + HOSTNAME + link);
                    Log.i(TAG, "EleItem: " + eleStr);
                }
                for (Element e : elements) {
                    String name = e.nodeName();
                    Log.i(TAG, "所有NodeName: " + name + " " + count);
                    count++;
                }
                Log.i(TAG, "head: " + head.toString());
                Log.i(TAG, "title: " + title);

                getBodyInfo(doc);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return articles1;
        }

        @Override
        protected void onPostExecute(List<Article> articles) {
            dialog.dismiss();
            Log.i(TAG, "article size: " + articles.size());
            if (articles.size() != 0) {
                articles1.addAll(articles);
                adapter.setItems(articles);
                listView.setAdapter(adapter);
            }
            swipeRefresh.setRefreshing(false);
        }
    }

    private void getBodyInfo(Document doc) {
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
//            String coverText = eleCover.text();
//            String cover = eleCover.attr("src");
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
//        Elements lists = lEle.getElementsByAttributeValue("class", "list");
//        Element link = lists.first();
//        Elements infos = link.getElementsByAttributeValue("class", "left");
//        Element info = infos.first();
//        String str = info.getElementsByTag("a").first().attr("href");
//
//        String text = info.getElementsByTag("a").first().text();
//        Log.i(TAG, "Text: " + text + "Link: " + HOSTNAME + str);
//        testUrl = HOSTNAME + str;
//        Log.i(TAG, "info: " + info.toString());
//        Log.i(TAG, "link--->: " + link.toString());
//        Log.i(TAG, "Href :" + str);
//        Log.i(TAG, "list: " + lists.size());
//        Log.i(TAG, "List 集合：" + lEle.toString());
//        Log.i(TAG, "List--->：" + element5.toString());
//
//
//        Log.i(TAG, "FirstEle: " + firstEle.toString());
    }

}
