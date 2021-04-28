
# HousePricePrediction-ServingAPI

## Main Idea

 - Load the trained model into memory when it starts. 
 - Encapsulate the loaded model as a bean (Singleton pattern compliant).
 - Contoller injects the bean by @Autowired and provide serving service outwards.

## Before deploy you should
 - Prepare a local spark environment or remote spark URL.

 - Specify the location of the model file. (e.g. Local disk/ hdfs/ S3)

## After deploy you can

 - View the API at [online API page](http://localhost:8081/swagger-ui/index.html#/). 
-  Fill in the parameters one by one into the double array, then you can get the predicted value.

## Implementation roadmap

 1. Have to solve the dependent confict between Spark and SpringBoot
    because of the Logging Factory.

	 - 1.1. Exlude the slf4j-log4j12 and log4j out of spark.
		```
		<dependency>
		    <groupId>org.apache.spark</groupId>
		    <artifactId>spark-mllib_2.11</artifactId>
		    <version>2.4.7</version>
		    <scope>provided</scope>
            <exclusions>  
                <exclusion>   
                    <groupId>org.slf4j</groupId>  
                    <artifactId>slf4j-log4j12</artifactId>  
                </exclusion>  
                <exclusion>   
                    <groupId>log4j</groupId>  
                    <artifactId>log4j</artifactId>  
                </exclusion>  
            </exclusions> 
		</dependency>
		```
		
	 - 1.2. Exlude the logging out of spring-boot-starter-web.
		```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		    <exclusions>
	               <exclusion>
	                   <groupId>org.springframework.boot</groupId>
	                   <artifactId>spring-boot-starter-logging</artifactId>
	               </exclusion>
		    </exclusions>
		</dependency>
		```
	 - 1.3. Import the needed logging jar.
		```
		<dependency>
		    <groupId>org.slf4j</groupId>
		    <artifactId>log4j-over-slf4j</artifactId>
		    <version>1.7.25</version>
		</dependency>
		<dependency>
		    <groupId>org.slf4j</groupId>
		    <artifactId>slf4j-api</artifactId>
		    <version>1.7.5</version>
		</dependency>
		<dependency>
		    <groupId>org.slf4j</groupId>
		    <artifactId>slf4j-simple</artifactId>
		    <version>1.6.4</version>
		</dependency>
		```

 2. Define two Java **Beans** inside the [configuration](https://github.com/saLeox/HousePricePrediction-ServingAPI/blob/main/src/main/java/go5/bigdata/init/ModelConfiguration.java): one for SparkContext, another for specific model.

	```
	@Configuration
	public class ModelConfiguration {
		@Bean(value = "sparkContext")
		public SparkContextBean sparkContext() {
			...
		}
		@Bean("decisionTreeModel")
		@ConditionalOnBean(SparkContextBean.class)
		public DecisionTreeModel decisionTreeModel(SparkContextBean bean) {
			...
		}
	}
	```

 3. Implement the **initiate and destroy function** for the [SparkContextBean](https://github.com/saLeox/HousePricePrediction-ServingAPI/blob/main/src/main/java/go5/bigdata/init/SparkContextBean.java) since I/O issue

	```
	public class SparkContextBean implements InitializingBean, DisposableBean {
		@PostConstruct
		public void init() {
			...
		}
		@Override
		public void destroy() throws Exception {
			...
		}
		@Override
		public void afterPropertiesSet() throws Exception {
			...
		}
	}
	```
