package com.desserthub.dlike;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DlikeRepository extends JpaRepository<Dlike, Long> {
    
    // targetIdでLikeを検索
    List<Dlike> findByTargetIdAndTarget(Long targetId, String target);

    // userIdとtargetIdでLikeを検索
    Dlike findByUserIdAndTargetIdAndTarget(Long userId, Long targetId, String target);

    // mypageで使用
    List<Dlike> findByUserIdAndTarget(Long userId, String target);

    void deleteAllByUserId(Long userId);

}