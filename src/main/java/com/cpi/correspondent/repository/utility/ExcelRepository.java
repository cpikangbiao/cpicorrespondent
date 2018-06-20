/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CompanyRepository
 * Author:   admin
 * Date:     2018/3/30 10:31
 * Description: for cpicommon Micro Service
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.correspondent.repository.utility;

import com.cpi.correspondent.client.AuthorizedFeignClient;

import com.cpi.correspondent.config.LongTimeFeignConfiguration;
import com.cpi.correspondent.web.bean.CPICorrespondentBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈for cpicommon Micro Service〉
 *
 * @author admin
 * @create 2018/3/30
 * @since 1.0.0
 */


@AuthorizedFeignClient(name = "cpiexcel", configuration = LongTimeFeignConfiguration.class) // , fallback = TicketClientHystrix.class)
public interface ExcelRepository {
//    @RequestMapping(value = "/api/teams/", method = RequestMethod.GET)
//    List<Team> findTeams();
//
//    @RequestMapping(value = "/api/test/ports", method = RequestMethod.GET)
////    @HystrixProperty(name = "hystrix.command.default.execution.timeout.enabled", value = "false")
//    List findPorts();


    @RequestMapping(value = "/api/test/excel", method = RequestMethod.GET)
    ResponseEntity<byte[]> processExcel(@RequestParam("jxlid") Long jxlid, @RequestParam("datalist") List<CPICorrespondentBean> listData, @RequestParam("datamap") Map mapData) ;


//    private RestTemplate restTemplate = new RestTemplate();
//    ResponseDTO responseDTO = restTemplate.getForEntity("/api/endpoint", ResponseDTO.class).getBody();

//    @RequestMapping(method = RequestMethod.POST, value = "/create")
//    Message<String> create(
//        @RequestParam(value = "Type") Integer Type,
//        @RequestParam(value = "amount") String amount,
//        @RequestParam(value = "userId") String userId,
//        @RequestParam(value = "mobile") String mobile,
//        @RequestParam(value = "status") Integer status,
//        @RequestParam(value = "belong") Integer belong,
//        @RequestParam(value = "useProfit")String useProfit,
//        @RequestParam(value = "useCounter")String useCounter);
}
