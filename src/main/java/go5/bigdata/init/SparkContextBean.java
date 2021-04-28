package go5.bigdata.init;

import javax.annotation.PostConstruct;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SparkContextBean implements InitializingBean, DisposableBean {
	private static Logger log = LoggerFactory.getLogger(SparkContextBean.class);

	private JavaSparkContext sparkContext;

	public JavaSparkContext getSparkContext() {
		return sparkContext;
	}

	@PostConstruct
	public void init() {
		log.info("JavaSparkContext init start...");
		SparkConf conf = new SparkConf().setAppName("Main").setMaster("local[2]").set("spark.executor.memory", "3g")
				.set("spark.driver.memory", "3g");
		sparkContext = new JavaSparkContext(conf);
		log.info("JavaSparkContext init finish");
	}

	@Override
	public void destroy() throws Exception {
		log.info("JavaSparkContext stop...");
		sparkContext.stop();
		log.info("JavaSparkContext close...");
		sparkContext.close();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("afterPropertiesSet...");
	}

}
