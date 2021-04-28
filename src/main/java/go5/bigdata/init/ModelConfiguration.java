package go5.bigdata.init;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfiguration {

	private Logger log = LoggerFactory.getLogger(ModelConfiguration.class);

	@Value("${model.path.decisionTree}")
	private String modelPathDecisionTree;

	@Bean(value = "sparkContext")
	public SparkContextBean sparkContext() {
		return new SparkContextBean();
	}

	@Bean("decisionTreeModel")
	@ConditionalOnBean(SparkContextBean.class)
	public DecisionTreeModel decisionTreeModel(SparkContextBean bean) {
		log.info("DecisionTreeSingleton init start...");
		JavaSparkContext sc = bean.getSparkContext();
		DecisionTreeModel model = DecisionTreeModel.load(sc.sc(), modelPathDecisionTree);
		log.info("DecisionTreeSingleton init finish");
		return model;
	}

}
