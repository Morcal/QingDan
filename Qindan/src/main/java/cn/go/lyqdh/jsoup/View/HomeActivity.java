package cn.go.lyqdh.jsoup.View;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import cn.go.lyqdh.jsoup.ArticleManager;
import cn.go.lyqdh.jsoup.R;
import cn.go.lyqdh.jsoup.adapter.ArticleAdapter;
import cn.go.lyqdh.jsoup.model.Article;

/**
 * Created by lyqdh on 2016/1/4.
 */
public class HomeActivity extends AppCompatActivity {
    private ListView listView;
    private ArticleAdapter adapter;
    private List<Article> list;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_home);
        // 初始化list
        new ArticleManager().init();
        adapter=new ArticleAdapter(this);
        adapter.setItems(list);
        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
