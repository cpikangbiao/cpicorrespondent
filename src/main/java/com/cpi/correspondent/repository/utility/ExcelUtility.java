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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
public interface ExcelUtility {

    @RequestMapping(value = "/api/excel/export", method = RequestMethod.POST)
    ResponseEntity<byte[]> processExcel(@RequestBody() Map mapData) ;

}
