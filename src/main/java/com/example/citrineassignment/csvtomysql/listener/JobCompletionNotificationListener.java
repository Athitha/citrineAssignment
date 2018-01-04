package com.example.citrineassignment.csvtomysql.listener;



import com.example.citrineassignment.csvtomysql.model.Entry;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.common.util.impl.Log;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //This code listens for when a job is BatchStatus.COMPLETED, and then uses JdbcTemplate to verify the results.

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {


            List<Entry> results = jdbcTemplate
                    .query("SELECT * FROM Data", new RowMapper<Entry>() {
                        @Override
                        public Entry mapRow(ResultSet rs, int row) throws SQLException {
                            return new Entry(rs.getString(1), rs.getString(2), rs.getString(3),
                                    rs.getString(4), rs.getString(5));
                        }
                    });
            /*for (Entry entry : results) {
                log.info("Found <" + entry + "> in the database.");
            }*/

        }
    }
}
