package cn.go.lyqdh.qindan.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.go.lyqdh.qindan.R;
import cn.go.lyqdh.qindan.model.Article;
import cn.go.lyqdh.qindan.model.Event;
import cn.go.lyqdh.qindan.model.User;
import cn.go.lyqdh.qindan.util.ViewUtils;
import cn.go.lyqdh.qindan.weight.UnderLineLinearLayout;
import de.greenrobot.event.EventBus;


/**
 * Created by lyqdh on 2016/1/4.
 */
public class CollectActivity extends AppCompatActivity {
    private static final String TAG = CollectActivity.class.getSimpleName();
    @Bind(R.id.collect_toolbar)
    Toolbar toolbar;
    @Bind(R.id.underline_layout)
    UnderLineLinearLayout timeLayout;
    @Bind(R.id.btn_add)
    Button add;
    @Bind(R.id.cur_marginside)
    TextView textView;

    private int i = 0;
    public View node = null;
    private List<View> nodes;

    // 测试
    private List<Article> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        initData();
        intiEvent();
    }

    private void initData() {
        BmobQuery<User> query = new BmobQuery<User>();
        User user = User.getCurrentUser(CollectActivity.this, User.class);
        query.getObject(CollectActivity.this, user.getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                ViewUtils.showToastShort(CollectActivity.this, "收藏记录查询成功");
                list = user.getClollects();
                for (int i = 0; i < list.size(); i++) {
                    addItem(list.get(i));
                }
            }
            @Override
            public void onFailure(int i, String s) {
                ViewUtils.showToastShort(CollectActivity.this, "收藏记录查询失败");
            }
        });


    }

    private void intiEvent() {

        toolbar.setNavigationIcon(R.drawable.btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    private View addItem(Article article) {
        View v = LayoutInflater.from(this).inflate(R.layout.item_vertical, timeLayout, false);
        ((TextView) v.findViewById(R.id.tx_action)).setText(article.getTitle());
        ((TextView) v.findViewById(R.id.tx_action_time)).setText(article.getPublishTime());
        ((TextView) v.findViewById(R.id.tx_action_status)).setText("finish");
        timeLayout.addView(v);
        i++;
        return v;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
