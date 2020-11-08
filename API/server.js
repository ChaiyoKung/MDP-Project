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
  dbConn.query("SELECT * FROM `product` WHERE `product_amount`>0", function (
    error,
    results,
    fields
  ) {
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
    "SELECT `product`.`product_id`, `product`.`product_image`, `product`.`product_name`, `product`.`product_price`, `order_detail`.`order_detail_product_amount`, (`product`.`product_price` * `order_detail`.`order_detail_product_amount`) as `product_total` FROM `customer` INNER JOIN `order` on `customer`.`cus_id`=`order`.`cus_id` INNER JOIN `order_detail` on `order`.`order_id`=`order_detail`.`order_id` INNER JOIN `product` ON `order_detail`.`product_id`=`product`.`product_id` WHERE `order`.`status_id`=1 AND `customer`.`cus_id`=?",
    cid,
    function (error, results, fields) {
      if (error) throw error;
      res.send(results);
    }
  );
});

app.get("/get-now-orderid-of-customer/:cid", function (req, res) {
  let cid = req.params.cid;

  dbConn.query(
    "SELECT `order`.`order_id` FROM `customer` INNER JOIN `order` ON `customer`.`cus_id`=`order`.`cus_id` WHERE `order`.`status_id`=1 AND `customer`.`cus_id`=?",
    cid,
    function (error, results, fields) {
      if (error) throw error;
      res.send(results[0]);
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
      if (error) throw error;

      // 0 row updated
      if (results.affectedRows == 0) {
        return res.sendStatus(404);
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

app.delete("/delete-item-in-order-detail/:oid/:pid", function (req, res) {
  let oid = req.params.oid;
  let pid = req.params.pid;

  dbConn.query(
    "DELETE FROM `order_detail` WHERE `order_id`=? AND `product_id`=?",
    [oid, pid],
    function (error, results, fields) {
      if (error) throw error;
      return res.send({
        error: false,
        message: "Item in orderdetail has been deleted.",
      });
    }
  );
});

app.get("/select-customer-order-transport/:cid", function (req, res) {
  let cid = req.params.cid;

  dbConn.query(
    "SELECT `customer`.`cus_address`, `transport`.`transport_id`, `transport`.`transport_name`, `transport`.`transport_cost` FROM `customer` INNER JOIN `order` ON `customer`.`cus_id`=`order`.`cus_id` INNER JOIN `transport` ON `order`.`transport_id`=`transport`.`transport_id` WHERE `order`.`status_id`=1 AND `customer`.`cus_id`=?",
    cid,
    function (error, results, fields) {
      if (error) throw error;
      res.send(results[0]);
    }
  );
});

app.put("/update-address-and-transport/:cid", function (req, res) {
  let cid = req.params.cid;
  let body = req.body;

  dbConn.query(
    "UPDATE `customer` INNER JOIN `order` ON `customer`.`cus_id`=`order`.`cus_id` SET ? WHERE `order`.`status_id`=1 AND `customer`.`cus_id`=?",
    [body, cid],
    function (error, results, fields) {
      if (error) throw error;
      res.send({
        error: false,
        message:
          "Customer address and Order trandsport id has been updated success.",
      });
    }
  );
});

app.put("/update-product-amount/:pid", function (req, res) {
  let pid = req.params.pid;
  let body = req.body;

  dbConn.query(
    "UPDATE `product` SET ? WHERE `product_id`=?",
    [body, pid],
    function (error, results, fields) {
      if (error) throw error;
      res.send({
        error: false,
        message: "Product amount has been updated success.",
      });
    }
  );
});

app.put("/return-product-amount/:pid/:oamt", function (req, res) {
  let pid = req.params.pid;
  let oamt = req.params.oamt;

  dbConn.query(
    "UPDATE `product` SET `product_amount`=((SELECT `product_amount` FROM `product` WHERE `product_id`=?)+?) WHERE `product_id`=?",
    [pid, oamt, pid],
    function (error, results, fields) {
      if (error) throw error;
      res.send({
        error: false,
        message: "Product amount has been updated success.",
      });
    }
  );
});

app.put("/change-order-status/:cid/:oid", function (req, res) {
  let cid = req.params.cid;
  let oid = req.params.oid;
  let body = req.body;

  dbConn.query(
    "UPDATE `order` SET ? WHERE `status_id`=1 AND `order_id`=? AND `cus_id`=?",
    [body, oid, cid],
    function (error, results, fields) {
      if (error) throw error;
      res.send({
        error: false,
        message: "Order status has been updated success.",
      });
    }
  );
});

app.post("/create-new-order", function (req, res) {
  let body = req.body;

  dbConn.query("INSERT INTO `order` SET ?", body, function (
    error,
    results,
    fields
  ) {
    if (error) throw error;
    res.send({
      error: false,
      messgae: "New order has been created success.",
    });
  });
});

app.get("/check-customer-order/:cid", function (req, res) {
  let cid = req.params.cid;

  dbConn.query(
    "SELECT * FROM `order` WHERE `status_id`=1 AND `cus_id`=?",
    cid,
    function (error, results, fields) {
      if (error) throw error;
      // ไม่เจอ order ของ cus_id นี้
      if (results.length == 0) {
        res.send(results[0]);
      } else {
        res.sendStatus(404);
      }
    }
  );
});

app.get("/select-customer/:cid", function (req, res) {
  let cid = req.params.cid;

  dbConn.query("SELECT * FROM `customer` WHERE `cus_id`=?", cid, function (
    error,
    results,
    fields
  ) {
    if (error) throw error;
    res.send(results[0]);
  });
});

app.put("/update-customer-profile/:cid", function (req, res) {
  let cid = req.params.cid;
  let body = req.body;

  dbConn.query(
    "UPDATE `customer` SET ? WHERE `cus_id`=?",
    [body, cid],
    function (error, results, fields) {
      if (error) throw error;
      res.send({ error: false, message: "Customer has been updated success." });
    }
  );
});

app.get("/select-product", function (req, res) {
  dbConn.query("SELECT * FROM `product` ORDER BY `product_id` DESC", function (error, results, fields) {
    if (error) throw error;
    res.send(results);
  });
});

app.put("/update-product/:pid", function (req, res) {
  let pid = req.params.pid;
  let body = req.body;

  dbConn.query(
    "UPDATE `product` SET ? WHERE `product_id`=?",
    [body, pid],
    function (error, results, fields) {
      if (error) throw error;
      res.send({ error: false, message: "Product has been updated success." });
    }
  );
});

app.post("/insert-product", function (req, res) {
  let body = req.body;

  dbConn.query("INSERT INTO `product` SET ?", body, function (
    error,
    results,
    fields
  ) {
    if (error) throw error;
    res.send({ error: false, message: "Product has been inserted success." });
  });
});

app.delete("/delete-product/:pid", function (req, res) {
  let pid = req.params.pid;

  dbConn.query("DELETE FROM `product` WHERE `product_id`=?", pid, function (
    error,
    results,
    fields
  ) {
    if (error) throw error;
    res.send({ error: false, message: "Product has been deleted success." });
  });
});

app.get("/select-customer-order", function (req, res) {
  dbConn.query(
    "SELECT * FROM `order` WHERE `status_id`>1 ORDER BY `status_id` ASC, `order_date` DESC",
    function (error, results, fields) {
      if (error) throw error;
      res.send(results);
    }
  );
});

app.get("/select-order-detail/:oid", function (req, res) {
  let oid = req.params.oid;

  dbConn.query(
    "SELECT `customer`.`cus_id`, `customer`.`cus_fname`, `customer`.`cus_lname`, `customer`.`cus_address`, `customer`.`cus_tel`, `order`.`order_id`, `order`.`order_date`, `order`.`order_track`, `transport`.*, `status`.* FROM `customer` INNER JOIN `order` ON `customer`.`cus_id`=`order`.`cus_id` INNER JOIN `transport` ON `order`.`transport_id`=`transport`.`transport_id` INNER JOIN `status` ON `order`.`status_id`=`status`.`status_id` WHERE `order`.`order_id`=?",
    oid,
    function (error, results, fields) {
      if (error) throw error;
      res.send(results[0]);
    }
  );
});

app.get("/select-order-detail-product/:oid", function (req, res) {
  let oid = req.params.oid;

  dbConn.query(
    "SELECT `product`.`product_id`, `product`.`product_name`, `product`.`product_detail`, `product`.`product_price`, `product`.`product_image`, `order_detail`.`order_detail_product_amount`, (`product`.`product_price` * `order_detail`.`order_detail_product_amount`) AS `product_sum` FROM `order_detail` INNER JOIN `product` ON `order_detail`.`product_id`=`product`.`product_id` WHERE `order_detail`.`order_id`=?",
    oid,
    function (error, results, fields) {
      if (error) throw error;
      res.send(results);
    }
  );
});

app.put("/update-order-track/:oid", function (req, res) {
  let oid = req.params.oid;
  let body = req.body;

  dbConn.query(
    "UPDATE `order` SET ? WHERE `order_id`=?",
    [body, oid],
    function (error, results, fields) {
      if (error) throw error;
      res.send({
        error: false,
        message: "Order track has been updated success.",
      });
    }
  );
});

app.get("/select-customer-order-has-transport/:cid", function (req, res) {
  let cid = req.params.cid;

  dbConn.query(
    "SELECT * FROM `order` WHERE `status_id`=3 AND `cus_id`=? ORDER BY `order_date` DESC, `order_id` DESC",
    cid,
    function (error, results, fields) {
      if (error) throw error;
      res.send(results);
    }
  );
});

app.get("/select-customer-order-history/:cid", function (req, res) {
  let cid = req.params.cid;

  dbConn.query(
    "SELECT * FROM `order` WHERE `status_id`>1 AND `cus_id`=? ORDER BY `status_id` ASC, `order_date` DESC, `order_id` DESC",
    cid,
    function (error, results, fields) {
      if (error) throw error;
      res.send(results);
    }
  );
});

/* ======================================== END API HERE ======================================== */

app.listen(3000, function () {
  console.log("Node app is running on port 3000");
});

module.exports = app;
