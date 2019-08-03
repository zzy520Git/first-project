package com.zzy.main;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/11/9 13:02
 */
public class TestMain1 {
    public static void main(String[] args) {
        //        Long a = 1L;

//        System.out.println(resp);
//        StringBuilder sb = new StringBuilder("gy.jd.com.");
//        System.out.println(sb.substring(0, sb.length()-1));
//
//        Runnable a = ()->{
//            //dosomething
//        };
//        List<Long> list = new ArrayList<>();
//        list.add(1L);
//        list.forEach(System.out::println);
//
//        String s = "1,3,5,7,9";
//        List<Long> data = Stream.of(s.split(",")).map(Long::valueOf).collect(Collectors.toList());
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_MONTH, -1);
//        Date d = calendar.getTime();
//        System.out.println(d.toString());

    }
}
@Setter
@Getter
class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String code;
//    private String agentNo;
//    private String merchantNo;
//    private String message;
    private List<Map> dataList;
    private List<IssuerVo> data;
}

@Setter
@Getter
class IssuerVo {
//    /**
//     * 银行编码
//     */
//    private Integer cnapBankCode;
//    /**
//     * 银行英文简称
//     */
//    private String issuerCode;
//    /**
//     * 银行名称（用于页面显示）
//     */
//    private String issuerName;
//    /**
//     * 银行简称
//     */
//    private String issuerShort;
    /**
     * 状态 ON表示可用
     */
    private String status;
}

