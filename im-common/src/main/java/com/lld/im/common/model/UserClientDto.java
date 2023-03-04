package com.lld.im.common.model;

import lombok.Data;

/**
 * @description: 这里UserClientDto作为HashMap的key，需要重写equals和hashCode方法
 * @author: lld
 * @version: 1.0
 */
@Data
public class UserClientDto {

    private Integer appId;

    private Integer clientType;

    private String userId;

    private String imei;

}
