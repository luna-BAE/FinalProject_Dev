package com.bit.fn.model.vo;

import java.sql.*;

public class CompanyInfoVo {
	private int comCode,officeNum,point;
	private String comName,ceo,manager,comPhone;
	private Date contractDate,rentStartDate,rentFinishDate;
	public CompanyInfoVo() {}
	public CompanyInfoVo(int comCode, int officeNum, int point, String comName, String ceo, String manager,
			String comPhone, Date contractDate, Date rentStartDate, Date rentFinishDate) {
		super();
		this.comCode = comCode;
		this.officeNum = officeNum;
		this.point = point;
		this.comName = comName;
		this.ceo = ceo;
		this.manager = manager;
		this.comPhone = comPhone;
		this.contractDate = contractDate;
		this.rentStartDate = rentStartDate;
		this.rentFinishDate = rentFinishDate;
	}
	public int getComCode() {
		return comCode;
	}
	public void setComCode(int comCode) {
		this.comCode = comCode;
	}
	public int getOfficeNum() {
		return officeNum;
	}
	public void setOfficeNum(int officeNum) {
		this.officeNum = officeNum;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getCeo() {
		return ceo;
	}
	public void setCeo(String ceo) {
		this.ceo = ceo;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getComPhone() {
		return comPhone;
	}
	public void setComPhone(String comPhone) {
		this.comPhone = comPhone;
	}
	public Date getContractDate() {
		return contractDate;
	}
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	public Date getRentStartDate() {
		return rentStartDate;
	}
	public void setRentStartDate(Date rentStartDate) {
		this.rentStartDate = rentStartDate;
	}
	public Date getRentFinishDate() {
		return rentFinishDate;
	}
	public void setRentFinishDate(Date rentFinishDate) {
		this.rentFinishDate = rentFinishDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ceo == null) ? 0 : ceo.hashCode());
		result = prime * result + comCode;
		result = prime * result + ((comName == null) ? 0 : comName.hashCode());
		result = prime * result + ((comPhone == null) ? 0 : comPhone.hashCode());
		result = prime * result + ((contractDate == null) ? 0 : contractDate.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + officeNum;
		result = prime * result + point;
		result = prime * result + ((rentFinishDate == null) ? 0 : rentFinishDate.hashCode());
		result = prime * result + ((rentStartDate == null) ? 0 : rentStartDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyInfoVo other = (CompanyInfoVo) obj;
		if (ceo == null) {
			if (other.ceo != null)
				return false;
		} else if (!ceo.equals(other.ceo))
			return false;
		if (comCode != other.comCode)
			return false;
		if (comName == null) {
			if (other.comName != null)
				return false;
		} else if (!comName.equals(other.comName))
			return false;
		if (comPhone == null) {
			if (other.comPhone != null)
				return false;
		} else if (!comPhone.equals(other.comPhone))
			return false;
		if (contractDate == null) {
			if (other.contractDate != null)
				return false;
		} else if (!contractDate.equals(other.contractDate))
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (officeNum != other.officeNum)
			return false;
		if (point != other.point)
			return false;
		if (rentFinishDate == null) {
			if (other.rentFinishDate != null)
				return false;
		} else if (!rentFinishDate.equals(other.rentFinishDate))
			return false;
		if (rentStartDate == null) {
			if (other.rentStartDate != null)
				return false;
		} else if (!rentStartDate.equals(other.rentStartDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CompanyInfoVo [comCode=" + comCode + ", officeNum=" + officeNum + ", point=" + point + ", comName="
				+ comName + ", ceo=" + ceo + ", manager=" + manager + ", comPhone=" + comPhone + ", contractDate="
				+ contractDate + ", rentStartDate=" + rentStartDate + ", rentFinishDate=" + rentFinishDate + "]";
	}
	
}
