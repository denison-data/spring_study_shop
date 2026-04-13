package com.denison.shops.repository;

public interface ProductProjection {
    Long getId();
    String getCode();
    String getCategory();
    String getCategorycode();
    String getName();
    String getMdname();
    String getSmallPicture();
    String getMiddlePicture();
    String getComment();
    Integer getVendor();

}
