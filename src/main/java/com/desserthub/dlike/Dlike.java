package com.desserthub.dlike;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Dlike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //いいねのID

    private Long userId; //いいねした投稿のユーザーID
    private String target; //いいねした投稿のタイプ（掲示板ORギャラリー）
    private Long targetId; //いいねした投稿のID
    private String targetTitle; //いいねした投稿のタイトル
    private String targetContent; //いいねした投稿の内容

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetContent() {
        return targetContent;
    }

    public void setTargetContent(String targetContent) {
        this.targetContent = targetContent;
    }

    public String getTargetTitle() {
        return targetTitle;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
    }    

}
