package com.desserthub.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
    List<Board> findAllByIdNotNullOrderByIdDesc();    
    List<Board> findAllByIdNotNullOrderByBoardLikedDesc();    
    List<Board> findAllByIdNotNullOrderByIdAsc();    
    List<Board> findAllByBoardLikedNotNullOrderByIdDesc();    

    List<Board> findByBoardCategoryOrderByBoardWritedayDesc(String boardCategory);
    List<Board> findByBoardCategoryOrderByBoardLikedDesc(String boardCategory);
    List<Board> findByBoardCategoryOrderByBoardWritedayAsc(String boardCategory);


    List<Board> findByBoardContentContaining(String boardContent);

    List<Board> findByBoardTitleContaining(String boardTitle);

    List<Board> findByUserNnContaining(String userNn);

    
    List<Board> findByUserId(Long userId);

    void deleteAllByUserId(Long userId);

}
