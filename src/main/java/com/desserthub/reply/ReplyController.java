package com.desserthub.reply;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//rest apiで内容のみロードするためrestControllerを使用
@RestController
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping
    public String getAllReplys(Model model) {
        model.addAttribute("replyList", replyService.getAllReplys());
        return "reply/list";
    }

    @GetMapping("/new/{id}")
    public void createReplyForm(Model model) {
        model.addAttribute("reply", new Reply());
    }

    @PostMapping
    public String createReply(@ModelAttribute Reply Reply) {
        replyService.createReply(Reply);
        return "redirect:/Reply";
    }

    @GetMapping("/{id}")
    public String getReply(@PathVariable Long id, Model model) {
        model.addAttribute("Reply", replyService.getReply(id).orElseThrow(null));
        return "reply/detail";
    }

    @PostMapping("/{id}/delete")
    public String deleteReply(@PathVariable Long id) {
        replyService.deleteReply(id);
        return "redirect:/Reply";
    }

    
}
