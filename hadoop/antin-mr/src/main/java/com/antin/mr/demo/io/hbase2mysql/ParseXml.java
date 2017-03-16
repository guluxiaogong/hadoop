package com.antin.mr.demo.io.hbase2mysql;

import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/13.
 */
public class ParseXml {
    public static void main(String[] args) {
        //parseOp0101Model(xml);
        //SaxDemo.parserXml("F:\\CommonDevelop\\hadoop\\project\\hadoop2\\antin-mr\\src\\main\\java\\com\\antin\\mr\\dbio\\tomysql3\\test.xml");
        //Dom4jDemo.parserXml(xml);
        //xml2Map2(xml);
        parseOp0101Model(xml);

    }

    public static Op0101Model parseOp0101Model(String xml) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sex", "/ClinicalDocument/recordTarget/patient/sex/@code");
        map.put("birth", "/ClinicalDocument/recordTarget/patient/birthDate/");
        map.put("marriage", "/ClinicalDocument/recordTarget/patient/marriage/@code");
        map.put("onsetTime", "/ClinicalDocument/component/section/entry/onsetTime/");
        map.put("treatTime", "/ClinicalDocument/component/section/entry/treatTime/");
        map.put("diagnosisDate", "/ClinicalDocument/component/section/entry/diagnosisDate/");
        map.put("reg", "/ClinicalDocument/component/section/entry/reg/value/");
        map.put("type", "/ClinicalDocument/component/section/entry/reg/type/@code");
        map.put("secType", "/ClinicalDocument/component/section/entry/sec/value/");
        map.put("secNo", "/ClinicalDocument/component/section/entry/sec/type/@code");
        map.put("deptName", "/ClinicalDocument/component/section/entry/dept/");
        map.put("deptCode", "/ClinicalDocument/component/section/entry/dept/@code");
        map.put("doctorId", "/ClinicalDocument/component/section/entry/doctor/id/@extension");
        map.put("doctor", "/ClinicalDocument/component/section/entry/doctor/name/");
        map.put("techTitle", "/ClinicalDocument/component/section/entry/doctor/title/@code");
        map.put("symptomName", "/ClinicalDocument/component/section/entry/symptom/item/");
        map.put("symptomCode", "/ClinicalDocument/component/section/entry/symptom/item/@code");
        map.put("diagnosisName", "/ClinicalDocument/component/section/entry/diagnosis/item/icd/");
        map.put("diagnosisIcd", "/ClinicalDocument/component/section/entry/diagnosis/item/icd/@code");
        map.put("diagnosisProp", "/ClinicalDocument/component/section/entry/diagnosis/item/prop/@code");
        map.put("result", "/ClinicalDocument/component/section/entry/diagnosis/item/result/@code");

        Map<String, String> result = xml2Map3(xml, map);
        JSONObject obj = JSONObject.fromObject(result);
        Op0101Model op0101Model = (Op0101Model) JSONObject.toBean(obj, Op0101Model.class);
        // System.out.println(op0101Model);
        return op0101Model;

    }

    private static String getValue(Map<String, String> map, String key) {
        String value = null;
        try {
            value = map.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Map<String, String> xml2Map3(String xml, Map<String, String> maps) {
        try {
            Map<String, String> result = new HashMap<String, String>();
            String value = null;
            Document document = DocumentHelper.parseText(xml);
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                String path = entry.getValue();
                int point = path.lastIndexOf("/");
                String node = path.substring(0, point), attr = path.substring(point + 1);
                Element element = (Element) document.selectSingleNode(node);
                if (element != null) {
                    if (attr.startsWith("@"))
                        value = element.attributeValue(attr.substring(1));
                    else
                        value = element.getTextTrim();
                }
                result.put(entry.getKey(), value);
            }
            return result;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> xml2Map2(String xml) {
        try {
            Document document = DocumentHelper.parseText(xml);
            Element e = (Element) document.selectSingleNode("/ClinicalDocument/version");
            Element root = document.getRootElement();
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element element = (Element) i.next();
                System.out.println(element.getQName() + ":" + element.attributeCount());
                for (Iterator j = element.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
                    System.out.println(node.getName() + ":" + node.getText());
                }

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> xml2Map(String xml) {
        try {
            Map<String, String> maps = new HashMap<String, String>();
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            List<Element> eles = root.elements();
            for (Element e : eles) {
                maps.put(e.getName(), e.getTextTrim());
            }
            return maps;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<ClinicalDocument>\n" +
            "  <!--固定值-->\n" +
            "  <version code=\"2.0.0.0\" date=\"2009-12-01\">根据卫生部标准第一次修订</version>\n" +
            "  <!--固定值。codeSystem是code的编码体系，可以是ISO的对象标识符OID(一组数字)、国家标准(以GB开头)、\n" +
            "      卫生部健康档案数据标准(以CV开头)、本系统标准(以STD开头)或其它医学标准-->\n" +
            "  <ehr code=\"健康类别代码(字典STD_EHR)：固定值0101\" codeSystem=\"STD_EHR\">门诊基本诊疗信息</ehr>\n" +
            "  <!--可根据需要自定义报告标题-->\n" +
            "  <title>门诊基本诊疗信息</title>\n" +
            "  <!--医疗机构编号使用卫生局的标准编码-->\n" +
            "  <org code=\"医疗机构编号(字典STD_HEALTH_ORG)\" codeSystem=\"STD_HEALTH_ORG\">医疗机构名称</org>\n" +
            "  <!--报告单号用于唯一标识该文档，事件号用于标识一次诊疗过程，同过程的所有文档事件号相同-->\n" +
            "  <id extension=\"报告单号\" eventno=\"事件号\"></id>\n" +
            "  <!--文档创建时间，格式是“yyyy-MM-dd hh24:mi:ss”，\n" +
            "  以下未特殊说明的“日期”均使用“yyyy-MM-dd”格式，如2009-12-01\n" +
            "  未特殊说明的“日期时间”均使用“yyyy-MM-dd hh24:mi:ss”格式，如2009-12-01 16:00:00-->\n" +
            "  <effectiveTime value=\"文档创建时间\"/>\n" +
            "  <recordTarget>\n" +
            "    <patient>\n" +
            "      <id extension=\"市民健康卡号\"></id>\n" +
            "      <name>姓名</name>\n" +
            "      <sex code=\"性别代码(字典STD_SEX)\" codeSystem=\"GB/T2261.1-2003\">性别名称</sex>\n" +
            "      <birthDate>出生日期</birthDate>\n" +
            "      <marriage code=\"婚姻状态代码(字典STD_MARRIAGE)\" codeSystem=\"GB/T 2261.2-2003\">婚姻状态</marriage>\n" +
            "    </patient>\n" +
            "  </recordTarget>\n" +
            "  <component>\n" +
            "    <section>\n" +
            "      <!--档案子类别，用于类别的扩展，code子类别编码；displayName名称。当前为固定值-->\n" +
            "      <code code=\"common\" codeSystem=\"\"  displayName=\"门诊基本诊疗信息\"/>\n" +
            "      <entry>\n" +
            "        <onsetTime>发病日期时间</onsetTime>\n" +
            "        <treatTime>就诊日期时间</treatTime>\n" +
            "        <diagnosisDate>主诊断日期</diagnosisDate>\n" +
            "        <reg>\n" +
            "          <value>门诊挂号号</value>\n" +
            "          <type code=\"挂号类别代码(字典STD_REG_TYPE)\" codeSystem=\"STD_REG_TYPE\">挂号类别名称</type>\n" +
            "        </reg>\n" +
            "        <sec>\n" +
            "          <value>保险号</value>\n" +
            "          <type code=\"保险类别代码(字典STD_SEC_TYPE)\" codeSystem=\"STD_SEC_TYPE\">保险类别名称</type>\n" +
            "        </sec>\n" +
            "        <dept code=\"科室代码(字典STD_DEPT)\" codeSystem=\"GB/T 17538-1998\">科室名称</dept>\n" +
            "        <doctor>\n" +
            "          <id extension=\"医生编码\"/>\n" +
            "          <name>医生名称</name>\n" +
            "          <title code=\"职称代码(字典STD_TECH_TITLE)\" codeSystem=\"GB/T 8561-1988\">职称名称</title>\n" +
            "        </doctor>\n" +
            "        <symptom>\n" +
            "          <!--门诊症状体征子项：开始(多项时重复此节点)-->\n" +
            "          <item code=\"症状代码(字典STD_OUTPATIENT_SYMPTOM)\" codeSystem=\"CV5101.27\">门诊症状-名称</item>\n" +
            "          <!--门诊症状体征子项：结束-->\n" +
            "        </symptom>\n" +
            "        <diagnosis>\n" +
            "          <!--门诊诊断子项：开始(多项时重复此节点)-->\n" +
            "          <item>\n" +
            "            <icd code=\"诊断icd10(字典STD_ICD)\" codeSystem=\"ICD-10\">疾病诊断名称</icd>\n" +
            "            <result code=\"诊疗结果代码(字典STD_DIAGNOSIS_RESULT)\" codeSystem=\"CV5501.11\">诊疗结果</result>\n" +
            "            <prop code=\"诊断性质代码(字典STD_DIAGNOSIS_PROP)\" codeSystem=\"STD_DIAGNOSIS_PROP\">诊断性质</prop>\n" +
            "          </item>\n" +
            "          <!--门诊诊断子项：结束-->\n" +
            "        </diagnosis>\n" +
            "      </entry>\n" +
            "    </section>\n" +
            "  </component>\n" +
            "</ClinicalDocument>\n";

    public static String xml2 = "\n" +
            "<ClinicalDocument>\n" +
            "  <version date=\"2009-12-01\" code=\"2.0.0.0\">根据卫生部标准第一次修订</version>\n" +
            "  <ehr codeSystem=\"STD_EHR\" code=\"0101\">门诊基本诊疗信息</ehr>\n" +
            "  <title>门诊基本诊疗信息</title>\n" +
            "  <org codeSystem=\"STD_HEALTH_ORG\" code=\"350211A2003\"/>\n" +
            "  <id eventno=\"\" extension=\"\"/>\n" +
            "  <effectiveTime/>\n" +
            "  <recordTarget>\n" +
            "    <patient>\n" +
            "      <id extension=\"1\"/>\n" +
            "      <name>美兰测试2016</name>\n" +
            "      <sex codeSystem=\"GB/T2261.1-2003\" code=\"2\">女</sex>\n" +
            "      <birthDate>1976-10-14 00:00:00.0</birthDate>\n" +
            "      <marriage codeSystem=\"GB/T 2261.2-2003\" code=\"\"/>\n" +
            "    </patient>\n" +
            "  </recordTarget>\n" +
            "  <component>\n" +
            "    <section>\n" +
            "      <code displayName=\"门诊基本诊疗信息\" codeSystem=\"\" code=\"common\"/>\n" +
            "      <entry>\n" +
            "        <onsetTime/>\n" +
            "        <treatTime/>\n" +
            "        <diagnosisDate/>\n" +
            "        <reg>\n" +
            "          <value/>\n" +
            "          <type codeSystem=\"STD_REG_TYPE\" code=\"null\">null</type>\n" +
            "        </reg>\n" +
            "        <sec>\n" +
            "          <value>null</value>\n" +
            "          <type codeSystem=\"STD_SEC_TYPE\" code=\"null\">null</type>\n" +
            "        </sec>\n" +
            "        <dept codeSystem=\"GB/T 17538-1998\" code=\"null\"/>\n" +
            "        <doctor>\n" +
            "          <id extension=\"null\"/>\n" +
            "          <name/>\n" +
            "          <title codeSystem=\"GB/T 8561-1988\" code=\"null\">null</title>\n" +
            "        </doctor>\n" +
            "        <symptom/>\n" +
            "        <diagnosis/>\n" +
            "      </entry>\n" +
            "    </section>\n" +
            "  </component>\n" +
            "</ClinicalDocument>";


}
