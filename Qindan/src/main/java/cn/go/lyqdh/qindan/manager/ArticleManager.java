package cn.go.lyqdh.qindan.manager;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.go.lyqdh.qindan.bean.Article;
import cn.go.lyqdh.qindan.bean.Image;
import cn.go.lyqdh.qindan.bean.User;

/**
 * Created by lyqdh on 2016/1/6.
 */
public class ArticleManager {
    private static final String TAG = ArticleManager.class.getSimpleName();
    public static final String TRAVEL = "https://qdan.me/list/Vm4qtjSjy0Zqj6IF";
    public static final String QINDAN = "https://qdan.me/topic/VNcd-A1Rjqr6Lfsl"; // 旅游类
    public static final String HOSTNAME = "https://qdan.me";  // Host Name
    public boolean REFRESH = false;
    public String testUrl = "";
    public List<Article> articles;
    public Article article;
    public User user;
    public Image image;

    public ArticleManager() {
        articles = new ArrayList<>();
    }

    public List<Article> getArtList() {
        return articles;
    }

    public void init() {
        new JsoupTask().execute();
    }

    class JsoupTask extends AsyncTask<Void, Void, List<Article>> {
        @Override
        protected List<Article> doInBackground(Void... params) {
            try {
                int count = 0;
                Document doc = Jsoup.connect(QINDAN).get();
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
            return articles;
        }

        @Override
        protected void onPostExecute(List<Article> articles) {
            Log.i(TAG, "article size: " + articles.size());
            REFRESH = true;
        }
    }

    private void getBodyInfo(Document doc) {
        articles = new ArrayList<>();
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
            articles.add(article);
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
        Elements lists = lEle.getElementsByAttributeValue("class", "list");
        Element link = lists.first();
        Elements infos = link.getElementsByAttributeValue("class", "left");
        Element info = infos.first();
        String str = info.getElementsByTag("a").first().attr("href");

        String text = info.getElementsByTag("a").first().text();
        Log.i(TAG, "Text: " + text + "Link: " + HOSTNAME + str);
        testUrl = HOSTNAME + str;
        Log.i(TAG, "info: " + info.toString());
        Log.i(TAG, "link--->: " + link.toString());
        Log.i(TAG, "Href :" + str);
        Log.i(TAG, "list: " + lists.size());
        Log.i(TAG, "List 集合：" + lEle.toString());
        Log.i(TAG, "List--->：" + element5.toString());


        Log.i(TAG, "FirstEle: " + firstEle.toString());
    }

}
