package com.denison.shops.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class BoardController
{
    private final KeywordService keywordService;
    private final BoardService boardService;
    @GetMapping("/cs_qna_w")
    public String csQnaWrite(Model model, HttpServletRequest request, String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus2 = true;

        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus2", leftStatus2);

        log.info("[1 모델 info = currentPage ]" + page);
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/qna_write";
    }
    @GetMapping("/cs_main")
    public String csMain(Model model, HttpServletRequest request, String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus2 = true;

        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus2", leftStatus2);

        log.info("[1 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/notice";
    }
    @GetMapping("/cs_etc_main")
    public String csEtcMain(Model model, HttpServletRequest request, String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus2 = true;

        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus2", leftStatus2);

        log.info("[4 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/etc";
    }

    @GetMapping("/cs_prod_main")
    public String csProdMain(Model model, HttpServletRequest request, String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus2 = true;

        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus2", leftStatus2);

        log.info("[3 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/prod";
    }
    @GetMapping("/ev_main")
    public String eventMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus1 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus1", leftStatus1);

        log.info("[2 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/event";
    }
    @GetMapping("/review_main")
    public String reviewMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus1 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus1", leftStatus1);

        log.info("[2 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/review";
    }
    @GetMapping("/review_v")
    public String reviewViewMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus1 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus1", leftStatus1);

        log.info("[2 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/review_view";
    }
    @GetMapping("/winner_main")
    public String winnerMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus1 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus1", leftStatus1);

        log.info("[2 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/winner";
    }

    @GetMapping("/winner_v")
    public String winnerViewMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus1 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus1", leftStatus1);

        log.info("[2 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/winner_view";
    }

    @GetMapping("/ev_v")
    public String eventView(Model model, HttpServletRequest request, @RequestParam("no") Integer no, String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean  leftStatus1 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus1", leftStatus1);

        log.info("[event 모델 info = currentPage ]" + page);

        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/event_view";

    }

    @GetMapping("/cs_v")
    public String csNoticeView(Model model, HttpServletRequest request, @RequestParam("no") Integer no,  @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean  leftStatus2 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus2", leftStatus2);
        log.info("📦 notice 조회 detail 호출  {}", page);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/notice_view";
    }
    @GetMapping("/faq_main")
    public String faqMain(Model model, @RequestParam(required = false, defaultValue = "faq/main") String page, @RequestParam(required = false) String catContent) {

        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        log.info("📦 faq 조회 detail 호출  {}");
        return "board/faq";
    }

    @GetMapping("/club_main")
    public String clubMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus3 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus3", leftStatus3);

        log.info("[2 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/club";
    }

    @GetMapping("/blog_main")
    public String blogMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus3 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus3", leftStatus3);

        log.info("[2 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/blog";
    }
    @GetMapping("/blog_v")
    public String blog_view(Model model, HttpServletRequest request, @RequestParam("no") Integer no, @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus3 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus3", leftStatus3);

        log.info("[2 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);


        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/blog_view";
    }

    @GetMapping("/rev_main")
    public String revMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus3 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus3", leftStatus3);

        log.info("[2 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/exper";
    }

    @GetMapping("/exper_v")
    public String reViewMain(Model model, HttpServletRequest request, @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus3 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus3", leftStatus3);

        log.info("[2 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/exper_view";
    }

    @GetMapping("/cartoon_v")
    public String cartoon_view(Model model, HttpServletRequest request, @RequestParam("no") Integer no, @RequestParam(required = false) String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus3 = true;
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus3", leftStatus3);

        log.info("[2 모델 info = currentPage ]" + page);
        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);


        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "board/club_view";
    }
}
