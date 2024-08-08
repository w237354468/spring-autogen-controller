package org.lcdpframework.server.executer;

import jakarta.persistence.EntityManager;
import org.lcdpframework.query.DeleteCondition;
import org.lcdpframework.query.InsertCondition;
import org.lcdpframework.query.UpdateCondition;
import org.lcdpframework.server.concat.assemble.DeleteConditionHelper;
import org.lcdpframework.server.concat.assemble.InsertConditionHelper;
import org.lcdpframework.server.concat.assemble.UpdateConditionHelper;
import org.lcdpframework.server.concat.assemble.extend.DeleteConditionProcessor;
import org.lcdpframework.server.concat.assemble.extend.InsertConditionProcessor;
import org.lcdpframework.server.concat.assemble.extend.UpdateConditionProcessor;
import org.lcdpframework.server.enums.MethodIntentTypeEnum;
import org.lcdpframework.server.event.impl.LcdpAfterExecuteRequestEvent;
import org.lcdpframework.server.util.LcdpEventUtil;
import org.lcdpframework.sql.DeleteSQLAssemble;
import org.lcdpframework.sql.InsertSQLAssemble;
import org.lcdpframework.sql.UpdateSQLAssemble;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransactionQuery implements TransactionCallback<Object> {

    private final MethodIntentTypeEnum methodIntentTypeEnum;
    private final EntityManager entityManager;

    public TransactionQuery(MethodIntentTypeEnum methodIntent, EntityManager entityManager) {
        this.methodIntentTypeEnum = methodIntent;
        this.entityManager = entityManager;
    }

    @Override
    public Object doInTransaction(TransactionStatus status) {
        switch (methodIntentTypeEnum) {
            case OPERATE_UPDATE: {
                List<UpdateCondition> updateConditionList = UpdateConditionHelper.getUpdateCondition();
                executeSql(UpdateSQLAssemble.assembleSQL(updateConditionList, new UpdateConditionProcessor()));
                break;
            }
            case OPERATE_INSERT: {
                List<InsertCondition> insertConditionList = InsertConditionHelper.getInsertConditionFromRequest();
                executeSql(InsertSQLAssemble.assembleSQL(insertConditionList, new InsertConditionProcessor()));
                break;
            }
            case OPERATE_DELETE: {
                List<DeleteCondition> deleteConditionList = DeleteConditionHelper.getDeleteCondition();
                executeSql(DeleteSQLAssemble.assembleSQL(deleteConditionList, new DeleteConditionProcessor()));
                break;
            }
        }
        LcdpEventUtil.publishEvent(new LcdpAfterExecuteRequestEvent(this, null, null));
        return null;
    }

    public void executeSql(LinkedHashMap<String, List<Object>> sqls) {
        for (Map.Entry<String, List<Object>> entry : sqls.entrySet()) {
            String key = entry.getKey();
            entityManager.createNativeQuery(key).executeUpdate();
        }
    }
}
