/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: JasperReportRepository
 * Author:   admin
 * Date:     2018/6/22 14:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.correspondent.repository.utility;


import com.cpi.correspondent.client.AuthorizedFeignClient;
import com.cpi.correspondent.config.LongTimeFeignConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/6/22
 * @since 1.0.0
 */
@AuthorizedFeignClient(name = "cpijasperreport", configuration = LongTimeFeignConfiguration.class)
public interface JasperReportRepository {

    @RequestMapping(value = "/api/jasperreport/pdf-withid", method = RequestMethod.POST)
    ResponseEntity<byte[]> processPDF(@RequestParam(value = "typeid", required = true)  Integer typeid,
                                             @RequestBody Map<String, Object> parameters);

    @RequestMapping(value = "/api/jasperreport/pdf-withfile", method = RequestMethod.POST)
    ResponseEntity<byte[]> processPDF(@RequestParam(value = "filename", required = true)  String jasperFileName,
                                             @RequestBody Map<String, Object> parameters);

    @PostMapping("/api/test/addimage")
Map<String, Object> addImageMapParamete(@RequestParam(value = "path", required = true)  String path,
                                                   @RequestParam(value = "imageFileName", required = true)  String imageFileName,
                                                   @RequestParam(value = "imageParameterName", required = true)  String imageParameterName);
}
