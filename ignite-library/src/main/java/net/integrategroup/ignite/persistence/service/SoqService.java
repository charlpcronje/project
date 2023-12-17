package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.Soq;
import net.integrategroup.ignite.persistence.model.SoqSubSchedule;
import net.integrategroup.ignite.persistence.model.TripLogger;
import net.integrategroup.ignite.persistence.model.TripLoggerDto;
import net.integrategroup.ignite.persistence.model.TripLoggerPointNameDto;
import net.integrategroup.ignite.persistence.model.VTripLogger;
import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;

public interface SoqService {


	List<Soq> viewSoq();
	
	List<SoqSubSchedule> viewSoqSubSchedule();

	Soq findBySoqCode(Long itemNo);

	Soq save(Soq soq);
	
	void updateSoq(String value,Long id);
}
