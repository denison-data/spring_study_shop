package com.denison.shops.controller;

import com.denison.shops.domain.member.Member;
import com.denison.shops.dto.api.MemberDto;
import com.denison.shops.dto.api.NoticeDto;
import com.denison.shops.service.board.BoardService;
import com.denison.shops.service.board.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor

public class MemberController {
    private final KeywordService keywordService;
    private final BoardService boardService;
    @GetMapping("/login")
    public String loginMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent) {
        // 1. 어떤 컨텐츠를 보여줄지 결정
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);

        // 2. 모델에 추가z
        model.addAttribute("contentTemplate", contentTemplate);

        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "member/login";
    }
    @GetMapping("/member/register")
    public String registerMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent) {
        // 1. 어떤 컨텐츠를 보여줄지 결정
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);

        // 2. 모델에 추가z
        model.addAttribute("contentTemplate", contentTemplate);

        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "member/register";
    }

    @PostMapping("/member/register")
    public String submitRegisterForm(@RequestParam Map<String,String> params,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        if (!params.containsKey("agreeTerms") ||
                !params.containsKey("agreePrivacy") ||
                !params.containsKey("agreePrivacy2") ||
                !params.containsKey("agreePrivacy3")) {
            redirectAttributes.addFlashAttribute("error", "모든 약관에 동의해야 합니다.");
            return "redirect:/member/register";
        }

        // 동의 완료 플래그 세션에 저장
        session.setAttribute("registerAgreed", true);

        return "redirect:/member/join";
    }

    @GetMapping("/member/orders")
    public String orderMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent)
    {
        // 1. 어떤 컨텐츠를 보여줄지 결정
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);

        // 2. 모델에 추가z
        model.addAttribute("contentTemplate", contentTemplate);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);

        return "member/order";
    }

    @GetMapping("/member/join")
    public String showJoinForm(Model model, HttpServletRequest request, HttpSession session, @RequestParam(required = false) String catContent)
    {
        Boolean agreed = (Boolean) session.getAttribute("registerAgreed");
        if (agreed == null || !agreed) {
            // 동의하지 않고 직접 URL로 접근한 경우 → register로 돌려보냄
            return "redirect:/member/register";
        }

        // 1. 어떤 컨텐츠를 보여줄지 결정
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);

        // 2. 모델에 추가z
        model.addAttribute("contentTemplate", contentTemplate);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);

        return "member/join";
    }

    @PostMapping("/member/join")
    public String submitJoinForm(@ModelAttribute MemberDto memberDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "member/join"; // 입력 오류 시 다시 폼 표시
        }

        // DB 저장은 하지 않고 값만 넘김
        redirectAttributes.addFlashAttribute("memberDto", memberDto);

        return "redirect:/member/joinSuccess"; // 3단계로 이동
    }



    @GetMapping("/member/modify")
    public String memberModify(Model model, HttpServletRequest request, String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);

        // 2. 모델에 추가z
        model.addAttribute("contentTemplate", contentTemplate);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        return "member/modify";
    }
}
