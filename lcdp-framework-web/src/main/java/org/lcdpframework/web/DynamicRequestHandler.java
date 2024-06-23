package org.lcdpframework.web;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lcdpframework.server.executer.MappingExecutor;
import org.lcdpframework.server.holder.LcdpRequestHolder;
import org.lcdpframework.web.model.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class DynamicRequestHandler {

    @Resource
    private MappingExecutor mappingExecutor;

    public Response<Object> get(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable(value = "pid", required = false) String pid,
            @RequestBody(required = false) Map<String, Object> body,
            @RequestParam(required = false) Map<String, Object> params) {
        LcdpRequestHolder.setRequest(getRequestMap(params, body, pid)); // cache request inside thread
        return Response.ok(mappingExecutor.executeQuery(request, response));
    }

    public Response<Object> post(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable(value = "pid", required = false) String pid,
            @RequestBody(required = false) Map<String, Object> body,
            @RequestParam(required = false) Map<String, Object> params) {
        LcdpRequestHolder.setRequest(getRequestMap(body, params, pid)); // cache request inside thread
        return Response.ok(mappingExecutor.executeQuery(request, response));
    }

    public Response<Object> put(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable(value = "pid", required = false) String pid,
            @RequestBody(required = false) Map<String, Object> body,
            @RequestParam(required = false) Map<String, Object> params) {
        LcdpRequestHolder.setRequest(getRequestMap(body, params, pid)); // cache request inside thread
        return Response.ok(mappingExecutor.executeQuery(request, response));
    }

    public Response<Object> delete(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable(value = "pid", required = false) String pid,
            @RequestBody(required = false) Map<String, Object> body,
            @RequestParam(required = false) Map<String, Object> params) {
        LcdpRequestHolder.setRequest(getRequestMap(body, params, pid)); // cache request inside thread
        return Response.ok(mappingExecutor.executeQuery(request, response));
    }

    private Map<String, Object> getRequestMap(Map<String, Object> body, Map<String, Object> params, String pid) {
        Map<String, Object> map = new LinkedHashMap<>();
        Optional.ofNullable(body).ifPresent(map::putAll);
        Optional.ofNullable(params).ifPresent(map::putAll);
        Optional.ofNullable(pid).ifPresent(e -> map.put("pid", pid));
        return map;
    }
}
