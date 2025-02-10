package com.desserthub.dlike;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.desserthub.board.BoardService;
import com.desserthub.gallery.GalleryService;
import com.desserthub.user.UserService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/like")
public class DlikeController {
    
    private final DlikeService dlikeService;
    private final UserService userService;
    private final BoardService boardService;
    private final GalleryService galleryService;
    
    public DlikeController(DlikeService dlikeService, UserService userService, BoardService boardService, GalleryService galleryService) {
        this.dlikeService = dlikeService;
        this.userService = userService;
        this.boardService = boardService;
        this.galleryService = galleryService;        
    }

    @GetMapping
    public String getAllLikes(Model model) {
        model.addAttribute("like", dlikeService.getAllLikes());
        return "like/list";
    }

    @PostMapping
    public String createLike(@ModelAttribute Dlike dlike) {
        dlikeService.createLike(dlike);
        return "redirect:/like";
    }

    @GetMapping("/board/{id}/like")
    @ResponseBody
    public void likeBoard(@PathVariable Long id, HttpSession session) {
        
        Long userId = (Long)session.getAttribute("userId");

        if(userId != null) {
            if(dlikeService.getLike(userId, id, "board") == null) {
                Dlike dlike = new Dlike();
                dlike.setUserId(userId);
                dlike.setTarget("board");
                dlike.setTargetId(id);
                dlike.setTargetTitle(boardService.getBoard(id).orElseThrow(null).getBoardTitle());
                dlike.setTargetContent(boardService.getBoard(id).orElseThrow(null).getBoardContent());
                
                boardService.updateBoardCounts(id);

                dlikeService.createLike(dlike);
            }
        }  
    }
    @GetMapping("/board/{id}/unlike")
    @ResponseBody
    public void unlikeBoard(@PathVariable Long id, HttpSession session) {
        Dlike dlike = null;

        try {
            dlike = dlikeService.getLike((Long)session.getAttribute("userId"), id, "board");
        
            boardService.updateBoardCounts(id);

            dlikeService.deleteLike(dlike.getId());
        } catch (Exception e) {

        }
    }

    @GetMapping("/gallery/{id}/like")
    @ResponseBody
    public void likeGallery(@PathVariable Long id, HttpSession session) {

        Long userId = (Long)session.getAttribute("userId");

        if(userId != null) {
            if(dlikeService.getLike(userId, id, "gallery") == null) {
                Dlike dlike = new Dlike();
                dlike.setUserId(userId);
                dlike.setTarget("gallery");
                dlike.setTargetId(id);
                dlike.setTargetTitle(galleryService.getGallery(id).orElseThrow(null).getGalleryTitle());
                dlike.setTargetContent(galleryService.getGallery(id).orElseThrow(null).getGalleryImg());
                
                galleryService.updateGalleryCount(id);

                dlikeService.createLike(dlike);
            }
        }   
    }

    @GetMapping("/gallery/{id}/unlike")
    @ResponseBody
    public void unlikeGallery(@PathVariable Long id, HttpSession session) {
        Dlike dlike = null;

        try {
            dlike = dlikeService.getLike((Long)session.getAttribute("userId"), id, "gallery");
        
            galleryService.updateGalleryCount(id);

            dlikeService.deleteLike(dlike.getId());
        } catch (Exception e) {

        }
    }

    @GetMapping("/{id}")
    public String getLike(@PathVariable Long id, Model model) {
        model.addAttribute("like", dlikeService.getLike(id).orElseThrow(null));
        return "like/detail";
    }    

    @PostMapping("/user/")
    public String postMethodName(@RequestBody String entity) {
        
        return entity;
    }

    @PostMapping("/{id}/delete")
    public void deleteLike(@PathVariable Long id) {
        dlikeService.deleteLike(id);
    }
}
