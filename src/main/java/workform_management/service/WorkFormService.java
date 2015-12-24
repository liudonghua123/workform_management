package workform_management.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import workform_management.model.StatisticRecord;
import workform_management.model.WorkForm;
import workform_management.repository.WorkFormRepository;
import workform_management.util.Util;

@Service
public class WorkFormService {
	@Autowired
	WorkFormRepository workFormRepository;

	public List<WorkForm> findAll() {
		List<WorkForm> workForms = Util.copyIterator(workFormRepository.findAll().iterator());
		return workForms;
	}

	public List<WorkForm> updateAll(List<WorkForm> workForms) {
		return Util.copyIterator(workFormRepository.save(workForms).iterator());
	}

	public List<WorkForm> findByDateBetween(Date startDate, Date endDate) {
		return workFormRepository.findByDateBetween(startDate, endDate, new Sort(Sort.Direction.DESC, "id"));
	}

	public WorkForm add(WorkForm workForm) {
		return workFormRepository.save(workForm);
	}

	public WorkForm update(WorkForm workForm) {
		return workFormRepository.save(workForm);
	}

	public void delete(WorkForm workForm) {
		workFormRepository.delete(workForm);
	}
	
	public List<StatisticRecord> findAllStatisticRecord() {
		List<Object[]> rawStatisticRecords = workFormRepository.findStatisticResult();
		List<StatisticRecord> statisticRecords = new ArrayList<StatisticRecord>();
		for(Object[] rawStatisticRecord : rawStatisticRecords) {
			statisticRecords.add(new StatisticRecord((String)rawStatisticRecord[0], ((BigInteger)rawStatisticRecord[1]).intValue(), ((BigInteger)rawStatisticRecord[2]).intValue()));
		}
		return statisticRecords;
//		return workFormRepository.findStatisticResult();
	}

	public List<StatisticRecord> findStatisticRecordByDateBetween(Date startDate, Date endDate) {
		List<Object[]> rawStatisticRecords = workFormRepository.findStatisticResultByDateBetween(startDate, endDate);
		List<StatisticRecord> statisticRecords = new ArrayList<StatisticRecord>();
		for(Object[] rawStatisticRecord : rawStatisticRecords) {
			statisticRecords.add(new StatisticRecord((String)rawStatisticRecord[0], ((BigInteger)rawStatisticRecord[1]).intValue(), ((BigInteger)rawStatisticRecord[2]).intValue()));
		}
		return statisticRecords;
	}

}
