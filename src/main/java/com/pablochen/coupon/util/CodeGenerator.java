package com.pablochen.coupon.util;

import java.util.UUID;

public class CodeGenerator {

    public static String createCode(){
        String code = UUID.randomUUID().toString().replace("-", "").substring(0,19);
        return code.substring(0,5) + "-" + code.substring(5,11) + "-" + code.substring(11);
    }

}
