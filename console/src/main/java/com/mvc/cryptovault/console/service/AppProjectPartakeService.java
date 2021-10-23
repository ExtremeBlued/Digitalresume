package com.mvc.cryptovault.console.service;

import com.mvc.cryptovault.common.bean.AppOrder;
import com.mvc.cryptovault.common.bean.AppProject;
import com.mvc.cryptovault.common.bean.AppProjectPartake;
import com.mvc.cryptovault.common.bean.dto.ImportPartake;
import com.mvc.cryptovault.common.util.ConditionUtil;
import com.mvc.cryptovault.console.common.AbstractService;
import com.mvc.cryptovault.console.common.BaseService;
import com.mvc.cryptovault.console.dao.AppProjectPartakeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppProjectPartakeService extends AbstractService<AppProjectPartake> implements BaseService<AppProjectPartake> {

    @Autowired
    AppProjectPartakeMapper appProjectPartakeMapper;
    @Autowired
    AppUserBalanceService appUserBalanceService;
    @Value("${project.frequency}")
    Long frequency;
    @Autowired
    AppMessageService appMessageService;
    @Autowired
    AppProjectService appProjectService;
    @Autowired
    AppOrderService appOrderService;

    public void savePartake(ImportPartake partake, AppProject appProject) {
        AppProjectPartake appProjectPartake = new AppProjectPartake();
        appProjectPartake.setProjectId(partake.getProjectId());
        appProjectPartake.setUserId(partake.getUserId());
        appProjectPartake = findOneByEntity(appProjectPartake);
        if (appProjectPartake == null) {
            appProjectPartake = new AppProjectPartake();
            appProjectPartake.setProjectId(partake.getProjectId());
            appProjectPartake.setUserId(partake.getUserId());
            appProjectPartake.setTokenId(appProject.getTokenId());
            appProjectPartake.setValue(partake.getValue());
            appProjectPartake.setPublishTime(appProject.getPublishAt());
            appProjectPartake.setTimes(new Float(100f / appProject.getReleaseValue()).intValue());
            appProjectPartake.setReverseValue(appProjectPartake.getValue().divide(BigDecimal.valueOf(appProjectPartake.getTimes())));
            save(appProjectPartake);
        }
    }

    public void sendProject() {
        Long time = System.currentTimeMillis();
        Condition condition = new Condition(AppProjectPartake.class);
        Example.Criteria criteria = condition.createCriteria();
        ConditionUtil.andCondition(criteria, "publish_time <= ", time);
        ConditionUtil.andCondition(criteria, "times > ", 0);
        List<AppProjectPartake> list = appProjectPartakeMapper.selectByCondition(condition);
        Map<BigInteger, AppProject> projectMap = new HashMap<>(5);
        List<AppOrder> orders = new ArrayList<>(list.size());
        list.forEach(appProjectPartake -> {
            //统计所有项目,统一发送推送
            AppProject appProject = projectMap.get(appProjec