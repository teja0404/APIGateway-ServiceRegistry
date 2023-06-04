package com.example.APIGatewayLoadBalancer.Messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveInstanceBO {
    private String service;
    private String instances;
}
