package com.lld.im.common.route.algorithm.consistenthash;

import com.lld.im.common.route.RouteHandle;

import java.util.List;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
public class ConsistentHashHandle implements RouteHandle {

    //TreeMap
    private AbstractConsistentHash hash;

    // Bean注入时通过反射调用这个方法
    public void setHash(AbstractConsistentHash hash) {
        this.hash = hash;
    }

    @Override
    public String routeServer(List<String> values, String key) {
        return hash.process(values,key);
    }
}
