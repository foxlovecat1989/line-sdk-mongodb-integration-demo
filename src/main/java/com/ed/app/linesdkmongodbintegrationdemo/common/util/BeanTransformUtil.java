package com.ed.app.linesdkmongodbintegrationdemo.common.util;

import com.ed.app.linesdkmongodbintegrationdemo.common.entity.po.BasePo;
import com.ed.app.linesdkmongodbintegrationdemo.common.exception.BeanTransformIllegalArgumentException;
import com.ed.app.linesdkmongodbintegrationdemo.common.exception.BeanTransformIllegalStateException;
import com.ed.app.linesdkmongodbintegrationdemo.common.model.pojo.BasePojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
@Slf4j
public class BeanTransformUtil {

    private BeanTransformUtil() {
    }

    /**
     * <pre>
     * 將 Pojo轉換至相對應的 Po型態且具有原有 Properties值, 可透過 ignoreProperties參數, 選擇忽略要移轉的 Properties值
     *
     * @param pojo - 要轉換的 Pojo
     * @param poClass - 要轉換至的 Po型別
     * @param ignoreProperties - 要忽略的移轉的 Properties
     * @return - 轉換後的 Po型別物件具有被轉換的 Pojo's Properties值
     * @throws BeanTransformIllegalArgumentException - 若是參數 pojo與參數 poClass不是相對應的型別時，將會拋出此錯誤
     * @throws BeanTransformIllegalStateException - 當 Pojo轉換至 Po時，發生 ClassNotFoundException |
     * InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException，
     * 將會拋出此錯誤
     *
     * </pre>
     */
    public static <T extends BasePojo, R extends BasePo> R transformPojo2Po(
            T pojo, Class<? extends BasePo> poClass, String... ignoreProperties) {
        var pojoSimpleClassName = pojo.getClass().getSimpleName();
        var pojoSimpleClassNameAfterTrimPojo = pojoSimpleClassName.substring(0, pojoSimpleClassName.lastIndexOf("Pojo"));
        var poSimpleClassName = poClass.getSimpleName();
        var poSimpleClassNameAfterTrimPo = poSimpleClassName.substring(0, poSimpleClassName.lastIndexOf("Po"));
        checkIsCorrespondingClassType(poSimpleClassNameAfterTrimPo, pojoSimpleClassNameAfterTrimPojo);

        R newPo;
        try {
            Class<R> poFullClassName = (Class<R>) Class.forName(poClass.getName());
            newPo = poFullClassName.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(pojo, newPo, ignoreProperties);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | NoSuchMethodException |
                 IllegalAccessException e) {
            var errorMsg = "Pojo轉換Po時發生錯誤";
            log.error(errorMsg);

            throw new BeanTransformIllegalStateException(errorMsg);
        }

        return newPo;
    }

    /**
     * <pre>
     * 將 Po轉換至相對應的 Pojo型態且具有原有 Properties值, 可透過 ignoreProperties參數, 選擇忽略要移轉的 Properties值
     *
     * @param po - 要轉換的 Po
     * @param pojoClass - 要轉換至的 Pojo型別
     * @param ignoreProperties - 要忽略的移轉的 Properties
     * @return - 轉換後的 Pojo型別物件具有被轉換的 Po's Properties值
     * @throws BeanTransformIllegalArgumentException - 若是參數 po與參數 pojoClass不是相對應的型別時，將會拋出此錯誤
     * @throws BeanTransformIllegalStateException - 當 Po轉換至 Pojo時，發生 ClassNotFoundException |
     * InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException，
     * 將會拋出此錯誤
     *
     * </pre>
     */
    public static <T extends BasePo, R extends BasePojo> R transformPo2Pojo(
            T po, Class<? extends BasePojo> pojoClass, String... ignoreProperties) {
        var poSimpleClassName = po.getClass().getSimpleName();
        var poSimpleClassNameAfterTrimPo = poSimpleClassName.substring(0, poSimpleClassName.lastIndexOf("Po"));
        var pojoSimpleClassName = pojoClass.getSimpleName();
        var pojoSimpleClassNameAfterTrimPojo = pojoSimpleClassName.substring(0, pojoSimpleClassName.lastIndexOf("Pojo"));
        checkIsCorrespondingClassType(poSimpleClassNameAfterTrimPo, pojoSimpleClassNameAfterTrimPojo);

        R newPojo;
        try {
            Class<R> pojoFullClassName = (Class<R>) Class.forName(pojoClass.getName());
            newPojo = pojoFullClassName.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(po, newPojo, ignoreProperties);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | NoSuchMethodException |
                 IllegalAccessException e) {
            var errorMsg = "Pojo轉換Po時發生錯誤";
            log.error(errorMsg);

            throw new BeanTransformIllegalStateException(errorMsg);
        }

        return newPojo;
    }

    /**
     * <pre>
     * 將 Pojo轉換至相對應的 Po型態且具有原有 Properties值, 可透過 ignoreProperties參數, 選擇忽略要移轉的 Properties值
     *
     * @param firstSimpleClassNameAfterTrim - 要比較的類別名稱(已去掉尾部的 Pojo或 Po)
     * @param secondSimpleClassNameAfterTrim - 被比較的類別名稱(已去掉尾部的 Pojo或 Po)
     * @throws BeanTransformIllegalArgumentException - 若是兩個不相等，將會拋出此錯
     *
     * </pre>
     */
    private static void checkIsCorrespondingClassType(
            String firstSimpleClassNameAfterTrim, String secondSimpleClassNameAfterTrim) {
        var isCorrespondingClassType = secondSimpleClassNameAfterTrim.equals(firstSimpleClassNameAfterTrim);
        if (isCorrespondingClassType)
            return;

        var errorMsg = "Pojo與Po非相對應的型別";
        log.error(errorMsg);

        throw new BeanTransformIllegalArgumentException(errorMsg);
    }
}
