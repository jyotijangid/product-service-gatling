package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
class ProductInventorySimulation extends Simulation {

  //conf
  val value_conf = http.baseUrl("http://localhost:8081")
    .header("Accept",value="application/json")
    .header(name="content-type", value ="application/json")


  //scenario
  val scn = scenario("Get all Product Inventory")
    .exec(http("get all Product Inventory details")
      .get("/rvy/api/pis/v1/product")
      .check(status is 200)

    )
	 
    .exec(http("Get Product Inventory By Id")
      .get("/rvy/api/pis/v1/product/5720")
      .check(status is 200)
    )
	
	.exec(http("Post Product Inventory")
      .post("/rvy/api/pis/v1/product")
      .body(RawFileBody(filePath = "./src/test/resources/bodies/addProductInventory.json")).asJson
      .header(name="content-type",value = "application/json")
      .check(status is 200))

	.exec(http("Update Product Inventory")
      .post("/rvy/api/pis/v1/product")
      .body(RawFileBody(filePath = "./src/test/resources/bodies/updateProductInventory.json")).asJson
      .header(name="content-type",value = "application/json")
      .check(status is 200))

    

  //setup
  setUp(scn.inject(atOnceUsers(users=100))).protocols(value_conf)



}
