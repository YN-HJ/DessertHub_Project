package com.desserthub.gallery;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desserthub.dlike.DlikeService;
import com.desserthub.user.User;
import com.desserthub.user.UserService;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

    private final GalleryService galleryService;
    private final UserService userService;
    private final DlikeService dlikeService;

    public GalleryController(GalleryService galleryService, UserService userService, DlikeService dlikeService) {
        this.galleryService = galleryService;
        this.userService = userService;
        this.dlikeService = dlikeService;
    }

    @GetMapping
    public String getAllGallerys(Model model, HttpSession session) {
        Long uid = (Long)session.getAttribute("userId");

        if(uid != null) {
            model.addAttribute("likeListGallery", dlikeService.getUserLikes(uid, "gallery"));
        }

        model.addAttribute("galleryList", galleryService.getAllGallerys());
        return "gallery/main";
    }

    @GetMapping("/upload")
    public String createGalleryForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if(session.getAttribute("userId") != null) {
            model.addAttribute("gallery", new Gallery());
            return "gallery/upload";
        } else {
            // 警告後ホーム画面にリダイレクト
            redirectAttributes.addFlashAttribute("message", "画像のアップロードにはログインが必要です。");
            redirectAttributes.addFlashAttribute("target", "/user/login");
            return "redirect:/remessage";
        }
    }

    @PostMapping("/upload")
    public String createGallery(@ModelAttribute Gallery gallery, HttpSession session, RedirectAttributes redirectAttributes) {
        Long uid = (Long)session.getAttribute("userId");

        User user = userService.getUser(uid).orElseThrow(null);
        
        gallery.setUserId(uid);
        gallery.setUserNn(user.getUserNn());

        gallery.setGalleryLiked(0);
        gallery.set_now();


        galleryService.createGallery(gallery);

        redirectAttributes.addFlashAttribute("message", "登録しました。");
        redirectAttributes.addFlashAttribute("target", "/gallery");
        return "redirect:/remessage";
    }
    
    // 特定の画像をリクエスト（GETリクエスト）
    @GetMapping("/gview/{id}")
    public String viewImage(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        // idがマッチするGalleryオブジェクトを検索
        Gallery gallery = galleryService.getGallery(id).orElseThrow(() -> new RuntimeException("Gallery not found"));

        if (gallery != null) {
            // Galleryオブジェクトをモデルに追加
            model.addAttribute("gallery", gallery);
            model.addAttribute("isLike", dlikeService.getLike(id, "gallery"));
            return "gallery/galleryView"; // galleryView.htmlへリダイレクト
        } else {
            redirectAttributes.addFlashAttribute("message", "画像が見つかりませんでした。");
            redirectAttributes.addFlashAttribute("target", "/gallery");
            return "redirect:/remessage";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteGallery(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        galleryService.deleteGallery(id);
        
        redirectAttributes.addFlashAttribute("message", "削除しました。");
        redirectAttributes.addFlashAttribute("target", "/gallery");
        return "redirect:/remessage";
    }
    @PostMapping("/{id}/udelete")
    public String deleteGalleryInUserPage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        galleryService.deleteGallery(id);
        
        redirectAttributes.addFlashAttribute("message", "削除しました。");
        redirectAttributes.addFlashAttribute("target", "/user/profile/manage-content");
        return "redirect:/remessage";
    }
}
