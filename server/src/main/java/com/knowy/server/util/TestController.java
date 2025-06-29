package com.knowy.server.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    JwtService jwtService;

    public TestController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("sara")
    public Sara sara() {
        Sara sara = new Sara(1, "Sara");
        return jwtService.decode(jwtService.encode(sara), Sara.class);
    }

}
