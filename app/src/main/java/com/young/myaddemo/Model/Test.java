package com.young.myaddemo.Model;

import com.young.myaddemo.utils.Constant;

public class Test {

    TaskModel taskModel = new TaskModel();
    TaskManager taskManager = new TaskManager();


    public void test() {
        taskModel.platform = Constant.ST_API;
        //第一步创建对应api平台的模型
        taskModel.parserAds();
        BrushRequestManager.postAdRequest(taskModel.adModel, taskManager);
    }

}
