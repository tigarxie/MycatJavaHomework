package com.tigar.intercept;

import com.tigar.common.response.AjaxResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tigar
 * @date 2019/1/25.
 */
@RestController
@RequestMapping("/webtests")
public class WebTestsController {
    @GetMapping("check")
    public AjaxResponse<String> check() throws Exception {
        throw new Exception("s");
    }
}
