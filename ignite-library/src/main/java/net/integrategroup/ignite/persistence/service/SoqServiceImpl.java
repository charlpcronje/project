package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Soq;
import net.integrategroup.ignite.persistence.model.SoqSubSchedule;
import net.integrategroup.ignite.persistence.model.TripLogger;
import net.integrategroup.ignite.persistence.model.TripLoggerDto;
import net.integrategroup.ignite.persistence.model.TripLoggerPointNameDto;
import net.integrategroup.ignite.persistence.model.VTripLogger;
import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;
import net.integrategroup.ignite.persistence.repository.SoqRepository;
import net.integrategroup.ignite.persistence.repository.TripLoggerRepository;


@Service
public class SoqServiceImpl implements SoqService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SoqRepository soqRepository;

	@Override
	public List<Soq> viewSoq() {
		return soqRepository.viewSoq();
	}

	@Override
	public Soq findBySoqCode(Long itemNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Soq save(Soq soq) {
		return soqRepository.save(soq);
	}

	@Override
	public void updateSoq(String value,Long id) {
		soqRepository.updateSoq(value, id);
	}

	@Override
	public List<SoqSubSchedule> viewSoqSubSchedule() {
		return soqRepository.viewSoqSubSchedule();
	}



}

