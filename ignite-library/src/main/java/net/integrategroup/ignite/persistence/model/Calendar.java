package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

@Entity
@Table(schema="ig_db", name="Calendar")
public class Calendar implements Serializable {

	private static final long serialVersionUID = 6748137623265798990L;

	@Id
	@Column(name="CalendarDate")
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date calendarDate;

	@Column(name="EventName")
	public String eventName;

	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	@Column(name="EventDescription")
	public String eventDescription;

	@Column(name="IsPublicHoliday")
	public String isPublicHoliday;

	@JsonSerialize(using=JsonDateSerializer.class)
	@Column(name="LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name="LastUpdateUserName")
	private String lastUpdateUserName;

	public Date getCalendarDate() {
		return calendarDate;
	}

	public void setCalendarDate(Date calendarDate) {
		this.calendarDate = calendarDate;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getIsPublicHoliday() {
		return isPublicHoliday;
	}

	public void setIsPublicHoliday(String isPublicHoliday) {
		this.isPublicHoliday = isPublicHoliday;
	}

}
