package com.antin.mr.test.tomysql5;

import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/13.
 */
public class ParseXml {
    public static void main(String[] args) {
        //ByteArrayInputStream baos = new ByteArrayInputStream(xml.getBytes());
        //XmlOp0101Model xmlOp0101Model = parseXmlOp0101Model(baos);
        //System.out.println(xmlOp0101Model);

        LinkedHashMap<String, String> map = parseXmlIn0201Model(xml3);
        for (Map.Entry<String, String> entry : map.entrySet()) {

            System.out.println(entry.getKey() + "->" + entry.getValue());

        }

    }

    public static XmlOp0101Model parseXmlOp0101Model(String xml) {
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

        Map<String, String> result = xml2Map(xml, map);
        JSONObject obj = JSONObject.fromObject(result);
        XmlOp0101Model xmlOp0101Model = (XmlOp0101Model) JSONObject.toBean(obj, XmlOp0101Model.class);
        return xmlOp0101Model;

    }

    public static LinkedHashMap<String, String> parseXmlIn0201Model(String xml) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("sex", "/ClinicalDocument/recordTarget/patient/sex/@code");
        map.put("birth", "/ClinicalDocument/recordTarget/patient/birthDate/");
        map.put("marriage", "/ClinicalDocument/recordTarget/patient/marriage/@code");
        map.put("residenceNo", "/ClinicalDocument/component/section/entry/residence/code/");
        map.put("serial", "/ClinicalDocument/component/section/entry/residence/serial/");
        map.put("secType", "/ClinicalDocument/component/section/entry/sec/type/@code");
        map.put("secNo", "/ClinicalDocument/component/section/entry/sec/value/");
        map.put("inDept", "/ClinicalDocument/component/section/entry/in/dept/");
        map.put("inDeptCode", "/ClinicalDocument/component/section/entry/in/dept/@code");
        map.put("inTime", "/ClinicalDocument/component/section/entry/in/time/");
        map.put("inBed", "/ClinicalDocument/component/section/entry/in/bed/");
        map.put("outDept", "/ClinicalDocument/component/section/entry/out/dept/");
        map.put("outDeptId", "/ClinicalDocument/component/section/entry/out/dept/@code");
        map.put("outTime", "/ClinicalDocument/component/section/entry/out/time/");
        map.put("outBed", "/ClinicalDocument/component/section/entry/out/bed/");
        map.put("moveTime", "/ClinicalDocument/component/section/entry/move/item/time/");
        map.put("moveDeptId", "/ClinicalDocument/component/section/entry/move/item/dept/@code");
        map.put("moveDept", "/ClinicalDocument/component/section/entry/move/item/dept/");
        map.put("onsetTime", "/ClinicalDocument/component/section/entry/onsetTime/");
        map.put("diagnosisDate", "/ClinicalDocument/component/section/entry/diagnosisDate/");
        map.put("inCauseCode", "/ClinicalDocument/component/section/entry/cause/@code");
        map.put("inCause", "/ClinicalDocument/component/section/entry/cause/");
        map.put("symptomCode", "/ClinicalDocument/component/section/entry/symptom/item/@code");
        map.put("symptomName", "/ClinicalDocument/component/section/entry/symptom/item/");
        map.put("illStatusCode", "/ClinicalDocument/component/section/entry/illStatus/@code");
        map.put("illStatusName", "/ClinicalDocument/component/section/entry/illStatus/");
        map.put("infectiousStatus", "/ClinicalDocument/component/section/entry/infectiousStatus/@code");
        map.put("inDiagnosis", "/ClinicalDocument/component/section/entry/inDiagnosis/item/icd/");
        map.put("inDiagnosisIcd", "/ClinicalDocument/component/section/entry/inDiagnosis/item/icd/@code");
        map.put("outDiagnosis", "/ClinicalDocument/component/section/entry/outDiagnosis/item/icd/");
        map.put("outDiagnosisIcd", "/ClinicalDocument/component/section/entry/outDiagnosis/item/icd/@code");
        map.put("diagnosisProp", "/ClinicalDocument/component/section/entry/outDiagnosis/item/prop/@code");
        map.put("result", "/ClinicalDocument/component/section/entry/outDiagnosis/item/result/@code");
        map.put("deathTime", "/ClinicalDocument/component/section/entry/deathTime/");
        map.put("rootCause", "/ClinicalDocument/component/section/entry/causeOfDeath/@code");

        LinkedHashMap<String, String> result = xml2Map(xml, map);
        return result;

    }


    public static LinkedHashMap<String, String> xml2Map(String xml, Map<String, String> maps) {
        try {
            LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
            Document document = DocumentHelper.parseText(xml);
            //SAXReader saxReader = new SAXReader();
            //saxReader.setEncoding("utf-16");
            //Document document = saxReader.read(xml);
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                String value = null;
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

    private static String xml2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<ClinicalDocument>\n" +
            "    <!--固定值-->\n" +
            "    <version code=\"2.0.0.1\" date=\"2011-06-01\">根据卫生部标准第2次修订</version>\n" +
            "    <!--固定值。codeSystem是code的编码体系，可以是ISO的对象标识符OID(一组数字)、国家标准(以GB开头)、\n" +
            "        卫生部健康档案数据标准(以CV开头)、本系统标准(以STD开头)或其它医学标准-->\n" +
            "    <ehr code=\"健康档案类别代码(字典STD_EHR)：固定值0201\" codeSystem=\"STD_EHR\">住院诊疗基本信息</ehr>\n" +
            "    <!--可根据需要自定义报告标题-->\n" +
            "    <title>住院诊疗基本信息</title>\n" +
            "    <!--医疗机构编号使用卫生局的标准编码-->\n" +
            "    <org code=\"医疗机构编号(字典STD_HEALTH_ORG)\" codeSystem=\"STD_HEALTH_ORG\">医疗机构名称</org>\n" +
            "    <!--报告单号用于唯一标识该文档，事件号用于标识一次诊疗过程，同过程的所有文档事件号相同-->\n" +
            "    <id extension=\"报告单号\" eventno=\"事件号\"></id>\n" +
            "    <!--文档创建时间，格式是“yyyy-MM-dd hh24:mi:ss”，\n" +
            "    以下未特殊说明的“日期”均使用“yyyy-MM-dd”格式，如2009-12-01\n" +
            "    未特殊说明的“时间”均使用“yyyy-MM-dd hh24:mi:ss”格式，如2009-12-01 16:00:00-->\n" +
            "    <effectiveTime value=\"文档创建时间\"/>\n" +
            "    <recordTarget>\n" +
            "        <patient>\n" +
            "            <id extension=\"市民健康卡号\"></id>\n" +
            "            <name>姓名</name>\n" +
            "            <sex code=\"性别代码(字典STD_SEX)\" codeSystem=\"GB/T2261.1-2003\">性别名称</sex>\n" +
            "            <birthDate>出生日期</birthDate>\n" +
            "            <marriage code=\"婚姻状态代码(字典STD_MARRIAGE)\" codeSystem=\"GB/T 2261.2-2003\">婚姻状态</marriage>\n" +
            "        </patient>\n" +
            "    </recordTarget>\n" +
            "    <component>\n" +
            "        <section>\n" +
            "            <!--档案子类别，用于类别的扩展，code子类别编码；displayName名称。当前为固定值-->\n" +
            "            <code code=\"common\" codeSystem=\"\" displayName=\"住院诊疗基本信息\"/>\n" +
            "            <entry>\n" +
            "                <residence>\n" +
            "                    <serial>病人第几次住院</serial>\n" +
            "                    <code>病案号</code>\n" +
            "                </residence>\n" +
            "                <sec>\n" +
            "                    <type code=\"保险类别(字典STD_SEC_TYPE)\" codeSystem=\"STD_SEC_TYPE\">保险类别名称</type>\n" +
            "                    <value>保险号</value>\n" +
            "                </sec>\n" +
            "                <in>\n" +
            "                    <time>入院时间</time>\n" +
            "                    <dept code=\"入院科室代码(字典STD_DEPT)\" codeSystem=\"GB/T 17538-1998\">入院科室名称</dept>\n" +
            "                    <bed>床号</bed>\n" +
            "                </in>\n" +
            "                <move>\n" +
            "                    <!--转科子项：开始(多项时重复此节点)-->\n" +
            "                    <item>\n" +
            "                        <time>转科时间</time>\n" +
            "                        <dept code=\"转科科室代码(字典STD_DEPT)\" codeSystem=\"GB/T 17538-1998\">转科后科室名称</dept>\n" +
            "                        <bed>转科后床号</bed>\n" +
            "                    </item>\n" +
            "                    <!--转科子项：结束(多项时重复此节点)-->\n" +
            "                </move>\n" +
            "                <out>\n" +
            "                    <time>出院时间</time>\n" +
            "                    <dept code=\"出院科室代码(字典STD_DEPT)\" codeSystem=\"GB/T 17538-1998\">出院科室名称</dept>\n" +
            "                    <bed>床号</bed>\n" +
            "                </out>\n" +
            "                <onsetTime>发病时间</onsetTime>\n" +
            "                <diagnosisDate>主诊断日期</diagnosisDate>\n" +
            "                <cause code=\"住院原因代码(字典STD_INPATIENT_CAUSE)\" codeSystem=\"CV5401.04\">住院原因</cause>\n" +
            "                <symptom>\n" +
            "                    <!--症状体征子项：开始(多项时重复此节点)-->\n" +
            "                    <item code=\"症状代码(字典STD_OUTPATIENT_SYMPTOM)\" codeSystem=\"CV5101.27\">症状名称</item>\n" +
            "                    <!--症状体征子项：结束-->\n" +
            "                </symptom>\n" +
            "                <illStatus code=\"住院患者疾病状态代码(字典STD_INPATIENT_ILL_STATUS)\" codeSystem=\"CV5502.18\">住院患者疾病状态名称</illStatus>\n" +
            "                <infectiousStatus code=\"是否具有传染性(字典STD_JUDGE)\" codeSystem=\"STD_JUDGE\">是、否</infectiousStatus>\n" +
            "                <inDiagnosis>\n" +
            "                    <!--入院诊断子项：开始(多项时重复此节点)-->\n" +
            "                    <item>\n" +
            "                        <icd code=\"诊断icd10(字典STD_ICD)\" codeSystem=\"ICD-10\">疾病诊断名称</icd>\n" +
            "                    </item>\n" +
            "                    <!--入院诊断子项：结束-->\n" +
            "                </inDiagnosis>\n" +
            "                <outDiagnosis>\n" +
            "                    <!--出院诊断子项：开始(多项时重复此节点)-->\n" +
            "                    <item>\n" +
            "                        <icd code=\"出院诊断icd10(字典STD_ICD)\" codeSystem=\"ICD-10\">出院诊断名称</icd>\n" +
            "                        <result code=\"诊疗结果代码(字典STD_DIAGNOSIS_RESULT)\" codeSystem=\"CV5501.11\">治疗结果</result>\n" +
            "                        <prop code=\"诊断性质代码(字典STD_DIAGNOSIS_PROP)\" codeSystem=\"STD_DIAGNOSIS_PROP\">诊断性质名称</prop>\n" +
            "                    </item>\n" +
            "                    <!--出院诊断子项：结束(多项时重复此节点)-->\n" +
            "                </outDiagnosis>\n" +
            "                <deathTime>死亡时间</deathTime>\n" +
            "                <causeOfDeath code=\"根本死因代码icd10(字典STD_ICD)\" codeSystem=\"ICD-10\">死亡原因</causeOfDeath>\n" +
            "            </entry>\n" +
            "        </section>\n" +
            "    </component>\n" +
            "</ClinicalDocument>";

    private static String xml3 = "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
            "<ClinicalDocument>\n" +
            "  <version code=\"2.0.0.0\" date=\"2010-05-11\">根据卫生部标准第一次修订</version>\n" +
            "  <ehr code=\"0701\" codeSystem=\"STD_EHR\">预防接种</ehr>\n" +
            "  <title>预防接种</title>\n" +
            "  <org code=\"350211A1001\" codeSystem=\"STD_HEALTH_ORG\">厦门市第一医院</org>\n" +
            "  <id extension=\"HEBP_01\" eventno=\"HEBP_01\"/>\n" +
            "  <effectiveTime value=\"2012-1-9 0:00:00\"/>\n" +
            "  <recordTarget>\n" +
            "    <patient>\n" +
            "      <id extension=\"CL350247695\"/>\n" +
            "      <name></name>\n" +
            "      <birthDate>2012-1-9 12:17:00</birthDate>\n" +
            "      <sex code=\"1\" codeSystem=\"GB/T 2261.1-2003\">男性</sex>\n" +
            "      <address>\n" +
            "        <type code=\"09\" codeSystem=\"CV0300.01\">现住址</type>\n" +
            "        <adminDivision code=\"行政区划分代码\" codeSystem=\"GB/T 2260-2007\"/>\n" +
            "        <state>350000</state>\n" +
            "        <city>350200</city>\n" +
            "        <county>350203</county>\n" +
            "        <street></street>\n" +
            "        <village></village>\n" +
            "        <streetAddressLine>福建省连城县莒溪镇莒市村桐耕寮2号</streetAddressLine>\n" +
            "        <postalCode></postalCode>\n" +
            "      </address>\n" +
            "      <address>\n" +
            "        <type code=\"01\" codeSystem=\"CV0300.01\">福建省连城县莒溪镇莒市村桐耕寮2号</type>\n" +
            "        <adminDivision code=\"行政区划分代码\" codeSystem=\"GB/T 2260-2007\">行政区划分名称</adminDivision>\n" +
            "        <streetAddressLine>福建省连城县莒溪镇莒市村桐耕寮2号</streetAddressLine>\n" +
            "        <postalCode></postalCode>\n" +
            "      </address>\n" +
            "      <telephone>\n" +
            "        <type code=\"04\" codeSystem=\"CV0400.01\">家庭电话</type>\n" +
            "        <value></value>\n" +
            "      </telephone>\n" +
            "    </patient>\n" +
            "  </recordTarget>\n" +
            "  <component>\n" +
            "    <section>\n" +
            "      <code code=\"HRB03_01\" displayName=\"预防接种\">\n" +
            "        <entry>\n" +
            "          <certification displayName=\"出生医学证明编号\">L350247695</certification>\n" +
            "          <barcode_no displayName=\"条码号\"></barcode_no>\n" +
            "          <fathername displayName=\"父亲姓名\">江勇</fathername>\n" +
            "          <mothername displayName=\"母亲姓名\">沈爱萍</mothername>\n" +
            "          <doctor code=\"\" displayName=\"疫苗接种者姓名\">谢吟梅</doctor>\n" +
            "          <hospital code=\"350211A1001\" displayName=\"疫苗接种单位名称\">厦门市第一医院</hospital>\n" +
            "          <immunitydate displayName=\"疫苗接种日期\">2012-1-9 0:00:00</immunitydate>\n" +
            "          <bacterin displayName=\"疫苗-名称\">乙肝疫苗</bacterin>\n" +
            "          <times displayname=\"疫苗针次\"></times>\n" +
            "          <tabno displayName=\"疫苗-批号\">20101154-4</tabno>\n" +
            "          <validperiod displayName=\"疫苗有效期\"></validperiod>\n" +
            "          <injection_mode displayName=\"注射方式\">皮下</injection_mode>\n" +
            "          <injection_position displayName=\"注射部位\"></injection_position>\n" +
            "          <dose displayName=\"疫苗剂量\">5.000000</dose>\n" +
            "          <producer displayName=\"疫苗产商\">深圳生物制品有限公司</producer>\n" +
            "          <character displayName=\"疫苗性质\"></character>\n" +
            "          <remark displayName=\"疫苗备注\"></remark>\n" +
            "        </entry>\n" +
            "      </code>\n" +
            "    </section>\n" +
            "  </component>\n" +
            "</ClinicalDocument>";

}