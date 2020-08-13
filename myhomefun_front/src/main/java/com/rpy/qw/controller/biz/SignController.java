package com.rpy.qw.controller.biz;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.biz.service.SignService;
import com.rpy.qw.biz.vo.SignVo;
import com.rpy.qw.result.Result;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: myfunhome
 * @description: 签到管理
 * @author: 任鹏宇
 * @create: 2020-07-13 21:22
 **/
@RestController
@RequestMapping("sign")
@Api(tags = "签到管理")
@ApiSort(11)
public class SignController {

    @Autowired
    private SignService signService;



    /**
     *  用户签到
     * @return
     */
    @ApiOperation(value = "用户签到")
    @ApiOperationSupport(order=1
    )
    @RequestMapping(value = "signIn",method = RequestMethod.GET)
    public Result<Object> signIn(){
        Map<String,Object>  data =signService.sigIn();
        Integer continueToday = (Integer) data.get("continueToday");
        String msg=null;
        if( 1 == continueToday){
            msg="签到成功！获得"+data.get("todayPoints")+"积分";
        }else {
            msg="签到成功！你已连续签到"+continueToday+"天\n获得"+data.get("todayPoints")+"积分";
        }
        return new Result<>(msg);
    }



    /**
     * 分页查询今天的所有签到情况
     * @return
     */
    @ApiOperation(value = "查询今天的所有签到情况")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping(value = "/getTodayAllSign",method = RequestMethod.POST)
    public Result<PageVo<SignVo>> getTodayAllSign(@RequestBody PageVo<SignVo> pageVo){
        PageVo<SignVo> data=signService.getTodayAllSign(pageVo);
        return new Result<>(data);
    }

    /**
     * 分页查询用户总签到连续数
     * @return
     */
    @ApiOperation(value = "分页查询用户总签到连续数")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping(value = "/getContinueSignDay",method = RequestMethod.POST)
    public Result<PageVo<SignVo>> getContinueSignDay(@RequestBody PageVo<SignVo> pageVo){
        PageVo<SignVo> data=signService.getContinueSignDay(pageVo);
        return new Result<>(data);
    }

}
