let express = require("express");
let app = express();
let bodyParser = require("body-parser");
let mysql = require("mysql");

app.use(bodyParser.json());
app.use(
  bodyParser.urlencoded({
    extended: true,
  })
);

app.get("/", function (req, res) {
  return res.send({ error: false, message: "Connected to API" });
});

let dbConn = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "md-project",
});

dbConn.connect();

/* ======================================== START API HERE ======================================== */

app.get("/loginCus/:u/:p", function (req, res) {
  let u = req.params.u;
  let p = req.params.p;

  dbConn.query(
    "SELECT * FROM `customer` WHERE `cus_username`=? AND `cus_password`=?",
    [u, p],
    function (error, results, fields) {
      if (error) throw error;
      if (results[0]) {
        return res.send(results[0]);
      }
    }
  );
});

app.get("/loginEmp/:u/:p", function (req, res) {
  let u = req.params.u;
  let p = req.params.p;

  dbConn.query(
    "SELECT * FROM `employee` WHERE `emp_username`=? AND `emp_password`=?",
    [u, p],
    function (error, results, fields) {
      if (error) throw error;
      if (results[0]) {
        return res.send(results[0]);
      }
    }
  );
});

app.post("/register", function (req, res) {
  let body = req.body;

  if (!body) {
    return res.status(400).send({ error: true, message: "Error register" });
  }

  dbConn.query("INSERT INTO `customer` SET ?", body, function (
    error,
    results,
    fields
  ) {
    if (error) throw error;
    return res.send(results);
  });
});

app.get("/select-all-product", function (req, res) {
  dbConn.query("SELECT * FROM `product`", function (error, results, fields) {
    if (error) throw error;
    res.send(results);
  });
});

app.get("/search-product/:pn", function (req, res) {
  let pn = req.params.pn;

  dbConn.query(
    "SELECT * FROM `product` WHERE `product_name` LIKE ?",
    pn,
    function (error, results, fields) {
      if (error) throw error;
      res.send(results);
    }
  );
});

app.get("/customer-order-product/:cid", function (req, res) {
  let cid = req.params.cid;

  dbConn.query(
    "SELECT * FROM `customer` INNER JOIN `order` on `customer`.`cus_id`=`order`.`cus_id` INNER JOIN `order_detail` on `order`.`order_id`=`order_detail`.`order_id` INNER JOIN `product` ON `order_detail`.`product_id`=`product`.`product_id` WHERE `customer`.`cus_id`=?",
    cid,
    function (error, results, fields) {
      if (error) throw error;
      res.send(results);
    }
  );
});

app.get("/orderdetail/:cid", function (req, res) {
  let cid = req.params.cid;

  dbConn.query(
    "SELECT `product`.`product_id`, `product`.`product_image`, `product`.`product_name`, `product`.`product_price`, `order_detail`.`order_detail_product_amount`, (`product`.`product_price` * `order_detail`.`order_detail_product_amount`) as `product_total` FROM `customer` INNER JOIN `order` on `customer`.`cus_id`=`order`.`cus_id` INNER JOIN `order_detail` on `order`.`order_id`=`order_detail`.`order_id` INNER JOIN `product` ON `order_detail`.`product_id`=`product`.`product_id` WHERE `customer`.`cus_id`=?",
    cid,
    function (error, results, fields) {
      if (error) throw error;
      res.send(results);
    }
  );
});

app.get("/product/:pid", function (req, res) {
  let pid = req.params.pid;

  dbConn.query("SELECT * FROM `product` WHERE `product_id`=?", pid, function (
    error,
    results,
    fields
  ) {
    if (error) throw error;
    res.send(results[0]);
  });
});

app.get("/select-orderid-for-customer/:cid", function (req, res) {
  let cid = req.params.cid;

  dbConn.query(
    "SELECT `order`.`order_id` FROM `order` INNER JOIN `customer` ON `order`.`cus_id`=`customer`.`cus_id` WHERE `order`.`status_id`=1 AND `order`.`cus_id`=?",
    cid,
    function (error, results, fields) {
      if (error) throw error;
      res.send(results[0]);
    }
  );
});

app.put("/update-product-in-order-detail/:oid/:pid/:pamt", function (req, res) {
  let oid = req.params.oid;
  let pid = req.params.pid;
  let pamt = req.params.pamt;

  dbConn.query(
    "UPDATE `order_detail` SET `order_detail_product_amount`=((SELECT `order_detail_product_amount` FROM `order_detail` WHERE `order_id`=? AND `product_id`=?)+?) WHERE `order_id`=? AND `product_id`=?",
    [oid, pid, pamt, oid, pid],
    function (error, results, fields) {
      if (error) throw error

      // 0 row updated
      if(results.affectedRows==0){
        return res.sendStatus(404)
      }

      return res.send({
        error: false,
        message: "OrderDetail has been updated successfully",
      });
    }
  );
});

app.post("/add-product-to-order-detail", function (req, res) {
  let body = req.body;

  if (!body) {
    return res
      .status(400)
      .send({ error: true, message: "Do not have  data to insert" });
  }

  dbConn.query("INSERT INTO `order_detail` SET ?", body, function (
    error,
    results,
    fields
  ) {
    if (error) throw error;
    return res.send(results);
  });
});

/* ======================================== END API HERE ======================================== */

app.listen(3000, function () {
  console.log("Node app is running on port 3000");
});

module.exports = app;
