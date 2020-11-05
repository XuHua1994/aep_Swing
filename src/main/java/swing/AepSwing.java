package swing;

import aep.ApiExample;
import config.SysConfig;
import model.Instruction;
import model.ResultBean;
import model.ViewType;
import utils.PromptUtil;
import utils.JTextFieldHintListener;
import utils.JsonUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AepSwing extends JFrame implements ActionListener {
    private JPanel jp = new JPanel();
    private ButtonGroup bg = new ButtonGroup();
    private JRadioButton[] jrb = new JRadioButton[]{new JRadioButton("单发", true), new JRadioButton("群发")};

//    private JButton[] jb = {new JButton("浏览")};
    private JButton[] jb_confirm = {new JButton("确定"), new JButton("取消")};
    private JLabel[] jlChange = {new JLabel("设备ID:"), new JLabel("群组ID:")};
    //0:设备ID&1:群组ID
    private JTextField[] jtf = {new JTextField(25), new JTextField(25)};


    //AEP配置参数 secret,appKey,MasterKey,
    private JLabel[] jlAep = {new JLabel("产品ID:"), new JLabel("MasterKey:"),
            new JLabel("App Key:"), new JLabel("App Secret:"), new JLabel("操作者:")};
    //0:产品ID&1:MasterKey&2:App Key&3:App Secret&4:操作者
    private JTextField[] jtfAep = {new JTextField(25), new JTextField(50),
            new JTextField(25), new JTextField(25), new JTextField(25)};


    private JLabel[] jl = {new JLabel("下发模式："), new JLabel("下发指令:")};
    private JLabel jl_variable = new JLabel();
    private JComboBox[] jcb = {};
    private JFileChooser fc = new JFileChooser();
    private JComboBox cmb1 = new JComboBox();    //创建JComboBox

    //下拉框联动组件
    private JComboBox cmb_Select = new JComboBox();    //创建JComboBox
    private JTextField jtf_Select = new JTextField(25);

    //    private JTextArea jt = new JTextArea();
//    private JScrollPane js= new JScrollPane(jt);
    public AepSwing() {
//绝对布局
        jp.setLayout(null);

//下发模式
        jl[0].setBounds(20, 20, 150, 30);
        jp.add(jl[0]);
        for (int i = 0; i < jrb.length; i++) {
            jrb[i].setBounds(130 + 120 * i, 20, 120, 30);
            jp.add(jrb[i]);
            jrb[i].addActionListener(this);
            bg.add(jrb[i]);
        }

//AEP配置
//        jlAep,jtfAep
        //0:产品ID&1:MasterKey&2:App Key&3:App Secret&4:操作者
//            String productId = jtfAep[0].getText();
//            String MasterKey = jtfAep[1].getText();
//            String appKey = jtfAep[2].getText();
//            String secret = jtfAep[3].getText();
//            String operator = jtfAep[4].getText();
        //SysConfig.getProperty("appkey");
        jtfAep[0].setText(SysConfig.getProperty("aep.productId"));
        jtfAep[1].setText(SysConfig.getProperty("aep.MasterKey"));
        jtfAep[2].setText(SysConfig.getProperty("aep.appKey"));
        jtfAep[3].setText(SysConfig.getProperty("aep.secret"));
        jtfAep[4].setText(SysConfig.getProperty("aep.operator"));

        for (int i = 0; i < jlAep.length; i++) {
            jlAep[i].setBounds(370, 20 + 40 * i, 80, 30);
            jp.add(jlAep[i]);
            jtfAep[i].setBounds(370 + 80, 20 + 40 * i, 160, 30);
            jp.add(jtfAep[i]);
        }


//初始化显示单发
        jlChange[0].setBounds(20, 55, 100, 40);
        jp.add(jlChange[0]);
        jtf[0].addFocusListener(new JTextFieldHintListener(jtf[0], "请输入设备ID"));
        jtf[0].setBounds(110, 62, 200, 30);
        jp.add(jtf[0]);

        jlChange[1].setVisible(false);
        jtf[1].setVisible(false);
//        jb[0].setVisible(false);
        jlChange[1].setBounds(20, 55, 100, 40);
        jp.add(jlChange[1]);
        jtf[1].addFocusListener(new JTextFieldHintListener(jtf[1], "请输入群组ID"));
        jtf[1].setBounds(110, 62, 200, 30);
        jp.add(jtf[1]);
        //导入按钮
//        jb[0].setBounds(320,62,60,30);
//        jp.add(jb[0]);
//        jb[0].addActionListener(this);


//下发指令模块
        jl[1].setBounds(20, 20 + 100, 150, 30);
        jp.add(jl[1]);
        //下拉框
        cmb1.addItem("--请选择--");    //向下拉列表中添加一项
        List<Instruction> instructions = JsonUtil.getInstruction();
        List<ViewType> list = new ArrayList<>();
        for (Instruction instruction : instructions) {
            ViewType viewType = new ViewType();
            viewType.setProCode(instruction.getName());
            viewType.setTypeName(instruction.getSerSign());
            list.add(viewType);
        }
        for (ViewType v : list) {
            cmb1.addItem(v);
        }
        cmb1.setBounds(110, 127, 200, 30);
        cmb1.addActionListener(this);
        jp.add(cmb1);


//确定||取消
        jb_confirm[0].setBounds(380, 400, 100, 20);
        jp.add(jb_confirm[0]);
        jb_confirm[0].addActionListener(this);
        jb_confirm[1].setBounds(500, 400, 100, 20);
        jb_confirm[1].addActionListener(this);
        jp.add(jb_confirm[1]);
//        jt.setLineWrap(true);
//        jt.setEditable(false);

//面板参数设计
        this.add(jp);
        this.setTitle("AEP指令下发平台");
        this.setBounds(550, 250, 650, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent a) {
        //单发点击事件
        if (a.getSource() == jrb[0] || a.getSource() == jrb[1]) {
            Boolean sign = (a.getSource() == jrb[0]) ? true : false;
            showModel(sign);
        }
//        else if (a.getSource() == jb[0]) { //导入按钮
//            int intRetVal = fc.showOpenDialog(this);
//            if (intRetVal == JFileChooser.APPROVE_OPTION) {
//                jtf[1].setText(fc.getSelectedFile().getPath());
//            }
//        }
        else if (a.getSource() == cmb1) {
            if (cmb1.getSelectedIndex() == 0) {
                jl_variable.setVisible(false);
                JOptionPane.showMessageDialog(null, "请选择指令!", "提示", JOptionPane.INFORMATION_MESSAGE);
//                        System.exit( 0 );
                return;
            }
            Object c = cmb1.getSelectedItem();//首先获取被选中的项
            String proCode = ((ViewType) c).getProCode();//强制转换为ViewType后取得procode
            String typeName = ((ViewType) c).getTypeName();
            Instruction instruction = JsonUtil.instructionMap.get(typeName);
//            System.out.println(proCode);

            if (instruction.getSign()) {
                jp.remove(jtf_Select);
                jp.remove(jl_variable);
                cmb_Select.removeAllItems();
                cmb_Select.addItem("--请选择--");
                Map<Integer, String> map = instruction.getParameterValue();
                List<ViewType> list = new ArrayList<>();
                for (Integer key : map.keySet()) {
//                    System.out.println("key值："+key+" value值："+map.get(key));
                    ViewType viewType = new ViewType();
                    viewType.setProCode(key.toString());
                    viewType.setTypeName(map.get(key));
                    list.add(viewType);
                }
                for (ViewType v : list) {
                    cmb_Select.addItem(v);
                }
                cmb_Select.setBounds(110, 210, 200, 30);
//                cmb_Select.addActionListener(this);
                jp.add(cmb_Select);
            } else {
                jp.remove(cmb_Select);
                jp.remove(jl_variable);
                jl_variable = new JLabel(instruction.getAnnotation());
                jl_variable.setBounds(110, 160, 200, 40);
                jl_variable.setVisible(true);
                jp.add(jl_variable);

                jtf_Select.setText("");
                jtf_Select.setBounds(110, 210, 200, 30);
                jp.add(jtf_Select);
            }

        } else if (a.getSource() == jb_confirm[0]) {//确认提交
            String deviceId = "";
            String groupId = "";
            Integer level;

            String config = null;
            for (int i = 0; i < jtfAep.length; i++) {
                config = jtfAep[i].getText();
                if (config == null || "".equals(config)) {
                    PromptUtil.outputError("AEP配置参数不能为空!");
                    return;
                }
            }


            if (jrb[0].isSelected()) {
                if (jtf[0].getText().length() <= 0 || "请输入设备ID".equals(jtf[0].getText())) {
                    PromptUtil.outputError("请输入设备ID!");
                    return;
                }
                level = 1;
                deviceId = jtf[0].getText();
            } else {
                if (jtf[1].getText().length() <= 0 || "请输入群组ID".equals(jtf[1].getText())) {
                    PromptUtil.outputError("请输入群组ID!");
                    return;
                }
                level = 3;
                groupId = jtf[1].getText();
            }
            if (cmb1.getSelectedIndex() == 0) {
                PromptUtil.outputError("请选择指令!");
//                        System.exit( 0 );
                return;
            }
            Object c = cmb1.getSelectedItem();//首先获取被选中的项
            //服务标识
            String serviceSgn = ((ViewType) c).getTypeName();
            Instruction instruction = JsonUtil.instructionMap.get(serviceSgn);
            //指令参数key
            String parameterKey = instruction.getParameterKey();
            //指令参数值value
            Integer value;
            if (instruction.getSign()) {
                if (cmb_Select.getSelectedIndex() == 0) {
                    PromptUtil.outputError("请选择参数!");
//                        System.exit( 0 );
                    return;
                } else {
                    Object x = cmb_Select.getSelectedItem();//首先获取被选中的项
                    //服务标识
                    String xValue = ((ViewType) x).getProCode();
                    value = Integer.valueOf(xValue);
                }
            } else {
                if (jtf_Select.getText().length() <= 0) {
                    PromptUtil.outputError("请填写指令参数!");
                    return;
                } else {
                    try {
                        value = Integer.valueOf(jtf_Select.getText());
                    } catch (Exception e) {
                        PromptUtil.outputError("请填写数字格式参数!");
                        return;
                    }
                }
            }
            //0:产品ID&1:MasterKey&2:App Key&3:App Secret&4:操作者
            String productId = jtfAep[0].getText();
            String MasterKey = jtfAep[1].getText();
            String appKey = jtfAep[2].getText();
            String secret = jtfAep[3].getText();
            String operator = jtfAep[4].getText();

            //请求BODY,到文档中心->使能平台API文档打开要调用的api中，在“请求BODY”中查看
            String bodyString = "{\n" +
                    "  \"content\": {\n" +
                    "   \"params\": {\n" +
                    "   \n" +
                    "    \"" + parameterKey + "\": \"" + value + "\"\n" +
                    "   \n" +
                    "       },\n" +
                    "      \"serviceIdentifier\": \"" + serviceSgn + "\"\n" +
                    "     \n" +
                    "  },\n" +
                    "  \"deviceId\": \"" + deviceId + "\",\n" +
                    "  \"operator\": \"" + operator + "\",\n" +
                    "  \"productId\": " + productId + ",\n" +
                    "  \"ttl\": 100000,\n" +
                    "  \"deviceGroupId\":\"" + groupId + "\" ,\n" +
                    "  \"level\": " + level + "\n" +
                    "}";
            try {
                PromptUtil.outputError("指令正在下发,请稍候!");
                ResultBean resultBean=ApiExample.lwm2mExample1(secret, appKey, MasterKey, bodyString);
                if (resultBean.getCode()==0){
                    PromptUtil.outputError("指令下发成功!");
//                    String mes="code:"+resultBean.getCode()+";message:"+resultBean.getMessage();
////                    PromptUtil.outputError(mes);
                    return;
                }else {
                    PromptUtil.outputError("指令下发失败!");
                    String mes="code:"+resultBean.getCode()+";message:"+resultBean.getMessage();
                    PromptUtil.outputError(mes);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.exit( 0 );
        }
    }

    //    public Test2() {
//        jtf[1] = new JTextField(15);
//        jp.add(jtf[1]); // 把一个文本框添加到面板
//        jp.add(jb[0]); // 把一个浏览按钮添加到面板
//        jb[0].addActionListener(this);
//        btnOK = new JButton("确定");
//        jp.add(btnOK);// 把一个确定按钮添加到面板
//        btnOK.addActionListener(this);
//    }
    public void showModel(Boolean sign) {
        if (sign) {
            //群组ID输入隐藏
            jlChange[1].setVisible(false);
            jtf[1].setVisible(false);
            jtf[1].setText("");
//            jb[0].setVisible(false);
            //设备ID输入显示
            jlChange[0].setVisible(true);
            jtf[0].setVisible(true);
        } else {
            jlChange[0].setVisible(false);
            jtf[0].setVisible(false);
            jtf[0].setText("");

            jlChange[1].setVisible(true);
            jtf[1].setVisible(true);
//            jb[0].setVisible(true);
        }
    }

//    public static void main(String args[]) {
//        new AepVote();
//    }
}