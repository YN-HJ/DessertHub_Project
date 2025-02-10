package com.desserthub.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主キーID

    private String userId; //　ID
    private String userPw; //　パスワード
    private String userEm; //　メールアドレス
    private String userNn; //　ニックネーム
    private String userPi; //　プロフィール画像

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserEm() {
        return userEm;
    }

    public void setUserEm(String userEm) {
        this.userEm = userEm;
    }

    public String getUserNn() {
        return userNn;
    }

    public void setUserNn(String userNn) {
        this.userNn = userNn;
    }

    public String getUserPi() {
        return userPi;
    }

    public void setUserPi(String userPi) {
        this.userPi = userPi;
    }
}
