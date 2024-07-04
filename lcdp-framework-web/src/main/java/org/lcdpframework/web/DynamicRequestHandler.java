package org.lcdpframework.web;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lcdpframework.server.executer.MappingExecutor;
import org.lcdpframework.web.model.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DynamicRequestHandler {

    @Resource
    private MappingExecutor mappingExecutor;

    public Response<Object> get(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable(value = "pid", required = false) String pid,
            @RequestBody(required = false) Map<String, Object> body,
            @RequestParam(required = false) Map<String, Object> params) {
        return Response.ok(mappingExecutor.executeQuery(request, response));
    }

    public Response<Object> post(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable(value = "pid", required = false) String pid,
            @RequestBody(required = false) Map<String, Object> body,
            @RequestParam(required = false) Map<String, Object> params) {
        return Response.ok(mappingExecutor.executeQuery(request, response));
    }

    public Response<Object> put(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable(value = "pid", required = false) String pid,
            @RequestBody(required = false) Map<String, Object> body,
            @RequestParam(required = false) Map<String, Object> params) {
        return Response.ok(mappingExecutor.executeQuery(request, response));
    }

    public Response<Object> delete(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable(value = "pid", required = false) String pid,
            @RequestBody(required = false) Map<String, Object> body,
            @RequestParam(required = false) Map<String, Object> params) {
        return Response.ok(mappingExecutor.executeQuery(request, response));
    }
}
