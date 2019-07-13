package com.linguochao.swagger.controller.user;

import com.linguochao.swagger.config.BaseResult;
import com.linguochao.swagger.model.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * 用户管理API
 *
 * @author linguochao
 * @date 2019\7\13 0013
 */

@Api(value = "用户管理", description = "用户管理API", position = 100, protocols = "http")
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseResult{
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());

    @ApiOperation(value = "获取用户列表", notes = "查询用户列表")
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 100, message = "异常数据")
    })
    public BaseResult<List<User>> getUserList() {
        ArrayList<User> users = new ArrayList<>(UserController.users.values());
        return success(users);
    }


    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "age", value = "年龄", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ipAddr", value = "ip哟", required = false, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseResult<User> postUser(@ApiIgnore User user) {
        users.put(user.getId(), user);
        return success(user);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return users.get(id);
    }

    @ApiOperation(value = "更新用户信息", notes = "根据用户ID更新信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "age", value = "年龄", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BaseResult<User> putUser(@PathVariable Long id, @ApiIgnore User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return success(u);
    }

    @ApiOperation(value = "删除用户", notes = "根据用户ID删除用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResult deleteUser(@PathVariable Long id) {
        users.remove(id);
        return success();
    }

    @ApiOperation(value = "忽略用户", notes = "根据用户ID忽略用户")
    @RequestMapping(value = "/ignoreMe/{id}", method = RequestMethod.DELETE)
    public BaseResult ignoreMe(@PathVariable Long id) {
        users.remove(id);
        return success();
    }
}
