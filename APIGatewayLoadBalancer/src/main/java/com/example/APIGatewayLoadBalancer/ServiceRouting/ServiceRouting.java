package com.example.APIGatewayLoadBalancer.ServiceRouting;

import com.example.APIGatewayLoadBalancer.Registry.ServiceRegistry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

@Service
public class ServiceRouting {
    public ResponseEntity<String> getRequestResponse(HttpServletRequest request) {
        System.out.println("Incoming Request URI is "+ request.getRequestURI());
        String activeInstance = getActiveInstance(request.getRequestURI());
        String URL = getActiveInstanceEndpoint(activeInstance);
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Authorization", "teja");
        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        return ResponseEntity.ok(response.getBody());
    }
    private static String getActiveInstance(String URL) {
        String service = URL.split("/")[1];
        System.out.println("Service from incoming URL is "+ service);
        List<String> activeInstancesForService= ServiceRegistry.activeSerivceToInstancesMap.get(service);
        System.out.println("Active instances for service "+service+" are "+activeInstancesForService);
        Random rand = new Random();
        int randomIndex = rand.nextInt(activeInstancesForService.size());
        System.out.println("Routing the request to "+ activeInstancesForService.get(randomIndex));
        return activeInstancesForService.get(randomIndex);
    }
    private static String getActiveInstanceEndpoint(String activeInstance) {
        String URL = ServiceRegistry.instanceToURLMap.get(activeInstance);
        return URL;
    }
}