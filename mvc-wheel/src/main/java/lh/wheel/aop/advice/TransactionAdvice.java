package lh.wheel.aop.advice;

import lh.wheel.annotation.transaction.Transaction;
import lh.wheel.helper.DBHelper;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

/**
 * 事务增强类
 */
public class TransactionAdvice extends Advice{

    private final Logger LOGGER = Logger.getLogger(TransactionAdvice.class);

    /**
     * 防止在事务方法中调用另一个事务方法时开启新事务
     */
    private final ThreadLocal<Boolean> FLAG_CONTAINER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public  Object doAdvice(AdviceChain adviceChain) throws Throwable {
        Method method = adviceChain.getMethod();
        Object ret;
        // 如果已经在一个事务中，则跳过该事务增强
        if(!FLAG_CONTAINER.get() && method.isAnnotationPresent(Transaction.class)) {
            FLAG_CONTAINER.set(true);
            DBHelper.startTransaction();
            LOGGER.debug("开启事务");
            try {
                ret = adviceChain.doAdviceChain();
                DBHelper.commitTransaction();
                LOGGER.debug("提交事务");
            }
            catch (Throwable throwable) {
                DBHelper.rollBackTransaction();
                LOGGER.debug("事务回滚");
                throw throwable;
            }
            finally {
                FLAG_CONTAINER.remove();
            }
        }
        else
            ret = adviceChain.doAdviceChain();

        return ret;
    }
}