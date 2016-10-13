package com.infinium.glmcoupons.pojo;

/**
 * Created by Lalit Dhameliya on 09/28/2016.
 */

public class Category {
    String ServiceTypeId;
    String ServiceTypeCode;
    String ServiceTypeName;
    String IconPath;
    String DisplayOrder;

    public Category(String serviceTypeId, String serviceTypeCode, String serviceTypeName, String iconPath, String displayOrder) {
        ServiceTypeId = serviceTypeId;
        ServiceTypeCode = serviceTypeCode;
        ServiceTypeName = serviceTypeName;
        IconPath = iconPath;
        DisplayOrder = displayOrder;
    }

    public String getServiceTypeId() {
        return ServiceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        ServiceTypeId = serviceTypeId;
    }

    public String getServiceTypeCode() {
        return ServiceTypeCode;
    }

    public void setServiceTypeCode(String serviceTypeCode) {
        ServiceTypeCode = serviceTypeCode;
    }

    public String getServiceTypeName() {
        return ServiceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        ServiceTypeName = serviceTypeName;
    }

    public String getIconPath() {
        return IconPath;
    }

    public void setIconPath(String iconPath) {
        IconPath = iconPath;
    }

    public String getDisplayOrder() {
        return DisplayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        DisplayOrder = displayOrder;
    }
}
