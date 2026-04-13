package com.denison.shops.utils;

import org.springframework.stereotype.Component;

@Component
public class StringFormatterUtil {
    /**
     * PHP의 stripslashes()와 동일한 기능
     * 역슬래시로 이스케이프된 문자를 원래 문자로 변환
     */
    public static String stripSlashes(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // 역슬래시 제거 처리
        StringBuilder result = new StringBuilder();
        boolean escape = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (escape) {
                // 이스케이프 문자 다음 문자는 그대로 추가
                result.append(c);
                escape = false;
            } else if (c == '\\') {
                // 역슬래시는 이스케이프 시작
                escape = true;
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    /**
     * PHP의 nl2br()과 동일한 기능
     * 줄바꿈 문자를 <br> 태그로 변환
     */
    public static String nl2br(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // \r\n, \n, \r을 <br>로 변환
        return str.replace("\r\n", "<br>")
                .replace("\n", "<br>")
                .replace("\r", "<br>");
    }

    /**
     * nl2br(stripSlashes(str))을 한 번에 처리
     */
    public static String nl2brStripSlashes(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String stripped = stripSlashes(str);
        return nl2br(stripped);
    }

    /**
     * 더 안전한 nl2br 처리 (XSS 방지 포함)
     */
    public static String nl2brSafe(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // HTML 특수문자 이스케이프
        String escaped = escapeHtml(str);

        // 줄바꿈 처리
        return escaped.replace("\r\n", "<br>")
                .replace("\n", "<br>")
                .replace("\r", "<br>");
    }

    /**
     * HTML 특수문자 이스케이프 (XSS 방지)
     */
    public static String escapeHtml(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    /**
     * PHP와 완전히 동일한 처리를 위한 메서드
     */
    public static String formatDescription(String description) {
        if (description == null) {
            return "";
        }

        // 1. stripslashes 처리
        String stripped = stripSlashes(description);

        // 2. nl2br 처리
        String withBr = nl2br(stripped);

        return withBr;
    }
}
