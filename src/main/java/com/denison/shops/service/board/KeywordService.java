    package com.denison.shops.service.board;

    import com.denison.shops.domain.board.Keyword;;
    import com.denison.shops.repository.board.KeywordRepository;
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
    public class KeywordService {
        private final KeywordRepository keywordRepository;
        public List<Map<String, Object>> getMainKeywords() {
            log.info("메인 키워드 조회 시작");
            Pageable pageable = PageRequest.of(0, 3);
            // 1. Repository로 DB에서 데이터 가져오기
            List<Keyword> keywords = keywordRepository.findRandomKeywordsJpql("S_TXT", pageable);

            // 2. Map으로 변환 (간단하게)
            List<Map<String, Object>> result = new ArrayList<>();
            for (Keyword keyword : keywords) {
                Map<String, Object> item = new HashMap<>();
                item.put("no", keyword.getNo());
                item.put("keyword", keyword.getKeyword());
                item.put("division", keyword.getDivision());
                // URL 변환 적용
                String oldUrl = keyword.getUrl();
                String newUrl = converOldUrl(oldUrl);
                item.put("url", newUrl);
                result.add(item);
            }
            log.info("메인 키워드 조회 완료: {}건", result.size());
            return result;
        }
        public String converOldUrl(String oldUrl) {
            if (oldUrl == null) return null;

            if (oldUrl.startsWith("/event/ev_v_01")) {
                return oldUrl.replace("/event/ev_v_01", "/board/ev_v").replace(".php", "");
            }
            else if (oldUrl.startsWith("/product/productDetail.php")) {
                String[] parts = oldUrl.split("\\?");
                if (parts.length > 1) {
                    // 쿼리 파라미터 처리
                    String query = parts[1];
                    // 파라미터명 소문자로 변경
                    query = query.replace("Code=", "code=")
                            .replace("Cat_Content=", "cat_content=")
                            .replace("Types=", "types=");
                    return "/product/detail?" + query;
                }
                return "/product/detail";
            }
            return oldUrl;
        }
    }
