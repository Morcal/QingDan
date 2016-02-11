package cn.go.lyqdh.qindan.View;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import cn.go.lyqdh.qindan.ArticleManager;
import cn.go.lyqdh.qindan.R;
import cn.go.lyqdh.qindan.adapter.ArticleAdapter;
import cn.go.lyqdh.qindan.model.Article;

/**
 * Created by lyqdh on 2016/1/4.
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_home);

    }
}
