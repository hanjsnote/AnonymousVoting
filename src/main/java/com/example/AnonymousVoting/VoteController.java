package com.example.AnonymousVoting;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class VoteController {

    private Map<String, Integer> voteCount = new HashMap<>();

    public VoteController() {
        voteCount.put("찬성", 0);
        voteCount.put("반대", 0);
    }

    @GetMapping("/vote")
    public String voteForm() {
        return "voteForm";
    }


    @PostMapping("/vote")
    public String submitVote(@RequestParam String choice, HttpSession session, RedirectAttributes redirectAttributes) {

        if (session.getAttribute("voted") != null) {
            redirectAttributes.addFlashAttribute("message", "이미 투표하셨습니다!");
            return "redirect:/voteResult";
        }

        voteCount.put(choice, voteCount.get(choice) + 1);
        session.setAttribute("voted", true);

        redirectAttributes.addFlashAttribute("votes", voteCount);
        return "redirect:/voteResult";
    }

    @GetMapping("/voteResult")
    public String showResult(Model model) {
        return "voteResult";  // voteResult.html 렌더링
    }
}
