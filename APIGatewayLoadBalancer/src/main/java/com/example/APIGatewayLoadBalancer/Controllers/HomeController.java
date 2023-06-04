package com.example.APIGatewayLoadBalancer.Controllers;

import com.example.APIGatewayLoadBalancer.HealthService.ServiceHealthIdentifier;
import com.example.APIGatewayLoadBalancer.Messages.ActiveInstanceBO;
import com.example.APIGatewayLoadBalancer.Registry.ServiceRegistry;
import com.example.APIGatewayLoadBalancer.ServiceRouting.ServiceRouting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class HomeController {

    @Autowired
    ServiceRouting serviceRouting;

    @RequestMapping("/**")
    public ResponseEntity<String> getRequestResponse(HttpServletRequest request) {
        return serviceRouting.getRequestResponse(request);
    }

    @RequestMapping("/activeinstances")
    public List<ActiveInstanceBO> getActiveInstances() {
        List<ActiveInstanceBO> activeInstances = new ArrayList<>();
        for(String key : ServiceRegistry.activeSerivceToInstancesMap.keySet()) {
            ActiveInstanceBO activeInstanceBO = new ActiveInstanceBO();
            activeInstanceBO.setService(key);
            activeInstanceBO.setInstances(ServiceRegistry.activeSerivceToInstancesMap.get(key).toString());
            activeInstances.add(activeInstanceBO);
        }
        return activeInstances;
    }
}
