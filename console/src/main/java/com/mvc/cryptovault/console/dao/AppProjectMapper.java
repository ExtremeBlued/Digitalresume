package com.mvc.cryptovault.console.dao;

import com.mvc.cryptovault.common.bean.AppProject;
import com.mvc.cryptovault.console.common.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AppProjectMapper extends MyMapper<AppProject> {

    @Update("update app_project set `status` = 1 WHERE started_at <= #{currentTimeMillis} and `status` = 0")
    Integer upd