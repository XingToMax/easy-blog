package org.nuaa.tomax.easyblog.service.impl;

import org.nuaa.tomax.easyblog.entity.ClassificationEntity;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.repository.IClassificationRepository;
import org.nuaa.tomax.easyblog.service.IClassificationService;
import org.nuaa.tomax.easyblog.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/1 23:31
 */
@Service
public class ClassificationServiceImpl implements IClassificationService{

    private final IClassificationRepository classificationRepository;
    private final IResourceService resourceService;

    @Autowired
    public ClassificationServiceImpl(IClassificationRepository classificationRepository, IResourceService resourceService) {
        this.classificationRepository = classificationRepository;
        this.resourceService = resourceService;
    }

    @Override
    public Response addClassification(ClassificationEntity classification) {
        // TODO : classification can be managed by file system later
        // check null
        if (classification == null) {
            return new Response(Response.INPUT_ERROR_CODE, "input must not be null");
        }

        Response check = checkDuplication(classification.getFather(), classification.getName());
        if (check != null) {
            return check;
        }

        // save
        ClassificationEntity result = classificationRepository.save(classification);
        return new Response(Response.SUCCESS_CODE, "save success");
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Response updateClassification(ClassificationEntity classification) {
        // check update name duplication
        if (classificationRepository.findClassificationEntityByFatherAndNameAndIdNot(
                classification.getFather(), classification.getName(), classification.getId())
                .isPresent()) {
            return new Response(Response.SERVER_DATA_DUPLICATION, "name exists");
        }
        classificationRepository.updateClassificationById(
                classification.getName(), classification.getBrief(), classification.getId());
        return new Response(Response.SUCCESS_CODE, "update success");
    }

    @Override
    public Response getClassificationData(int beg, int end) {
        List<ClassificationEntity> classificationEntities = classificationRepository.findClassificationEntitiesByLimit(end - beg + 1, beg - 1);
        classificationEntities.forEach(
                e -> e.setFatherName(e.getFatherName() != null ? e.getFatherName() : "无"));
        return new Response<ClassificationEntity>(
                Response.SUCCESS_CODE,
                "get data success",
                classificationEntities
        );
    }

    @Override
    public Response deleteClassification(Long id) {
        // TODO : DELETE CLASSIFICATION ?
        // LOGISTIC IS NOT CONFIRM
        return null;
    }

    /**
     * check name is duplicated under father or not
     * @param father
     * @param name
     * @return null represents not duplicated, not null return response
     */
    private Response checkDuplication(Long father, String name) {
        // check duplication
        Optional<ClassificationEntity> check = classificationRepository
                .findClassificationEntityByFatherAndName(father,
                        name);
        if (check.isPresent()) {
            return new Response(Response.SERVER_DATA_DUPLICATION, "classification has exists");
        }

        return null;
    }
}
