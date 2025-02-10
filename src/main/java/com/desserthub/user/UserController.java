package com.desserthub.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desserthub.board.BoardService;
import com.desserthub.dlike.DlikeService;
import com.desserthub.gallery.GalleryService;
import com.desserthub.reply.ReplyService;


// ログイン、会員登録関連のコントローラー
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final DlikeService dlikeService;
    private final BoardService boardService;
    private final GalleryService galleryService;
    private final ReplyService replyService;

    public UserController(UserService userService, DlikeService dlikeService, BoardService boardService, GalleryService galleryService, ReplyService replyService) {
        this.userService = userService;
        this.dlikeService = dlikeService;
        this.boardService = boardService;
        this.galleryService = galleryService;
        this.replyService = replyService;
    }

    @GetMapping("/login")
    public String request_login(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    @GetMapping("/find")
    public String request_find(Model model) {
        model.addAttribute("user", new User());
        return "user/find";
    }

    //ログインフォームの処理
    @PostMapping("/login-check")
    public String login_handler(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        if(userService.login_check(user)) {
            User tuser = userService.getUser(user.getUserId()).orElseThrow(null);
            session.setAttribute("userId", tuser.getId());
            session.setAttribute("userNn", tuser.getUserNn());
            redirectAttributes.addFlashAttribute("message", "ようこそ、" + (String)tuser.getUserNn() + "さん！");
            redirectAttributes.addFlashAttribute("target", "/home");
            return "redirect:/remessage";
        } else {
            // ログインに失敗した場合login.htmlへリダイレクト
            redirectAttributes.addFlashAttribute("message", "IDまたはパスワードが一致しませんでした。");
            redirectAttributes.addFlashAttribute("target", "/user/login");
            return "redirect:/remessage";
        }
    }

    // 使用可能なIDかを確認
    @GetMapping("/check-username")
    @ResponseBody
    public Map<String, Object> checkUsername(@RequestParam String username) {
        Map<String, Object> response = new HashMap<>();
        
        // IDが既に存在するかを確認
        boolean exists = userService.id_check(username);
        response.put("exists", exists);  // exists: true/false
        
        return response;  // JSON形式で返す
    }

    // 会員登録フォームの処理
    @PostMapping("/register")
    public String register_handler(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        if(userService.register_check(user)) {
            User tuser = userService.getUser(user.getUserId()).orElseThrow(null);
            session.setAttribute("userId", tuser.getId());
            redirectAttributes.addFlashAttribute("message", "会員登録に成功しました。");
            redirectAttributes.addFlashAttribute("target", "/home");
            return "redirect:/remessage";
        } else {
            // 会員登録に失敗した場合login.htmlへリダイレクト
            redirectAttributes.addFlashAttribute("message", "会員登録中にエラーが発生しました。");
            redirectAttributes.addFlashAttribute("target", "/user/login");
            return "redirect:/remessage";
        }
    }

    // ログアウト処理及びセッションの削除
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // セッションからユーザーの情報を削除
        session.invalidate();  // 全体のセッションを無効化しログアウトの処理を行う
        
        redirectAttributes.addFlashAttribute("message", "ログアウトしました。");
        redirectAttributes.addFlashAttribute("target", "/home");
        return "redirect:/remessage";
    }
    
    // ID検を探す
    @PostMapping("/find-id")
    @ResponseBody
    public Map<String, Object> findId(@RequestParam String userNn, @RequestParam String userEm) {
        Map<String, Object> response = new HashMap<>();
        
        // 入力したニックネームとメールアドレスでユーザー検索
        User user = userService.find_id(userNn, userEm);
        
        if (user != null) {
            // 一致するユーザーがある場合IDを返却
            response.put("success", true);
            response.put("userId", user.getUserId());
        } else {
            // 一致するユーザーが存在しない場合、失敗メッセージを返却
            response.put("success", false);
        }

        return response;  // JSON形式で返す
    }

    //パスワードを探す
    @PostMapping("/find-pw")
    @ResponseBody
    public Map<String, Object> findPassword(@RequestParam String userId, @RequestParam String userEm) {
        Map<String, Object> response = new HashMap<>();
        
        // IDとメールアドレスでユーザー検索
        User user = userService.find_pw(userId, userEm);
        
        if (user != null) {
            // 一致するユーザーがある場合、パスワードを返却
            response.put("success", true);
            response.put("userPw", user.getUserPw());
        } else {
            // 一致するユーザーが存在しない場合、失敗メッセージを返却
            response.put("success", false);
        }

        return response;  // JSON形式で返す
    }
    

    @GetMapping("/profile")
    public String request_get_user_profile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        
        //ユーザーのデータのDBからロードし、modelを通じてビューに転送
        Long uid = (Long)session.getAttribute("userId");
        User user = null;

        if(uid == null) {
            // 警告後homeにリダイレクト
            redirectAttributes.addFlashAttribute("message", "不正なアクセスです。");
            redirectAttributes.addFlashAttribute("target", "/home");
            return "redirect:/remessage";
        } else {
            user = userService.getUser(uid).orElseThrow(null);
            model.addAttribute("user", user);
            return "user/profile";
        }
    }

    @GetMapping("/profile/edit-image")
    public String request_profile_image_edit(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Long uid = (Long)session.getAttribute("userId");
        User user = null;

        if(uid == null) {
            // 警告後homeにリダイレクト
            redirectAttributes.addFlashAttribute("message", "不正なアクセスです。");
            redirectAttributes.addFlashAttribute("target", "/home");
            return "redirect:/remessage";
        } else {
            user = userService.getUser(uid).orElseThrow(null);
            model.addAttribute("user", user);
            return "user/profile/profile-edit-image";
        }
    }

    @GetMapping("/profile/edit")
    public String request_profile_edit(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Long uid = (Long)session.getAttribute("userId");
        User user = null;

        if(uid == null) {
            // 警告後homeにリダイレクト
            redirectAttributes.addFlashAttribute("message", "不正なアクセスです。");
            redirectAttributes.addFlashAttribute("target", "/home");
            return "redirect:/remessage";
        } else {
            user = userService.getUser(uid).orElseThrow(null);
            model.addAttribute("user", user);
            return "user/profile/profile-edit";
        }

        
    }

    @GetMapping("/profile/favorites-list")
    public String request_fav(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Long uid = (Long)session.getAttribute("userId");

        if(uid == null) {
            // 警告後homeにリダイレクト
            redirectAttributes.addFlashAttribute("message", "不正なアクセスです。");
            redirectAttributes.addFlashAttribute("target", "/home");
            return "redirect:/remessage";
        } else {
            model.addAttribute("likeListBoard", dlikeService.getUserLikes(uid, "board"));
            model.addAttribute("likeListGallery", dlikeService.getUserLikes(uid, "gallery"));
    
            return "user/profile/favorites-list";
        }
    }
    

    @GetMapping("/profile/manage-content")
    public String request_manage_content(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Long uid = (Long)session.getAttribute("userId");

        User user = userService.getUser(uid).orElseThrow(null);
        
        if(user == null) {
            // 警告後homeにリダイレクト
            redirectAttributes.addFlashAttribute("message", "不正なアクセスです。");
            redirectAttributes.addFlashAttribute("target", "/home");
            return "redirect:/remessage";
        } else {
            model.addAttribute("boardList", boardService.getUserBoard(uid));
            model.addAttribute("galleryList", galleryService.getUserGallery(uid));
            model.addAttribute("replyList", replyService.getUserReply(uid));
            model.addAttribute("user", user);
            return "user/profile/manage-content";
        }
    }

    @GetMapping("/withdraw")
    public String request_withdraw(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = userService.getUser((Long)session.getAttribute("userId")).orElseThrow(null);
        
        if(user == null) {
            // 警告後homeにリダイレクト
            redirectAttributes.addFlashAttribute("message", "不正なアクセスです。");
            redirectAttributes.addFlashAttribute("target", "/home");
            return "redirect:/remessage";
        } else {
            model.addAttribute("user", user);
            return "user/profile/delete-account";
        }
    }
    

    @PostMapping("update")
    public String profile_update_handler(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        Long uid = (Long)session.getAttribute("userId");

        if(userService.updateUser(uid, user)) {
            redirectAttributes.addFlashAttribute("message", "編集しました。");
            redirectAttributes.addFlashAttribute("target", "/user/profile");
            return "redirect:/remessage";
        } else {
            redirectAttributes.addFlashAttribute("message", "編集中エラーが発生しました。");
            redirectAttributes.addFlashAttribute("target", "/user/profile");
            return "redirect:/remessage";
        }
    }

    @PostMapping("update/image")
    public String profile_image_update_handler(@ModelAttribute User user, RedirectAttributes redirectAttributes, HttpSession session) {

        if(userService.updateUserProfileImage((Long)session.getAttribute("userId"), user.getUserPi())) {
            redirectAttributes.addFlashAttribute("message", "編集しました。");
            redirectAttributes.addFlashAttribute("target", "/user/profile");
            return "redirect:/remessage";
        } else {
            redirectAttributes.addFlashAttribute("message", "編集中エラーが発生しました。");
            redirectAttributes.addFlashAttribute("target", "/user/profile");
            return "redirect:/remessage";
        }
    }
    

    // 회원 탈퇴 처리 및 세션 삭제
    @GetMapping("/delete")
    public String delet_user(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
        User tuser = userService.getUser(user.getUserId()).orElseThrow(null);

        if(tuser != null) {
            userService.deleteUser(user.getUserId(), user.getUserPw());
            session.invalidate();  // 전체 세션을 무효화하여 로그아웃 처리

            redirectAttributes.addFlashAttribute("message", "회원 탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.");
            redirectAttributes.addFlashAttribute("target", "/home");
            return "redirect:/remessage";
        } else {
            redirectAttributes.addFlashAttribute("message", "아이디나 비밀번호가 일치하지 않습니다.");
            redirectAttributes.addFlashAttribute("target", "/user/withdraw");
            return "redirect:/remessage";
        }
    }
}
