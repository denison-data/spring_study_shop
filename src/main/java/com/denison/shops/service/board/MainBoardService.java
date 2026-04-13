package com.denison.shops.service.board;

import com.denison.shops.domain.board.Faq;
import com.denison.shops.domain.board.GoodReview;
import com.denison.shops.domain.board.Notice;
import com.denison.shops.repository.board.FaqRepository;
import com.denison.shops.repository.board.GoodReviewRepository;
import com.denison.shops.repository.board.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MainBoardService {
    private final NoticeRepository noticeRepository;
    private final GoodReviewRepository goodReviewRepository;
    private final FaqRepository faqRepository;
    public List<Map<String, Object>> getMainNotices() {
        log.info("하단 공지사항 조회 시작");
        Pageable pageable = PageRequest.of(0, 4);
        List<Notice> notices = noticeRepository.findMainBoardNoticeJpql(pageable);
        // 2. Map으로 변환 (간단하게)
        List<Map<String, Object>> result = new ArrayList<>();
        for (Notice notice : notices) {
            Map<String, Object> item = new HashMap<>();
            item.put("no", notice.getNo());
            item.put("subject", notice.getSubject());
            item.put("hit", notice.getHit());
            item.put("recdate", notice.getFormattedDate());
            result.add(item);
        }
        log.info("메인 공지사항 조회 완료: {}건", result.size());
        return result;
    }
    public List<Map<String, Object>> getMainGoodReviews() {
        Pageable pageable = PageRequest.of(0, 4);
        List<GoodReview> reviews = goodReviewRepository.findMainBoardGoodReviewJpql(pageable);
        List<Map<String, Object>> result = new ArrayList<>();
        for (GoodReview goodreview : reviews) {
            Map<String, Object> item = new HashMap<>();
            item.put("no", goodreview.getNo());
            item.put("subject", goodreview.getSubject());
            item.put("recdate", goodreview.getFormattedDate());
            result.add(item);
        }
        return result;
    }
    public List<Map<String, Object>> getMainFaqs() {
        log.info("하단 Faq 조회 시작");
        Pageable pageable = PageRequest.of(0, 4);
        List<Faq> faqs = faqRepository.findMainBoardFaqJpql(Faq.DeleteFlag.N, pageable);
        // 2. Map으로 변환 (간단하게)
        List<Map<String, Object>> result = new ArrayList<>();
        for (Faq faq : faqs) {
            Map<String, Object> item = new HashMap<>();
            item.put("no", faq.getId());
            item.put("subject", faq.getSubject());
            item.put("division", faq.getDivision());
            item.put("category", faq.getBoonru());
            item.put("recdate", faq.getFormattedDate());
            result.add(item);
        }
        log.info("메인 FAQ 조회 완료: {}건", result.size());
        return result;
    }
}
