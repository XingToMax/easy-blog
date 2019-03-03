package org.nuaa.tomax.easyblog.controller.admin;

import org.nuaa.tomax.easyblog.annotation.ServiceLog;
import org.nuaa.tomax.easyblog.entity.ClassificationEntity;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.service.IClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/2 9:35
 */
@Controller
@CrossOrigin
@RequestMapping("/admin/classification")
public class ClassificationController {
    private final IClassificationService classificationService;

    @Autowired
    public ClassificationController(IClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    @GetMapping
    public @ResponseBody
    @ServiceLog
    Response getClassificationData(int page, int limit) {
        return classificationService.getClassificationData(page, limit);
    }

    @PostMapping
    @ServiceLog
    public @ResponseBody
    Response createNewClassification(ClassificationEntity classification) {
        return classificationService.addClassification(classification);
    }

    @PutMapping
    @ServiceLog
    public @ResponseBody
    Response updateClassification(ClassificationEntity classification) {
        return classificationService.updateClassification(classification);
    }

    @DeleteMapping
    @ServiceLog
    public @ResponseBody
    Response deleteClassification(Long id) {
        return classificationService.deleteClassification(id);
    }
}
