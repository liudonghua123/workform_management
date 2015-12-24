package workform_management.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.type.descriptor.java.CalendarDateTypeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import workform_management.Constants;
import workform_management.model.Result;
import workform_management.model.StatisticRecord;
import workform_management.model.WorkForm;
import workform_management.service.WorkFormService;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

	@Autowired
	WorkFormService workFormService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public List<StatisticRecord> findWorkForms(@RequestParam(value = "startDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
		if(startDate == null || endDate == null) {
			return workFormService.findAllStatisticRecord();
		}
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		endDate.setHours(23);
		endDate.setMinutes(59);
		endDate.setSeconds(59);
		return workFormService.findStatisticRecordByDateBetween(startDate, endDate);
	}
}
