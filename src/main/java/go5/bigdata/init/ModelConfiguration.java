package go5.bigdata.init;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfiguration {

	private static Logger log = LoggerFactory.getLogger(ModelConfiguration.class);

	@Bean(value = "sparkContext")
	public SparkContextBean sparkContext() {
		return new SparkContextBean();
	}

	@Bean("decisionTreeModel")
	@ConditionalOnBean(SparkContextBean.class)
	public DecisionTreeModel decisionTreeModel(SparkContextBean bean) {
		log.info("DecisionTreeSingleton init start...");
		String modelPath = "D:\\Code\\Personal\\BigData-HousePricePrediction\\src\\main\\resources\\model\\DecisionTree";
		JavaSparkContext sc = bean.getSparkContext();
		DecisionTreeModel model = DecisionTreeModel.load(sc.sc(), modelPath);
		log.info("DecisionTreeSingleton init finish");
		return model;
	}

}
