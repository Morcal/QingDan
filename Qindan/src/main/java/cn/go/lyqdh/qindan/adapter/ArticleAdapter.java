package cn.go.lyqdh.qindan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.go.lyqdh.qindan.R;
import cn.go.lyqdh.qindan.model.Article;
import cn.go.lyqdh.qindan.util.UIUtil;
import cn.go.lyqdh.qindan.weight.CircleImageView;
import cn.go.lyqdh.qindan.weight.HWRatioImageView;

/**
 * Created by lyqdh on 2016/1/4.
 */
public class ArticleAdapter extends BaseAdapter {
    private Context context;
    private List<Article> articles;

    public ArticleAdapter(Context context) {
        this.context = context;
        articles = new ArrayList<>();
    }

    public void setItems(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_qindan, null);
            holder = new ViewHolder();
            holder.civAvatar = (CircleImageView) convertView.findViewById(R.id.civ_avatar);
            holder.publishName = (TextView) convertView.findViewById(R.id.tv_author_name);
            holder.authorNum = (TextView) convertView.findViewById(R.id.tv_describe);
            holder.publishData = (TextView) convertView.findViewById(R.id.tv_publish_data);
            holder.rivImage = (HWRatioImageView) convertView.findViewById(R.id.riv_image);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.authorName = (TextView) convertView.findViewById(R.id.tv_author_name1);
            holder.content = (TextView) convertView.findViewById(R.id.tx_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Article article = (Article) getItem(position);
        holder.publishName.setText(article.getPublisher().getNickname());
        holder.authorNum.setText(article.getPublisher().getCount());
        holder.publishData.setText(article.getPublishTime());

        UIUtil.setAvatar(article.getPublisher().getAvatar(), holder.civAvatar, 240, 240);
        UIUtil.setImage(article.getCover().getUrl(), holder.rivImage);

        holder.title.setText(article.getTitle());
        holder.authorName.setText(article.getPublisher().getNickname());
        holder.content.setText(article.getSummary());

        return convertView;
    }

    static class ViewHolder {
        private CircleImageView civAvatar;
        private TextView publishName;
        private TextView authorNum;
        private TextView publishData;

        private HWRatioImageView rivImage;
        private TextView title;
        private TextView authorName;
        private TextView content;
    }
}
