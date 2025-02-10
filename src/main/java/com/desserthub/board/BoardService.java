package com.desserthub.board;

import org.springframework.stereotype.Service;

import com.desserthub.dlike.Dlike;
import com.desserthub.dlike.DlikeRepository;
import com.desserthub.reply.Reply;
import com.desserthub.reply.ReplyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final DlikeRepository dlikeRepository;

    public BoardService(BoardRepository boardRepository, ReplyRepository replyRepository, DlikeRepository dlikeRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
        this.dlikeRepository = dlikeRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public List<Board> getAllBoardsDesc() {
        return boardRepository.findAllByIdNotNullOrderByIdDesc();
    }

    public List<Board> getAllBoardsPopular() {
        return boardRepository.findAllByBoardLikedNotNullOrderByIdDesc();
    }

    public List<Board> getBoards(String category, String order) {
        if(category.equals("all")) {
            if(order.equals("latest")) {
                return boardRepository.findAllByIdNotNullOrderByIdDesc();
            } else if(order.equals("popular")) {
                return boardRepository.findAllByIdNotNullOrderByBoardLikedDesc();
            } else if(order.equals("oldest")) {
                return boardRepository.findAllByIdNotNullOrderByIdAsc();
            } else {
                return null;
            }
        } else {
            if(order.equals("latest")) {
                return boardRepository.findByBoardCategoryOrderByBoardLikedDesc(category);
            } else if(order.equals("popular")) {
                return boardRepository.findByBoardCategoryOrderByBoardLikedDesc(category);
            } else if(order.equals("oldest")) {
                return boardRepository.findByBoardCategoryOrderByBoardWritedayAsc(category);
            } else {
                return null;
            }
        }
    }

    
    public List<Board> searchBoards(String search, String stext) {
        if(search.equals("title")) {
            return boardRepository.findByBoardTitleContaining(stext);
        } else if(search.equals("content")) {
            return boardRepository.findByBoardContentContaining(stext);
        } else if(search.equals("nick")) {
            return boardRepository.findByUserNnContaining(stext);
        } else {
            return null;
        }
    }


    public Optional<Board> getBoard(Long id) {
        return boardRepository.findById(id);
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Board updateBoard(Long id, Board boardDetails) {
        Board board = boardRepository.findById(id).orElseThrow(null);
        board.setBoardTitle(boardDetails.getBoardTitle());
        board.setBoardContent(boardDetails.getBoardContent());
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public Board increaseView(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(null);

        board.setBoardView(board.getBoardView() + 1);
        return boardRepository.save(board);
    }

    
    public List<Board> getUserBoard(Long uid) {
        return boardRepository.findByUserId(uid);
    }

    public void updateBoardCounts(Long bid) {
        List<Reply> replyList =  replyRepository.findByBoardId(bid);
        List<Dlike> likeList = dlikeRepository.findByTargetIdAndTarget(bid, "board");

        Board board = boardRepository.findById(bid).orElseThrow(null);
        board.setBoardComment(replyList.size());
        board.setBoardLiked(likeList.size());
        boardRepository.save(board);
    }
}
