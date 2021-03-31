// Import the sequelize module
const Sequelize = require("sequelize");

const sequelize = new Sequelize("pricechecker", "root", "", {
  host: "localhost",
  dialect: "mysql",
  operatorAliases: false,
  define: { 
    freezeTableName: true,
  },
  pool: {
    max: 5,
    min: 0,
    idle: 10000
  }
});

module.exports = sequelize;
