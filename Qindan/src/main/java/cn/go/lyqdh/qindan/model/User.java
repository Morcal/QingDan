package cn.go.lyqdh.qindan.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by lyqdh on 2016/1/4.
 */
public class User extends BmobUser{
    // id
    private int id;
    // nickname
    private String nickname;
    // 头像
    private String avatar;
    // 作品数
    private String count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
