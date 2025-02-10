package com.desserthub.gallery;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Gallery {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ギャラリーのID

    private Long userId; // 投稿者のユーザーID
    private String userNn; // 投稿者のニックネーム
    private String galleryTitle; //　投稿のタイトル
    private String galleryHashtag; //　投稿に付いているハッシュタグ
    private String galleryImg; //　投稿された画像
    private int galleryLiked; //　いいね数
    private LocalDateTime galleryWriteday; //　投稿日時

    public Gallery() {
        this.galleryWriteday = LocalDateTime.now();
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
    public String getGalleryTitle() {
        return galleryTitle;
    }
    public void setGalleryTitle(String galleryTitle) {
        this.galleryTitle = galleryTitle;
    }
    public String getGalleryHashtag() {
        return galleryHashtag;
    }
    public void setGalleryHashtag(String galleryHashtag) {
        this.galleryHashtag = galleryHashtag;
    }
    public String getGalleryImg() {
        return galleryImg;
    }
    public void setGalleryImg(String galleryImg) {
        this.galleryImg = galleryImg;
    }
    public int getGalleryLiked() {
        return galleryLiked;
    }
    public void setGalleryLiked(int galleryLiked) {
        this.galleryLiked = galleryLiked;
    }
    public LocalDateTime getGalleryWriteday() {
        return galleryWriteday;
    }
    public void setGalleryWriteday(LocalDateTime galleryWriteday) {
        this.galleryWriteday = galleryWriteday;
    }

    public void set_now() {
        this.galleryWriteday = LocalDateTime.now();
    }
}