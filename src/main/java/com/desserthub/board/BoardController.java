package com.desserthub.board;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desserthub.dlike.DlikeService;
import com.desserthub.reply.Reply;
import com.desserthub.reply.ReplyService;
import com.desserthub.user.User;
import com.desserthub.user.UserService;


@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;
    private final DlikeService dlikeService;
    private final ReplyService replyService;
    

    public BoardController(BoardService boardService, UserService userService, DlikeService dlikeService, ReplyService replyService) {
        this.boardService = boardService;
        this.userService = userService;
        this.dlikeService = dlikeService;
        this.replyService = replyService;
    }

    @GetMapping
    public String getAllBoards(Model model) {
        model.addAttribute("selected1", "all");
        model.addAttribute("selected2", "latest");
        model.addAttribute("board", boardService.getAllBoardsDesc());
        model.addAttribute("userList", userService.getAllUsers());
        return "board/list";
    }

    @GetMapping("/order")
    public String getAllBoards(@RequestParam(name = "cate", defaultValue = "all") String category, @RequestParam(name = "ord", defaultValue = "latest") String order, Model model) {
        
        model.addAttribute("selected1", category);
        model.addAttribute("selected2", order);
        model.addAttribute("board", boardService.getBoards(category, order));

        return "board/list";
    }

    @GetMapping("/search")
    public String getAllBoardsBySearch(@RequestParam(name = "search", defaultValue = "title") String search, @RequestParam(name = "stext", defaultValue = "") String stext, Model model) {
        model.addAttribute("board", boardService.searchBoards(search, stext));
        return "board/list";
    }

    @GetMapping("/new")
    public String createBoardForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        if(session.getAttribute("userId") != null) {
            model.addAttribute("board", new Board());
            return "board/new";
        } else {
            // 警告後ホーム画面にリダイレクト
            redirectAttributes.addFlashAttribute("message", "投稿にはログインが必要です。");
            redirectAttributes.addFlashAttribute("target", "/user/login");
            return "redirect:/remessage";
        }
    }

    @PostMapping("/upload")
    public String createBoard(@ModelAttribute Board board, HttpSession session, RedirectAttributes redirectAttributes) {
        Long userId = (Long)session.getAttribute("userId");
        User user = userService.getUser(userId).orElseThrow(null);

        board.setUserId(userId);
        board.setUserNn(user.getUserNn());
        boardService.createBoard(board);

        redirectAttributes.addFlashAttribute("message", "投稿しました。");
        redirectAttributes.addFlashAttribute("target", "/board");
        return "redirect:/remessage";
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable Long id, Model model, HttpSession session) {
        boardService.increaseView(id);//閲覧数の増加

        model.addAttribute("board", boardService.getBoard(id).orElseThrow(null));
        model.addAttribute("isLike", dlikeService.getLike(id, "board"));
        model.addAttribute("replyList", replyService.getReplys(id));
        model.addAttribute("reply", new Reply());

        return "board/detail";
    }

    @GetMapping("/{id}/edit")
    public String editBoardForm(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Board board = boardService.getBoard(id).orElseThrow(null);

        if(board.getUserId().equals((Long)session.getAttribute("userId"))) {
            model.addAttribute("board", boardService.getBoard(id).orElseThrow(null));
            return "board/edit";
        } else {
            // 警告後ホーム画面にリダイレクト
            redirectAttributes.addFlashAttribute("message", "投稿の編集は投稿者のみ可能です。");
            redirectAttributes.addFlashAttribute("target", "/board");
            return "redirect:/remessage";
        }

    }

    @PostMapping("/{id}/edit")
    public String updateBoard(@PathVariable Long id, HttpSession session, @ModelAttribute Board board, RedirectAttributes redirectAttributes) {

        boardService.updateBoard(id, board);
        
        redirectAttributes.addFlashAttribute("message", "編集しました。");
        redirectAttributes.addFlashAttribute("target", "/board");
        return "redirect:/remessage";
    }

    @PostMapping("/{id}/delete")
    public String deleteBoard(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boardService.deleteBoard(id);
        
        redirectAttributes.addFlashAttribute("message", "削除しました。");
        redirectAttributes.addFlashAttribute("target", "/board");
        return "redirect:/remessage";
    }

    @PostMapping("/{id}/udelete")
    public String deleteBoardInUserPage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boardService.deleteBoard(id);
        
        redirectAttributes.addFlashAttribute("message", "削除しました。");
        redirectAttributes.addFlashAttribute("target", "/user/profile/manage-content");
        return "redirect:/remessage";
    }

    @PostMapping("/{id}/reply")
    public String addReply(@PathVariable Long id, @ModelAttribute Reply reply, HttpSession session, RedirectAttributes redirectAttributes) {

        Long uid = (Long)session.getAttribute("userId");
        Reply nr = new Reply();

        if(uid != null) {
            User user = userService.getUser(uid).orElseThrow(null);
            
            nr.setUserId(uid);
            nr.setUserNn(user.getUserNn());
            nr.setBoardId(id);
            nr.setReplyBody(reply.getReplyBody());
    
            replyService.createReply(nr);
            boardService.updateBoardCounts(id);
                        
            redirectAttributes.addFlashAttribute("target", "board/" + id);
            return "redirect:/remessage";
        } else {
            // 警告後ホーム画面にリダイレクト
            redirectAttributes.addFlashAttribute("message", "コメント作成にはログインが必要です。");
            redirectAttributes.addFlashAttribute("target", "board/" + id);
            return "redirect:/remessage";
        }
    }

    @PostMapping("/{id}/dereply") //this id is reply's id
    public String deleteReply(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Reply reply = replyService.getReply(id).orElseThrow(null);
        Long boardId = reply.getBoardId();
        boardService.updateBoardCounts(boardId);

        replyService.deleteReply(id);
    
        redirectAttributes.addFlashAttribute("target", "board/" + boardId);
        return "redirect:/remessage";
    }

    @PostMapping("/{id}/udereply") //this id is reply's id
    public String deleteReplyInUserPage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Reply reply = replyService.getReply(id).orElseThrow(null);
        Long boardId = reply.getBoardId();
        boardService.updateBoardCounts(boardId);

        replyService.deleteReply(id);
        
        redirectAttributes.addFlashAttribute("target", "/user/profile/manage-content");
        return "redirect:/remessage";
    }
    

}