package com.denison.shops.controller;

import com.denison.shops.dto.api.NoticeDto;
import com.denison.shops.dto.api.ProductDetailDto;
import com.denison.shops.service.ProductService;
import com.denison.shops.service.board.BoardService;
import com.denison.shops.service.board.KeywordService;
import com.denison.shops.utils.StringFormatterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/product") // ✅ 이 컨트롤러의 모든 URL은 /product로 시작
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final KeywordService keywordService;
    private final ProductService productService;
    private final BoardService boardService;
    @GetMapping("/detail")
    public String detail(
            // 필수 파라미터
        @RequestParam("code") String code,
        // 선택적 파라미터 (기본값 설정)
        @RequestParam(value = "Cat_Content", required = false, defaultValue = "Best10")
        String catContent,
        @RequestParam(value = "Types", required = false, defaultValue = "Best10")
        String types,
        // 추가 파라미터들
        HttpServletRequest request,
        @RequestParam(value = "size", required = false, defaultValue = "10")
        int size,
        @RequestParam(value = "memberGrade", required = false) Integer memberGrade,
        Model model) {
            try {
                String uri = request.getRequestURI();        // /product/detail
                String query = request.getQueryString();     // code=...&Types=Best10
                String page = uri + (query != null ? "?" + query : "");


                log.info("상품 상세 페이지 접속 - 코드: {}, 카테고리: {}, 페이지: {}, 회원등급: {}",
                        code, catContent, page, memberGrade);

                // 1. 상품 조회
                ProductDetailDto product = productService.getProductByCode(code, 1);

                if (product == null) {
                    log.warn("상품이 존재하지 않음 - 코드: {}", code);
                    model.addAttribute("error", "상품을 찾을 수 없습니다.");
                    return "product/detail"; // 에러 페이지로 이동
                }

                log.info("상품 조회 성공 - ID: {}, 이름: {}", product.getId(), product.getName());
                // 2. PHP 스타일 포맷팅 적용
                product.processDescription();

                // 1. 어떤 컨텐츠를 보여줄지 결정
                String contentTemplate = Constants.determineContentTemplate(page, catContent);
                model.addAttribute("currentPage", page);
                model.addAttribute("code", code);
                model.addAttribute("catContent", catContent);
                model.addAttribute("types", types);
                model.addAttribute("product", product);

                // 2. 모델에 추가
                model.addAttribute("contentTemplate", contentTemplate);
                model.addAttribute("currentPage", page);

                // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
                List<Map<String, Object>> keywords = keywordService.getMainKeywords();
                model.addAttribute("commonKeywords", keywords);

                Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
                Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
                model.addAttribute("commonNotices", notices);

                return "product/detail";
            } catch (Exception e) {
                    log.error("상품 상세 페이지 로딩 중 오류 발생 - 코드: {}", code, e);
                    model.addAttribute("error", "상품 정보를 불러오는 중 오류가 발생했습니다.");
                    return "error/500";
            }
     }
    @GetMapping("/cart")
    public String cartMain(Model model, HttpServletRequest request, String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);

        log.info("[1 모델 info = currentPage ]" + page);
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "product/cart";
    }

    @GetMapping("/social")
    public String socialMain(Model model, HttpServletRequest request, String catContent) {
        String page = request.getRequestURI();
        String contentTemplate = Constants.determineContentTemplate(page, catContent);
        Boolean leftStatus1 = true;

        model.addAttribute("contentTemplate", contentTemplate);
        model.addAttribute("currentPage", page);
        model.addAttribute("leftStatus1", leftStatus1);

        log.info("[1 모델 info = currentPage ]" + page);
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();
        model.addAttribute("commonKeywords", keywords);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
        Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
        model.addAttribute("commonNotices", notices);
        return "product/social";
    }

    @GetMapping("/category")
    public String categoryMain(Model model,
            @RequestParam(value = "Cat_Content", required = false, defaultValue = "Best10") String catContent,
            HttpServletRequest request,
            @RequestParam(value = "Types", required = false, defaultValue = "W_F_cate1") String types
     ) {
        try {
            String page = request.getRequestURI();
            String contentTemplate = Constants.determineContentTemplate(page, catContent);
            log.info("catContent : {}", catContent);
            model.addAttribute("catContent", catContent);
            model.addAttribute("types", types);
            var CatName = "카테고리이름";
            switch (catContent.trim())
            {
                case "W_Age" :
                    CatName = "연령별";
                    break;
                case "W_Feat" :
                    CatName = "기능별";
                    break;
                case "W_Area" :
                    CatName = "지역별";
                    break;
                case "W_Helth" :
                    CatName = "건강생활용품";
                    break;
                case "W_Skin" :
                    CatName = "스킨케어";
                    break;
                case "W_Vitamin" :
                    CatName = "비타민";
                    break;
                case "W_Hongsam" :
                    CatName = "홍삼";
                    break;
                default :
                    CatName = "카테고리미선정";
            }
            log.info("types 성공 [types] - ID: {}", types);
            log.info("catContent 성공 [catContent] - ID: {}", catContent);
            log.info("CatName 성공 [CatName] - ID: {}", CatName);
            model.addAttribute("catName", CatName);
            // 2. 모델에 추가
            model.addAttribute("contentTemplate", contentTemplate);
            model.addAttribute("currentPage", page);

            // 2-2. ✅ Service에서 키워드 가져와서 직접 추가
            List<Map<String, Object>> keywords = keywordService.getMainKeywords();
            model.addAttribute("commonKeywords", keywords);

            Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "no"));
            Page<NoticeDto> notices = boardService.getNoticeLists(pageable, null, true, null);
            model.addAttribute("commonNotices", notices);
            return "product/category";
        } catch (Exception e) {

            return "error/500";
        }
     }
    /**
     * PHP 스타일 포맷팅 처리

    private void processProductFormatting(ProductDetailDto dto) {
        if (dto == null) return;
        log.info("포맷팅 처리 시작 - 상품: {}", dto.getName());

        // description 처리
        String originalDesc = dto.getDescription();
        if (originalDesc != null) {
            log.info("원본 description 길이: {}", originalDesc.length());

            // 이미지 URL 변환
            String descWithReplacedUrl = originalDesc.replace("http://image.wisefood.co.kr/", "/");

            // PHP 스타일 포맷팅
            String formattedDesc = StringFormatterUtil.nl2brStripSlashes(descWithReplacedUrl);

            dto.setFormattedDescription(formattedDesc);
            log.info("포맷팅된 description 길이: {}", formattedDesc.length());
        } else {
            log.warn("description이 null입니다");
            dto.setFormattedDescription("");
        }
        // oemInfo 처리
        if (dto.getOemInfo() != null) {
            String oemWithReplacedUrl = dto.getOemInfo().replace("http://image.wisefood.co.kr/", "/");
            dto.setFormattedOemInfo(StringFormatterUtil.nl2brStripSlashes(oemWithReplacedUrl));
        }
        log.info("포맷팅 처리 완료");
    }*/
}
