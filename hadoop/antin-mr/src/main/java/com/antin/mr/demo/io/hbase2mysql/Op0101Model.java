package com.antin.mr.demo.io.hbase2mysql;

import org.apache.hadoop.hbase.procedure2.util.StringUtils;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Created by Administrator on 2017/3/13.
 */
public class Op0101Model implements Writable, DBWritable {
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

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sex = dataInput.readUTF();
        this.birth = dataInput.readUTF();
        this.marriage = dataInput.readUTF();
        this.onsetTime = dataInput.readUTF();
        this.treatTime = dataInput.readUTF();
        this.diagnosisDate = dataInput.readUTF();
        this.reg = dataInput.readUTF();
        this.type = dataInput.readUTF();
        this.secType = dataInput.readUTF();
        this.secNo = dataInput.readUTF();
        this.deptName = dataInput.readUTF();
        this.deptCode = dataInput.readUTF();
        this.doctorId = dataInput.readUTF();
        this.doctor = dataInput.readUTF();
        this.techTitle = dataInput.readUTF();
        this.symptomName = dataInput.readUTF();
        this.symptomCode = dataInput.readUTF();
        this.diagnosisName = dataInput.readUTF();
        this.diagnosisIcd = dataInput.readUTF();
        this.diagnosisProp = dataInput.readUTF();
        this.result = dataInput.readUTF();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sex);
        dataOutput.writeUTF(this.birth);
        dataOutput.writeUTF(this.marriage);
        dataOutput.writeUTF(this.onsetTime);
        dataOutput.writeUTF(this.treatTime);
        dataOutput.writeUTF(this.diagnosisDate);
        dataOutput.writeUTF(this.reg);
        dataOutput.writeUTF(this.type);
        dataOutput.writeUTF(this.secType);
        dataOutput.writeUTF(this.secNo);
        dataOutput.writeUTF(this.deptName);
        dataOutput.writeUTF(this.deptCode);
        dataOutput.writeUTF(this.doctorId);
        dataOutput.writeUTF(this.doctor);
        dataOutput.writeUTF(this.techTitle);
        dataOutput.writeUTF(this.symptomName);
        dataOutput.writeUTF(this.symptomCode);
        dataOutput.writeUTF(this.diagnosisName);
        dataOutput.writeUTF(this.diagnosisIcd);
        dataOutput.writeUTF(this.diagnosisProp);
        dataOutput.writeUTF(this.result);

    }

    @Override
    public void write(PreparedStatement pstm) throws SQLException {
        pstm.setString(1, this.sex);
        pstm.setTimestamp(2, stringToTime(this.birth));
        pstm.setString(3, this.marriage);
        pstm.setTimestamp(4, stringToTime(this.onsetTime));
        pstm.setTimestamp(5, stringToTime(this.treatTime));
        pstm.setTimestamp(6, stringToTime(this.diagnosisDate));
        pstm.setString(7, this.reg);
        pstm.setString(8, this.type);
        pstm.setString(9, this.secType);
        pstm.setString(10, this.secNo);
        pstm.setString(11, this.deptName);
        pstm.setString(12, this.deptCode);
        pstm.setString(13, this.doctorId);
        pstm.setString(14, this.doctor);
        pstm.setString(15, this.techTitle);
        pstm.setString(16, this.symptomName);
        pstm.setString(17, this.symptomCode);
        pstm.setString(18, this.diagnosisName);
        pstm.setString(19, this.diagnosisIcd);
        pstm.setString(20, this.diagnosisProp);
        pstm.setString(21, this.result);
    }

    @Override
    public void readFields(ResultSet rs) throws SQLException {
        this.sex = rs.getString(1);
        this.birth = timeToString(rs.getTimestamp(2));
        this.marriage = rs.getString(3);
        this.onsetTime = timeToString(rs.getTimestamp(4));
        this.treatTime = timeToString(rs.getTimestamp(5));
        this.diagnosisDate = timeToString(rs.getTimestamp(6));
        this.reg = rs.getString(7);
        this.type = rs.getString(8);
        this.secType = rs.getString(9);
        this.secNo = rs.getString(10);
        this.deptName = rs.getString(11);
        this.deptCode = rs.getString(12);
        this.doctorId = rs.getString(13);
        this.doctor = rs.getString(14);
        this.techTitle = rs.getString(15);
        this.symptomName = rs.getString(16);
        this.symptomCode = rs.getString(17);
        this.diagnosisName = rs.getString(18);
        this.diagnosisIcd = rs.getString(19);
        this.diagnosisProp = rs.getString(20);
        this.result = rs.getString(21);
    }

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

    private Timestamp stringToTime(String timeString) {
        Timestamp timestamp = null;
        if (!StringUtils.isEmpty(timeString)) {
            try {
                timestamp = (Timestamp) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeString);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        return timestamp;
    }

    private String timeToString(Timestamp timestamp) {
        return timestamp == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
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
