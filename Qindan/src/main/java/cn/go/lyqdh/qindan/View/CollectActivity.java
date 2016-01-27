package cn.go.lyqdh.qindan.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.go.lyqdh.qindan.R;
import cn.go.lyqdh.qindan.model.Article;
import cn.go.lyqdh.qindan.weight.UnderLineLinearLayout;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


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

    private int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        intiEvent();
    }

    private void intiEvent() {
        toolbar.setNavigationIcon(R.drawable.btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem("this is test");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MainThread) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onUserEvent(Article artical) {//参数必须是ClassEvent类型, 否则不会调用此方法
        Log.i(TAG, "art" + artical.getTitle());
        addItem(artical.getTitle());
    }

    private void addItem(String title) {
        View v = LayoutInflater.from(this).inflate(R.layout.item_vertical, timeLayout, false);
        ((TextView) v.findViewById(R.id.tx_action)).setText(title);
        ((TextView) v.findViewById(R.id.tx_action_time)).setText("2016-01-21");
        ((TextView) v.findViewById(R.id.tx_action_status)).setText("finish");
        timeLayout.addView(v);
        i++;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
