package com.infinium.glmcoupons.pojo;

/**
 * Created by Lalit Dhameliya on 09/28/2016.
 */

public class Coupon {
    String SRegisterId;
    String ServiceName;
    String ShortHeading;
    String ImageHeading;
    String BalCoupons;
    String PhotoPath;
    String MerchantId;
    String MerchantName;
    String City;
    String Area;
    String ThumbPhoto;
    String MerchantTitle;
    String SOfferId;
    String OfferPrice;
    String Discount;
    String Price;

    public Coupon(String SRegisterId, String serviceName, String shortHeading, String imageHeading, String balCoupons, String photoPath, String merchantId, String merchantName, String city, String area, String thumbPhoto, String merchantTitle, String SOfferId, String offerPrice, String discount, String price) {
        this.SRegisterId = SRegisterId;
        ServiceName = serviceName;
        ShortHeading = shortHeading;
        ImageHeading = imageHeading;
        BalCoupons = balCoupons;
        PhotoPath = photoPath;
        MerchantId = merchantId;
        MerchantName = merchantName;
        City = city;
        Area = area;
        ThumbPhoto = thumbPhoto;
        MerchantTitle = merchantTitle;
        this.SOfferId = SOfferId;
        OfferPrice = offerPrice;
        Discount = discount;
        Price = price;
    }

    public String getSRegisterId() {
        return SRegisterId;
    }

    public void setSRegisterId(String SRegisterId) {
        this.SRegisterId = SRegisterId;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getShortHeading() {
        return ShortHeading;
    }

    public void setShortHeading(String shortHeading) {
        ShortHeading = shortHeading;
    }

    public String getImageHeading() {
        return ImageHeading;
    }

    public void setImageHeading(String imageHeading) {
        ImageHeading = imageHeading;
    }

    public String getBalCoupons() {
        return BalCoupons;
    }

    public void setBalCoupons(String balCoupons) {
        BalCoupons = balCoupons;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String merchantId) {
        MerchantId = merchantId;
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public void setMerchantName(String merchantName) {
        MerchantName = merchantName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getThumbPhoto() {
        return ThumbPhoto;
    }

    public void setThumbPhoto(String thumbPhoto) {
        ThumbPhoto = thumbPhoto;
    }

    public String getMerchantTitle() {
        return MerchantTitle;
    }

    public void setMerchantTitle(String merchantTitle) {
        MerchantTitle = merchantTitle;
    }

    public String getSOfferId() {
        return SOfferId;
    }

    public void setSOfferId(String SOfferId) {
        this.SOfferId = SOfferId;
    }

    public String getOfferPrice() {
        return OfferPrice;
    }

    public void setOfferPrice(String offerPrice) {
        OfferPrice = offerPrice;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
