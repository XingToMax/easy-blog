package org.nuaa.tomax.easyblog.service;

import org.nuaa.tomax.easyblog.entity.ClassificationEntity;
import org.nuaa.tomax.easyblog.entity.Response;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/1 23:28
 */
public interface IClassificationService {
    /**
     * add new classification
     * @param classification
     * @return
     */
    Response addClassification(ClassificationEntity classification);

    /**
     * update classification data
     * @param classification
     * @return
     */
    Response updateClassification(ClassificationEntity classification);

    /**
     * get all classification data
     * @return
     */
    Response getClassificationData(int beg, int end);

    /**
     * delete classification by id
     * @param id
     * @return
     */
    Response deleteClassification(Long id);

    Response getLabelList();
}
