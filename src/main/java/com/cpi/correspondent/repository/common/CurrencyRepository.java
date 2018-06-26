/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CountryRepository
 * Author:   admin
 * Date:     2018/4/26 8:44
 * Description: get country from remote microservice
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */


package com.cpi.correspondent.repository.common;

import com.cpi.correspondent.client.AuthorizedFeignClient;
import com.cpi.correspondent.service.dto.common.CurrencyDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 〈一句话功能简述〉<br>
 * 〈get country from remote microservice〉
 *
 * @author admin
 * @create 2018/4/26
 * @since 1.0.0
 */

@AuthorizedFeignClient(name = "cpicommon")
public interface CurrencyRepository {

    @RequestMapping(value = "/api/currencies/{id}", method = RequestMethod.GET)
    CurrencyDTO findCurrencyByID(@PathVariable("id") Long id);

}
