package com.fg.system.modules.base;

import cn.hutool.core.convert.Convert;
import com.fg.system.common.util.page.QueryRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BaseController {
    private Map<String, Object> getDataTable(PageInfo<?> pageInfo) {
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("pageNum",Convert.toInt(pageInfo.getPageNum()));
        rspData.put("pageSize",Convert.toInt(pageInfo.getSize()));
        rspData.put("total",pageInfo.getTotal());
        rspData.put("totalPage",Convert.toInt(pageInfo.getTotal()/pageInfo.getSize()+1));
        rspData.put("rows", pageInfo.getList());
        return rspData;
    }

    protected Map<String, Object> selectByPageNumSize(QueryRequest request, Supplier<?> s) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        PageInfo<?> pageInfo = new PageInfo<>((List<?>) s.get());
        PageHelper.clearPage();
        return getDataTable(pageInfo);
    }

}
