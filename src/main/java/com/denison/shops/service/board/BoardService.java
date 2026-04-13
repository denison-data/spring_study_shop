package com.denison.shops.service.board;

import com.denison.shops.domain.board.*;
import com.denison.shops.domain.product.Product;
import com.denison.shops.dto.api.*;
import com.denison.shops.repository.FilelistRepository;
import com.denison.shops.repository.ProductRepository;
import com.denison.shops.repository.board.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final EventRepository eventRepository;
    private final FilelistRepository filelistRepository;
    private final NoticeRepository noticeRepository;
    private final FaqRepository faqRepository;
    private final CartoonRepository cartoonRepository;
    private final ReviewRepository reviewRepository;
    private final BlogRepository blogRepository;
    private final GoodReviewRepository goodReviewRepository;
    private final ProductRepository productRepository;
    private final QnaRepository qnaRepository;

    public void qnaWrite(QnaDto qnaDto) {
        log.info("QNA 등록 요청: {}", qnaDto);
        int wdate = (int) (System.currentTimeMillis() / 1000);

        Integer minMain = qnaRepository.findMinMainByIdx(1);
        int main = (minMain == null) ? 99999999 : minMain - 1;

        Qna qna = Qna.builder()
                .idx(1)
                .main(main)
                .sub(0)
                .depth(0)
                .name(qnaDto.getName())
                .subject(qnaDto.getSubject())
                .contents(qnaDto.getContents())
                .ip(qnaDto.getIp())
                .userId(qnaDto.getUserId())
                .code(qnaDto.getProductId())
                .wdate(wdate)
                .delFlag("n")
                .division(qnaDto.getDivision())
                .build();
        qnaRepository.save(qna);
        log.info("Qna Write : {}", qna);
    }
    public void incrementHit(Long no, String division) {
        if (division == "cartoon") {
            cartoonRepository.incrementHitJpql(no);
        } else if (division == "review") {
            reviewRepository.incrementHitJpql(no);
        } else {
            noticeRepository.incrementHitJpql(no);
        }
    }
    public EventDto getEventDetail(Long no) {
        log.info("이벤트 상세 조회 : no ={}", no);

        Optional<Event> eventOpt = eventRepository.findByNo(no);
        if(!eventOpt.isPresent()){
//            throw new RuntimeException("이벤크 번호를 찿기 어려움 "+ no);
            log.warn("이벤트를 찾을 수 없음: no={}", no);
            return null;
        }

        Event event = eventOpt.get();

        if (event.isDeleted()) {
//            throw new RuntimeException("삭제된 이벤트임");
            log.warn("삭제된 이벤트 접근: no={}", no);
            return null;  // ✅ null 반환
        }

        EventDto eventDto = convertToDto(event);

        log.info("이벤크 조회 성공" + eventDto.getSubject());
        return eventDto;
    }

    public GoodReviewDto getGoodReviewDetail(Long no) {
        Optional<GoodReview> goodreviewOpt = goodReviewRepository.findById(no);
        if (!goodreviewOpt.isPresent()) {
            log.warn("리뷰를 찾을수 없음 : no - {}", no);
            return null;
        }
        GoodReview goodreview = goodreviewOpt.get();

        GoodReviewDto goodReviewDto = GoodReviewDto.builder()
                .id(goodreview.getNo())
                .subject(goodreview.getSubject())
                .formattedDate(goodreview.getFormattedDate())
                .formattedDateTime(goodreview.getFormatDataTime())
                .orderId(maskUserId(goodreview.getOrderId()))
                .orderNo(goodreview.getOrderNo())
                .productCode(goodreview.getProductCode())
                .starPoint(goodreview.getStarPoint())
                .hit(goodreview.getHit())
                .contents(goodreview.getContents())
                .build();
        goodReviewDto.setPreviousData(findGoodPrevReview(no));
        goodReviewDto.setNextData(findGoodNextReview(no));
        return goodReviewDto;
    }

    public CartoonDto getCartoonDetail(Long no) {
        Optional<Cartoon> cartoonOpt = cartoonRepository.findById(no);
        if(!cartoonOpt.isPresent()) {
            log.warn("카툰을 찾을수 없음: no - {}", no);
            return null;
        }
        Cartoon cartoon = cartoonOpt.get();

        CartoonDto cartoonDto = convertCartoonDto(cartoon);

        cartoonDto.setPreviousData(findPrevCartoon(no));
        cartoonDto.setNextData(findNextCartoon(no));
        return cartoonDto;

    }
    public BlogDto getBlogDetail(Long no) {
        Optional<Blog> blogOpt = blogRepository.findById(no);
        if(!blogOpt.isPresent()) {
            log.warn("블로그 글 찾을수 없음 - no : {}", no);
            return null;
        }
        Blog blog = blogOpt.get();
        BlogDto blogDto = convertBlogToDto(blog);

        blogDto.setPreviousData(findBlogPrevReview(no));
        blogDto.setNextData(findBlogNextReview(no));
        return blogDto;
    }
    public ReviewDto getReviewDetail(Long no) {
        Optional<Review> reviewOpt = reviewRepository.findById(no);
        if(!reviewOpt.isPresent()) {
            log.warn("리뷰 찾을수 없음 : no - {}", no);
            return null;
        }
        Review review = reviewOpt.get();
        ReviewDto reviewDto = convertReviewDto(review);

        reviewDto.setPreviousReview(findPrevReview(no));
        reviewDto.setNextReview(findNextReview(no));
        return reviewDto;
    }
    public NoticeDto getNoticeDetail(Long no) {
        Optional<Notice> noticeOpt = noticeRepository.findById(no);
        if(!noticeOpt.isPresent()){
            log.warn("공지를 찾을 수 없음: no={}", no);
            return null;
        }
        Notice notice = noticeOpt.get();

        NoticeDto noticeDto = convertNoticeDto(notice);
        return noticeDto;
    }
    private GoodReviewDto.PreviousNextData findGoodPrevReview(Long no) {
        return goodReviewRepository.findTopByNoLessThanAndDivisionAndDelFlagOrderByNoDesc(no, "ssprev", GoodReview.DeleteFlag.N)
                .map(goodReview -> GoodReviewDto.PreviousNextData.builder()
                        .id(goodReview.getNo())
                        .subject(goodReview.getSubject())
                        .formattedDate(goodReview.getFormattedDate())
                        .build()
                ).orElse(null);
    }
    private GoodReviewDto.PreviousNextData findGoodNextReview(Long no) {
        return goodReviewRepository.findTopByNoGreaterThanAndDivisionAndDelFlagOrderByNoAsc(no, "ssprev", GoodReview.DeleteFlag.N)
                .map(goodReview -> GoodReviewDto.PreviousNextData.builder()
                        .id(goodReview.getNo())
                        .subject(goodReview.getSubject())
                        .formattedDate(goodReview.getFormattedDate())
                        .build()
                ).orElse(null);
    }
    private BlogDto.PreviousNextData findBlogPrevReview(Long no) {
        return blogRepository.findTopByNoLessThanAndDelFlagOrderByNoDesc(no,  Blog.DeleteFlag.N)
                .map(blog -> BlogDto.PreviousNextData.builder()
                        .id(blog.getNo())
                        .subject(blog.getSubject())
                        .formattedDate(blog.getFormattedDate())
                        .build()
                ).orElse(null);
    }
    private BlogDto.PreviousNextData findBlogNextReview(Long no) {
        return blogRepository.findTopByNoGreaterThanAndDelFlagOrderByNoAsc(no,  Blog.DeleteFlag.N)
                .map(blog -> BlogDto.PreviousNextData.builder()
                        .id(blog.getNo())
                        .subject(blog.getSubject())
                        .formattedDate(blog.getFormattedDate())
                        .build()
                ).orElse(null);
    }
    private CartoonDto.PreviousNextData findPrevCartoon(Long id) {
        return cartoonRepository.findTopByIdLessThanAndDelFlagOrderByIdDesc(id, Cartoon.DeleteFlag.N)
                .map(cartoon->CartoonDto.PreviousNextData.builder()
                        .id(cartoon.getId())
                        .subject(cartoon.getSubject())
                        .formattedDate(cartoon.getFormattedDate())
                        .build()
                ).orElse(null);
    }

    private CartoonDto.PreviousNextData findNextCartoon(Long id) {
        return cartoonRepository.findTopByIdGreaterThanAndDelFlagOrderByIdAsc(id, Cartoon.DeleteFlag.N)
                .map(cartoon -> CartoonDto.PreviousNextData.builder()
                        .id(cartoon.getId())
                        .subject(cartoon.getSubject())
                        .formattedDate(cartoon.getFormattedDate())
                        .build()
                ).orElse(null);
    }
    private ReviewDto.PreviousNextReview findPrevReview(Long no) {
        return reviewRepository.findTopByNoLessThanAndDelFlagOrderByNoDesc(no, Review.DeleteFlag.N)
                .map(review -> ReviewDto.PreviousNextReview.builder()
                        .id(review.getNo())
                        .title(review.getTitles())
                        .formattedDate(review.getFormattedDate())
                        .build()
                ).orElse(null);
    }
    private ReviewDto.PreviousNextReview findNextReview(Long no) {
        return reviewRepository.findTopByNoGreaterThanAndDelFlagOrderByNoAsc(no, Review.DeleteFlag.N)
                .map(review -> ReviewDto.PreviousNextReview.builder()
                        .id(review.getNo())
                        .title(review.getTitles())
                        .formattedDate(review.getFormattedDate())
                        .build()
                ).orElse(null);
    }
    private CartoonDto convertCartoonDto(Cartoon cartoon) {
        CartoonDto cartoonDto = CartoonDto.builder()
                .id(cartoon.getId())
                .subject(cartoon.getSubject())
                .contents(cartoon.getContents())
                .formattedDateTime(cartoon.getFormatDataTime())
                .formattedDate(cartoon.getFormattedDate())
                .build();
        return cartoonDto;
    }

    private ReviewDto convertReviewDto(Review review) {
        ReviewDto reviewDto = ReviewDto.builder()
                .id(review.getNo())
                .userid(maskUserId(review.getUserid()))
                .title(review.getTitles())
                .contents(review.getContents())
                .regdate(review.getRegdate())
                .hit(review.getHit())
                .prod_code(review.getProd_code())
                .goodname(review.getGoodname())
                .ipaddr(review.getIpaddr())
                .formattedDate(review.getFormattedDate())
                .formattedDateTime(review.getFormatDataTime())
                .build();
        return reviewDto;
    }
    private NoticeDto convertNoticeDto(Notice notice) {
        NoticeDto noticeDto = NoticeDto.builder()
                .id(notice.getNo())
                .subject(notice.getSubject())
                .contents(notice.getContents())
                .useHtml(notice.getUseHtml())
                .hit(notice.getHit())
                .wdate(notice.getWdate())
                .formattedDate(notice.getFormattedDate())
                .formattedDateTime(notice.getFormatDataTime())
                .build();
        return noticeDto;
    }
    private BlogDto convertBlogToDto(Blog blog) {
        BlogDto blogDto = BlogDto.builder()
                .id(blog.getNo())
                .pass(maskUserId(blog.getPass()))
                .subject(blog.getSubject())
                .contents(blog.getContents())
                .linkurl(blog.getLinkurl())
                .ip(blog.getIp())
                .prod_code(blog.getProd_code())
                .hit(blog.getHit())
                .attach(blog.getAttach())
                .formattedDate(blog.getFormattedDate())
                .formattedDateTime(blog.getFormatDataTime())
                .build();
        return blogDto;
    }
    private EventDto convertToDto(Event event) {
        EventDto eventDto = EventDto.builder()
                .id(event.getId())
                .subject(event.getSubject())
                .contents(event.getContents())
                .writeDate(event.getEventFormattedDate())
                .formattedEventTo(event.getEventTo())
                .formattedEventFrom(event.getEventFrom())
                .eventWinDay(formatWinDay(event.getEventWinDay()))
                .build();

        return eventDto;
    }

    private String formatWinDay(String eventWinDay) {
        if (eventWinDay == null || eventWinDay.trim().isEmpty()) {
            return "당첨일 미입력";
        }

        // "00000000" 같은 더미 값도 체크
        if (eventWinDay.matches("^0+$") || eventWinDay.equals("00000000")) {
            return "당첨일 미입력";
        }

        // 길이 체크 (YYYYMMDD 형식이어야 함)
        if (eventWinDay.length() < 8) {
            log.warn("유효하지 않은 당첨일 형식: {}", eventWinDay);
            return "날짜 형식 오류";
        }

        try {
            // "20240115" -> "2024-01-15"
            return eventWinDay;
        } catch (Exception e) {
            log.error("당첨일 포맷팅 실패: {}", eventWinDay, e);
            return "날짜 포맷 오류";
        }
    }
    public Page<CartoonDto> getCartoonLists(Pageable pageable, String division) {
        Page<Cartoon> cartoons = getCartoonPage(pageable, division);

        List<CartoonDto> cartoonDtos = cartoons.getContent().stream()
                .map(cartoon -> {
                    return CartoonDto.builder()
                            .id(cartoon.getId())
                            .subject(cartoon.getSubject())
                            .contents(cartoon.getContents())
                            .delFlag(cartoon.getDelFlag().toString())
                            .wdate(cartoon.getWdate())
                            .hit(cartoon.getHit())
                            .formattedDate(cartoon.getFormattedDate())
                            .formattedDateTime(cartoon.getFormatDataTime())
                            .build();
                })
                .collect(Collectors.toList());

        return new PageImpl<>(
                cartoonDtos,
                pageable,
                cartoons.getTotalElements()
        );
    }

    public Page<ReviewDto> getReviewLists(Pageable pageable) {
        Page<Review> reviewPage = getReviewPage(pageable);
        List<ReviewDto> reviewDtos = reviewPage.getContent().stream()
                .map( review -> {
                    return ReviewDto.builder()
                            .id(review.getNo())
                            .userid(maskUserId(review.getUserid()))
                            .title(review.getTitles())
                            .goodname(review.getGoodname())
                            .formattedDate(review.getFormattedDate())
                            .formattedDateTime(review.getFormatDataTime())
                            .contents(review.getContents())
                            .regdate(review.getRegdate())
                            .ipaddr(review.getIpaddr())
                            .hit(review.getHit())
                            .build();
                })
                .collect(Collectors.toList());
        return new PageImpl<>(
                reviewDtos,
                pageable,
                reviewPage.getTotalElements()
        );
    }
    public Page<FaqDto> getFaqLists(Pageable pageable, String search, String title, String division) {
        Page<Faq> faqPage = getFaqPage(pageable, search, title, division);

        List<FaqDto> faqDtos = faqPage.getContent().stream()
                .map(faq -> {

                    // EventDto 생성
                    return FaqDto.builder()
                            .id(faq.getId())
                            .subject(faq.getSubject())
                            .contents(faq.getContents())
                            .division(faq.getDivision())
                            .boonru(faq.getBoonru())
                            .wdate(faq.getWdate())
                            .formattedDate(faq.getFormattedDate())
                            .build();
                })
                .collect(Collectors.toList());

        // 5. Page 객체 생성 (PageImpl 사용)
        return new PageImpl<>(
                faqDtos,
                pageable,
                faqPage.getTotalElements()
        );
    }

    public Page<GoodReviewDto> productReviewList(Pageable pageable, String division, String productCode) {
        Page<GoodReview> goodReviewPage = getGoodReviewPage(pageable, division, productCode);
        List<GoodReviewDto> goodReviewDtos = goodReviewPage.getContent().stream()
                .map( goodreview -> {
                    return GoodReviewDto.builder()
                            .id(goodreview.getNo())
                            .wdate(goodreview.getWriteDate())
                            .formattedDate(goodreview.getFormattedDate())
                            .orderId(maskUserId(goodreview.getOrderId()))
                            .ipAddress(goodreview.getIpAddress())
                            .productCode(goodreview.getProductCode())
                            .imageUrl(goodreview.getImageUrl())
                            .division(goodreview.getDivision())
                            .starPoint(goodreview.getStarPoint())
                            .subject(goodreview.getSubject())
                            .contents(goodreview.getContents())
                            .hit(goodreview.getHit())
                            .smallpicture(goodImgGet(goodreview.getProductCode()))
                            .goodname(goodName(goodreview.getProductCode()))
                            .formattedDateTime(goodreview.getFormatDataTime())
                            .build();
                })
                .collect(Collectors.toList());
        return new PageImpl<>(
                goodReviewDtos,
                pageable,
                goodReviewPage.getTotalElements()
        );
    }

    public Page<BlogDto> getBlogLists(Pageable pageable) {
        Page<Blog> blogPage = getBlogPage(pageable);

        List<BlogDto> blogDtos = blogPage.getContent().stream()
                .map(blog -> {
                    return BlogDto.builder()
                            .id(blog.getNo())
                            .subject(blog.getSubject())
                            .contents(blog.getContents())
                            .wdate(blog.getWdate())
                            .hit(blog.getHit())
                            .ip(blog.getIp())
                            .linkurl(blog.getLinkurl())
                            .formattedDate(blog.getFormattedDate())
                            .formattedDateTime(blog.getFormatDataTime())
                            .pass(maskUserId(blog.getPass()))
                            .attach(blog.getAttach())
                            .prod_code(blog.getProd_code())
                            .build();
                })
                .collect(Collectors.toList());
        return new PageImpl<>(
                blogDtos,
                pageable,
                blogPage.getTotalElements()
        );
    }
    public Page<NoticeDto> getNoticeLists(Pageable pageable, String search, Boolean onlyActive, String title) {
        Page<Notice> noticePage = getNoticePage(pageable, search, onlyActive, title);

        List<NoticeDto> noticeDtos = noticePage.getContent().stream()
                .map(notice -> {

                    // EventDto 생성
                    return NoticeDto.builder()
                            .id(notice.getNo())
                            .subject(notice.getSubject())
                            .contents(notice.getContents())
                            .hit(notice.getHit())
                            .wdate(notice.getWdate())
                            .useHtml(notice.getUseHtml())
                            .formattedDate(notice.getFormattedDate())
                            .build();
                })
                .collect(Collectors.toList());

        // 5. Page 객체 생성 (PageImpl 사용)
        return new PageImpl<>(
                noticeDtos,
                pageable,
                noticePage.getTotalElements()
        );
    }
    public Page<EventDto> getEventsWithFiles(Pageable pageable, String search, Boolean onlyActive) {
        Page<Event> eventPage = getEventPage(pageable, search, onlyActive);

        // 2. FileList 정보 조회를 위한 fileIdx 리스트 추출
        List<Long> fileIdxes = eventPage.getContent().stream()
                .map(Event::getFileIdx)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 3. Filelist 한번에 조회 (IN 쿼리)
        final Map<Long, Filelist> fileMap;
        if (!fileIdxes.isEmpty()) {
            List<Filelist> fileLists = filelistRepository.findByFidxIn(fileIdxes);
            fileMap = fileLists.stream()
                    .collect(Collectors.toMap(Filelist::getFidx, Function.identity()));
        } else {
            fileMap = new HashMap<>();
        }
        // 4. DTO 변환 (FileList 정보 포함)
        List<EventDto> eventDtos = eventPage.getContent().stream()
                .map(event -> {
                    String attach = null;
                    if (event.getFileIdx() != null) {
                        Filelist filelist = fileMap.get(event.getFileIdx());
                        if (filelist != null) {
                            attach = filelist.getFilename1();
                        }
                    }
                    // EventDto 생성
                    return EventDto.builder()
                            .id(event.getId())
                            .subject(event.getSubject())
                            .contents(event.getContents())
                            .formattedEventTo(event.getEventTo())
                            .formattedEventFrom(event.getEventFrom())
                            .attach(attach)
                            .eventWinDay(event.getEventWinDay())
                            .build();
                })
                .collect(Collectors.toList());

        // 5. Page 객체 생성 (PageImpl 사용)
        return new PageImpl<>(
                eventDtos,
                pageable,
                eventPage.getTotalElements()
        );
    }
    // ✅ 기존 페이징 로직 (재사용)
    private Page<Event> getEventPage(Pageable pageable, String search, Boolean onlyActive) {
        if (search != null && !search.trim().isEmpty()) {
            return eventRepository.findBySubjectContainingAndDelFlag(
                    search, Event.DeleteFlag.N, pageable);
        } else if (Boolean.TRUE.equals(onlyActive)) {
            return eventRepository.findByShowitAndDelFlag(
                    "Y", Event.DeleteFlag.N, pageable);
        } else {
            return eventRepository.findByDelFlag(Event.DeleteFlag.N, pageable);
        }
    }
    private Page<Cartoon> getCartoonPage(Pageable pageable, String division) {
        Cartoon.DeleteFlag deleteFlag = Cartoon.DeleteFlag.N;
        log.info("카툰 division {} ", division);
        return cartoonRepository.findByDelFlagAndDivisionOrderByWdateDesc(deleteFlag, division, pageable);
    }
    private Page<Review> getReviewPage(Pageable pageable) {
        Review.DeleteFlag deleteFlag = Review.DeleteFlag.N;
        return reviewRepository.findByDelFlagOrderByRegdateDesc(deleteFlag, pageable);
    }

    private Page<GoodReview> getGoodReviewPage(Pageable pageable, String division, String productCode) {
        GoodReview.DeleteFlag deleteFlag = GoodReview.DeleteFlag.N;
        if (productCode != null && !productCode.trim().isEmpty()) {
            return goodReviewRepository.findByDivisionAndDelFlagAndProductCodeIsNotNullAndProductCode(division, deleteFlag, productCode, pageable);
        } else {
            return goodReviewRepository.findByDivisionAndDelFlag(division, deleteFlag, pageable);
        }
    }
    private Page<Faq> getFaqPage(Pageable pageable, String search, String title, String division) {
        Faq.DeleteFlag delFlag = Faq.DeleteFlag.N;
        log.info("검색 조건 why - title: {}, search: {}, division: {}", title, search, division);
        if (search != null && !search.trim().isEmpty()) {
            String keyword = search.trim();
            if (delFlag != null) {
                if ("subject".equalsIgnoreCase(title)) {
                    return faqRepository.findBySubjectContainingAndDelFlagAndDivision(keyword, delFlag, division, pageable);
                } else if ("content".equalsIgnoreCase(title)) {
                    return faqRepository.findByContentsContainingAndDelFlagAndDivision(keyword, delFlag, division, pageable);
                } else {
                    return faqRepository.findBySubjectContainingAndDelFlagAndDivision(keyword, delFlag, division, pageable);
                }
            } else {
                return faqRepository.findBySubject(keyword, delFlag, division, pageable);
            }
        } else {
            if (delFlag != null) {
                // ✅ 정렬 조건 추가 (wdate 기준 내림차순)

                return faqRepository.findByDelFlagAndDivisionOrderByWdateDesc(delFlag, division, pageable);
            } else {
                return faqRepository.findAll(pageable);
            }
        }
    }
    private Page<Blog> getBlogPage(Pageable pageable) {
        Blog.DeleteFlag deleteFlag = Blog.DeleteFlag.N;
        return blogRepository.findByDelFlagOrderByWdateDesc(deleteFlag, pageable);
    }
    private Page<Notice> getNoticePage(Pageable pageable, String search, Boolean onlyActive, String title) {
        Notice.DeleteFlag delFlag = (onlyActive == null || onlyActive)
                ? Notice.DeleteFlag.N
                : null;

        log.info("검색 조건 - title: {}, search: {}", title, search);

        if (search != null && !search.trim().isEmpty()) {
            String keyword = search.trim();

            // ✅ title 파라미터에 따라 검색 필드 결정
            if (delFlag != null) {
                // 삭제되지 않은 것만 검색
                if ("subject".equalsIgnoreCase(title)) {
                    // 제목 검색
                    return noticeRepository.findBySubjectContainingAndDelFlag(keyword, delFlag, pageable);
                } else if ("content".equalsIgnoreCase(title)) {
                    // 내용 검색 (contents 필드)
                    return noticeRepository.findByContentsContainingAndDelFlag(keyword, delFlag, pageable);
                } else {
                    // 기본값: 제목 검색
                    return noticeRepository.findBySubjectContainingAndDelFlag(keyword, delFlag, pageable);
                }
            } else {
                return noticeRepository.findBySubject(keyword, Notice.DeleteFlag.N, pageable);
            }
        } else {
            // 검색어가 없는 경우
            if (delFlag != null) {
                // ✅ 정렬 조건 추가 (wdate 기준 내림차순)
                return noticeRepository.findByDelFlagOrderByWdateDesc(delFlag, pageable);
            } else {
                // 모든 공지사항
                return noticeRepository.findAll(pageable);
            }
        }
    }

    private String goodImgGet(String prodcode) {
        if (prodcode == null || prodcode.length() == 0) {
            return null;
        }
        Optional<Product> productOpt = productRepository.findByCode(prodcode);

        if (productOpt.isEmpty()) {
            return null;
        }
        Product product = productOpt.get();
        return product.getSmallPicture();
    }

    private String goodName(String prodcode) {
        if (prodcode == null || prodcode.length() == 0) {
            return null;
        }
        Optional<Product> productOpt = productRepository.findByCode(prodcode);

        if (productOpt.isEmpty()) {
            return null;
        }
        Product product = productOpt.get();
        return product.getName();
    }
    private String maskUserId(String userId) {
        if (userId == null || userId.length() <= 3) {
            // 3자리 이하는 전체 마스킹 또는 원본 반환
            return userId == null ? "" : userId;
        }

        // 처음 3자리 + 나머지 * 처리
        String firstThree = userId.substring(0, 3);
        String maskedPart = "*".repeat(userId.length() - 3);
        return firstThree + maskedPart;
    }
}
