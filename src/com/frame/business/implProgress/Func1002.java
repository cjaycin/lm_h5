package com.frame.business.implProgress;

import com.frame.business.comm.AbstractFunction;
import com.frame.comm.SystemConfigure;
import com.frame.domain.DataModel;
import com.frame.domain.ExecResult;
import com.frame.utils.HttpHelper;
import net.sf.json.JSONObject;

/**
 * Created by cjay on 2018-01-05.
 */
public class Func1002 extends AbstractFunction {
    @Override
    public ExecResult execute(DataModel dataModel) {
        ExecResult execResult;
        String acceptNo = dataModel.getString("acceptNo");
        checkNullParam("acceptNo", acceptNo);
        try {
            String url = SystemConfigure.getInstance().getConfig("remote").getString("location_url") + "?method=querystaffpoint&sonbr=" + acceptNo;
            System.out.println("请求坐标URL：" + url);
            String locationInfo = HttpHelper.doGet(url);
            System.out.println("坐标接口返回数据：" + locationInfo);
            DataModel data = new DataModel();
            data.put("result", locationInfo);
            execResult = new ExecResult(0, "调用成功", data);
        } catch (Exception e) {
            e.printStackTrace();
            execResult = new ExecResult(-1, "调用失败");
        }
        return execResult;
    }

    public static void main(String[] args){
        String locationInfo = "{\n" +
                "    \"so\": {\n" +
                "        \"so_nbr\": \"7117111073823231\", \n" +
                "        \"point\": {\n" +
                "            \"lng\": \"111.737318\",\n" +
                "            \"lat\": \"40.842713\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"staff:{point\": {\n" +
                "        \"lng\": \"111.682194\",\n" +
                "        \"lat\": \"40.867176\"\n" +
                "    }\"staff_name\": \"李岩_MOS\",\n" +
                "    \n" +
                "}\n" +
                "}";
        JSONObject object = JSONObject.fromObject(locationInfo);
        System.out.println(object);
    }
}
