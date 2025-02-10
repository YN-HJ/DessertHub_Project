package com.desserthub.dlike;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DlikeService {

    private final DlikeRepository dlikeRepository;

    public DlikeService(DlikeRepository dlikeRepository) {
        this.dlikeRepository = dlikeRepository;
    }

    public List<Dlike> getAllLikes() {
        return dlikeRepository.findAll();
    }

    public Optional<Dlike> getLike(Long id) {
        return dlikeRepository.findById(id);
    }

    public Dlike createLike(Dlike like) {
        return dlikeRepository.save(like);
    }

    public void deleteLike(Long id) {
        dlikeRepository.deleteById(id);
    }

    //for board detail
    public List<Dlike> getLike(Long targetId, String target) {
        return dlikeRepository.findByTargetIdAndTarget(targetId, target);
    }

    //for gallery
    public Dlike getLike(Long userId, Long targetId, String target) {
        return dlikeRepository.findByUserIdAndTargetIdAndTarget(userId, targetId, target);
    }

    // for mypage
    public List<Dlike> getUserLikes(Long userId, String target) {
        return dlikeRepository.findByUserIdAndTarget(userId, target);
    }

}
