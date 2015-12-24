package workform_management.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import workform_management.model.StatisticRecord;
import workform_management.model.WorkForm;

@Repository
//@NamedQueries({
//		@NamedQuery(name="findStatisticResult", query="select DATE_FORMAT(date, '%Y-%m-%d') as day, count(*) as total, count(case when is_finished=1 then 1 end) as finished from work_form group by DATE_FORMAT(date, '%Y-%m-%d')")
//})
public interface WorkFormRepository extends PagingAndSortingRepository<WorkForm, Integer> {
	public List<WorkForm> findByDateBetween(Date startDate, Date endDate, Sort sort);
	
	// http://stackoverflow.com/questions/9394758/count-rows-with-a-specific-condition-in-aggregate-query
	@Query(value = "select DATE_FORMAT(date, '%Y-%m-%d') as date, count(*) as total, count(case when is_finished=1 then 1 end) as finished from work_form group by DATE_FORMAT(date, '%Y-%m-%d');", nativeQuery = true)
	List<Object[]> findStatisticResult();

	@Query(value = "select DATE_FORMAT(date, '%Y-%m-%d') as date, count(*) as total, count(case when is_finished=1 then 1 end) as finished from work_form where date between ?1 and ?2 group by DATE_FORMAT(date, '%Y-%m-%d');", nativeQuery = true)
	List<Object[]> findStatisticResultByDateBetween(Date startDate, Date endDate);
}