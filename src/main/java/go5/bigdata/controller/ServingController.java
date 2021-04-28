package go5.bigdata.controller;

import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api("sample control")
public class ServingController {
	private Logger log = LoggerFactory.getLogger(ServingController.class);

	@Autowired
	private DecisionTreeModel decisionTreeModel;

	@GetMapping
	@ApiOperation(value = "ValuePredict")
	public Double ValuePredict(double[] param) {
		log.info("Value prediction start...");
		Vector newData = Vectors.dense(param);
		double prediction = decisionTreeModel.predict(newData);
		log.info("Value prediction finish...");
		return prediction;
	}
}
