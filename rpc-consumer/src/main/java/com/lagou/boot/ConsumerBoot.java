package com.lagou.boot;

import com.alibaba.fastjson.JSONObject;
import com.lagou.client.RPCConsumer;
import com.lagou.service.IUserService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.alibaba.fastjson.JSON;

public class ConsumerBoot {

    //参数定义

    public static void main(String[] args) throws InterruptedException {
        //2.循环给服务器写数据

        while (true){
            Map<String, Object> m = new HashMap<>();
            m.put("method", "add");
            m.put("arg1", "10");
            m.put("arg2", "10");
            JSONObject jsonObject = new JSONObject(m);
            String jsonString = jsonObject.toString();
            IUserService service = (IUserService) RPCConsumer.createProxy(IUserService.class, jsonString);
            String result = service.add("10", "10");
            System.out.println(result);
            Thread.sleep(2000);

            m.clear();
            m.put("method", "addSelf");
            m.put("arg1", "20");
            jsonObject = new JSONObject(m);
            jsonString = jsonObject.toString();
            service = (IUserService) RPCConsumer.createProxy(IUserService.class, jsonString);
            result = service.addSelf("20");
            System.out.println(result);
            Thread.sleep(2000);
            }
        }

}
