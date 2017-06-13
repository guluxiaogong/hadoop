var json = {
    "version": {"@code": "2.0.0.0", "@date": "2009-12-01", "#text": "根据卫生部标准第一次修订"},
    "ehr": {"@code": "健康类别代码(字典STD_EHR)：固定值0101", "@codeSystem": "STD_EHR", "#text": "门诊基本诊疗信息"},
    "title": "门诊基本诊疗信息",
    "org": {"@code": "医疗机构编号(字典STD_HEALTH_ORG)", "@codeSystem": "STD_HEALTH_ORG", "#text": "医疗机构名称"},
    "id": {"@extension": "报告单号", "@eventno": "事件号"},
    "effectiveTime": {"@value": "文档创建时间"},
    "recordTarget": [{
        "id": {"@extension": "市民健康卡号"},
        "name": "姓名",
        "sex": {"@code": "性别代码(字典STD_SEX)", "@codeSystem": "GB/T2261.1-2003", "#text": "性别名称"},
        "birthDate": "出生日期",
        "marriage": {"@code": "婚姻状态代码(字典STD_MARRIAGE)", "@codeSystem": "GB/T 2261.2-2003", "#text": "婚姻状态"}
    }],
    "component": [{
        "code": {"@code": "common", "@codeSystem": "", "@displayName": "门诊基本诊疗信息"},
        "entry": {
            "onsetTime": "发病日期时间",
            "treatTime": "就诊日期时间",
            "diagnosisDate": "主诊断日期",
            "reg": {
                "value": "门诊挂号号",
                "type": {"@code": "挂号类别代码(字典STD_REG_TYPE)", "@codeSystem": "STD_REG_TYPE", "#text": "挂号类别名称"}
            },
            "sec": {
                "value": "保险号",
                "type": {"@code": "保险类别代码(字典STD_SEC_TYPE)", "@codeSystem": "STD_SEC_TYPE", "#text": "保险类别名称"}
            },
            "dept": {"@code": "科室代码(字典STD_DEPT)", "@codeSystem": "GB/T 17538-1998", "#text": "科室名称"},
            "doctor": {
                "id": {"@extension": "医生编码"},
                "name": "医生名称",
                "title": {"@code": "职称代码(字典STD_TECH_TITLE)", "@codeSystem": "GB/T 8561-1988", "#text": "职称名称"}
            },
            "symptom": [{"@code": "症状代码(字典STD_OUTPATIENT_SYMPTOM)", "@codeSystem": "CV5101.27", "#text": "门诊症状-名称"}],
            "diagnosis": [{
                "icd": {"@code": "诊断icd10(字典STD_ICD)", "@codeSystem": "ICD-10", "#text": "疾病诊断名称"},
                "result": {"@code": "诊疗结果代码(字典STD_DIAGNOSIS_RESULT)", "@codeSystem": "CV5501.11", "#text": "诊疗结果"},
                "prop": {"@code": "诊断性质代码(字典STD_DIAGNOSIS_PROP)", "@codeSystem": "STD_DIAGNOSIS_PROP", "#text": "诊断性质"}
            }]
        }
    }]
};
