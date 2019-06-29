package com.linguochao.client;

import entity.Result;
import entity.StatusCode;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
public class UserClientImpl implements UserClient {
    @Override
    public Result updateFollowcount(String userid, int x) {
        return new Result(false, StatusCode.ERROR,"服务有问题，熔断器开启了...");
    }

    @Override
    public Result updateFanscount(String userid, int x) {
        return new Result(false, StatusCode.ERROR,"服务有问题，熔断器开启了...");
    }
}
