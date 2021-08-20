package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
class AddCustomerSimulation extends Simulation {

  //conf
  val value_conf = http.baseUrl("http://localhost:8081")
    .header("Accept",value="application/json")
    .header(name="content-type", value ="application/json")


  //scenario
  val scn = scenario("Get all products")
    .exec(http("get all product details")
      .get("rvy/api/pms/v1/products")
      .check(status is 200)

     )
	 
    .exec(http("Get Product By Id")
      .get("/rvy/api/pms/v1/product/2614")
      .check(status is 200)
    )
	
	.exec(http("Post product")
      .post("/rvy/api/pms/v1/product")
      .body(RawFileBody(filePath = "./src/test/resources/bodies/addProduct.json")).asJson
      .header(name="content-type",value = "application/json")
      .check(status is 200))

	.exec(http("Update product")
      .post("/rvy/api/pms/v1/product")
      .body(RawFileBody(filePath = "./src/test/resources/bodies/updateProduct.json")).asJson
      .header(name="content-type",value = "application/json")
      .check(status is 200))

    .exec(http("Get Product By Id")
      .get("/rvy/api/pms/v1/product/2614")
      .check(status is 200)
    )
    

  //setup
  setUp(scn.inject(atOnceUsers(users=100))).protocols(value_conf)



}
