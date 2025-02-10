package com.desserthub.reply;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository commentRepository) {
        this.replyRepository = commentRepository;
    }

    public List<Reply> getAllReplys() {
        return replyRepository.findAll();
    }

    public Optional<Reply> getReply(Long id) {
        return replyRepository.findById(id);
    }

    public Reply createReply(Reply reply) {
        return replyRepository.save(reply);
    }

    public void deleteReply(Long id) {
        replyRepository.deleteById(id);
    }


    

    public List<Reply> getReplys(Long boardId) {
        return replyRepository.findByBoardId(boardId);
    }

    
    public List<Reply> getUserReply(Long uid) {
        return replyRepository.findByUserId(uid);
    }
}