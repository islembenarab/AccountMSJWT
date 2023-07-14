package com.accountms.serviceFeignClient.client;

import com.accountms.entity.Structure;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("demand")
public interface AccountsFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "createStructure", consumes = "application/json")
    Structure createStructure(@RequestBody Structure customer);
}
