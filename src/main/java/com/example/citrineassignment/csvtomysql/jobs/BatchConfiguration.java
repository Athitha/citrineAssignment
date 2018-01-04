package com.example.citrineassignment.csvtomysql.jobs;

import com.example.citrineassignment.csvtomysql.listener.JobCompletionNotificationListener;
import com.example.citrineassignment.csvtomysql.model.Entry;
import com.example.citrineassignment.csvtomysql.processor.EntryItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;


    // readerwriterprocessor
    //This defines the input (from the data.csv), processor, and output; it parses each line to turn it into an Entry.
    @Bean
    public FlatFileItemReader<Entry> reader() {
        FlatFileItemReader<Entry> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Entry>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"chemicalFormula", "property1Name", "property1Value", "property2Name", "property2Value"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Entry>() {{
                setTargetType(Entry.class);
            }});
        }});
        return reader;
    }

    @Bean
    public EntryItemProcessor processor() {
        return new EntryItemProcessor();
    }

    /*creates an Writer. It automatically gets a copy of the dataSource created by @EnableBatchProcessing.
    It includes the SQL statement needed to insert a single Entry driven by Java bean properties*/

    @Bean
    public JdbcBatchItemWriter<Entry> writer() {
        JdbcBatchItemWriter<Entry> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<Entry>());
        writer.setSql("INSERT INTO Data (chemical_formula, property1_name, property1_value, property2_name, property2_value ) VALUES " +
                "(:chemicalFormula, :property1Name, :property1Value, :property2Name, :property2Value)");
        writer.setDataSource(dataSource);
        return writer;
    }


    // defines job
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer())
                .listener(listener).flow(step1()).end().build();
    }
//single step of the job. Each step involve a reader, a processor, and a writer.
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Entry, Entry>chunk(10).reader(reader())
                .processor(processor()).writer(writer()).build();
    }

}

