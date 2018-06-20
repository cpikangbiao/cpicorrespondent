/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CpieExcelService
 * Author:   admin
 * Date:     2018/6/15 9:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.correspondent.service;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/6/15
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class ExcelService {


    public final ByteArrayOutputStream exportExcelFromTemplate(String fileName, Map<String, Object> parameters) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Context context = new Context();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            context.putVar(entry.getKey(), entry.getValue());
        }


//        StringBuilder path = new StringBuilder().append("classpath:reports/").append(fileName);
        StringBuilder path = new StringBuilder().append("reports/").append(fileName);

        ClassPathResource classPathResource = new ClassPathResource(path.toString());
        try {
//            File file = ResourceUtils.getFile(path.toString());
//            InputStream inputStream = new FileInputStream(file);
            JxlsHelper.getInstance().processTemplate(classPathResource.getInputStream(), outputStream, context);
//            outputStream.write(body);
        } catch (IOException e) {
            e.printStackTrace();

        }

        return outputStream;
    }
}


