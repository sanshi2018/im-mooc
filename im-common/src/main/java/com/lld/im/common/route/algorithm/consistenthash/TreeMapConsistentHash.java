package com.lld.im.common.route.algorithm.consistenthash;

import com.lld.im.common.enums.UserErrorCode;
import com.lld.im.common.exception.ApplicationException;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
public class TreeMapConsistentHash extends AbstractConsistentHash {

    private final TreeMap<Long,String> treeMap = new TreeMap<>();

    /**
     * 虚拟节点的个数
     */
    private static final int NODE_SIZE = 2;

    /**
     * 添加节点,这里一个节点会对应多个虚拟节点
     * @param key
     * @param value
     */
    @Override
    protected void add(long key, String value) {
        // 创建虚拟节点
        for (int i = 0; i < NODE_SIZE; i++) {
            treeMap.put(super.hash("node" + key +i),value);
        }
        // 真实节点
        treeMap.put(key,value);
    }

    
    @Override
    protected String getFirstNodeValue(String value) {

        Long hash = super.hash(value);
        SortedMap<Long, String> last = treeMap.tailMap(hash);
        if(!last.isEmpty()){
            return last.get(last.firstKey());
        }

        if (treeMap.size() == 0){
            throw new ApplicationException(UserErrorCode.SERVER_NOT_AVAILABLE) ;
        }

        return treeMap.firstEntry().getValue();
    }

    @Override
    protected void processBefore() {
        treeMap.clear();
    }
}
