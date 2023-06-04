package com.example.APIGatewayLoadBalancer.HealthService;

import com.example.APIGatewayLoadBalancer.Registry.ServiceRegistry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.*;

@Service
public class ServiceHealthIdentifier {

    static {
        startServiceHealthCheck();
    }

    public static void startServiceHealthCheck() {

        boolean stopHealthCheck = false;
        new Thread(() -> {
            while (!stopHealthCheck) {
                for (String service : ServiceRegistry.serviceToInstanceMap.keySet()) {
                    List<String> activeInstances = new LinkedList<>();
                    List<String> instancesList = ServiceRegistry.serviceToInstanceMap.get(service);

                        for (String instance : instancesList) {
                            boolean isNotAlive = getStatus(instance);

                            if(isNotAlive) {
                                System.out.println("Alert !!!. Instance "+instance+" is not running.");
                            } else {
                                activeInstances.add(instance);
                            }
                        }
                    ServiceRegistry.activeSerivceToInstancesMap.put(service, activeInstances);
                }
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public static boolean getStatus(String instance) {
        try {

            String URL = ServiceRegistry.instanceHealthCheckURLMap.get(instance);
            HttpHeaders headers = new HttpHeaders();
            RestTemplate restTemplate = new RestTemplate();
            headers.add("Authorization", "teja");
            HttpEntity<String> entity = new HttpEntity(headers);
            ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);

            if(response.getStatusCodeValue() == 200 && response.getBody().equals("running")) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
    }
}