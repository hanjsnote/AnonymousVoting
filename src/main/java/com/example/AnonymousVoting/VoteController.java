package com.example.AnonymousVoting;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String submitVote(@RequestParam String choice, Model model, HttpSession session) {
        int totalVotes = voteCount.values().stream().mapToInt(Integer::intValue).sum(); //투표인원 카운트

        // 이미 투표한 사용자면 결과 페이지로 바로 이동
        if (session.getAttribute("voted") != null) {
            model.addAttribute("message", "이미 투표하셨습니다!");
            model.addAttribute("votes", voteCount);
            return "voteResult";
        }

        // 투표 로직 수행
        voteCount.put(choice, voteCount.get(choice) + 1);

        // 투표한 것으로 세션에 기록
        session.setAttribute("voted", true);

        model.addAttribute("votes", voteCount);
        return "voteResult";
    }
}
