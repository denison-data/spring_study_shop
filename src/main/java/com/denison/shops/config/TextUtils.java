package com.denison.shops.config;
import org.springframework.util.StringUtils;

public class TextUtils
{
    // HTML 태그 제거
    public static String stripTags(String html) {
        if (!StringUtils.hasText(html)) {
            return "";
        }

        // 정규식으로 HTML 태그 제거
        String text = html.replaceAll("<[^>]*>", "");

        // HTML 엔티티 디코딩 (선택사항)
        text = text.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&")
                .replaceAll("&quot;", "\"")
                .replaceAll("&#039;", "'")
                .replaceAll("&nbsp;", " ");

        return text.trim();
    }

    // 텍스트 자르기 (한글, 영문 혼합 고려)
    public static String textCut(String text, int maxLength) {
        return textCut(text, maxLength, "...");
    }

    public static String textCut(String text, int maxLength, String suffix) {
        if (!StringUtils.hasText(text)) {
            return "";
        }

        if (text.length() <= maxLength) {
            return text;
        }

        // 바이트 단위가 아닌 문자 단위로 자르기
        // 한글도 1자로 처리
        String cutText = text.substring(0, maxLength);

        // 마지막 문자가 깨지지 않도록 (공백에서 자르기)
        int lastSpace = cutText.lastIndexOf(' ');
        if (lastSpace > maxLength - 10) { // 너무 앞에서 자르지 않도록
            cutText = cutText.substring(0, lastSpace);
        }

        return cutText + suffix;
    }

    // HTML 태그 제거 + 텍스트 자르기 (한번에)
    public static String stripAndCut(String html, int maxLength) {
        String text = stripTags(html);
        return textCut(text, maxLength);
    }
}
