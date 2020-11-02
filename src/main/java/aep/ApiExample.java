package aep;

import com.ctg.ag.sdk.biz.AepDeviceCommandClient;
import com.ctg.ag.sdk.biz.aep_device_command.CreateCommandRequest;
import com.ctg.ag.sdk.biz.aep_device_command.CreateCommandResponse;
import model.ResultBean;

public class ApiExample {

    public static ResultBean lwm2mExample1(String secret , String appKey, String MasterKey, String bodyString)  {
        ResultBean result=new ResultBean();
        result.setCode(204);
        AepDeviceCommandClient client = AepDeviceCommandClient.newClient()
                .appKey(appKey).appSecret(secret)
                .build();

        CreateCommandRequest request = new CreateCommandRequest();
        // set your request params here
        request.setParamMasterKey(MasterKey);	// single value
//        request.addParamMasterKey(MasterKey);	// or multi values
        request.setBody(bodyString.getBytes());	//具体格式见前面请求body说明
        try {
            CreateCommandResponse response=client.CreateCommand(request);
            String message=response.getMessage();
            Integer code=response.getStatusCode();
            result.setMessage(message);
            result.setCode(code);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
        return result;
    }

}