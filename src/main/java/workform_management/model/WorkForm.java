package workform_management.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class WorkForm {
	/**
	 * 单号
	 */
	@Id
	//@GeneratedValue
	private int id;
	/**
	 * 日期
	 */
	private Date date;
	/**
	 * 是否已完成
	 */
	private boolean isFinished;
	/**
	 * 接单人
	 */
	private String receivePerson;
	/**
	 * 事由
	 */
	private String reason;
	/**
	 * 单位
	 */
	private String department;
	/**
	 * 片区
	 */
	private String district;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 教工号/学号
	 */
	private String personSchoolId;
	/**
	 * 检修人
	 */
	private String examinePerson;
	/**
	 * 检修原因
	 */
	private String examineReason;
	/**
	 * 身份证号
	 */
	private String personId;

	@Override
	public String toString() {
		return String.format(
				"工单\n" + "单号：%d\n" + "日期：%s\n" + "接单人：%s\n" + "事由：%s\n" + "单位：%s\n" + "片区：%s\n" + "联系人：%s\n" + "电话：%s\n"
						+ "教工号/学号：%s\n" + "检修人：%s\n" + "检修后原因：%s\n" + "身份证号：%s\n",
				id, date, receivePerson, reason, department, district, contacts, phone, personSchoolId, examinePerson,
				examineReason, personId);
	}

	public WorkForm() {
	}

	public WorkForm(int id, Date data, boolean isFinished, String receivePerson, String reason, String department, String district,
			String contacts, String phone, String personSchoolId, String examinePerson, String examineReason,
			String personId) {
		this.id = id;
		this.date = data;
		this.isFinished = isFinished;
		this.receivePerson = receivePerson;
		this.reason = reason;
		this.department = department;
		this.district = district;
		this.contacts = contacts;
		this.phone = phone;
		this.personSchoolId = personSchoolId;
		this.examinePerson = examinePerson;
		this.examineReason = examineReason;
		this.personId = personId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") 
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+08:00")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPersonSchoolId() {
		return personSchoolId;
	}

	public void setPersonSchoolId(String personSchoolId) {
		this.personSchoolId = personSchoolId;
	}

	public String getExaminePerson() {
		return examinePerson;
	}

	public void setExaminePerson(String examinePerson) {
		this.examinePerson = examinePerson;
	}

	public String getExamineReason() {
		return examineReason;
	}

	public void setExamineReason(String examineReason) {
		this.examineReason = examineReason;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

}
