const sequelize = require("./database/connection");
const bodyParser = require("body-parser");
const { Op } = require("sequelize");
const express = require("express");
const app = express();

// Access TV,Comparison models
const TV = require("./models/TV");
const Comparison = require("./models/Comparison");

// Sequelize Models Relation
TV.hasMany(Comparison, { foreignKey: "tv_id" });

// Body-parser for forms
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// DB Connection
require("./database/connection");

// DB Test
sequelize
  .authenticate()
  .then(() => console.log("Database connected..."))
  .catch((err) => console.log("Error: " + err));

// Set static folder
app.use(express.static("public"));
app.use("/../public", express.static("public"));

app.use(function (req, res, next) {
  res.header("Access-Control-Allow-Origin", "http://localhost:8080");
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept"
  );
  next();
});

// Data structure that will be accessed using the web service
let tvsArray;
let comparisonsArray;

TV.findAll({
  include: [
    {
      model: Comparison,
      attributes: ["price", "url"],
      required: true,
    },
  ],
}).then((tvs) => {
  tvsArray = JSON.stringify(tvs);
});

// Set up application to handle GET requests sent to the user path
app.get("/*", handleGetRequest);

// PORT setup
const PORT = process.env.PORT || 3000;

app.listen(PORT, console.log(`Server started on port ${PORT}`));

// Handles GET requests to our web service
function handleGetRequest(req, res) {
  // Split the path of the request into its components
  let pathArray = req.url.split("/");

  //Get the last part of the path
  let pathEnd = pathArray[pathArray.length - 1];
  let regEx = new RegExp("^[0-9]+$");

  if (pathEnd === "tvs") {
    // return all tvs
    TV.findAll({
      include: [
        {
          model: Comparison,
          attributes: ["price", "url"],
          required: true,
        },
      ],
    }).then((tvs) => {
      tvsArray = JSON.stringify(tvs);
    });
    res.send(tvsArray);
  } else if (pathEnd === "comparison") {
    // return all tvs
    Comparison.findAll().then((comparisons) => {
      comparisonsArray = JSON.stringify(comparisons);
    });
    res.send(comparisonsArray);
  } else if (regEx.test(pathEnd)) {
    TV.findAll({
      where: {
        id: pathEnd,
      },
    }).then((tvs) => {
      tvsArray = JSON.stringify(tvs);
    });
    res.send(tvsArray);
  } else if (pathEnd.includes("search")) {
    // return searched tvs
    const { searchInput } = req.query;
    console.log(searchInput);
    TV.findAll({
      where: {
        [Op.or]: [
          {
            brand: {
              [Op.like]: "%" + searchInput + "%",
            },
          },
          {
            model: {
              [Op.like]: "%" + searchInput + "%",
            },
          },
          {
            description: {
              [Op.like]: "%" + searchInput + "%",
            },
          },
          {
            screen_size: {
              [Op.like]: "%" + searchInput + "%",
            },
          },
        ],
      },
    }).then((tvs) => {
      tvsArray = JSON.stringify(tvs);
    });
    res.redirect("/#searchSection");
    res.send(tvsArray);
  } else res.send("error: Path not recognized");
}
