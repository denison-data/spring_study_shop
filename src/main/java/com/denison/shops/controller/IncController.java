package com.denison.shops.controller;

import com.denison.shops.dto.api.NoticeDto;
import com.denison.shops.service.board.BoardService;
import com.denison.shops.service.board.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class IncController {
    private final KeywordService keywordService;
    private final BoardService boardService;
    @GetMapping("/inc/company")
    public String companyInfo(Model model, @RequestParam(required = false, defaultValue = "inc/company") String page, @RequestParam(required = false) String catContent) {

        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);

        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "inc/company";
    }

    @GetMapping("/inc/map")
    public String mapView(Model model, @RequestParam(required = false, defaultValue = "inc/map") String page, @RequestParam(required = false) String catContent) {
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);

        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "inc/map";
    }


    @GetMapping("/inc/about01")
    public String about01(Model model, @RequestParam(required = false, defaultValue = "inc/about01") String page, @RequestParam(required = false) String catContent) {

        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);

        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "inc/about01";
    }

    @GetMapping("/inc/about02")
    public String about02(Model model, @RequestParam(required = false, defaultValue = "inc/about02") String page, @RequestParam(required = false) String catContent) {

        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        // 2. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);

        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "inc/about02";
    }

    @GetMapping("/inc/terms")
    public String agreeTerm() {
        return "inc/terms";
    }

    @GetMapping("/inc/prev1")
    public String agreePrev1() {
        return "inc/prev1";
    }
    @GetMapping("/inc/prev2")
    public String agreePrev2() {
        return "inc/prev2";
    }
    @GetMapping("/inc/prev3")
    public String agreePrev3() {
        return "inc/prev3";
    }
}
