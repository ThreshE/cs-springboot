package com.springboot.cs.common.enums;

import org.apache.commons.lang.StringUtils;

public class LanguageEnums {

    public enum LanguageTypeEnum{

        zh_cn("简体中文"),
        zh_hk("繁体中文"),
        eng("英文");

        private String name;

        public String getName() {
            return name;
        }

        LanguageTypeEnum(String name) {
            this.name = name;
        }

        public static String getName(String code) {
            return LanguageTypeEnum.valueOf(code).getName();
        }

    }


    public enum ResultMessageEnum {

        insert_success("Insert Success","新增成功","新增成功"),
        insert_failed("Insert Failed","新增失敗","新增失败"),
        update_success("Update Success","更新成功","更新成功"),
        update_failed("Update Failed","更新失敗","更新失败"),
        list_success("List Success","列表查詢成功","列表查询成功"),
        list_failed("List Failed","列表查詢失敗","列表查询失败"),
        detail_success("Detail Success","詳情查詢成功","详情查询成功"),
        detail_failed("Detail Failed","詳情查詢失敗","详情查询失败"),
        delete_success("Delete Success","刪除成功","删除成功"),
        delete_failed("Delete Failed","刪除失敗","删除失败"),
        delete_list_success("Delete List Success","批量刪除成功","批量删除成功"),
        delete_list_failed("Delete List Failed","批量刪除失敗","批量删除失败"),

        msg("eng","繁体","简体");

        private String simplifiedChinese;

        private String traditionalChinese;

        private String english;

        ResultMessageEnum(String english,String traditionalChinese,String simplifiedChinese){
            this.english = english;
            this.traditionalChinese = traditionalChinese;
            this.simplifiedChinese = simplifiedChinese;
        }

        private static String getSimplifiedChinese(String code) {
            return ResultMessageEnum.valueOf(code).simplifiedChinese;
        }

        private static String getTraditionalChinese(String code) {
            return ResultMessageEnum.valueOf(code).traditionalChinese;
        }

        private static String getEnglish(String code) {
            return ResultMessageEnum.valueOf(code).english;
        }

        public static String getMessage(String code,String lang) {
            try {

                if(StringUtils.isEmpty(code)) {
                    return "";
                }

                if(StringUtils.equals(lang,LanguageEnums.LanguageTypeEnum.zh_cn.toString())) {
                    return getSimplifiedChinese(code);
                }else if(StringUtils.equals(lang,LanguageEnums.LanguageTypeEnum.zh_hk.toString())) {
                    return getTraditionalChinese(code);
                } else if(StringUtils.equals(lang,LanguageEnums.LanguageTypeEnum.eng.toString())) {
                    return getEnglish(code);
                } else {
                    return getEnglish(code);
                }

            }catch (Exception e){
                return code;
            }
        }
    }
}
