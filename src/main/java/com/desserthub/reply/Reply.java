package com.desserthub.reply;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // コメントID

    private Long userId; // コメントしたユーザーのID
    private String userNn; // コメントしたユーザーのニックネーム
    private Long boardId; // コメントされた投稿のID
    private String replyBody; // コメントの内容
    private LocalDateTime replyTime; //コメント作成日時

    public Reply() {
        this.replyTime = LocalDateTime.now();
    }
    
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
    public String getUserNn() {
        return userNn;
    }
    public void setUserNn(String userNn) {
        this.userNn = userNn;
    }

    
    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
    
    
    public String getReplyBody() {
        return replyBody;
    }
    public void setReplyBody(String replyBody) {
        this.replyBody = replyBody;
    }
    public LocalDateTime getReplyTime() {
        return replyTime;
    }
    public void setReplyTime(LocalDateTime replyTime) {
        this.replyTime = replyTime;
    }

    

}