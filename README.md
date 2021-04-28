# HousePricePrediction-ServingAPI

When application start, load the model inside memory.

Then provide the RestFul API outward.

Need to local spark environment.

Need to specify the location of the model file. (Local / hdfs / s3)

The API can be viewed at http://localhost:8081/swagger-ui/index.html#/

Fill in the parameters one by one into the double array, then you can get the predicted value.
