package com.denison.shops.controller.api;

import com.denison.shops.domain.PageResponse;
import com.denison.shops.dto.api.*;
import com.denison.shops.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductApiController {

    private final ProductService productService;

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailDto>> getProductById(@PathVariable("id") Long productId, @RequestParam(required = false) Integer memberGrade) {
        log.info("📦 상품 조회 API 호출 - id: {}", productId);
        try {
            ProductDetailDto product = productService.getProductByNo(productId, memberGrade);
            return buildSuccessResponse(product, "상품 정보 조회 성공");

        } catch (Exception e) {
            log.error("상품 조회 실패 - id: {}, error: {}", productId, e.getMessage());
            return buildNotFoundResponse(e.getMessage());
        }
    }
    @GetMapping("/list")
    public ResponseEntity<List<ProductDetailDto>> productList() {
        List<ProductDetailDto> result = productService.productList("licover_w01");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/sellist")
    public ResponseEntity<List<ProductDetailDto>> productList_ver2() {
        List<ProductDetailDto> result = productService.productList_ver2("licover_w01");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<ProductDetailDto>> getProductByCode(@PathVariable("code") String prodCode, @RequestParam(required = false) Integer memberGrade) {
        log.info("📦 상품 코드로 조회 - code: {}", prodCode);
        try {
            ProductDetailDto product = productService.getProductByCode(prodCode, memberGrade);
            return buildSuccessResponse(product, "상품 정보 조회 성공");

        } catch (Exception e) {
            log.error("상품 코드 조회 실패 - code: {}, error: {}", prodCode, e.getMessage());
            return buildNotFoundResponse(e.getMessage());
        }
    }

    @GetMapping("/category/best")
    public ResponseEntity<ApiResponse<ProductDetailDto>> getProductByCategory(@PathVariable("cateCode") String cateCode){
        log.info("카테고리 상품 조회 - cate_code {}", cateCode);
        try {
           // ProductDetailDto product = productService.getProductByCategory(cateCode);
           // return buildSuccessResponse(product, "카테고리 상품 조회 성공");
        return null;
        } catch (Exception e) {
            log.error("카테고리 상품 조회 실패 - code : {}", cateCode);
            return buildNotFoundResponse(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProductDetailDto>> searchProduct(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "16") int size,
            @RequestParam(defaultValue = "id,DESC") String sort
    ) {
        ///search?keyword=테스트 로하면 됨
        log.info("search 조회 관련 - keyword {}", keyword);

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortField));

        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.ok(PageResponse.of(Page.empty(pageable)));
        }

        Page<ProductDetailDto> products = productService.searchProduct(keyword, pageable);
        PageResponse<ProductDetailDto> response = PageResponse.of(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product_category/{keyword}")
    public ResponseEntity<PageResponse<ProductDetailDto>> getProductsByCategory(
            @PathVariable String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "16") int size,
            @RequestParam(value = "dv", required = false, defaultValue = "W_A_cate3") String dv,
            @RequestParam(defaultValue = "id,DESC") String sort) {

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        log.info("정렬 필드: {}, 방향: {}, 확인 : {}", sortField, direction, dv);

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page -1, size, Sort.by(direction, sortField));
        Page<ProductDetailDto> products = productService.getProductsByCategoryKeyword(keyword, dv, pageable);

        PageResponse<ProductDetailDto> response = PageResponse.of(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/best_product")
    public ResponseEntity<PageResponse<ProductDetailDto>> getBestProducts(
            @RequestParam(defaultValue = "Best10_list") String division,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "regDate,desc") String sort) {

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortField));
        Page<ProductDetailDto> products = productService.bestprodList(division, pageable);

        return ResponseEntity.ok(PageResponse.of(products));

    }

    private String getSessionID(HttpSession session) {
        String sessionId = (String) session.getAttribute("Wisefood_Shop");

        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString().replace("-", ""); // 32자리
            session.setAttribute("Wisefood_Shop", sessionId);
        }
        return sessionId;
    }

    @PostMapping("shopping_cart")
    public ResponseEntity<?> addUpdateCart(HttpSession session, @RequestBody CartRequestDto req) {
        String sessionId = getSessionID(session);
        log.info("세션 : {} ", sessionId);
        try {
            productService.addOrUpdateCart(sessionId, req);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("shopping_cart/{cno}/quantity")
    public ResponseEntity<CartRequestDto> updateCartQuantity(HttpSession session,
                                                             @PathVariable Long cno,
                                                             @RequestBody CartRequestDto req) {
        String sessionId = getSessionID(session);
        CartRequestDto dto = productService.updateCartQuantity(sessionId, cno, req.getQuantity());
        return ResponseEntity.ok(dto); // ✅ JSON 응답
    }


    @GetMapping("shopping_cart")
    public List<Map<String, Object>> getCart(HttpSession session) {
        String sessionId = getSessionID(session);
        return productService.getCart(sessionId);
    }

    @DeleteMapping("shopping_cart/{cno}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cno) {
        boolean deleted = productService.removeCart(cno);
        if (deleted) {
            return ResponseEntity.ok().body(Map.of("success", true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "Cart not found"));
        }
    }

    @GetMapping({"/social", "/social/{code}"})
    public ResponseEntity<SocialDto> getSocial(@PathVariable(required = false) String code) {
        log.info("소셜 api code : {}", code);
        SocialDto dto = productService.getSocialData(code);
        log.info("소셜 dto : {}", dto);
        if (dto == null) {
            return ResponseEntity.ok(new SocialDto());
        }

        return ResponseEntity.ok(dto);
    }


}