package workform_management.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import workform_management.Constants;
import workform_management.model.Result;
import workform_management.model.WorkForm;
import workform_management.service.WorkFormService;

@RestController
@RequestMapping("/workform")
public class WorkFormController {

	@Autowired
	WorkFormService workFormService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public List<WorkForm> findWorkForms(@RequestParam(value = "startDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startDate,
			@RequestParam(value = "endDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endDate) {
		if(startDate == null || endDate == null) {
			return workFormService.findAll();
		}
		return workFormService.findByDateBetween(startDate, endDate);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public WorkForm addWorkForm(WorkForm workForm) {
		return workFormService.add(workForm);
	}
	
	@RequestMapping(value = "/{work_form_id}", method = RequestMethod.PUT, produces = "application/json")
	public WorkForm updateWorkForm(WorkForm workForm, @PathVariable("work_form_id") int workFormId) {
		return workFormService.update(workForm);
	}
	
	@RequestMapping(value = "/{work_form_id}", method = RequestMethod.DELETE, produces = "application/json")
	public Result deleteWorkForm(WorkForm workForm, @PathVariable("work_form_id") int workFormId) {
		workFormService.delete(workForm);
		return new Result(true, Constants.DELETE_SUCCESS);
	}
}
