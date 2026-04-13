package com.denison.shops.controller.api;

import com.denison.shops.domain.PageResponse;
import com.denison.shops.domain.board.Blog;
import com.denison.shops.dto.api.*;
import com.denison.shops.service.board.BoardService;
import com.denison.shops.service.board.KeywordService;
import com.denison.shops.service.board.MainBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardApiController {

    private final KeywordService keywordService;
    private final MainBoardService mainBoardService;
    private final BoardService boardService;

    // 성공 응답 생성 메서드
    private <T> ResponseEntity<ApiResponse<T>> buildSuccessResponse(T data, String message) {
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .status("success")
                        .message(message)
                        .data(data)
                        .build()
        );
    }

    // 실패 응답 생성 메서드
    private <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(ApiResponse.<T>builder()
                        .status("error")
                        .message(message)
                        .data(null)
                        .build());
    }

    // NOT FOUND 실패 응답 생성 메서드
    private <T> ResponseEntity<ApiResponse<T>> buildNotFoundResponse(String message) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, message);
    }

    @GetMapping("/mainkeyword")
    public Map<String, Object> getMainKeywords() {
        // Service 호출 → 결과 받기
        List<Map<String, Object>> keywords = keywordService.getMainKeywords();

        // 바로 JSON으로 변환
        return Map.of(
                "success", true,
                "message", "조회 성공",
                "data", keywords,
                "count", keywords.size()
        );
    }

    @GetMapping("/main_notice")
    public Map<String, Object> getMainNotices(){
        List<Map<String, Object>> notices = mainBoardService.getMainNotices();
        return Map.of(
                "success", true,
                "message", "조회 성공",
                "data", notices,
                "count", notices.size()
        );
    }

    @GetMapping("/main_goodreview")
    public Map<String, Object> getMainGoodReview(){
        List<Map<String, Object>> goodreviews = mainBoardService.getMainGoodReviews();
        return Map.of(
                "success", true,
                "message", "조회 성공",
                "data", goodreviews,
                "count", goodreviews.size()
        );
    }

    @GetMapping("/main_faq")
    public Map<String, Object> getMainFaq(){
        List<Map<String, Object>> goodreviews = mainBoardService.getMainFaqs();
        return Map.of(
                "success", true,
                "message", "조회 성공",
                "data", goodreviews,
                "count", goodreviews.size()
        );
    }

    @GetMapping("/faq")
    public ResponseEntity<PageResponse<FaqDto>> getProduct(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "division", required = false, defaultValue = "prod") String division,
            @RequestParam(defaultValue = "id,DESC") String sort) {
        // 정렬 설정 파싱
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        log.info("FAQ 정렬 필드: {}, 방향: {}, 키워드: {}, 타이틀: {}, division {}", sortField, direction,  search, title, division);

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page -1, size, Sort.by(direction, sortField));
        // 서비스 호출

        Page<FaqDto> result = boardService.getFaqLists(pageable, search, title, division);
        PageResponse<FaqDto> response = PageResponse.of(result);
        // PageDto로 변환하여 반환
        return ResponseEntity.ok(response);
    }
    @GetMapping("/goodreview")
    public ResponseEntity<PageResponse<ReviewDto>> getGoodPrevs(
            @RequestParam(defaultValue =  "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "no,DESC") String sort
    ) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page -1, size, Sort.by(direction, sortField));
        Page<ReviewDto> result = boardService.getReviewLists(pageable);
        PageResponse<ReviewDto> response = PageResponse.of(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/review")
    public ResponseEntity<PageResponse<ReviewDto>> getReviews(
            @RequestParam(defaultValue =  "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "no,DESC") String sort
    ) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page -1, size, Sort.by(direction, sortField));
        Page<ReviewDto> result = boardService.getReviewLists(pageable);
        PageResponse<ReviewDto> response = PageResponse.of(result);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/notice")
    public ResponseEntity<PageResponse<NoticeDto>> getNotices(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(required = false) Boolean onlyActive,
            @RequestParam(defaultValue = "no,DESC") String sort) {
        // 정렬 설정 파싱
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        log.info("Notice 정렬 필드: {}, 방향: {}, 추가: {}, 키워드: {}, 타이틀: {}", sortField, direction, onlyActive, search, title);

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page -1, size, Sort.by(direction, sortField));
        log.info("[pageable] 값 : {}", pageable);
        // 서비스 호출
        Page<NoticeDto> result = boardService.getNoticeLists(pageable, search, onlyActive, title);
        PageResponse<NoticeDto> response = PageResponse.of(result);
        // PageDto로 변환하여 반환
        return ResponseEntity.ok(response);
    }
    @GetMapping("/cartoon")
    public ResponseEntity<PageResponse<CartoonDto>> getCartoons(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,DESC") String sort
    ) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        log.info("Cartoon 정렬 필드: {}, 방향: {}", sortField, direction);
        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page -1, size, Sort.by(direction, sortField));
        // 서비스 호출
        Page<CartoonDto> result = boardService.getCartoonLists(pageable, "cartoon");
        PageResponse<CartoonDto> response = PageResponse.of(result);
        // PageDto로 변환하여 반환
        return ResponseEntity.ok(response);
    }

    @GetMapping("/goodreview/{code}")
    public ResponseEntity<PageResponse<GoodReviewDto>> productReviews(
            @RequestParam(defaultValue =  "1") int page,
            @RequestParam(defaultValue = "3") int size,
            @PathVariable("code") String code,
            @RequestParam(defaultValue = "no,DESC") String sort,
            @RequestParam(value = "division", required = false, defaultValue = "ssprev") String division
    ) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        // 일부 개선 처리
        if ("wisefood_licoverS".equals("code")) {
            code = "wisefood_licover01";
        }
        Pageable pageable = PageRequest.of(page -1, size, Sort.by(direction, sortField));
        Page<GoodReviewDto> result = boardService.productReviewList(pageable, division, code);
        PageResponse<GoodReviewDto> response = PageResponse.of(result);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/prodreview")
    public ResponseEntity<PageResponse<GoodReviewDto>> productReviews(
            @RequestParam(defaultValue =  "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "no,DESC") String sort
    ) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page -1, size, Sort.by(direction, sortField));
        Page<GoodReviewDto> result = boardService.productReviewList(pageable, "ssprev", "");
        PageResponse<GoodReviewDto> response = PageResponse.of(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/blog")
    public ResponseEntity<PageResponse<BlogDto>> getBlogs(
           @RequestParam(defaultValue = "1") int page,
           @RequestParam(defaultValue = "10") int size,
           @RequestParam(defaultValue = "no,DESC") String sort) {
        // 정렬 설정 파싱
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        log.info("정렬 필드: {}, 방향: {}", sortField, direction);

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page -1, size, Sort.by(direction, sortField));
        // 서비스 호출
        Page<BlogDto> result = boardService.getBlogLists(pageable);
        PageResponse<BlogDto> response = PageResponse.of(result);
        // PageDto로 변환하여 반환
        return ResponseEntity.ok(response);
    }
    @GetMapping("/event")
    public ResponseEntity<PageResponse<EventDto>> getEvents(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean onlyActive,
            @RequestParam(defaultValue = "id,DESC") String sort) {


        // 정렬 설정 파싱
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        log.info("정렬 필드: {}, 방향: {}", sortField, direction);

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page -1, size, Sort.by(direction, sortField));
        // 서비스 호출
        Page<EventDto> result = boardService.getEventsWithFiles(pageable, search, onlyActive);
        PageResponse<EventDto> response = PageResponse.of(result);
        // PageDto로 변환하여 반환
        return ResponseEntity.ok(response);

    }
    @PostMapping("/cartoon/{no}/hit")
    public ResponseEntity<Void> cartoonHit(@PathVariable Long no) {
        boardService.incrementHit(no, "cartoon");
        return ResponseEntity.ok().build();
    }
    @GetMapping("/cartoon/view")
    public ResponseEntity<ApiResponse<CartoonDto>> viewCartoon(@RequestParam("no") Long no) {
        try {
            CartoonDto cartoonDto = boardService.getCartoonDetail(no);
            ApiResponse<CartoonDto> response = ApiResponse.<CartoonDto>builder()
                    .status("success")
                    .message("카툰정보를 조회했습니다.")
                    .data(cartoonDto)
                    .meta(null)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/review/{no}/hit")
    public ResponseEntity<Void> reviewHit(@PathVariable Long no) {
        boardService.incrementHit(no, "review");
        return ResponseEntity.ok().build();
    }
    @GetMapping("/review/view")
    public ResponseEntity<ApiResponse<ReviewDto>> viewReview(@RequestParam("no") Long no) {
        try {
            ReviewDto reviewDto = boardService.getReviewDetail(no);
            ApiResponse<ReviewDto> response = ApiResponse.<ReviewDto>builder()
                    .status("success")
                    .message("리뷰를 조회했습니다")
                    .data(reviewDto)
                    .meta(null)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/goodreview/view")
    public ResponseEntity<ApiResponse<GoodReviewDto>> viewGoodReview(@RequestParam("no") Long no) {
        try {
            GoodReviewDto goodReviewDto = boardService.getGoodReviewDetail(no);
            ApiResponse<GoodReviewDto> response = ApiResponse.<GoodReviewDto>builder()
                    .status("success")
                    .message("리뷰를 조회했습니다")
                    .data(goodReviewDto)
                    .meta(null)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/blog/view")
    public ResponseEntity<ApiResponse<BlogDto>> viewBlog(@RequestParam("no") Long no) {
        try {
            BlogDto blogDto = boardService.getBlogDetail(no);
            ApiResponse<BlogDto> response = ApiResponse.<BlogDto>builder()
                    .status("success")
                    .message("블로그 조회했습니다")
                    .data(blogDto)
                    .meta(null)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/notice/view")
    public ResponseEntity<ApiResponse<NoticeDto>> viewNotice(@RequestParam("no") Long no) {
        try {
            NoticeDto noticeDto = boardService.getNoticeDetail(no);
            ApiResponse<NoticeDto> response = ApiResponse.<NoticeDto>builder()
                    .status("success")
                    .message("공지사항 상세 정보를 조회했습니다.")
                    .data(noticeDto)
                    .meta(null)  // 추가 메타데이터 필요시 사용
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/notice/{no}/hit")
    public ResponseEntity<Void> noticeHit(@PathVariable Long no) {
        boardService.incrementHit(no, "notice");
        return ResponseEntity.ok().build();
    }
    @GetMapping("/event/view")
    public ResponseEntity<ApiResponse<EventDto>> viewEvent(@RequestParam("no") Long no) {
        try {
            EventDto eventDto = boardService.getEventDetail(no);
            // 2. 성공 응답 생성
            ApiResponse<EventDto> response = ApiResponse.<EventDto>builder()
                    .status("success")
                    .message("이벤트 상세 정보를 조회했습니다.")
                    .data(eventDto)
                    .meta(null)  // 추가 메타데이터 필요시 사용
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/qna_write")
    @ResponseBody
    public ResponseEntity<?> qnaWrite(@RequestBody QnaDto dto, HttpServletRequest request, HttpSession session) {
        try {
            String ip = getClientIP(request);
            String name = Optional.ofNullable((String) session.getAttribute("Name")).orElse("Guest");
            String userId = Optional.ofNullable((String) session.getAttribute("ID")).orElse("Guest");

            dto.setIp(ip);
            dto.setName(name);
            dto.setUserId(userId);

            boardService.qnaWrite(dto); // BoardService로 위임
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록 실패");
        }
    }
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

//    @GetMapping("/event/{no}")
//    public ResponseEntity<ApiResponse<EventDto>> getEventView(@PathVariable("no") Long no, @RequestParam(required = false) String page) {
//        log.info("📦 이벤트 게시판 번호 조회 - no : {}", no);
//    }
}