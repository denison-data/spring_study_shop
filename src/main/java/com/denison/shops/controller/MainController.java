package com.denison.shops.controller;

import com.denison.shops.dto.api.NoticeDto;
import com.denison.shops.service.board.BoardService;
import com.denison.shops.service.board.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final KeywordService keywordService;
    private final BoardService boardService;
    @GetMapping("/")
    public String shopMain(Model model, @RequestParam(required = false, defaultValue = "main") String page, @RequestParam(required = false) String catContent) {
//        model.addAttribute("data", "hello!!!");
        // 1. 어떤 컨텐츠를 보여줄지 결정
        String contentTemplate = Constants.determineContentTemplate(page, catContent);

        // 2-1. 모델에 추가
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);

        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        // 3. 메인 레이아웃 반환
        return "index";
    }

    @GetMapping("/best/main")
    public String bestMain(Model model, @RequestParam(required = false, defaultValue = "best/index") String page, @RequestParam(required = false) String catContent) {
        // 1. 어떤 컨텐츠를 보여줄지 결정
        String contentTemplate = Constants.determineContentTemplate(page, catContent);

        // 2. 모델에 추가z
        model.addAttribute("contentTemplate", contentTemplate);

        // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "best/index";
    }


}
