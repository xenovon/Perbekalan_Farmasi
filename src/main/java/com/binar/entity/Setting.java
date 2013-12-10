package com.binar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.binar.entity.enumeration.EnumSettingGroup;

@Entity
@Table(name="setting")
public class Setting {

	@Id
	@Column(name="id_setting")
	private int idSetting;
	
	@Column(name="setting_name")
	private String settingName;
	
	@Column(name="setting_description")
	private String settingDescription;
	
	@Column(name="setting_key")
	private String settingKey;
	
	@Column(name="setting_group")
	private EnumSettingGroup settingGroup;
	
	@Column(name="setting_value", columnDefinition="TEXT")
	private String settingValue;

	public int getIdSetting() {
		return idSetting;
	}
    public void setSettingGroup(EnumSettingGroup settingGroup) {
		this.settingGroup = settingGroup;
	}
    public EnumSettingGroup getSettingGroup() {
		return settingGroup;
	}
	public void setIdSetting(int idSetting) {
		this.idSetting = idSetting;
	}

	public String getSettingName() {
		return settingName;
	}

	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	public String getSettingDescription() {
		return settingDescription;
	}

	public void setSettingDescription(String settingDescription) {
		this.settingDescription = settingDescription;
	}

	public String getSettingKey() {
		return settingKey;
	}

	public void setSettingKey(String settingKey) {
		this.settingKey = settingKey;
	}

	public String getSettingValue() {
		return settingValue;
	}

	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	
	
	
}
