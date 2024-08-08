package org.lcdpframework.server.executer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lcdpframework.query.QueryCondition;
import org.lcdpframework.server.concat.assemble.DetailQueryConditionHelper;
import org.lcdpframework.server.concat.assemble.ListQueryConditionHelper;
import org.lcdpframework.server.concat.assemble.extend.SelectFieldConvert;
import org.lcdpframework.server.concat.assemble.extend.SelectWhereFieldConvert;
import org.lcdpframework.server.dto.LcdpMappingDTO;
import org.lcdpframework.server.enums.MethodIntentTypeEnum;
import org.lcdpframework.server.event.impl.LcdpAfterExecuteRequestEvent;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.holder.LcdpRequestHolder;
import org.lcdpframework.server.log.Log;
import org.lcdpframework.server.util.LcdpEventUtil;
import org.lcdpframework.sql.SelectSQLAssemble;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.lcdpframework.server.constants.BaseConstants.PAGE_NUM;
import static org.lcdpframework.server.constants.BaseConstants.PAGE_SIZE;
import static org.lcdpframework.server.log.Log.LOGGER_TYPE.SYSTEM;

@Service
public class MappingExecutor {

    private final StopWatch stopWatch = new StopWatch();
    private final TransactionTemplate transactionTemplate;
    private final EntityManager entityManager;

    public MappingExecutor(TransactionTemplate transactionTemplate, EntityManager entityManager) {
        this.transactionTemplate = transactionTemplate;
        this.entityManager = entityManager;
    }

    public Object executeQuery(HttpServletRequest request, HttpServletResponse response) {

        LcdpMappingDTO mapping = LcdpGlobalParamHolder.getMapping();

        MethodIntentTypeEnum methodIntent = MethodIntentTypeEnum.getMethodIntentType(mapping.getMethodIntent());

        switch (methodIntent) {
            // ---------------transaction query ---------------
            case OPERATE_UPDATE:
            case OPERATE_DELETE:
            case OPERATE_INSERT: {
                transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                return transactionTemplate.execute(new TransactionQuery(methodIntent, entityManager));
            }
            // ---------------detail query ---------------
            case OPERATE_SELECT_DETAIL: {
                QueryCondition detailQueryCondition = DetailQueryConditionHelper.getDetailQueryCondition();
                Map.Entry<String, List<Object>> detailSQLs = SelectSQLAssemble.assembleSQL(detailQueryCondition, new SelectFieldConvert(), new SelectWhereFieldConvert());
                return executeDetailSQLQuery(detailSQLs);
            }
            // ---------------list query---------------
            case OPERATE_SELECT_LIST: {
                QueryCondition queryCondition = ListQueryConditionHelper.getListQueryCondition();
                Map.Entry<String, List<Object>> listSQLs = SelectSQLAssemble.assembleSQL(queryCondition, new SelectFieldConvert(), new SelectWhereFieldConvert());
                return executeListSQLQuery(listSQLs);
            }
        }
        return null;
    }

    private List executeDetailSQLQuery(Map.Entry<String, List<Object>> queryInfoPair) {
        Query query = resolveExecuteSQL(queryInfoPair, null);
        List resultList = query.getResultList();
        LcdpEventUtil.publishEvent(new LcdpAfterExecuteRequestEvent(this, null, resultList));
        return resultList;
    }

    private Page<List> executeListSQLQuery(Map.Entry<String, List<Object>> queryInfoPair) {
        stopWatch.start();
        PageRequest page = getPage();
        Query query = resolveExecuteSQL(queryInfoPair, page);

        PageImpl<List> lists = new PageImpl<List>(query.getResultList(), page, getTotal(queryInfoPair));

        stopWatch.stop();
        Log.info(SYSTEM, "execute list query for {} ms", stopWatch.getTotalTime(TimeUnit.MILLISECONDS));

        LcdpEventUtil.publishEvent(new LcdpAfterExecuteRequestEvent(this, lists, null));
        return lists;
    }

    private long getTotal(Map.Entry<String, List<Object>> queryInfoPair) {
        String sql = queryInfoPair.getKey();
        String countSql = "select count(*) from ( " + sql + " )";
        List<Object> params = queryInfoPair.getValue();
        Query query = entityManager.createNativeQuery(countSql);

        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i + 1, params.get(i));
        }
        return ((Number) query.getSingleResult()).longValue();
    }

    private Query resolveExecuteSQL(Map.Entry<String, List<Object>> queryInfoPair, PageRequest page) {
        String sql = queryInfoPair.getKey();
        List<Object> params = queryInfoPair.getValue();
        Query query = entityManager.createNativeQuery(sql, Map.class);

        for (int i = 0; i < params.size(); i++) {
            query.setParameter(1 + i, params.get(i));
        }
        if (page != null) {
            int pageNum = page.getPageNumber();
            int pageSize = page.getPageSize();
            query.setFirstResult((pageNum - 1) * pageSize); // offset
            query.setMaxResults(pageSize); // limit
        }
        return query;
    }

    private static PageRequest getPage() {
        Map<String, Object> queryMap = LcdpRequestHolder.getQueryMap();
        Integer pageSize = (Integer) queryMap.get(PAGE_SIZE);
        Integer pageNum = (Integer) queryMap.get(PAGE_NUM);
        if (Objects.isNull(pageSize) || Objects.isNull(pageNum)) {
            return PageRequest.of(10, 1);
        }
        return PageRequest.of(pageNum, pageSize);
    }
}
