package com.antin.mr.test.tohbase3;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/7.
 */
public class TestXml {
    public static void main(String[] args) throws Exception {
//        String xml_01 = "<root><org><item orgcode=\"\" orgname=\"\"/></org><activepatient><item orgcode=\"\" orgname=\"\" event_no=\"\" start_date=\"\" end_date=\"\" event_type=\"\" icd=\"\" diagnosis=\"\" card_no=\"B1203491459\" last_update_dtime=\"\" org_code=\"\" patient_id=\"\" health_record_no=\"B1203491459\" name=\"123\" sex_code=\"\" sex_name=\"男\" birth_date=\"2012-4-12\" id_type_code=\"\" id_type_name=\"出生证\" id_no=\"33322221111\" employer_name=\"\" tel_no=\"13400785639\" contact_name=\"\" contact_tel_no=\"\" residence_mark=\"1\" residence_mark_name=\"户籍\" nationality_code=\"\" nationality_name=\"汉族\" abo_code=\"未查\" abo_name=\"未查\" rh_code=\"\" rh_name=\"\" education_code=\"\" education_name=\"\" occupation_code=\"\" occupation_name=\"\" marriage_code=\"\" marriage_name=\"\" drug_allergy_mark=\"0\" drug_allergy_mark_name=\"否\" op_history_mark=\"0\" op_history_mark_name=\"否\" trauma_history_mark=\"0\" trauma_history_mark_name=\"否\" blood_transf_mark=\"0\" blood_transf_mark_name=\"否\" disability_mark=\"0\" disability_mark_name=\"否\" exhaust_facility_mark=\"1\" exhaust_facility_mark_name=\"有\" exhaust_facility_type_code=\"\" exhaust_facility_type_name=\"\" fuel_type_code=\"\" fuel_type_name=\"\" water_type_code=\"\" water_type_name=\"\" toilet_type_code=\"\" toilet_type_name=\"\" livestock_pen_type_code=\"\" livestock_pen_type_name=\"\" genetic_disease_history=\"\" operation_history=\"\" citizenship_code=\"\" citizenship_name=\"\" household_register_address=\"\" residence_address=\"2341234\" domicile_code=\"530100\" domicile_name=\"云南省云南昆明市\" community_code=\"350203001001\" community_name=\"思明区厦港街道鸿山社区居委会\" micile_of_origin=\"\" email=\"\" creator_code=\"\" creator=\"\" create_org_code=\"\" create_org_name=\"\" create_date=\"\" modify_author_code=\"\" modify_author_name=\"\" modify_org_code=\"\" modify_org_name=\"\" modify_date=\"\" perfect_author_code=\"\" perfect_author_name=\"\" perfect_org_code=\"\" perfect_org_name=\"\" perfect_date=\"\" status_code=\"3\" status_name=\"正常\"/>\n" +
//                "\t\t<environment/></activepatient><payway><item last_update_dtime=\"\" org_code=\"\" patient_id=\"\" fee_pay_way_code=\"undefined\" fee_pay_way_name=\"undefined\"/></payway><allergens/><envrisk/><diseasehistory/><ophistory/><traumhistory/><bloodtrans/><famhistroy/><disablility/></root>";
//        String xml_02 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "<ClinicalDocument><version code=\"1.0.0.0\" date=\"2008-08-15\"/><title>出生医学证明</title><org code=\"G350030521\">厦门市妇幼保健院</org><id extension=\"G350030521\" eventno=\"\"/><effectiveTime value=\"2006-06-18T21:18:18.0000000+08:00\"/><confidentialityCode code=\"N\" codeSystem=\"2.16.840.1.113883.5.25\"/><recordTarget><patient><id extension=\"2006-06-18T21:18:18.0000000+08:00\"/><name></name><administrativeGenderCode code=\"F\" codeSystem=\"2.16.840.1.113883.5.1\"/><birthTime></birthTime></patient></recordTarget><component><section><code code=\"common\" displayName=\"出生医学证明\"/><entry><birthTime value=\"\" year=\"2006\" month=\"06\" day=\"18\" hour=\"20\" minute=\"40\"/><birthAdd><state>福建省</state><city>厦门市</city><county>思明区</county><street></street></birthAdd><gestationWeek>41+6</gestationWeek><healthStatus>1</healthStatus><weight>3130.00</weight><height>50.00</height><mother name=\"傅娟娟\" age=\"28\" country=\"中国\" nation=\"汉族\" idno=\"350204197805112025\"/><father name=\"林文龙\" age=\"31\" country=\"中国\" nation=\"汉族\" idno=\"350204197503156014\"/><placeType>2</placeType><facilityOrg>厦门市妇幼保健院</facilityOrg><birthCertificateNo>G350030521</birthCertificateNo><issuingTime value=\"2006-06-18\" year=\"2006\" month=\"06\" day=\"18\"/><issuingOrg code=\"350211G1001\">厦门市妇幼保健院</issuingOrg></entry></section></component></ClinicalDocument>";
//
//        String json = XmlConverUtil.xmltoJson(xml_02);
//        JSONObject json1 = JSONObject.fromObject(json);
//        JSONArray names = json1.names();
//        for (int i = 0; i < names.size(); i++) {
//            Object value = json1.get(names.getString(i));
//            System.out.println(value.toString());
//        }

        //测试id
        //JSONArray jsonArray=JdbcUtil.queryAsJson("select MIN(tt.id), MAX(tt.id) from (select rownum as id from SEHR_XMAN_EHR_D where rownum <= 100000) tt",null);
        //测试时间
        //JSONArray jsonArray=JdbcUtil.queryAsJson("select MIN(t.commit_time), MAX(t.commit_time) from sehr_xman_ehr_A_test_2 t",null);
        //测试数据
        //JSONArray jsonArray = JdbcUtil.queryAsJson("select * from sehr_xman_ehr_A_test_2", null);

        Date d = new Date();
        System.out.println(d.toString());
        //Timestamp tp = (Timestamp)d;
        //System.out.println("TO_TIMESTAMP(\'" + d.toString() + "\', \'YYYY-MM-DD HH24:MI:SS.FF\')");
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
        //System.out.println(timestamp);


    }
}
