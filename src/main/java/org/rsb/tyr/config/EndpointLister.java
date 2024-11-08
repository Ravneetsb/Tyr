package org.rsb.tyr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EndpointLister {
  private final RequestMappingHandlerMapping handlerMapping;

  @Autowired
  public EndpointLister(RequestMappingHandlerMapping handlerMapping) {
    this.handlerMapping = handlerMapping;
  }

  public List<EndpointInfo> getEndpoints() {
    List<EndpointInfo> endpoints = new ArrayList<>();

    Map<RequestMappingInfo, HandlerMethod> handlers = handlerMapping.getHandlerMethods();

    for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlers.entrySet()) {
      RequestMappingInfo mapping = entry.getKey();
      HandlerMethod method = entry.getValue();

      // Skip Spring's internal endpoints
      if (method.getBeanType().getPackageName().startsWith("org.springframework")) {
        continue;
      }

      Set<String> patterns = mapping.getPatternValues();
      Set<String> methods =
          mapping.getMethodsCondition().getMethods().stream()
              .map(Enum::name)
              .collect(Collectors.toSet());

      String controllerName = method.getBeanType().getSimpleName();
      String methodName = method.getMethod().getName();

      for (String pattern : patterns) {
        endpoints.add(new EndpointInfo(pattern, methods, controllerName, methodName));
      }
    }

    return endpoints;
  }

  public record EndpointInfo(
      String path, Set<String> methods, String controller, String methodName) {}
}
