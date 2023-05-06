package org.moonzhou.consistenthash;

import java.util.List;

public interface LoadBalancer {

    Server select(List<Server> servers, Invocation invocation);
}
