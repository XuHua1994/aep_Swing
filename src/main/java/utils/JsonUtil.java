package utils;

import model.Instruction;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static Map<String, Instruction> instructionMap;

    public static List<Instruction> getInstruction() {
        List<Instruction> list = new ArrayList<>();
        List<String> joStrList = new ArrayList<>();
        //井盖指令
        joStrList.add("{\"serviceName\":\"获取所有参数\",\"serviceFlag\":\"get_all_para\",\"name\":\"get_all_para\",\"min\":\"null\",\"max\":\"null\",\"len\":1,\"unit\":\"null\"}");
        joStrList.add("{\"serviceName\":\"获取NB模组信息\",\"serviceFlag\":\"get_nb_module_imformation\",\"name\":\"get_all_para\",\"min\":\"null\",\"max\":\"null\",\"len\":1,\"unit\":\"null\"}");
        joStrList.add("{\"serviceName\":\"满溢检测冷却时间\",\"serviceFlag\":\"set_CD_timer\",\"name\":\"CD_timer\",\"min\":\"0\",\"max\":\"32767\",\"len\":2,\"unit\":\"min\"}");
        joStrList.add("{\"serviceName\":\"气体采样周期\",\"serviceFlag\":\"set_gas_sampling_period\",\"name\":\"gas_sampling_period\",\"min\":\"0\",\"max\":\"32767\",\"len\":2,\"unit\":\"min\"}");
        joStrList.add("{\"serviceName\":\"心跳周期\",\"serviceFlag\":\"set_heart_period\",\"name\":\"heart_period\",\"min\":\"1\",\"max\":\"32767\",\"len\":2,\"unit\":\"min\"}");
        joStrList.add("{\"serviceName\":\"设置安装状态\",\"serviceFlag\":\"set_installation_status\",\"name\":\"installation_status\",\"len\":1,\"enumDetail\":{\"0\":\"未安装\",\"1\":\"已安装\"}}");
        joStrList.add("{\"serviceName\":\"倾角自适应校准\",\"serviceFlag\":\"set_lean_angle_adaptive_calibration\",\"name\":\"lean_angle_adaptive_calibration\",\"len\":1,\"enumDetail\":{\"0\":\"校准成功\",\"1\":\"开始校准\"}}");
        joStrList.add("{\"serviceName\":\"设置倾斜告警阈值\",\"serviceFlag\":\"set_lean_angle_threshold\",\"name\":\"lean_angle_threshold\",\"min\":\"0\",\"max\":\"180\",\"len\":1,\"unit\":\"°\"}");
        joStrList.add("{\"serviceName\":\"满溢自适应校准\",\"serviceFlag\":\"set_overflow_adaptive_calibration\",\"name\":\"overflow_adaptive_calibration\",\"len\":1,\"enumDetail\":{\"0\":\"校准成功\",\"1\":\"开始校准\"}}");
        joStrList.add("{\"serviceName\":\"满溢检测灵敏度\",\"serviceFlag\":\"set_overflow_sensitivity\",\"name\":\"overflow_sensitivity\",\"min\":\"0\",\"max\":\"255\",\"len\":1,\"unit\":\"null\"}");
        joStrList.add("{\"serviceName\":\"安全角度阈值\",\"serviceFlag\":\"set_safe_angle_threshold\",\"name\":\"safe_angle_threshold\",\"min\":\"0\",\"max\":\"180\",\"len\":1,\"unit\":\"°\"}");
        joStrList.add("{\"serviceName\":\"系统复位\",\"serviceFlag\":\"set_systerm_reset\",\"name\":\"systerm_reset\",\"len\":1,\"enumDetail\":{\"0\":\"系统复位OK\",\"1\":\"系统重启\",\"2\":\"恢复出厂默认参数\"}}");

        //新增地磁初始化指令
        joStrList.add("{\"serviceName\":\"下发设备控制指令\",\"serviceFlag\":\"device_control\",\"name\":\"command_type\",\"len\":1,\"enumDetail\":{\"0\":\"重置无车\",\"1\":\"重置标尺\",\"2\":\"关机\",\"3\":\"强制重启\"}}");

        instructionMap = new HashMap<>();
        for (String joStr : joStrList) {
            list.add(getParsing(joStr));
        }
        return list;
    }

    public static Instruction getParsing(String joStr) {
//        String joStr = "{\"serviceName\":\"系统复位\",\"serviceFlag\":\"set_systerm_reset\",\"name\":\"systerm_reset\",\"len\":1,\"enumDetail\":{\"0\":\"系统复位OK\",\"1\":\"系统重启\",\"2\":\"恢复出厂默认参数\"}}";
        //将json字符串转化为JSONObject
        JSONObject jsonObject = JSONObject.fromObject(joStr);
        //通过getString("")分别取出里面的信息
        Instruction instruction = new Instruction();
        instruction.setName(jsonObject.getString("serviceName"));
        instruction.setSerSign(jsonObject.getString("serviceFlag"));
        instruction.setParameterKey(jsonObject.getString("name"));

        try {
            String enumDetail = jsonObject.getString("enumDetail");
            String x = enumDetail.substring(1, enumDetail.length());
            String[] strs = x.split(",");
            Map<Integer, String> map = new HashMap<>();
            for (String str : strs) {
                String[] str_vk = str.split(":");
                String keyStr, valueStr;
                keyStr = str_vk[0].substring(str_vk[0].indexOf("\"") + 1, str_vk[0].lastIndexOf("\""));
                valueStr = str_vk[1].substring(str_vk[1].indexOf("\"") + 1, str_vk[1].lastIndexOf("\""));

//                System.out.println("keyStr:"+keyStr);
//                System.out.println("valueStr:"+valueStr);
                map.put(Integer.valueOf(keyStr), keyStr + "-" + valueStr);
                instruction.setParameterValue(map);
            }
            instruction.setSign(true);
        } catch (Exception e) {
            instruction.setSign(false);
            String unit = jsonObject.getString("unit");
            if (unit == null || "null".equals(unit)) {
                unit = "";
            }
            instruction.setUnit(unit);

            String max=jsonObject.getString("max");

            if (max == null || "null".equals(max)) {
                instruction.setMax(null);
                instruction.setMin(null);
                instruction.setAnnotation(instruction.getName());
            }else {
                instruction.setMax(Integer.valueOf(jsonObject.getString("max")));
                instruction.setMin(Integer.valueOf(jsonObject.getString("min")));

                instruction.setAnnotation(instruction.getName() + "(" + instruction.getMin() + "~" + instruction.getMax() + ")" + instruction.getUnit());
            }
        }
//        System.out.println(instruction);
        instructionMap.put(instruction.getSerSign(), instruction);
        return instruction;
    }

    public static void main(String[] args) {

        getInstruction();


    }

    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param
     * @param
     * @param
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }
}
