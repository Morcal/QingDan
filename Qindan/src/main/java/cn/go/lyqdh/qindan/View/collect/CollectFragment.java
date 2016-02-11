package cn.go.lyqdh.qindan.View.collect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.go.lyqdh.qindan.R;
import cn.go.lyqdh.qindan.model.Article;
import cn.go.lyqdh.qindan.model.User;
import cn.go.lyqdh.qindan.util.ViewUtils;
import cn.go.lyqdh.qindan.weight.UnderLineLinearLayout;

/**
 * Created by lyqdhgo on 2016/2/11.
 */
public class CollectFragment extends Fragment {

    @Bind(R.id.underline_layout)
    UnderLineLinearLayout timeLayout;

    private int i = 0;
    private List<Article> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        ButterKnife.bind(CollectFragment.this, view);
        initData();
        return view;
    }

    //Bmob获取收藏数据
    private void initData() {
        BmobQuery<User> query = new BmobQuery<User>();
        User user = User.getCurrentUser(getActivity(), User.class);
        query.getObject(getActivity(), user.getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                ViewUtils.showToastShort(getActivity(), "收藏记录查询成功");
                list = user.getClollects();
                for (int i = 0; i < list.size(); i++) {
                    addItem(list.get(i));
                }
            }

            @Override
            public void onFailure(int i, String s) {
                ViewUtils.showToastShort(getActivity(), "收藏记录查询失败");
            }
        });
    }

    //创建收藏节点
    private View addItem(Article article) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_vertical, timeLayout, false);
        ((TextView) v.findViewById(R.id.tx_action)).setText(article.getTitle());
        ((TextView) v.findViewById(R.id.tx_action_time)).setText(article.getPublishTime());
        ((TextView) v.findViewById(R.id.tx_action_status)).setText("finish");
        timeLayout.addView(v);
        i++;
        return v;
    }
}
