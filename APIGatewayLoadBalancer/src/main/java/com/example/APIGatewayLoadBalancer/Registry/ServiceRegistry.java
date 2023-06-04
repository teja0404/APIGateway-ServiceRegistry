package com.example.APIGatewayLoadBalancer.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class ServiceRegistry {
    public static Map<String, List<String>> serviceToInstanceMap;
    public static Map<String,String> instanceToURLMap;
    public static Map<String,String> instanceHealthCheckURLMap;
    public static Map<String,List<String>> activeSerivceToInstancesMap = new HashMap<>();

    static {
        serviceToInstanceMap = new HashMap<>();

        List<String> usersInstanceList = new LinkedList<>();
        usersInstanceList.add("users");
        usersInstanceList.add("users2");
        usersInstanceList.add("users3");
        serviceToInstanceMap.put("users", usersInstanceList);

        List<String> postsInstanceList = new LinkedList<>();
        postsInstanceList.add("posts");
        postsInstanceList.add("posts2");
        serviceToInstanceMap.put("posts", postsInstanceList);

        instanceToURLMap = new HashMap<>();
        instanceToURLMap.put("users", "http://localhost:8081/users");
        instanceToURLMap.put("users2", "http://localhost:8083/users");
        instanceToURLMap.put("users3", "http://localhost:8084/users");
        instanceToURLMap.put("posts", "http://localhost:8082/posts");
        instanceToURLMap.put("posts2", "http://localhost:8085/posts");

        instanceHealthCheckURLMap = new HashMap<>();
        instanceHealthCheckURLMap.put("users", "http://localhost:8081/ping");
        instanceHealthCheckURLMap.put("users2", "http://localhost:8083/ping");
        instanceHealthCheckURLMap.put("users3", "http://localhost:8084/ping");
        instanceHealthCheckURLMap.put("posts", "http://localhost:8082/ping");
        instanceHealthCheckURLMap.put("posts2", "http://localhost:8085/ping");
    }
}
