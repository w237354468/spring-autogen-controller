package org.lcdpframework.server.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;

import static org.lcdpframework.server.log.LcdpLog.LOGGER_TYPE.*;

public class LcdpLog {

    private static final Logger SYSTEM_LOGGER = LoggerFactory.getLogger("LCDP_SYSTEM");
    private static final Logger BUSINESS_CONTROLLER_LOGGER = LoggerFactory.getLogger("LCDP_BUSINESS_CONTROLLER");
    private static final Logger BUSINESS_SERVICE_LOGGER = LoggerFactory.getLogger("LCDP_BUSINESS_SERVICE");
    private static final Logger MDL_CONNECTOR_LOGGER = LoggerFactory.getLogger("LCDP_MDL_CONNECTOR");
    private static final Logger MDL_LIST_LOGGER = LoggerFactory.getLogger("LCDP_MDL-LIST-Connector");
    private static final Logger MDL_UPDATE_LOGGER = LoggerFactory.getLogger("LCDP_MDL-UPDATE-Connector");
    private static final Logger MDL_ONE_QUERY_LOGGER = LoggerFactory.getLogger("LCDP_MDL-ONE-Connector");
    private static final Logger MDL_ADD_LOGGER = LoggerFactory.getLogger("LCDP_MDL-ADD-Connector");
    private static final Logger MDL_DELETE_LOGGER = LoggerFactory.getLogger("LCDP_MDL-DELETE-Connector");
    private static final Logger MDL_IMPORT_LOGGER = LoggerFactory.getLogger("LCDP_MDL-IMPORT-Connector");
    private static final Logger MDL_EXPORT_LOGGER = LoggerFactory.getLogger("LCDP_MDL-EXPORT-Connector");
    private static final Logger THREADLOCAL_LOGGER = LoggerFactory.getLogger("LCDP_THREADLOCAL");

    public enum LOGGER_TYPE {
        MDL_CONNECTOR, EXT_CONNECTOR, SQL_CONNECTOR, BUSINESS_CONTROLLER, BUSINESS_SERVICE,
        MDL_UPDATE, MDL_LIST, MDL_ONE, MDL_ADD, MDL_DELETE, MDL_IMPORT, MDL_EXPORT, ASSEMBLE,
        SYSTEM, WORK_FLOW, THREAD_LOCAL
    }

    private static final Map<LOGGER_TYPE, Logger> typeWithLogger = new EnumMap<>(LOGGER_TYPE.class);

    static {
        typeWithLogger.put(SYSTEM, SYSTEM_LOGGER);
        typeWithLogger.put(BUSINESS_CONTROLLER, BUSINESS_CONTROLLER_LOGGER);
        typeWithLogger.put(BUSINESS_SERVICE, BUSINESS_SERVICE_LOGGER);
        typeWithLogger.put(MDL_CONNECTOR, MDL_CONNECTOR_LOGGER);
        typeWithLogger.put(MDL_UPDATE, MDL_UPDATE_LOGGER);
        typeWithLogger.put(MDL_LIST, MDL_LIST_LOGGER);
        typeWithLogger.put(MDL_ONE, MDL_ONE_QUERY_LOGGER);
        typeWithLogger.put(MDL_ADD, MDL_ADD_LOGGER);
        typeWithLogger.put(MDL_DELETE, MDL_DELETE_LOGGER);
        typeWithLogger.put(MDL_IMPORT, MDL_IMPORT_LOGGER);
        typeWithLogger.put(MDL_EXPORT, MDL_EXPORT_LOGGER);
        typeWithLogger.put(THREAD_LOCAL, THREADLOCAL_LOGGER);
    }

    public static void printDebug(LOGGER_TYPE type, String format, Object... args) {

        Logger logger = typeWithLogger.get(type);
        if (logger.isDebugEnabled()) {
            logger.debug(format, args);
        }
    }

    public static void printDebug(LOGGER_TYPE type, String format, Object arg) {
        Logger logger = typeWithLogger.get(type);
        if (logger.isDebugEnabled()) {
            logger.debug(format, arg);
        }
    }

    public static void printDebug(LOGGER_TYPE type, String msg) {
        Logger logger = typeWithLogger.get(type);
        if (logger.isDebugEnabled()) {
            logger.debug(msg);
        }
    }

    public static void printInfo(LOGGER_TYPE type, String format, Object... args) {

        typeWithLogger.get(type).info(format, args);
    }

    public static void printInfo(LOGGER_TYPE type, String format, Object arg) {

        typeWithLogger.get(type).info(format, arg);
    }

    public static void printInfo(LOGGER_TYPE type, String msg) {

        typeWithLogger.get(type).info(msg);
    }

    public static void printWarn(LOGGER_TYPE type, String format, Object... args) {

        typeWithLogger.get(type).warn(format, args);
    }

    public static void printWarn(LOGGER_TYPE type, String format, Object arg) {

        typeWithLogger.get(type).warn(format, arg);
    }

    public static void printWarn(LOGGER_TYPE type, String msg) {

        typeWithLogger.get(type).warn(msg);
    }

    public static void printError(LOGGER_TYPE type, String msg) {

        typeWithLogger.get(type).error(msg);
    }

    public static void printError(LOGGER_TYPE type, String format, Object arg) {

        typeWithLogger.get(type).error(format, arg);
    }

    public static void printError(LOGGER_TYPE type, String format, Object arg, Object arg2) {

        typeWithLogger.get(type).error(format, arg, arg2);
    }

    public static void printError(LOGGER_TYPE type, String format, Object... args) {

        typeWithLogger.get(type).error(format, args);
    }
}
