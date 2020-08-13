package com.lagou.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lagou.service.UserServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 自定义的业务处理器
 */
public class UserServiceHandler extends ChannelInboundHandlerAdapter {

    //当客户端读取数据时,该方法会被调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //注意:  客户端将来发送请求的时候会传递一个参数:  UserService#sayHello#are you ok
         //1.判断当前的请求是否符合规则

        Map<String, Object> map = (Map<String, Object>)JSON.parse((String)msg);
        String method = (String)map.get("method");
        String arg1 = (String)map.get("arg1");
        String arg2 = (String)map.get("arg2");
        UserServiceImpl service = new UserServiceImpl();
        String result = null;
        Method curMethod = null;
        Method[] methods = service.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; ++i) {
            if (methods[i].getName().equals(method)) {
                curMethod = methods[i];
            }
        }
        if (arg2 != null) {
            result = (String)curMethod.invoke(service, arg1, arg2);
        } else {
            result = (String)curMethod.invoke(service, arg1);
        }
        ctx.writeAndFlush(result);
        /*if(msg.toString().startsWith("UserService")){
            //2.如果符合规则,调用实现类货到一个result
            UserServiceImpl service = new UserServiceImpl();
            String result = service.sayHello(msg.toString().substring(msg.toString().lastIndexOf("#")+1));
            //3.把调用实现类的方法获得的结果写到客户端
            ctx.writeAndFlush(result);
        }*/



    }
}
