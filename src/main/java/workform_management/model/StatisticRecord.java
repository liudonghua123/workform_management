package workform_management.model;

import javax.persistence.NamedNativeQuery;

// http://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-creating-database-queries-with-named-queries/
//@NamedNativeQuery(name="StatisticRecord.findStatisticResult",
//		query="select DATE_FORMAT(date, '%Y-%m-%d') as date, count(*) as total, count(case when is_finished=1 then 1 end) as finished from work_form group by DATE_FORMAT(date, '%Y-%m-%d');", 
//		resultClass=StatisticRecord.class)
public class StatisticRecord {
	private String date;
	private int total;
	private int finished;

	public StatisticRecord(String date, int total, int finished) {
		this.date = date;
		this.total = total;
		this.finished = finished;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}

}
