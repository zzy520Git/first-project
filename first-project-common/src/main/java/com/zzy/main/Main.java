package com.zzy.main;

import com.firstproject.common.util.JsonUtil;
import lombok.Data;

import java.util.List;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * @date 2019/7/30 14:12
 * @ see
 * @since
 */
public class Main {
    public static void main(String[] args) {
        String filt = "[{\"catId\":\"123\", \"valVOList\":[{\"valId\":\"666\"},{\"valId\":\"777\"}]}," +
                "{\"catId\":\"123\", \"valVOList\":[{\"valId\":\"666\"},{\"valId\":\"777\"}]}]";
        String vo = "{\"filtName\":\"abc\", \"filtValue\":" + filt + "}";
        AttrFilt attrFilt = JsonUtil.parseObject(vo, AttrFilt.class);
        for(SearchAttrCatVO cat : attrFilt.getFiltValue()) {
            System.out.println(cat);
        }
    }

    @Data
    public static class SearchAttrCatVO {
        /**
         * 销售属性类型
         */
        private String catId;
        /**
         * 销售属性类型名称
         */
        private String catName;

        /**
         * 值列表
         */
        private List<SearchAttrValVO> valVOList;

        @Data
        public static class SearchAttrValVO {
            /**
             * 销售属性值ID
             */
            private String valId;
            /**
             * 销售属性值名称
             */
            private String valName;
        }
    }
}

@Data
class AttrFilt {
    /**
     * 暂时不用
     */
    private String filtName;
    /**
     * 自营非药扩展属性筛选
     */
    private List<Main.SearchAttrCatVO> filtValue;
}
