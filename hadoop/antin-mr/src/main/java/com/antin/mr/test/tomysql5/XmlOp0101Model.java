package com.antin.mr.test.tomysql5;


/**
 * Created by Administrator on 2017/3/13.
 */
public class XmlOp0101Model {
    private String sex;//性别
    private String birth;//出生日期
    private String marriage;//婚姻状况
    private String onsetTime;//发病日期时间
    private String treatTime;//就诊日期时间
    private String diagnosisDate;//诊断日期
    private String reg;//挂号号
    private String type;//挂号类别
    private String secType;//保险类型
    private String secNo;//医疗保险号
    private String deptName;//挂号科室名称
    private String deptCode;//挂号科室代码
    private String doctorId;//医生编码
    private String doctor;//医生
    private String techTitle;//医生职称
    private String symptomName;//门诊症状-名称
    private String symptomCode;//门诊症状-诊断代码
    private String diagnosisName;//诊断名称
    private String diagnosisIcd;//诊断icd10
    private String diagnosisProp;//诊断性质
    private String result;//诊疗结果

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getOnsetTime() {
        return onsetTime;
    }

    public void setOnsetTime(String onsetTime) {
        this.onsetTime = onsetTime;
    }

    public String getTreatTime() {
        return treatTime;
    }

    public void setTreatTime(String treatTime) {
        this.treatTime = treatTime;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public String getSecNo() {
        return secNo;
    }

    public void setSecNo(String secNo) {
        this.secNo = secNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getTechTitle() {
        return techTitle;
    }

    public void setTechTitle(String techTitle) {
        this.techTitle = techTitle;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getSymptomCode() {
        return symptomCode;
    }

    public void setSymptomCode(String symptomCode) {
        this.symptomCode = symptomCode;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public String getDiagnosisIcd() {
        return diagnosisIcd;
    }

    public void setDiagnosisIcd(String diagnosisIcd) {
        this.diagnosisIcd = diagnosisIcd;
    }

    public String getDiagnosisProp() {
        return diagnosisProp;
    }

    public void setDiagnosisProp(String diagnosisProp) {
        this.diagnosisProp = diagnosisProp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "Op0101Model{" +
                "sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", marriage='" + marriage + '\'' +
                ", onsetTime='" + onsetTime + '\'' +
                ", treatTime='" + treatTime + '\'' +
                ", diagnosisDate='" + diagnosisDate + '\'' +
                ", reg='" + reg + '\'' +
                ", type='" + type + '\'' +
                ", secType='" + secType + '\'' +
                ", secNo='" + secNo + '\'' +
                ", deptName='" + deptName + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", doctor='" + doctor + '\'' +
                ", techTitle='" + techTitle + '\'' +
                ", symptomName='" + symptomName + '\'' +
                ", symptomCode='" + symptomCode + '\'' +
                ", diagnosisName='" + diagnosisName + '\'' +
                ", diagnosisIcd='" + diagnosisIcd + '\'' +
                ", diagnosisProp='" + diagnosisProp + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
