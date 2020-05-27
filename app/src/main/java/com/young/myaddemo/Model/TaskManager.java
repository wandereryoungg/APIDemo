package com.young.myaddemo.Model;


import com.young.myaddemo.utils.Constant;

import java.util.List;

public class TaskManager implements BrushRequestManager.RequestCallback<List<AdModel>> {

    @Override
    public void onSuccessCallBack(List<AdModel> response, Object... objs) {
        if (response == null) {
            return;
        }
        for (AdModel model : response) {
            switch (model.platform) {
                case Constant.ST_API:
                    new STApiProcess((STApiModel) model).start();
                    break;
            }
        }
    }

    @Override
    public void onFailCallback(Exception e) {

    }
}
