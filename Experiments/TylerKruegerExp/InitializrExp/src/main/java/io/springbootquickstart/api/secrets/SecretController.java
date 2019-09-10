package io.springbootquickstart.api.secrets;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecretController {

    @RequestMapping("/secrets")
    public String secretMessage(){
        return "This was supposed to be hidden!";
    }
}
