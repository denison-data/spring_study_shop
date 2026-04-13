package com.denison.shops.service;

import com.denison.shops.domain.product.*;
import com.denison.shops.dto.api.*;
import com.denison.shops.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final GoodOptionRepository goodOptionRepository;
    private final CategoryRepository categoryRepository;
    private final BestGoodRepository bestGoodRepository;
    private final CartRepository cartRepository;
    private final SocialRepository socialRepository;

    public SocialDto getSocialData(String code) {
        Social social;
        if (code != null && !code.isEmpty()) {
            social = socialRepository.findByCodeAndDelFlag(code, "n")
                    .orElse(null);
        } else {
            social = socialRepository.findTopByDelFlagAndDisplayOrderByInsertDateDesc("n", "y")
                    .orElse(null);
        }
        if (social == null) {
            return null;
        }

        // 엔티티 → DTO 변환
        return convertSocialToDto(social);
    }
    // 삭제
    public boolean removeCart(Long cno) {
        if (cartRepository.existsById(cno)) {
            cartRepository.deleteById(cno);
            return true;
        }
        return false;
    }

    // 조회
    public List<Map<String, Object>> getCart(String sessionId) {
        List<Cart> carts = cartRepository.findBySessionIdOrderByCnoDesc(sessionId);

        return carts.stream()
                .map(cart -> {
                    ProductDetailDto productDetail = getProductByNo((long) cart.getNo(), 1);

                    Map<String, Object> result = new HashMap<>();
                    result.put("cart", new CartResponseDto(cart));   // 장바구니 정보
                    result.put("productDetail", productDetail);      // 상품 상세 정보
                    return result;

                })
                .toList();
    }
    public CartRequestDto updateCartQuantity(String sessionId, Long cno, int quantity) {
        Cart cart = cartRepository.findById(cno)
                .filter(c -> c.getSessionId().equals(sessionId))
                .orElseThrow(() -> new IllegalStateException("장바구니 항목을 찾을 수 없습니다."));

        if (quantity > 5) {
            throw new IllegalArgumentException("5개 이상은 주문할 수 없습니다.");
        }

        cart.setQuantity(quantity);
        cartRepository.save(cart);

        // CartRequestDto로 변환해서 반환
        return new CartRequestDto();
    }

    public void addOrUpdateCart(String sessionId, CartRequestDto req) {
        Optional<Cart> existing = cartRepository.findBySessionIdAndNoAndOptionNoAndQuantity(
            sessionId, req.getProductNo(), req.getOptionNo(), req.getQuantity()
        );

        if (existing.isEmpty()) {
            Cart cart = new Cart();
            cart.setSessionId(sessionId);
            cart.setNo(req.getProductNo());
            cart.setProductType(req.getProductType());
            cart.setQuantity(req.getQuantity());
            cart.setOptionNo(req.getOptionNo());
            cart.setReferUrls(req.getReferUrls() == null ? "" : req.getReferUrls());
            cart.setDate((int) Instant.now().getEpochSecond());
            cartRepository.save(cart);
        } else {
            Cart cart = existing.get();
            int newQty = cart.getQuantity() + req.getQuantity();
            if (newQty > 5) {
                throw new IllegalArgumentException("5개 이상은 주문할 수 없습니다.");
            }
            cart.setQuantity(newQty);
            cartRepository.save(cart);
        }

    }
    public Page<ProductDetailDto> getProductsByCategoryKeyword(String keyword, String dv, Pageable pageable) {
        // 1. 카테고리 조회 (delFlag=N 조건)
        if ("W_Feat".equals(keyword.trim())) {
            keyword = "W_F_cate1";
        }
        List<Category> categories = categoryRepository.findByKeywordAndDelFlag(keyword, Category.DeleteFlag.N);

        if (categories.isEmpty()) {
            return Page.empty(pageable);
        }

        // 2. 하나의 카테고리만 선택 (예: 첫 번째)
        String catNo2 = null;
        Page<Product> productPage;
        Boolean checked = false;
        Category category = categories.get(0);
        log.info("카테고리 catcd : {} , dv : {}, 1차검색 : {}", category.getId(), dv, keyword);
        if ("W_Age".equals(keyword.trim())) {
            List<Category> categorylist2 = categoryRepository.findByKeywordAndDelFlag(dv, Category.DeleteFlag.N);
            Category category2 = categorylist2.get(0);
            catNo2 = category2.getId().toString();
            checked = true;
            log.info("카테고리 no : {}", category2.getId());
        }
        // 3. 해당 카테고리 번호로 상품 조회 (페이징)
        if (checked) {
            productPage = productRepository.findByCategoryCodeLikeAge(category.getId().toString(), catNo2,  pageable);
        } else {
            log.info("체크 2 : {}", keyword.trim());
            if (category.getId() == 7) {
                log.info("category 로 만 : {} ", category.getId());
                productPage = productRepository.findByCategoryLike(category.getId().toString(), pageable);

            } else {
                productPage = productRepository.findByCategoryCodeLike(category.getId().toString(), pageable);
            }
        }

        List<ProductDetailDto> dtoList = productPage.getContent().stream()
                .map(product -> {
                    // 상품별 옵션 조회
                    List<GoodOption> options = goodOptionRepository.findByProductIdAndIngFlag(product.getId(), "y");

                    // 엔티티 옵션 → DTO 옵션 변환
                    List<ProductOptionDto> optionDtos = options.stream()
                            .map(option -> ProductOptionDto.builder()
                                    .id(option.getGeneratedId())
                                    .optionName(option.getOptionName())
                                    .optionA(option.getOptionA())
                                    .guestPrice(option.getGuestPrice())
                                    .memberPrice(option.getMemberPrice())
                                    .silverPrice(option.getSilverPrice())
                                    .realPrice(option.getRealPrice())
                                    .ingFlag(option.getIngFlag())
                                    .rate(option.getRate())
                                    .optionNo(option.getOptionNo())
                                    .build())
                            .toList();

                    // ProductDetailDto 생성
                    return ProductDetailDto.builder()
                            .id(product.getId())
                            .code(product.getCode())
                            .name(product.getName())
                            .smallPicture(product.getSmallPicture())
                            .category_code(product.getCategoryCode())
                            .categoryname(category.getCatname())
                            .option_array(optionDtos) // ✅ 변환된 DTO 옵션 리스트 포함
                            .build();
                })
                .toList();

        return new PageImpl<>(dtoList, pageable, productPage.getTotalElements());

    }
    public Page<ProductDetailDto> bestprodList(String division, Pageable pageable) {
        Page<ProductProjection> products = bestGoodRepository.findBestProducts(division, pageable);
        return products.map(p -> {
            List<GoodOption> options = goodOptionRepository.findByProductIdAndIngFlag(p.getId(), "y");

            // 엔티티 옵션 → DTO 옵션 변환
            List<ProductOptionDto> optionDtos = options.stream()
                    .map(option -> ProductOptionDto.builder()
                            .id(option.getGeneratedId())
                            .optionName(option.getOptionName())
                            .optionA(option.getOptionA())
                            .guestPrice(option.getGuestPrice())
                            .memberPrice(option.getMemberPrice())
                            .silverPrice(option.getSilverPrice())
                            .realPrice(option.getRealPrice())
                            .ingFlag(option.getIngFlag())
                            .rate(option.getRate())
                            .optionNo(option.getOptionNo())
                            .build())
                    .toList();
            // ProductDetailDto 생성
            return ProductDetailDto.builder()
                    .id(p.getId())
                    .code(p.getCode())
                    .name(p.getName())
                    .mdName(p.getMdname())
                    .vender(p.getVendor())
                    .category(p.getCategory())
                    .category_code(p.getCategorycode())
                    .smallPicture(p.getSmallPicture())
                    .middlePicture(p.getMiddlePicture())
                    .comment(p.getComment())
                    .option_array(optionDtos)   // ✅ 옵션 리스트 추가
                    .build();
        });

    }

    public List<ProductDetailDto> productList(String prodCode) {
        List<Product> products = productRepository.findByDelFlagAndCodeIsNotNullAndCodeNotOrderByNameDesc("n", prodCode);

        return products.stream()
                .map(product -> ProductDetailDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .code(product.getCode())
                        .shippingLocation(product.getShippingLocation())
                        .manufacturer(product.getManufacturer())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ProductDetailDto> productList_ver2(String prodCode) {
        List<Product> products = productRepository.findByDelFlagAndCodeIsNotNullAndCodeNotOrderByNameDesc_ver2("n", prodCode);

        return products.stream()
                .map(product -> ProductDetailDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .code(product.getCode())
                        .build())
                .collect(Collectors.toList());
    }

    public ProductDetailDto getProductByCode(String prodCode, Integer memberGrade) {
        log.info("상품 코드로 조회 - 코드: {}", prodCode);
        Optional<Product> productOpt = productRepository.findByCode(prodCode);

        if (productOpt.isEmpty()) {
            throw new RuntimeException("상품을 찾을 수 없습니다 (코드: " + prodCode + ")");
        }

        Product product = productOpt.get();
        log.info("코드 상품 정보: {}", product.getId());
        if ("y".equals(product.getDelFlag())) {
            throw new RuntimeException("삭제된 상품입니다: " + prodCode);
        }

        // ✅ 옵션 정보 조회 추가
        log.info("프러덕트 no : {}", product.getId());
        List<GoodOption> options = goodOptionRepository.findByProductIdAndIngFlag(product.getId(),"y");
        log.info("옵션 조회 완료: {}개", options.size(), " 추가정보 : {}", product);

        // ✅ 각 옵션 확인 -- 의미 없음 (나중에 제거용)
        for (int i = 0; i < options.size(); i++) {
            GoodOption opt = options.get(i);
            log.info("1 옵션 {}: No={}, OptionNo={}, OptionName={}",
                    i+1, opt.getProduct(), opt.getOptionNo(), opt.getOptionName());
        }
        List<Product> product_etcList = productRepository.findRandomProducts("n","y", product.getOemId(), product.getCode());

        Integer gradeStr = convertMemberGrade(memberGrade);
        ProductDetailDto dto = convertToProductDetailDto(product, options, gradeStr, product_etcList);
//        dto.processDescription();
        return dto;
    }
    public ProductDetailDto getProductByNo(Long productNo, Integer memberGrade) {
        log.info("상품 조회 - 번호: {}", productNo);

        Optional<Product> productOpt = productRepository.findById(productNo);
//        Optional<Product> productOpt = productRepository.findByIdAndDelFlag(productNo, "n");

        if (productOpt.isEmpty()) {
            throw new RuntimeException("상품을 찾을 수 없습니다: " + productNo);
        }

        Product product = productOpt.get();

        // 삭제된 상품인지 확인
        if ("y".equals(product.getDelFlag())) {
            throw new RuntimeException("삭제된 상품입니다: " + productNo);
        }

        // ✅ 옵션 정보 조회 추가
        List<GoodOption> options = goodOptionRepository.findByProductIdAndIngFlag(productNo, "y");
        log.info("tt 옵션 조회 완료: {}개", options.size());

        // ✅ 각 옵션 확인
        for (int i = 0; i < options.size(); i++) {
            GoodOption opt = options.get(i);
            log.info("옵션 {}: No={}, OptionNo={}, OptionName={}",
                    i+1, opt.getProduct(), opt.getOptionNo(), opt.getOptionName());
        }
       //List<Product> product_etcList = productRepository.findByDelFlagAndDisplayAndOemIdAndCodeNot("n","y", product.getOemId(), product.getCode());
        List<Product> product_etcList = productRepository.findRandomProducts("n","y", product.getOemId(), product.getCode());

        Integer gradeStr = convertMemberGrade(memberGrade);
        ProductDetailDto dto = convertToProductDetailDto(product, options, gradeStr, product_etcList);
        return dto;
    }
    /*
    public Page<CategoryProductDto> category_lists(Pageable pageable, String Cat_content, String searchKeyword ) {

    }
    */

    private ProductDetailDto convertToProductDetailDto(Product product, List<GoodOption> options, Integer memberGrade, List<Product> oemlist) {
        ProductDetailDto dto = ProductDetailDto.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .comment(product.getComment())
                .mdName(product.getMdName())
                .mnName(product.getMnName())
                .description(product.getDescription())
                .oemInfo(product.getOemInfo())
                .stock(product.getStock())
                .smallPicture(product.getSmallPicture())
                .middlePicture(product.getMiddlePicture())
                .bigImage(product.getBigImage())
                .category(product.getCategory())
                .type(product.getType())
                .category_code(product.getCategoryCode())
                .manufacturer(product.getManufacturer())
                .productAdditionalInfo(product.getProductAdditionalInfo())
                .mainIngredient(product.getMainIngredient())
                .oemId(product.getOemId())
                .unit(product.getUnit())
                .shortDescription(product.getShortDescription())
                .formattedDescription(product.getDescription())
                .formattedOemInfo(product.getOemInfo())
                .createdAt(product.getInsertDateTime())  // UNIX timestamp 변환
                .updatedAt(product.getUpdateDateTime())  // UNIX timestamp 변환
                .build();
        // ✅ 옵션 정보 변환 및 추가
        processImageUrls(dto);

        List<ProductOptionDto> optionDtos = connvertToOptionDtos(options, memberGrade);
        dto.setOption_array(optionDtos);

        List<ProductDetailDto> prodEtcDtos = convertToprodoemDtos(oemlist, memberGrade);
        dto.setProd_etc_array(prodEtcDtos);
        // 로깅
        log.info("DTO 생성 완료 - description 존재: {}, 길이: {}",
                dto.getDescription() != null,
                dto.getDescription() != null ? dto.getDescription().length() : 0);
        return dto;
    }
    private void processImageUrls(ProductDetailDto dto) {
        if (dto == null) return;
        // description 필드 변환

        if (dto.getDescription() != null) {
            String originalDesc = dto.getDescription();
            dto.setDescription(originalDesc.replace("http://image.wisefood.co.kr/AdmArea/FCKeditor/data/geditor/", "https://img.debco.link/product_info/"));
            log.debug("description URL 변환: {}자 -> {}자", originalDesc.length(), dto.getDescription().length());
        }

        // oemInfo 필드 변환
        if (dto.getOemInfo() != null) {
            String originalOem = dto.getOemInfo();
            dto.setOemInfo(originalOem.replace("http://image.wisefood.co.kr/AdmArea/FCKeditor/data/geditor/", "https://img.debco.link/product_info/"));
            log.debug("oemInfo URL 변환: {}자 -> {}자", originalOem.length(), dto.getOemInfo().length());
        }

        // productInfo 필드 변환
        if (dto.getProductInfo() != null) {
            dto.setProductInfo(dto.getProductInfo().replace("http://image.wisefood.co.kr/AdmArea/FCKeditor/data/geditor/", "https://img.debco.link/product_info/"));
        }

        // nature 필드 변환
        if (dto.getNature() != null) {
            dto.setNature(dto.getNature().replace("http://image.wisefood.co.kr/AdmArea/FCKeditor/data/geditor/", "/img/product/"));
        }

        log.info("이미지 URL 변환 처리 완료", dto);
    }
    private List<ProductDetailDto> convertToprodoemDtos(List<Product> prods, Integer memberGrade) {
        if (prods == null || prods.isEmpty()) {
            return Collections.emptyList();
        }

        return prods.stream()
                .map(product -> {
                    List<GoodOption> options = goodOptionRepository.findByProductIdAndIngFlag(product.getId(),"y");
                    List<ProductOptionDto> optionDtos = connvertToOptionDtos(options, memberGrade);

                    ProductDetailDto dto = ProductDetailDto.from(product, optionDtos);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    private List<ProductOptionDto> connvertToOptionDtos(List<GoodOption> options, Integer memberGrade) {
        if (options == null || options.isEmpty()) {
            return Collections.emptyList();
        }

        return options.stream()
                .filter(GoodOption::isAvailable)
                .map(option -> {
                    ProductOptionDto dto = ProductOptionDto.from(option);
                    dto.setCurrentPrice(memberGrade);
                    return dto;
                })
                .sorted(ProductOptionDto.getOptionNoComparator())
                .collect(Collectors.toList());
    }

    private SocialDto convertSocialToDto(Social social) {
        return SocialDto.builder()
                .no(social.getNo())
                .code(social.getCode())
                .name(social.getName())
                .vendor(social.getVendor())
                .type(social.getType())
                .category(social.getCategory())
                .categoryCode(social.getCategoryCode())
                .display(social.getDisplay())
                .stock(social.getStock())
                .mileage(social.getMileage())
                .smallPicture(social.getSmallPicture())
                .middlePicture(social.getMiddlePicture())
                .largePictureA(social.getLargePictureA())
                .comment(social.getComment())
                .docType(social.getDocType())
                .productInfor(social.getProductInfor())
                .nature(social.getNature())
                .oemInfo(social.getOemInfo())
                .discription(social.getDiscription())
                .startTime(social.getStartTime())
                .endTime(social.getEndTime())
                .pick1(social.getPick1())
                .pick2(social.getPick2())
                .pick3(social.getPick3())
                .pick4(social.getPick4())
                .insertDate(social.getInsertDate())
                .updateDate(social.getUpdateDate())
                .delFlag(social.getDelFlag())
                .wonCreate(social.getWonCreate())
                .wonro(social.getWonro())
                .oemYs(social.getOemYs())
                .oemId(social.getOemId())
                .danwi(social.getDanwi())
                .keywords(social.getKeywords())
                .prodInfo(social.getProdInfo())
                .endYn(social.getEndYn())
                .prExplain(social.getPrExplain())
                .basongCh(social.getBasongCh())
                .basongMa(social.getBasongMa())
                .basongInfo(social.getBasongInfo())
                .bigImage(social.getBigImage())
                .mdName(social.getMdName())
                .mnName(social.getMnName())
                .build();
    }


    private Page<BestGood> getBestGoods(String division, Pageable pageable) {
        return null;
    }
    private Integer convertMemberGrade(Integer grade) {
        if (grade == null) return 1;
        return grade;
    }
}