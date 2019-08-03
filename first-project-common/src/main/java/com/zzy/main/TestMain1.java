package com.zzy.main;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/11/9 13:02
 */
public class TestMain1 {
    public static void main(String[] args) {
        Pattern JDURLREGEXP = Pattern.compile("^((http)|(https)|(openapp\\.jdmedicine))?://([-_\\w]+\\.)+jd\\.com(/.*)?$");
        Pattern MDURLREGEXP = Pattern.compile("^((http)|(https)|(openapp\\.jdmedicine))?://([-_\\w]+\\.)+jd\\.com(/.*)?$");
        System.out.println(JDURLREGEXP.matcher("https://pro.m.jd.com/yaojingcai/active/2KTGBpzEcoucQP9q7hgAsaxn8HUY/index.html").matches());
        //System.out.println("1235234".matches("^[\\u4e00-\\u9fa5a-zA-Z0-9]{2,50}$"));
//        Long a = 1L;
//        System.out.println(a + "xx");
//        final Pattern JDURLREGEXP = Pattern.compile("^((http)|(https)|(openapp\\.jdmedicine))?://([-_\\w]+\\.)+jd\\.com(/.*)?$");
//        ;
//        System.out.println(JDURLREGEXP.matcher(
//                "https://123-___-.m.jd.com/yaojingcai/active/4RVg8JditUSiXFg4roB9RWauDD1W/index.html?env=beta").matches());
////        String a  =null;
//        System.out.println(a+"asdbaj");
//        String str = "{\"code\":\"200\", \"dataList\":[{\"key\":1},{\"key\":1}], data:[{\"status\":\"ON\", \"b\":\"qw\"},{\"status\":\"ON\", \"b\":\"qw\"}]}" ;
//        Response<List<IssuerVo>> resp = JSONObject.parseObject(str, Response.class);
//        if(CollectionUtils.isNotEmpty(resp.getData())) {
//            System.out.println(JSON.toJSONString(resp.getData()));
//            List<IssuerVo> aa = resp.getData();
//            List<IssuerVo> rr  = aa.stream().filter(t->t.getStatus().equals("ON")).collect(Collectors.toList());
//        }
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

