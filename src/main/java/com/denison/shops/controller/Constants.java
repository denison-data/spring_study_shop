package com.denison.shops.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Set;

@Slf4j
public class Constants {
    // private 생성자로 인스턴스화 방지
    private Constants() {}

    public static final Set<String> BEST10_CONTENTS = Set.of(
            "best/index", "Search", "main", "login", "regist",
            "join", "mem_rs", "me_o_h_login_form", "find", "mydiet",
            "mem_od_h", "mem_od_v", "mem_od_n", "ev_main", "ev_ym_main",
            "ev_v_01", "ev_ym_v", "ev_dc_list", "ev_dc_v_01", "mem_mod_01",
            "mem_get", "mypoint", "cs_main", "cs_n_v", "cs_qna_l",
            "cs_qna_w", "cs_prod_l", "cs_etc_l", "product_login", "company",
            "map", "shopping_cart", "orders", "pay_suc", "pays_end",
            "bl_main", "ca_v_01", "bl_l_01", "bl_v_01", "ep_l_01",
            "ch_hv_01", "about01", "about02", "ep_w_01", "cs_qna_v"
    );

    // 다른 상수들도 추가 가능
    public static final String DEFAULT_CATEGORY = "Best10";
    public static final int PAGE_SIZE = 10;


    /**
     * 템플릿 경로 결정 (정적 메서드)
     */
    public static String determineContentTemplate(String page, String catContent) {
        // 특별 케이스: m_test는 Best_T
        if ("m_test".equals(page)) {
            return "createfiles/content/best_t";
        }
        log.info("[명칭 page] : " + page);
        // BEST10에 포함되는 페이지들
        if (BEST10_CONTENTS.contains(page)) {
            log.info("best10 hear : " + page);
            return "createfiles/content/best10";
        }

        // catContent가 지정된 경우
        if (catContent != null && templateExists("createfiles/content/" + catContent)) {
            log.info("no hear : " + catContent);
            return "createfiles/content/" + catContent;
        }
        log.info("check : " + catContent);
        // 기본값
        return "createfiles/content/best10";
    }

    /**
     * 템플릿 존재 여부 확인
     */
    public static boolean templateExists(String templatePath) {
        try {
            Resource resource = new ClassPathResource("templates/" + templatePath + ".html");
            return resource.exists();
        } catch (Exception e) {
            System.out.println(templatePath);
            return false;
        }
    }

}
