package com.lld.im.service.config;

import com.lld.im.common.config.AppConfig;
import com.lld.im.common.enums.ImUrlRouteWayEnum;
import com.lld.im.common.enums.RouteHashMethodEnum;
import com.lld.im.common.route.RouteHandle;
import com.lld.im.common.route.algorithm.consistenthash.AbstractConsistentHash;
import com.lld.im.common.route.algorithm.consistenthash.ConsistentHashHandle;
import com.lld.im.common.route.algorithm.consistenthash.TreeMapConsistentHash;
import com.lld.im.common.route.algorithm.loop.LoopHandle;
import com.lld.im.common.route.algorithm.random.RandomHandle;
import com.lld.im.service.utils.SnowflakeIdWorker;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
@Configuration
public class BeanConfig {

    @Autowired
    AppConfig appConfig;

    @Bean
    public ZkClient buildZKClient() {
        return new ZkClient(appConfig.getZkAddr(),
                appConfig.getZkConnectTimeOut());
    }

    // 构造这个bean并放入spring容器中，是为了在其他地方可以直接注入使用
    // 构造这个bean的时候，会根据配置文件中的配置，选择不同的路由算法
    @Bean
    public RouteHandle routeHandle() throws Exception {

        Integer imRouteWay = appConfig.getImRouteWay();
        String routWay = "";

        ImUrlRouteWayEnum handler = ImUrlRouteWayEnum.getHandler(imRouteWay);
        routWay = handler.getClazz();
        // 通过反射构造出不同的路由算法(负载均衡策略)
        RouteHandle routeHandle = (RouteHandle) Class.forName(routWay).newInstance();
        if(handler == ImUrlRouteWayEnum.HASH){
            // 如果是一致性hash算法，需要将hash算法注入到路由算法中
            Method setHash = Class.forName(routWay).getMethod("setHash", AbstractConsistentHash.class);
            Integer consistentHashWay = appConfig.getConsistentHashWay();
            String hashWay = "";
            // 构造出不同的hash算法
            RouteHashMethodEnum hashHandler = RouteHashMethodEnum.getHandler(consistentHashWay);
            hashWay = hashHandler.getClazz();
            AbstractConsistentHash consistentHash
                    = (AbstractConsistentHash) Class.forName(hashWay).newInstance();
            // 调用setHash方法，将hash注入到routeHandle中
            setHash.invoke(routeHandle,consistentHash);
        }

        return routeHandle;
    }

    @Bean
    public EasySqlInjector easySqlInjector () {
        return new EasySqlInjector();
    }

    @Bean
    public SnowflakeIdWorker buildSnowflakeSeq() throws Exception {
        return new SnowflakeIdWorker(0);
    }


}
